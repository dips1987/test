package com.jpmorgan.messagenotification.listener;

import com.jpmorgan.messagenotification.model.Sale;

/**
 * Listeners listens and logs information required.
 */
public interface IApplicationListener {

    /**
     *
     * Log information based on set of parameters.
     * @param numberOfMessage - total message received.
     * @param sale - entire sale information to log.
     */
    void logs(int numberOfMessage, Sale sale);
}
