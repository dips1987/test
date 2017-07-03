package com.jpmorgan.messagenotification.util;

/**
 * Util functions.
 */
public class StringUtil {

    /**
    * Transform string to singular form.
     * It only looks for 's' at the end of word.
     */
    public static String transformToSingularForms(String input) {
        char lastLetter = input.charAt(input.length() - 1);
        if ('s' == lastLetter) {
            return input.substring(0, input.length()-1);
        } else {
            return input;
        }
    }
}
