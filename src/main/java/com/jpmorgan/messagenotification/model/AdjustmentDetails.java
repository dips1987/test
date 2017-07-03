package com.jpmorgan.messagenotification.model;

/**
 * Stores adjustment details.
 */
public class AdjustmentDetails {

    /**
     * Total adjustment value.
     */
    private long totalAdjustmentAmount;

    public long getTotalAdjustmentAmount() {
        return totalAdjustmentAmount;
    }

    public void setTotalAdjustmentAmount(long totalAdjustmentAmount) {
        this.totalAdjustmentAmount = totalAdjustmentAmount;
    }

    @Override
    public String toString() {
        return "AdjustmentDetails{" +
                "totalAdjustmentAmount=" + totalAdjustmentAmount +
                '}';
    }
}
