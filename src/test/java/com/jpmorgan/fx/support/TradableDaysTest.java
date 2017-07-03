package com.jpmorgan.fx.support;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Tests for TradableDays
 */
public class TradableDaysTest {

    ITradableDays tradableDays = new TradableDays();

    @Test
    public void shouldReturnTheSameWorkingDatePassedInTheInputForDefaultCurrency() {
        // given working day for default
        LocalDate workingDay = LocalDate.parse("2017-07-03");

        // then exxpect same working day.
        Assertions.assertThat(
                tradableDays.
                        getTradableDate("GBP",
                                workingDay)).isEqualTo(workingDay);
    }


    @Test
    public void shouldReturnTheSameWorkingDatePassedInTheInputForAEDAndSARCurrency() {
        // given working day for default
        LocalDate workingDay = LocalDate.parse("2017-07-03");


        List<String> forCurrencies = Arrays.asList("AED", "SAR");


        forCurrencies.stream().forEach(forCurrency -> {
            // then expect same working day.
            Assertions.assertThat(
                    tradableDays.
                            getTradableDate(forCurrency,
                                    workingDay)).isEqualTo(workingDay);
                });

    }


    @Test
    public void shouldReturnTheWorkingDateOfNonWorkingInputDateForDefaultCurrency() {
        // Example 1 for Saturday
        // given working day for default
        LocalDate nonWorkingDate_saturday = LocalDate.parse("2017-07-01");

        // then expect working day.
        Assertions.assertThat(
                tradableDays.
                        getTradableDate("GBP",
                                nonWorkingDate_saturday)).isEqualTo(LocalDate.parse("2017-07-03"));

        // Example 2 for sunday
        LocalDate nonWorkingDate_sunday = LocalDate.parse("2017-07-02");

        // then expect working day.
        Assertions.assertThat(
                tradableDays.
                        getTradableDate("GBP",
                                nonWorkingDate_sunday)).isEqualTo(LocalDate.parse("2017-07-03"));


    }


    @Test
    public void shouldReturnTheWorkingDateOfNonWorkingInputDateForAEDAndSARCurrency() {
        // Example 1 for Friday
        // given non working day for AED
        LocalDate nonWorkingDate_friday = LocalDate.parse("2017-06-30");

        List<String> forCurrencies = Arrays.asList("AED", "SAR");


        forCurrencies.stream().forEach(forCurrency -> {
            // then expect working day.
            Assertions.assertThat(
                    tradableDays.
                            getTradableDate(forCurrency,
                                    nonWorkingDate_friday)).isEqualTo(LocalDate.parse("2017-07-02"));
        });


        // Example 2 for Saturday
        LocalDate nonWorkingDate_saturday = LocalDate.parse("2017-07-01");

        forCurrencies.stream().forEach(forCurrency -> {
            // then expect working day.
            Assertions.assertThat(
                    tradableDays.
                            getTradableDate(forCurrency,
                                    nonWorkingDate_saturday)).isEqualTo(LocalDate.parse("2017-07-02"));
                });
    }


}
