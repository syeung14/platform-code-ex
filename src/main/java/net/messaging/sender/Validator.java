package net.messaging.sender;

import java.util.List;

public interface Validator {
	public boolean validate(MessageVo source, List<String> errors);
	public String getErrorMsg();
}
