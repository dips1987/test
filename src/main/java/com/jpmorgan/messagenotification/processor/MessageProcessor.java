package com.jpmorgan.messagenotification.processor;

import com.jpmorgan.messagenotification.config.ListenerConfigurator;
import com.jpmorgan.messagenotification.constant.Constant;
import com.jpmorgan.messagenotification.model.Product;
import com.jpmorgan.messagenotification.model.Sale;
import com.jpmorgan.messagenotification.parser.MessageParser;
import com.jpmorgan.messagenotification.store.MessageStore;

/**
 * Process message.
 */
public class MessageProcessor implements IMessageProcessor{


    private final ListenerConfigurator listenerConfigurator = new ListenerConfigurator();
    private final MessageParser parser = new MessageParser();
    private final Sale sale = new Sale();

    private final MessageStore messageStore = new MessageStore();

    /**
     * Check limit reached or can process further message.
     */
    private boolean limitReached(int size) {
        boolean limitReached = false;
        if (size == Constant.LIMIT) {
            System.out.println(" Limit Reached. current size="+size);
            limitReached = true;
        }
        return limitReached;
    }


    /**
     * Check message limit and process messages.
     * If limit received , no message is processed.
     */
    public boolean processMessage(String message) {
        // limit reached. This code is added assuming single threaded.
         if (limitReached(messageStore.getMessageCount())) {
             return false;
         }

        Product product = parser.parseMessage(message);
        sale.addProduct(product);
        messageStore.increaseMessageStore();
        listenerConfigurator.getListeners()
                .forEach(iApplicationListener ->
                        iApplicationListener.logs(messageStore.getMessageCount(), sale));
        return true;
    }
}
