package net.messaging;

import java.io.IOException;
import java.io.Writer;

import net.messaging.sender.Sender;

public class Main {
	private static Writer network;
	private static Writer console;
	private static Sender eMailsender;

	public static void setNetwork(Writer network) {
		Main.network = network;
	}

	public static void setConsole(Writer console) {
		Main.console = console;
	}

	public static void setSender(Sender eMailsender) {
		Main.eMailsender = eMailsender;
	}

	private void execute(String... args) {
		if ( (args.length != 2) ) {
			return;
		}

		String email = args[0];
		String message = args[1];
		try {
			Main.eMailsender.send(email, message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String... args) {

		Main main = new Main();
		main.execute(args);

	}

}
