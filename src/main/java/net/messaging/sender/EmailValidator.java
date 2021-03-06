package net.messaging.sender;

import java.util.List;

public class EmailValidator implements Validator {
	private static final String ERROR_MSG =  "Invalid email address: %s";
	private static final String ERROR_MSGS = "Invalid email addresses: %s";
	private static final String VALID_EMAIL = "^.+@.+\\..+$";

	private String error_msg = "";
	@Override
	public boolean validate(MessageVo source, List<String> errors) {
		if (errors == null) 
			throw new IllegalArgumentException("Error object cannot be null.");

		if (source == null) {
			error_msg = ERROR_MSG;
			return false;
		}
		List<String> emails = source.getEmails();
		if (emails.isEmpty()) {
			error_msg = ERROR_MSG;
			return false;
		}
		for (String email : emails) {
			if (!email.matches(VALID_EMAIL)) {
				errors.add(email);
			}
		}
		if (!errors.isEmpty() ) {
			if (errors.size() > 1)
				error_msg = ERROR_MSGS;
			else 
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
