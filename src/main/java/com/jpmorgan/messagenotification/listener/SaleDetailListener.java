package com.jpmorgan.messagenotification.listener;

import com.jpmorgan.messagenotification.model.Sale;

/**
 * This listener listens and logs.
 */
public class SaleDetailListener implements IApplicationListener {

    /**
     * Logs after every 10th message
     */
    public void logs(int numberOfMessage, Sale sale) {
        if (numberOfMessage % 10 == 0) {
            System.out.println("10 Messages arrived...");
            sale.getProducts().forEach((s, product) -> {
                System.out.println(product);
            });
        }
    }

}
