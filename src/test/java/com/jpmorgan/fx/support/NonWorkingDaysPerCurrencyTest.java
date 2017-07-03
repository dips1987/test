package com.jpmorgan.fx.support;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 *  Tests for NonWorkingDaysPerCurrency
 */
public class NonWorkingDaysPerCurrencyTest {

    @Test
    public void shouldReturnNonWorkingDaysForAEDOrSARCurrencies() {
        // given currencies AER and SAR
        List<String> forCurrencies = Arrays.asList("AED", "SAR");

        // expects Friday and Saturday as weekends
        forCurrencies.stream().forEach(forCurrency -> {
            Set<DayOfWeek> nonWorkingDays = NonWorkingDaysPerCurrency.nonWorkingDays(forCurrency);
        Assertions.assertThat(nonWorkingDays).containsExactly(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);
        });

    }

    @Test
    public void shouldReturnNonWorkingDaysForOtherCurrencies() {
        // given currencies which have default non working days.
        List<String> forCurrencies = Arrays.asList("GBP", "EUR", "USA");

        // expects Friday and Saturday as weekends
        forCurrencies.stream().forEach(forCurrency -> {
            Set<DayOfWeek> nonWorkingDays = NonWorkingDaysPerCurrency.nonWorkingDays(forCurrency);
            Assertions.assertThat(nonWorkingDays).containsExactlyInAnyOrder(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        });

    }


}
