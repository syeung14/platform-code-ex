package net.messaging.sender;

import java.io.IOException;
import java.io.Writer;
import static net.messaging.sender.Constants.*;

public class EmailMessageSender implements Sender {
	private static final String CONNECT_MSG = "connect smtp";
	private static final String CLOSING_MSG = "disconnect";
	private static final String MSG_INDICATOR = "To: ";
	
	private Writer destination;
	public EmailMessageSender(Writer destination) {
		this.destination = destination;
	}
	
	@Override public void send(MessageVo message) throws IOException{
		if (destination != null) {
			destination.write(CONNECT_MSG);
			destination.write(DELIMITER);
			for (String emails: message.getEmails()) {
				destination.write(MSG_INDICATOR);
				destination.write(emails);
				destination.write(DELIMITER);
			}
			destination.write(DELIMITER);
			destination.append(message.getMessageBody());
			destination.write(DELIMITER);
			destination.write(DELIMITER);
			destination.write(CLOSING_MSG);
			destination.write(DELIMITER);
			
		} else { 
			System.out.println("Destination is not available.");
		}
	}
}
