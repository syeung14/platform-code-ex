package net.messaging;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.messaging.sender.Constants;
import net.messaging.sender.MessageVo;
import net.messaging.sender.Sender;
import net.messaging.sender.Validator;
import net.messaging.util.MessageUtil;
import net.messaging.util.SenderFactory;

public class Main {
	private static Writer network;
	private static Writer console;
	private static Map<String, Sender> senders;
	private static List<Validator> validators;

	public static void setNetwork(Writer network) {
		Main.network = network;
	}

	public static void setConsole(Writer console) {
		Main.console = console;
	}

	public static void setSenders(Map<String, Sender> senders) {
		Main.senders = senders;
	}
	public static void setValidators(List<Validator> validators) {
		Main.validators = validators;
	}
	private static void writeToConsole(String message) throws IOException {
		Main.console.append(message).append(Constants.DELIMITER);
	}
	
	private MessageVo createMessageVo(String dest, String messageBody)  {
		List<String>emails = new ArrayList<>();
		emails = MessageUtil.getEmails(dest);
		MessageVo msgVo = new MessageVo(emails, messageBody);
		return msgVo;
	}
	private void execute(String... args) throws IOException {
		String option = Constants.EMAIL_MSG_OPTION;
		if (args.length == 3) {
			option = args[0];
		} else if ((args.length != 2)) {
			System.out.println("Invalid parameters: <email> <message> ");
			return;
		}
		try {
			
			String email = args[0];
			String message = args[1];
			
			if (option.equals(Constants.CHAT_MSG_OPTION)) {
				email = args[1];
				message = args[2];
			}
			
			MessageVo msgVo = createMessageVo(email, message);
			Sender sender = SenderFactory.getSender(option, senders);
			
			List<String>errors = new ArrayList<>();
			for (Validator validator: Main.validators) {
				if (!validator.validate(msgVo, errors)) {
	 				StringBuilder sb = new StringBuilder();
					for (String e : errors) {
						if (sb.length() == 0)
							sb.append(e);
						else 
							sb.append(" ").append(e);
	 				}
	 				Main.writeToConsole(String.format(validator.getErrorMsg(), sb.toString()));
	 				return;
	 			}
			}
			
			sender.send(msgVo);
		} catch (IOException e) {
			Main.writeToConsole("Connection error. Please try again.");
		}
	}

	public static void main(String... args) {
		Main main = new Main();
		try {
			main.execute(args);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
