package cn.edu.szu.entity;

public class KeyBindingEntity {
	private String triggerSequence;
	private String commandId;
	private String commandName;
	private String commandDescription;
	private String commandCategory;

	public String getTriggerSequence() {
		return triggerSequence;
	}
	public void setTriggerSequence(String triggerSequence) {
		this.triggerSequence = triggerSequence;
	}
	public String getCommandId() {
		return commandId;
	}
	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}
	public String getCommandName() {
		return commandName;
	}
	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}
	public String getCommandDescription() {
		return commandDescription;
	}
	public void setCommandDescription(String commandDescription) {
		this.commandDescription = commandDescription;
	}
	public String getCommandCategory() {
		return commandCategory;
	}
	public void setCommandCategory(String commandCategory) {
		this.commandCategory = commandCategory;
	}
	
}
