package net.messaging;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import net.messaging.sender.Constants;
import net.messaging.sender.Sender;
import net.messaging.sender.Validator;

public class Main {
	private static Writer network;
	private static Writer console;
	private static Sender sender;
	private static List<Validator> validators;

	public static void setNetwork(Writer network) {
		Main.network = network;
	}

	public static void setConsole(Writer console) {
		Main.console = console;
	}

	public static void setSender(Sender eMailsender) {
		Main.sender = eMailsender;
	}
	public static void setValidators(List<Validator> validators) {
		Main.validators = validators;
	}
	private static void writeToConsole(String message) throws IOException {
		Main.console.append(message).append(Constants.DELIMITER);
	}
	private void execute(String... args) {
		if ( (args.length != 2) ) {
			System.out.println("Invalid parameters: <email> <message> ");
			return;
		}
		try {
			String email = args[0];
			String message = args[1];
			
			List<String>errors = new ArrayList<>();
			for (Validator validator: Main.validators) {
				if (validator.validate(email, errors)) {
					Main.sender.send(email, message);
				}else {
	 				StringBuilder sb = new StringBuilder();
					for (String e : errors) {
						sb.append(e);
	 				}
	 				Main.writeToConsole(String.format(validator.getErrorMsg(), sb.toString()));
	 			}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String... args) {

		Main main = new Main();
		main.execute(args);

	}

}
