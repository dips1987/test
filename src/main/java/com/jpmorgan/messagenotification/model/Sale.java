package com.jpmorgan.messagenotification.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Sale for products.
 */
public class Sale {

    /**
     * Sale of products.
     */
    private Map<String, Product> products = new HashMap<>();

    public Map<String, Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        Product existingProduct = products.get(product.getProductName());
        if (existingProduct != null) {
            if (product.isAdjustment()) {
                existingProduct.makeAdjustmentForSaleType(product.getSaleType(), product.getValue());
            } else {
                existingProduct.addValue(product.getValue(), product.getNumberOfItems());
            }

        } else {
                products.put(product.getProductName(), product);
        }
        this.products = products;
    }




    @Override
    public String toString() {
        return "Sale{" +
                "products=" + products +
                '}';
    }
}
