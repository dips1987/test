package com.jpmorgan.messagenotification.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Product model.
 */
public class Product {

    /**
     * product name
     */
    private String productName;

    /**
     * product total sale value.
     */
    private long value;

    /**
     * total number of items.
     */
    private int numberOfItems;

    private boolean adjustment;

    private String saleType;

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public boolean isAdjustment() {
        return adjustment;
    }

    public void setAdjustment(boolean adjustment) {
        this.adjustment = adjustment;
    }

    private Map<String, AdjustmentDetails> adjustmentPerSaleType;

    public Map<String, AdjustmentDetails> getAdjustmentPerSaleType() {
        return adjustmentPerSaleType;
    }

    public void makeAdjustmentForSaleType(String saleType, long value) {
        // store adjustment to log
        if (adjustmentPerSaleType == null ) {
            adjustmentPerSaleType = new HashMap<>();
        }
        if (this.adjustmentPerSaleType.containsKey(saleType)) {
            AdjustmentDetails adjustmentDetails = this.adjustmentPerSaleType.get(saleType);
            adjustmentDetails.setTotalAdjustmentAmount(adjustmentDetails.getTotalAdjustmentAmount() + value);
        } else {
            AdjustmentDetails adjustmentDetails = new AdjustmentDetails();
            adjustmentDetails.setTotalAdjustmentAmount(value);
            this.adjustmentPerSaleType.put(saleType, adjustmentDetails);
        }

        // do adjustment for this product
        this.value += value;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public void addValue(long value, int numberOfItems) {
        this.numberOfItems+= numberOfItems;
        this.value+= value * numberOfItems;

    }

    public void adjustment() {

    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", value=" + value +
                ", numberOfItems=" + numberOfItems +
                '}';
    }
}
