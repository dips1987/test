package com.jpmorgan.fx.support;

import java.time.LocalDate;

/**
 *  Provides method to get tradable date i.e returns working date for non working date.
 */
public interface ITradableDays {

    /**
     * returns working date for non working date.
     * @param currency - currency
     * @param localDate - date received in a instruction.
     * @return working date on which settlement can be done for the currency.
     */
    LocalDate getTradableDate(String currency, LocalDate localDate);
}
