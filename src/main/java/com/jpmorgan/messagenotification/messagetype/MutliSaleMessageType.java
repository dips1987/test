package com.jpmorgan.messagenotification.messagetype;

import com.jpmorgan.messagenotification.model.Product;
import com.jpmorgan.messagenotification.model.Sale;
import com.jpmorgan.messagenotification.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Process message for multi sale
 */
public class MutliSaleMessageType implements IMessageType {

    private static final String re1="(\\d+)";	// Integer Number 1
    private static final String re2=".*?";	// Non-greedy match on filler
    private static final String re3="(?:[a-z][a-z]+)";	// Uninteresting: word
    private static final String re4=".*?";	// Non-greedy match on filler
    private static final String re5="(?:[a-z][a-z]+)";	// Uninteresting: word
    private static final String re6=".*?";	// Non-greedy match on filler
    private static final String re7="((?:[a-z][a-z]+))";	// Word 1
    private static final String re8=".*?";	// Non-greedy match on filler
    private static final String re9="(\\d+)";	// Integer Number 2
    private static final String re10="(p)";	// Variable Name 1


    private static final Pattern ACCEPTS_MESSAGE = Pattern.compile(re1+re2+re3+re4+re5+re6+re7+re8+re9+re10,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);


    private Matcher match(String message) {
        return ACCEPTS_MESSAGE.matcher(message);


    }

    public boolean support(String message) {
        return match(message).find();
    }

    public Product extractMessage(String message) {

        Product product = new Product();
        Matcher m = match(message);
        if (m.find()) {
            String numberOfProducts=m.group(1);
            product.setProductName(StringUtil.transformToSingularForms(m.group(2)));
            product.setValue(Long.valueOf(m.group(3)));
            product.setNumberOfItems(Integer.parseInt(numberOfProducts));
        }

        return product;
    }
}
