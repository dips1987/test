package com.jpmorgan.messagenotification.store;

/**
 * Store message information.
 */
public class MessageStore {

    /**
     * number of message arrives
     */
    private int messageCount;

    public int getMessageCount() {
        return messageCount;
    }

    public void increaseMessageStore() {
        this.messageCount++;
    }
}
