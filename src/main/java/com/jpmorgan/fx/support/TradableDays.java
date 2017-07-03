package com.jpmorgan.fx.support;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

/**
 * Verify and calculate the tradable date.
 */
public class TradableDays implements ITradableDays {


    /**
     * method to get set of non working days and calculate the tradable date.
     * @param currency currency to find non working days.
     * @param localDate to check if it's not a non working date else returns the next tradable date.
     * @return tradable date.
     */
    @Override
    public LocalDate getTradableDate(String currency, LocalDate localDate) {

        Set<DayOfWeek> nonWorkingDays = NonWorkingDaysPerCurrency.nonWorkingDays(currency);
        return tradeDate(nonWorkingDays, localDate);

    }


    /**
     *  Recursively checks the tradable date.
     *  If inout date is tradable then it returns else recursively calls the method by passing next date.
     * @param nonWorkingDays - set of non working days.
     * @param localDate - recursively find the tradable date.
     * @return returns the tradable date.
     */
    private LocalDate tradeDate(Set<DayOfWeek> nonWorkingDays, LocalDate localDate) {
        LocalDate tradeDate = localDate;
        if (nonWorkingDays.contains(localDate.getDayOfWeek())) {
            return tradeDate(nonWorkingDays, localDate.plusDays(1));
        } else {
            return tradeDate;
        }
    }
}
