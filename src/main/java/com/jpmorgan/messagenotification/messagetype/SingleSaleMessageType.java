package com.jpmorgan.messagenotification.messagetype;

import com.jpmorgan.messagenotification.model.Product;
import com.jpmorgan.messagenotification.model.Sale;
import com.jpmorgan.messagenotification.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Process message for single sale.
 */
public class SingleSaleMessageType implements IMessageType{

    private static final String re1="((?:[a-z][a-z]+))";
    private static final String re2=".*?";
    private static final String re3="(\\d+)";
    private static final String re4="(p)";

    private static final Pattern ACCEPTS_MESSAGE = Pattern.compile(re1+re2+re3+re4,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);


    private Matcher match(String message) {
        return ACCEPTS_MESSAGE.matcher(message);


    }

    public boolean support(String message) {
        return match(message).matches();
    }

    public Product extractMessage(String message) {
        Product product = new Product();
        Matcher m = match(message);
        if (m.find()) {
            product.setProductName(StringUtil.transformToSingularForms(m.group(1)));
            product.setValue(Long.valueOf(m.group(2)));
            product.setNumberOfItems(1);
        }

        return product;
    }
}
