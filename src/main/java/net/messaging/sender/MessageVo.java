package net.messaging.sender;

import java.util.List;

public class MessageVo {
	List<String>emails;
	private String messageBody;
	
	public MessageVo(List<String> emails, String messageBody) {
		super();
		this.emails = emails;
		this.messageBody = messageBody;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	
	
}
