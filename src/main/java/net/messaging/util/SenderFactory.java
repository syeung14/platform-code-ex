package net.messaging.util;

import java.util.Map;

import net.messaging.sender.Constants;
import net.messaging.sender.Sender;

public class SenderFactory {
	public static Sender getSender(String type,Map<String, Sender> senders) {
		if (Constants.CHAT_MSG_OPTION.equals(type)) {
			return senders.get(Constants.CHAT_MSG_OPTION);
		} else {//everything else is emails
			return senders.get(Constants.EMAIL_MSG_OPTION);
		}
	}
}
