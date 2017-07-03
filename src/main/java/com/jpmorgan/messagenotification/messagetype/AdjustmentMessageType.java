package com.jpmorgan.messagenotification.messagetype;

import com.jpmorgan.messagenotification.model.Product;
import com.jpmorgan.messagenotification.model.Sale;
import com.jpmorgan.messagenotification.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  process adjustment messages.
 */
public class AdjustmentMessageType implements IMessageType {

    private static final String re1="((?:[a-z][a-z]+))";
    private static final String re2=".*?";
    private static final String re3="(\\d+)";
    private static final String re4="(p)";
    private static final String re5=".*?";
    private static final String re6="((?:[a-z][a-z]+))";

    private static final Pattern ACCEPTS_MESSAGE = Pattern.compile(re1+re2+re3+re4+re5+re6,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);


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
            long value = Long.valueOf(m.group(2));
            product.setProductName(StringUtil.transformToSingularForms(m.group(4)));
            String action =  m.group(1);
            if ("add".equalsIgnoreCase(action)) {
                value *= +1;
            } else  if ("subtract".equalsIgnoreCase(action)) {
                value *= -1;
            } else {
                throw new RuntimeException("Action not recognized " +action);
            }

            product.setValue(value);
            product.setNumberOfItems(0);
            product.setAdjustment(true);
            product.setSaleType(action);
        }
        return product;
    }
}
