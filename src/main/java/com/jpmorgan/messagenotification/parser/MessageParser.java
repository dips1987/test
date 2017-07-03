package com.jpmorgan.messagenotification.parser;

import com.jpmorgan.messagenotification.messagetype.AdjustmentMessageType;
import com.jpmorgan.messagenotification.messagetype.IMessageType;
import com.jpmorgan.messagenotification.messagetype.MutliSaleMessageType;
import com.jpmorgan.messagenotification.messagetype.SingleSaleMessageType;
import com.jpmorgan.messagenotification.model.Product;
import com.jpmorgan.messagenotification.model.Sale;

import java.util.ArrayList;
import java.util.List;

/**
 * Message parser to parse every supported message
 */
public class MessageParser {

    private final List<IMessageType> registeredMessageTypes;

    /**
     * Register all message types.
     */
    public MessageParser() {
        registeredMessageTypes = new ArrayList<>();
        registeredMessageTypes.add(new SingleSaleMessageType());
        registeredMessageTypes.add(new MutliSaleMessageType());
        registeredMessageTypes.add(new AdjustmentMessageType());

    }

    /**
     * Parse message and extract product info from each message.
     * @param message  - message to parse
     * @return - parsed message to extract product.
     */
    public Product parseMessage(String message) {

        return registeredMessageTypes.stream().
                filter(iMessageType -> iMessageType.support(message))
                .findFirst().get().extractMessage(message);
    }
}
