package net.messaging.sender;

import java.io.IOException;

public interface Sender {
	public void send(String recipient, String message)  throws IOException;
}
