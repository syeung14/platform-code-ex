package net.messaging.sender;

import java.io.IOException;
import java.io.Writer;
import static net.messaging.sender.Constants.*;

public class EmailMessageSender implements Sender {
	private static final String CONNECT_MSG = "connect smtp";
	private static final String CLOSING_MSG = "disconnect";
	private static final String MSG_INDICATOR = "To: ";
	
	private static Writer destination;
	public EmailMessageSender(Writer destination) {
		this.destination = destination;
	}
	
	@Override public void send(MessageVo message) throws IOException{
		if (destination != null) {
			destination.append(CONNECT_MSG).append(DELIMITER);
			for (String emails: message.getEmails()) {
				destination.append(MSG_INDICATOR).append(emails).append(DELIMITER);
			}
			destination.append(DELIMITER);
			destination.append(message.getMessageBody()).append(DELIMITER);
			destination.append(DELIMITER);
			destination.append(CLOSING_MSG);
			destination.append(DELIMITER);
		} else { 
			System.out.println("Destination is not available.");
		}
	}
}
