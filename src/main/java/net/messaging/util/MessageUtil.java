package net.messaging.util;

import java.util.Arrays;
import java.util.List;

public class MessageUtil {
	public static List<String>getEmails(String string) {
		String[] emails = string.split(",");
		return Arrays.asList(emails);
	}
}
