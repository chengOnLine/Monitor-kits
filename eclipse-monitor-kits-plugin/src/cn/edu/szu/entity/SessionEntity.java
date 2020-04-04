package cn.edu.szu.entity;

import java.util.ArrayList;
import java.util.Date;

import cn.edu.szu.monitor.DateJsonValueProcessor;
import cn.edu.szu.monitor.Monitor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class SessionEntity {
	private String sessionId;
	private String userName;
	private Date startTime;
	private Date endTime;
	private Long sessionTime;
	private ArrayList<ActionEntity> actions;
	private ArrayList<CommandEntity> commands;
	private ArrayList<FileEntity> files;
	private PreferenceEntity preference;
	private ArrayList<ErrorEntity> errors;
//	private ArrayList<WarnEntity> warns;
	private ArrayList<KeyBindingEntity> keyBinds;
	
	public SessionEntity() {
		actions = new ArrayList<ActionEntity>();
		commands = new ArrayList<CommandEntity>();
		files = new ArrayList<FileEntity>();
		preference = new PreferenceEntity();
		errors = new ArrayList<ErrorEntity>();
		keyBinds = new ArrayList<KeyBindingEntity>();
	}
	
	public String toJsonStr() {
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
		JSONObject json = JSONObject.fromObject(this,jc);
//		JSONArray json = JSONArray.fromObject(files,jc);
		System.out.println(json);
		return json.toString();
	}
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getSessionTime() {
		return sessionTime;
	}
	public void setSessionTime(Long sessionTime) {
		this.sessionTime = sessionTime;
	}
	public ArrayList<ActionEntity> getActions() {
		return actions;
	}
	public void setActions(ArrayList<ActionEntity> actions) {
		this.actions = actions;
	}
	public ArrayList<CommandEntity> getCommands() {
		return commands;
	}
	public void setCommands(ArrayList<CommandEntity> commands) {
		this.commands = commands;
	}
	public ArrayList<FileEntity> getFiles() {
		return files;
	}
	public void setFiles(ArrayList<FileEntity> files) {
		this.files = files;
	}
	public PreferenceEntity getPreference() {
		return preference;
	}
	public void setPreference(PreferenceEntity preference) {
		this.preference = preference;
	}
	public ArrayList<ErrorEntity> getErrors() {
		return errors;
	}
	public void setErrors(ArrayList<ErrorEntity> errors) {
		this.errors = errors;
	}
//	public ArrayList<WarnEntity> getWarns() {
//		return warns;
//	}
//	public void setWarns(ArrayList<WarnEntity> warns) {
//		this.warns = warns;
//	}
	public boolean isFileExist(String path) {
		for(FileEntity file :files) {
			if(file.getPath().equals(path)) {
				return true;
			}
		}
		return false;
	}
	public FileEntity getFile(String path) {
		for(FileEntity file : files) {
			if(file.getPath().equals(path)) {
				return file;
			}
		}
		return null;
	}

	public String getTriggerSequence(String commandId) {
		String sequence = null;
		if(keyBinds == null || commandId == null || commandId =="") 
			return "";
		for(KeyBindingEntity bind : keyBinds) {
			if(bind.getCommandId()!=null && bind.getCommandId()!="") {
				if(commandId.equals( bind.getCommandId() ))
					sequence = bind.getTriggerSequence();
			}
		}
		return sequence;
	}
	public ArrayList<KeyBindingEntity> getKeyBinds() {
		return keyBinds;
	}

	public void setKeyBinds(ArrayList<KeyBindingEntity> keyBinds) {
		this.keyBinds = keyBinds;
	}
	public void push(Object obj) {
		if(obj == null) return;
		if(obj.getClass().getName().equals("cn.edu.szu.entity.ErrorEntity")) {
			ErrorEntity ee= (ErrorEntity)obj;
			for(ErrorEntity error : errors) {
				if(ee.getName().equals(error.getName())) {
					error.increase();
					return;
				}
			}
			errors.add(ee);
		}else if(obj.getClass().getName().equals("cn.edu.szu.entity.FileEntity")) {
			FileEntity fe = (FileEntity)obj;
			files.add(fe);
		}else if(obj.getClass().getName().equals("cn.edu.szu.entity.CommandEntity")) {
			CommandEntity ce = (CommandEntity)obj;
			for(CommandEntity cde : commands) {
				if(cde.equals(ce)) {
					cde.setTimes( cde.getTimes()+1 );
					return;
				}
			}
			commands.add(ce);
		}else if(obj.getClass().getName().equals("cn.edu.szu.entity.CodingEntity")) {
			CodingEntity ce = (CodingEntity)obj;
			actions.add(ce);
		}else if(obj.getClass().getName().equals("cn.edu.szu.entity.DebugingEntity")) {
			DebugingEntity de = (DebugingEntity)obj;
			actions.add(de);		
		}else if(obj.getClass().getName().equals("cn.edu.szu.entity.BrowsingEntity")) {
			BrowsingEntity be = (BrowsingEntity)obj;
			actions.add(be);
		}else if(obj.getClass().getName().equals("cn.edu.szu.entity.SleepingEntity")) {
			SleepingEntity se = (SleepingEntity)obj;
			actions.add(se);
		}
		
	}
}
