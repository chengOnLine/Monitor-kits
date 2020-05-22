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
		    // 返回结果-字节输入流转换成字符输入流，控制台输出字符
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = br.readLine()) != null) {
	            sb.append(line);
	        }
	        System.out.println("登录结果："+sb.toString());
	        return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "网络异常";
		}
	}
	
	public static String doGetRegister(String url , String name ,String id, String password) {
		String result="";
		try {
			String params = "?name="+name+"&id="+id+"&password="+password;
			URL realurl = new URL(url+"/register"+params);
			HttpURLConnection connection = (HttpURLConnection) realurl.openConnection(); 
		    // 返回结果-字节输入流转换成字符输入流，控制台输出字符
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = br.readLine()) != null) {
	            sb.append(line);
	        }
	        System.out.println("注册结果："+sb.toString());
	        return sb.toString();
		} catch (IOException e) {
			return "网络异常";
		}
	}
	public static String doVerifyURL(String url) {
		try {
			URL realurl = new URL(url+"/verify");
			HttpURLConnection connection = (HttpURLConnection) realurl.openConnection(); 
		    // 返回结果-字节输入流转换成字符输入流，控制台输出字符
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = br.readLine()) != null) {
	            sb.append(line);
	        }
	    	return sb.toString();
		} catch (IOException e) {
			return "地址无效";
		}
	}
	/**
     * 提交file模拟form表单
     * @param url
     * @param savefileName
     * @param filePath
     * @param param
     * @return
     */
    public static String doPostWithFile(String url,String filePath, String userName) {
    	url = url + "/upload";
          try {  
                // 换行符  
                final String newLine = "\r\n";  
                final String boundaryPrefix = "--";  
                // 定义数据分隔线  
                String BOUNDARY = "========7d4a6d158c9";  
                // 服务器的域名  
                URL realurl = new URL(url);  
                // 发送POST请求必须设置如下两行
                HttpURLConnection connection = (HttpURLConnection) realurl.openConnection(); 
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection","Keep-Alive");
                connection.setRequestProperty("Charset","UTF-8");
                connection.setRequestProperty("Content-Type","multipart/form-data; boundary=" + BOUNDARY);
                // 头
                String boundary = BOUNDARY;
                // 传输内容
                StringBuffer contentBody =new StringBuffer("--" + BOUNDARY);
                // 尾
                String endBoundary ="\r\n--" + boundary + "--\r\n";

                // 1. 处理普通表单域(即形如key = value对)的POST请求（这里也可以循环处理多个字段，或直接给json）
                //这里看过其他的资料，都没有尝试成功是因为下面多给了个Content-Type
                //form-data  这个是form上传 可以模拟任何类型
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

                // 2. 处理file文件的POST请求（多个file可以循环处理）
                contentBody = new StringBuffer();
                contentBody.append("\r\n")
                .append("Content-Disposition:form-data; name=\"")
                .append("file" +"\"; ")   // form中field的名称
                .append("filename=\"")
                .append(filePath +"\"")   //上传文件的文件名，包括目录
                .append("\r\n")
                .append("Content-Type:multipart/form-data")
                .append("\r\n\r\n");
                String boundaryMessage2 = contentBody.toString();
//                System.out.println(boundaryMessage2);
                out.write(boundaryMessage2.getBytes("utf-8"));

                // 开始真正向服务器写文件
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

                // 4. 从服务器获得回答的内容
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
                System.out.println("发送POST请求出现异常！" + e);
                return "网络异常";
//                JOptionPane.showMessageDialog(null, "网络异常", "错误 ", JOptionPane.ERROR_MESSAGE);
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
		    FileSystemView fsv = FileSystemView.getFileSystemView();  //注意了，这里重要的一句
		    System.out.println(fsv.getHomeDirectory());                //得到桌面路径
		    fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
		    fileChooser.setDialogTitle("请选择文件的保存路径...");
		    fileChooser.setApproveButtonText("确定");
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
		    //设置超时间为3秒
		    conn.setConnectTimeout(3*1000);
		    //防止屏蔽程序抓取而返回403错误
		    conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		    //得到输入流
		    InputStream inputStream = conn.getInputStream();
		    //获取自己数组
		    byte[] getData = readInputStream(inputStream);

//		    String downloadPath = Const.defaultDownloadPath;
//		    Configuration config = ReadWriteFileUtil.readConfig();
//		    if(config != null && config.getSetting() !=null) {
//		    	if( !config.getSetting().getDownloadpath().equals("")) {
//		    		downloadPath = config.getSetting().getDownloadpath();
//		    	}
//		    }
		    //文件保存位置
		    File saveDir = new File(downloadPath);
		    if(!saveDir.exists()){
		        saveDir.mkdir();
		    }
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		    String fileName = dateFormat.format(new Date()) + ".txt";
			if(type.equals("REPORT")) {
				fileName = userName+"_综合测评报告_"+dateFormat.format(new Date())+".txt";
			}else if(type.equals("PROGRAMS")){
				fileName = userName+"_历史编程记录_"+dateFormat.format(new Date())+".txt";
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
     * 从输入流中获取字节数组
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
