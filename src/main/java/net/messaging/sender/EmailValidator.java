package net.messaging.sender;

import java.util.List;

import net.messaging.util.MessageUtil;

public class EmailValidator implements Validator {
	private static final String ERROR_MSG = "Invalid email address: %s";
	private static final String VALID_EMAIL = "^.+@.+\\..+$";

	private String error_msg = "";
	@Override
	public boolean validate(String source, List<String> errors) {
		if (errors == null) 
			throw new IllegalArgumentException("Error object cannot be null.");
		
		if (source == null) {
			error_msg = ERROR_MSG;
			return false;
		}
		List<String> emails = MessageUtil.getEmails(source);
		if (emails.isEmpty()) {
			error_msg = ERROR_MSG;
			return false;
		}
		for (String email : emails) {
			if (!email.matches(VALID_EMAIL)) {
				errors.add(email);
			}
		}
		if (!errors.isEmpty()) {
			error_msg = ERROR_MSG;
			return false;
		}
		return true;
	}

	@Override
	public String getErrorMsg() {
		return error_msg;
	}

}
