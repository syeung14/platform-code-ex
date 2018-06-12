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
			destination.write(CONNECT_MSG);
			destination.write(DELIMITER);
			for (String emails : message.getEmails()) {
				destination.write(String.format(MESSAGE_FORMAT, emails, message.getMessageBody()));
				destination.write(DELIMITER);
			}
			destination.write(CLOSING_MSG);
			destination.write(DELIMITER);
		} else {
			System.out.println("Destination is not available.");
		}
	}

}
