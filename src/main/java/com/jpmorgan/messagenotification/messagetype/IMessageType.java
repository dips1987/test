package com.jpmorgan.messagenotification.messagetype;

import com.jpmorgan.messagenotification.model.Product;
import com.jpmorgan.messagenotification.model.Sale;

/**
 * Interface for different message types.
 */
public interface IMessageType {

    /**
     * Check if it supports message or not.
     */
    boolean support(String message);

    /**
     * Extracts information from message and returns
     */
    Product extractMessage(String message);

}
