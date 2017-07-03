package com.jpmorgan.fx.support;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class stores static list of currency and non working days applied to the currency.
 */
public class NonWorkingDaysPerCurrency {

    /**
     * Map of currency and set of non working days.
     */
    private static final Map<String, NonWorkingDays> NON_WORKING_DAYS;

    /**
     * Default currency to hold set of non working days set
     * if specific currency's non working days set is not stored.
     */
    private static final String OTHERS = "OTHERS";


    static {
        NON_WORKING_DAYS = new HashMap<>();
        NON_WORKING_DAYS.put(OTHERS, new NonWorkingDays(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));
        NON_WORKING_DAYS.put("AED",new NonWorkingDays(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY));
        NON_WORKING_DAYS.put("SAR", new NonWorkingDays(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY));
    }

    /**
     * method returns non working days for a currency.
     * If not found then returns the default non working days set.
     * @param currency - currency to find set of non working days.
     * @return - set of non working days of type DayOfWeek for the currency.
     */
    public static Set<DayOfWeek> nonWorkingDays(String currency) {
        if (NON_WORKING_DAYS.containsKey(currency)) {
            return NON_WORKING_DAYS.get(currency).nonWorkingDays;
        } else {
            return NON_WORKING_DAYS.get(OTHERS).nonWorkingDays;
        }

    }


    /**
     * Inner class to construct non working days set.
     */
    private static class NonWorkingDays {

        /**
         * non working days set.
         */
        private Set<DayOfWeek> nonWorkingDays;

        /**
         * constructor to create set of non working days.
         * @param nonWorkingDays array of non working days of type DayOfWeek.
         */
        NonWorkingDays(DayOfWeek... nonWorkingDays) {
            this.nonWorkingDays = Arrays.stream(nonWorkingDays).collect(Collectors.toSet());
        }
    }
}
