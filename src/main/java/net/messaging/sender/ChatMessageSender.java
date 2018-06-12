package net.messaging.sender;

import static net.messaging.sender.Constants.DELIMITER;

import java.io.IOException;
import java.io.Writer;

public class ChatMessageSender implements Sender {
	private static final String CONNECT_MSG = "connect chat";
	private static final String CLOSING_MSG = "disconnect";
	private static final String MESSAGE_FORMAT = "<%s>(%s)";
	private static Writer destination;

	public ChatMessageSender(Writer destination) {
		this.destination = destination;
	}

	@Override
	public void send(MessageVo message) throws IOException {
		if (destination != null) {
			destination.append(CONNECT_MSG).append(DELIMITER);
			for (String emails : message.getEmails()) {
				destination.append(String.format(MESSAGE_FORMAT, emails, message.getMessageBody())).append(DELIMITER);
			}
			destination.append(CLOSING_MSG);
			destination.append(DELIMITER);
		} else {
			System.out.println("Destination is not available.");
		}
	}

}
