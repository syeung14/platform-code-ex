package net.messaging;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import net.messaging.sender.ChatMessageSender;
import net.messaging.sender.Constants;
import net.messaging.sender.EmailMessageSender;
import net.messaging.sender.EmailValidator;
import net.messaging.sender.MessageBodyValidator;
import net.messaging.sender.Sender;
import net.messaging.sender.Validator;

public class EndToEndTest {
    private static final String NO_OUTPUT = "";
    private StringWriter network = new StringWriter();
    private StringWriter console = new StringWriter();
    private Sender emailSender;
    private Sender chatSender;
    private Validator emailValidator;
    private Validator msgBodyValidator;

    @Before public void configureMainClassWithFakeOutputs() {
        Main.setNetwork(network);
        Main.setConsole(console);
        emailSender = new EmailMessageSender(network);
        chatSender = new ChatMessageSender(network);
        Map<String, Sender> senders = new HashMap<>();
        senders.put(Constants.EMAIL_MSG_OPTION, emailSender);
        senders.put(Constants.CHAT_MSG_OPTION, chatSender);
        Main.setSenders(senders);
        
        emailValidator = new EmailValidator();
        msgBodyValidator = new MessageBodyValidator();
        Main.setValidators(Arrays.asList(emailValidator,msgBodyValidator));
    }

    @Test public void sendAnEmail_story1() {
        Main.main("joe@example.com", "Hi there!");
        networkShouldReceive("connect smtp\n" +
                "To: joe@example.com\n" +
                "\n" +
                "Hi there!\n" +
                "\n" +
                "disconnect\n");
        consoleShouldReceive(NO_OUTPUT);
    }

    	@Test public void sendAnEmail_AnotherExample_story1() {
        Main.main("sally@example.com", "Greetings.\nHow's it going?");
        networkShouldReceive("connect smtp\n" +
                "To: sally@example.com\n" +
                "\n" +
                "Greetings.\n" +
                "How's it going?\n" +
                "\n" +
                "disconnect\n");
        consoleShouldReceive(NO_OUTPUT);
    }

    @Test public void showAnErrorAndDoNotSendIfTheEmailAddressIsInvalid_story2() {
        Main.main("noatsign", "Hi there!");
        networkShouldReceive(NO_OUTPUT);
        consoleShouldReceive("Invalid email address: noatsign\n");
    }

    @Test public void showAnErrorAndDoNotSendIfTheBodyIsInvalid_story3() {
        Main.main("dinah@example.com", "");
        networkShouldReceive(NO_OUTPUT);
        consoleShouldReceive("Cannot send an email with no body.\n");
    }

    @Test public void sendAnEmailToMultipleAddresses_story4() {
        Main.main("sally@example.com,joe@example.com", "Hi everyone!");
        networkShouldReceive("connect smtp\n" +
                "To: sally@example.com\n" +
                "To: joe@example.com\n" +
                "\n" +
                "Hi everyone!\n" +
                "\n" +
                "disconnect\n");
        consoleShouldReceive(NO_OUTPUT);
    }

    @Test public void betterErrorHandlingsForMultipleAddresses_story5() {
        Main.main("sallyatexample.com,joeatexample.com", "Hi everyone!");
        networkShouldReceive(NO_OUTPUT);
        consoleShouldReceive("Invalid email addresses: sallyatexample.com joeatexample.com\n");
    }

    @Test public void sendAMessageInAnotherFormat_story6() {
        Main.main("-im", "leslie@chat.example.com", ":-) hey there!");
        networkShouldReceive("connect chat\n" +
                "<leslie@chat.example.com>(:-) hey there!)\n" +
                "disconnect\n");
        consoleShouldReceive(NO_OUTPUT);
    }

    @Test public void chatsToMultipleAddressesGetSentIndividually_story7() {
        Main.main("-im", "leslie@chat.example.com,joey@chat.example.com", "Hello.");
        networkShouldReceive("connect chat\n" +
                "<leslie@chat.example.com>(Hello.)\n" +
                "<joey@chat.example.com>(Hello.)\n" +
                "disconnect\n");
        consoleShouldReceive(NO_OUTPUT);
    }

    @Test
    public void handleErrorsGracefully_story8() {
    		Writer writer = new BadNetworkConnection();
        
    		Main.setNetwork(writer);
        
    		emailSender = new EmailMessageSender(writer);
        chatSender = new ChatMessageSender(writer);
        Map<String, Sender> senders = new HashMap<>();
        senders.put(Constants.EMAIL_MSG_OPTION, emailSender);
        senders.put(Constants.CHAT_MSG_OPTION, chatSender);
        Main.setSenders(senders);
        
        Main.main("joe@example.com", "Hi there!");
        consoleShouldReceive("Connection error. Please try again.\n");
    }

    private void networkShouldReceive(String output) {
        assertThat(network.toString(), equalTo(output));
    }

    private void consoleShouldReceive(String output) {
        assertThat(console.toString(), equalTo(output));
    }

    private static class BadNetworkConnection extends Writer {
        @Override public void write(char[] cbuf, int off, int len) throws IOException {
            throw new IOException("Oh no the network is down!!!!!!!111one");
        }
        @Override public void write(String str) throws IOException {
        		throw new IOException("Oh no the network is down!!!!!!!111one");
        }

        @Override public void flush() throws IOException {
        }

        @Override public void close() throws IOException {
        }
    }
}