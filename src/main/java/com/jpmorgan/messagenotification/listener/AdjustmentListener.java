package com.jpmorgan.messagenotification.listener;

import com.jpmorgan.messagenotification.constant.Constant;
import com.jpmorgan.messagenotification.model.Sale;

/**
 * This listener listens and log information.
 */
public class AdjustmentListener implements IApplicationListener {

    /**
     * This listener listens and log all messages after message count reaches 50
     * @param numberOfMessage - total message received.
     * @param sale - entire sale information to log.
     */
    @Override
    public void logs(int numberOfMessage, Sale sale) {

        if (numberOfMessage/ Constant.LIMIT == 1) {
            System.out.println("Pausing...limit reached");
            sale.getProducts().forEach((s, product) -> {
                System.out.print("productName="+product.getProductName());

                product.getAdjustmentPerSaleType().forEach((k,v) -> {
                    System.out.print(" ,saletype="+k);
                    System.out.print(" ,total adjustment="+v.getTotalAdjustmentAmount());
                });
                System.out.println();
            });
        }
    }
}
