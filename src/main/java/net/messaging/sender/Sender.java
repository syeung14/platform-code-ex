package net.messaging.sender;

import java.io.IOException;

public interface Sender {
	public void send(MessageVo message)  throws IOException;
}
