package net.messaging.sender;

import java.util.List;

public class MessageBodyValidator implements Validator {
	private static final String ERROR_MSG = "Cannot send an email with no body.";

	private String error_msg = "";

	@Override
	public boolean validate(MessageVo source, List<String> errors) {

		if (source == null || "".equals(source.getMessageBody().trim())) {
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
