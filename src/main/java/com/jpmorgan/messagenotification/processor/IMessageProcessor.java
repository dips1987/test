package com.jpmorgan.messagenotification.processor;

/**
 * Interface provides method to process single message.
 */
public interface IMessageProcessor {

    /**
     * Parse the message and stores it.
     * @param message to process.
     * @return - returns whether message is processed or failed to process.
     */
    boolean processMessage(String message);
}
