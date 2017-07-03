package com.jpmorgan.messagenotification.util;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Arrays;

/**
 * Test for StringUtil
 */
public class StringUtilTest {


    @Test
    public void shouldReturnSingularFormOfPluralWords() {
        //given plural words
        String[][] words_with_expected_word = {
                {"apples", "apple"},
                {"bananas", "banana"},
                {"oranges", "orange"}
        };

        Arrays.stream(words_with_expected_word).forEach(word-> {
            Assertions.assertThat(StringUtil.transformToSingularForms(word[0])).isEqualTo(word[1]);
        });

    }

    @Test
    public void shouldReturnSingularFormOfSingularWords() {
        //given singular words
        String[][] words_with_expected_word = {
                {"apple", "apple"},
                {"banana", "banana"},
                {"orange", "orange"}
        };

        Arrays.stream(words_with_expected_word).forEach(word-> {
            Assertions.assertThat(StringUtil.transformToSingularForms(word[0])).isEqualTo(word[1]);
        });

    }
}
