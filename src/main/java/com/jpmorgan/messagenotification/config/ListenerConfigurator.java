package com.jpmorgan.messagenotification.config;

import com.jpmorgan.messagenotification.listener.AdjustmentListener;
import com.jpmorgan.messagenotification.listener.IApplicationListener;
import com.jpmorgan.messagenotification.listener.SaleDetailListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Configurator configures all listeners for messages.
 */
public class ListenerConfigurator {

    private final List<IApplicationListener> listeners;

    /**
     * Create unmodifiable list of listeners.
     */
    public ListenerConfigurator() {
        List<IApplicationListener> list = new ArrayList<>();
        list.add(new SaleDetailListener());
        list.add(new AdjustmentListener());
        listeners = Collections.unmodifiableList(list);
    }

    /**
     * @return list of available listeners.
     */
    public List<IApplicationListener> getListeners() {
        return listeners;
    }
}
