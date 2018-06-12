package net.messaging.sender;

import java.io.IOException;
import java.io.Writer;
import static net.messaging.sender.Constants.*;

public class EmailMessageSender implements Sender {
	private static final String CONNECT_MSG = "connect smtp";
	protected static final String CLOSING_MSG = "disconnect";
	private static final String MSG_INDICATOR = "To: ";
	
	private static Writer destination;
	public EmailMessageSender(Writer destination) {
		this.destination = destination;
	}
	
	@Override public void send(String emailAddress, String message) throws IOException{
		if (destination != null) {
			destination.append(CONNECT_MSG).append(DELIMITER);
			destination.append(MSG_INDICATOR).append(emailAddress).append(DELIMITER);
			destination.append(DELIMITER);
			destination.append(message).append(DELIMITER);
			destination.append(DELIMITER);
			destination.append(CLOSING_MSG);
			destination.append(DELIMITER);
		} else { 
			System.out.println("Destination is not available.");
		}
	}
}
