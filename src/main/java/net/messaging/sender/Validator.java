package net.messaging.sender;

import java.util.List;

public interface Validator {
	public boolean validate(String source, List<String> errors);
	public String getErrorMsg();
}
