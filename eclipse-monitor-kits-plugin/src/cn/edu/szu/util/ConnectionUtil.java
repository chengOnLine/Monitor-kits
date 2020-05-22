package cn.edu.szu.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import cn.edu.szu.config.Configuration;

public class ConnectionUtil {
//	private static String host = "http://192.168.182.1:8080";

	public static String doGetLogin(String url , String name ,String id, String password) {
		String result="";
		try {
			String params = "?name="+name+"&id="+id+"&password="+password;
			URL realurl = new URL(url+"/login"+params  );
			HttpURLConnection connection = (HttpURLConnection) realurl.openConnection(); 
		    // ���ؽ��-�ֽ�������ת�����ַ�������������̨����ַ�
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = br.readLine()) != null) {
	            sb.append(line);
	        }
	        System.out.println("��¼�����"+sb.toString());
	        return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "�����쳣";
		}
	}
	
	public static String doGetRegister(String url , String name ,String id, String password) {
		String result="";
		try {
			String params = "?name="+name+"&id="+id+"&password="+password;
			URL realurl = new URL(url+"/register"+params);
			HttpURLConnection connection = (HttpURLConnection) realurl.openConnection(); 
		    // ���ؽ��-�ֽ�������ת�����ַ�������������̨����ַ�
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = br.readLine()) != null) {
	            sb.append(line);
	        }
	        System.out.println("ע������"+sb.toString());
	        return sb.toString();
		} catch (IOException e) {
			return "�����쳣";
		}
	}
	public static String doVerifyURL(String url) {
		try {
			URL realurl = new URL(url+"/verify");
			HttpURLConnection connection = (HttpURLConnection) realurl.openConnection(); 
		    // ���ؽ��-�ֽ�������ת�����ַ�������������̨����ַ�
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = br.readLine()) != null) {
	            sb.append(line);
	        }
	    	return sb.toString();
		} catch (IOException e) {
			return "��ַ��Ч";
		}
	}
	/**
     * �ύfileģ��form��
     * @param url
     * @param savefileName
     * @param filePath
     * @param param
     * @return
     */
    public static String doPostWithFile(String url,String filePath, String userName) {
    	url = url + "/upload";
          try {  
                // ���з�  
                final String newLine = "\r\n";  
                final String boundaryPrefix = "--";  
                // �������ݷָ���  
                String BOUNDARY = "========7d4a6d158c9";  
                // ������������  
                URL realurl = new URL(url);  
                // ����POST�������������������
                HttpURLConnection connection = (HttpURLConnection) realurl.openConnection(); 
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection","Keep-Alive");
                connection.setRequestProperty("Charset","UTF-8");
                connection.setRequestProperty("Content-Type","multipart/form-data; boundary=" + BOUNDARY);
                // ͷ
                String boundary = BOUNDARY;
                // ��������
                StringBuffer contentBody =new StringBuffer("--" + BOUNDARY);
                // β
                String endBoundary ="\r\n--" + boundary + "--\r\n";

                // 1. ������ͨ����(������key = value��)��POST��������Ҳ����ѭ���������ֶΣ���ֱ�Ӹ�json��
                //���￴�����������ϣ���û�г��Գɹ�����Ϊ�������˸�Content-Type
                //form-data  �����form�ϴ� ����ģ���κ�����
                contentBody.append("\r\n")
                .append("Content-Disposition: form-data; name=\"")
                .append("name" + "\"")
                .append("\r\n")
                .append("\r\n")
                .append(userName)
                .append("\r\n")
                .append("--")
                .append(boundary);
                String boundaryMessage1 =contentBody.toString();
//                System.out.println(boundaryMessage1);
                
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.write(boundaryMessage1.getBytes("utf-8"));

                // 2. ����file�ļ���POST���󣨶��file����ѭ������
                contentBody = new StringBuffer();
                contentBody.append("\r\n")
                .append("Content-Disposition:form-data; name=\"")
                .append("file" +"\"; ")   // form��field������
                .append("filename=\"")
                .append(filePath +"\"")   //�ϴ��ļ����ļ���������Ŀ¼
                .append("\r\n")
                .append("Content-Type:multipart/form-data")
                .append("\r\n\r\n");
                String boundaryMessage2 = contentBody.toString();
//                System.out.println(boundaryMessage2);
                out.write(boundaryMessage2.getBytes("utf-8"));

                // ��ʼ�����������д�ļ�
                File file = new File(filePath);
                DataInputStream dis= new DataInputStream(new FileInputStream(file));
                int bytes = 0;
                byte[] bufferOut =new byte[(int) file.length()];
                bytes =dis.read(bufferOut);
                out.write(bufferOut,0, bytes);
                dis.close();
                byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();  
                out.write(endData);  
                out.flush();  
                out.close(); 

                // 4. �ӷ�������ûش������
                String strLine="";
                String strResponse ="";
                InputStream in =connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while((strLine =reader.readLine()) != null)
                {
                        strResponse +=strLine +"\n";
                }
                System.out.print(strResponse);
                return strResponse.trim();
            } catch (Exception e) {  
                System.out.println("����POST��������쳣��" + e);
                return "�����쳣";
//                JOptionPane.showMessageDialog(null, "�����쳣", "���� ", JOptionPane.ERROR_MESSAGE);
            }
    }
    public static String download(String urlStr,String userName , String type){
    	if(type.equals("REPORT")) {
    		urlStr = urlStr + "/download?userName="+userName;
    	}else if(type.equals("PROGRAMS")){
    		urlStr = urlStr + "/download/all?userName="+userName;
    	}
    	
    	System.out.println("url:"+urlStr + "type:"+type);
    	URL url;
		try {
		    JFileChooser fileChooser = new JFileChooser();
		    FileSystemView fsv = FileSystemView.getFileSystemView();  //ע���ˣ�������Ҫ��һ��
		    System.out.println(fsv.getHomeDirectory());                //�õ�����·��
		    fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
		    fileChooser.setDialogTitle("��ѡ���ļ��ı���·��...");
		    fileChooser.setApproveButtonText("ȷ��");
		    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		    int result = fileChooser.showOpenDialog(null);
		    String downloadPath = "";
		    if (JFileChooser.APPROVE_OPTION == result) {
		    	downloadPath=fileChooser.getSelectedFile().getPath();
		    }
		    if(downloadPath.equals(""))
		    	return "ERROR";
		    
			url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		    //���ó�ʱ��Ϊ3��
		    conn.setConnectTimeout(3*1000);
		    //��ֹ���γ���ץȡ������403����
		    conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		    //�õ�������
		    InputStream inputStream = conn.getInputStream();
		    //��ȡ�Լ�����
		    byte[] getData = readInputStream(inputStream);

//		    String downloadPath = Const.defaultDownloadPath;
//		    Configuration config = ReadWriteFileUtil.readConfig();
//		    if(config != null && config.getSetting() !=null) {
//		    	if( !config.getSetting().getDownloadpath().equals("")) {
//		    		downloadPath = config.getSetting().getDownloadpath();
//		    	}
//		    }
		    //�ļ�����λ��
		    File saveDir = new File(downloadPath);
		    if(!saveDir.exists()){
		        saveDir.mkdir();
		    }
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��");
		    String fileName = dateFormat.format(new Date()) + ".txt";
			if(type.equals("REPORT")) {
				fileName = userName+"_�ۺϲ�������_"+dateFormat.format(new Date())+".txt";
			}else if(type.equals("PROGRAMS")){
				fileName = userName+"_��ʷ��̼�¼_"+dateFormat.format(new Date())+".txt";
			}

		    File file = new File(saveDir+File.separator+fileName);
		    FileOutputStream fos = new FileOutputStream(file);
		    fos.write(getData);
		    if(fos!=null){
		        fos.close();
		    }
		    if(inputStream!=null){
		        inputStream.close();
		    }
		    System.out.println("info: download success");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ERROR";
		}
    	return "SUCCESS";
    }
    /**
     * ���������л�ȡ�ֽ�����
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }  
}
