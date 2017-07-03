package com.jpmorgan.messagenotification.consumer;

import com.jpmorgan.messagenotification.processor.IMessageProcessor;
import com.jpmorgan.messagenotification.processor.MessageProcessor;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Test Consumer class to use Message processor.
 */
public class Main {

    public static void main(String[] args) throws URISyntaxException{
        ClassLoader classLoader = Main.class.getClassLoader();
        String filePath = Paths.get(classLoader.getResource("messages.txt").toURI()).toString();
        File file = new File(filePath);
        Main main = new Main();
        main.processMessage(file);
    }

    private void processMessage(File file) {
        IMessageProcessor messageProcessor = new MessageProcessor();

        try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process message.
                messageProcessor.processMessage(line);
            }
        } catch (FileNotFoundException fex) {
            fex.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }


}
