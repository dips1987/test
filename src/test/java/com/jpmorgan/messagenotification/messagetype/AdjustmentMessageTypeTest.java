package com.jpmorgan.messagenotification.messagetype;

import com.jpmorgan.messagenotification.model.Product;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Arrays;

/**
 * Tests for AdjustmentMessageType
 */
public class AdjustmentMessageTypeTest {

    private AdjustmentMessageType adjustmentMessageType = new AdjustmentMessageType();


    // TEST: correct format
    @Test
    public void shouldAcceptTheCorrectMessageFormat() {
        //given messages with correct format
        String[] messages = {"Add 20p apples",
                "Add 20p bananas",
                "Subtract 20p bananas"};

        // then AdjustmentMessageType class accepts.
        Arrays.stream(messages).forEach(message -> {
            Assertions.assertThat(adjustmentMessageType.support(message)).isTrue();
        });
    }


    // Incorrect formats
    @Test
    public void shouldRejectsTheIncorrectCorrectMessageFormat() {
        //given messages with correct format
        String[] messages = {"Add 20 apples",
                "Add p bananas",
                "20p to apples",
                "Subtract 20p"
        };


        // then AdjustmentMessageType class rejects.
        Arrays.stream(messages).forEach(message -> {
            Assertions.assertThat(adjustmentMessageType.support(message)).isFalse();
        });
    }

    @Test
    public void shouldExtractProductInfoFromTheCorrectMessageFormat() {
        //given messages with correct format.
        // create array with messages with its expcted product.
        String[][] messages_with_expectedProduct_info = {
                {"Add 20p apples","apple", "20"},
                {"Add 20p bananas","banana", "20"},
                {"Subtract 20p bananas","banana","-20"},
                {"Subtract 20p apples","apple","-20"}
        };


        // then AdjustmentMessageType class extracts Product info.
        Arrays.stream(messages_with_expectedProduct_info).forEach(message -> {
            Product product = adjustmentMessageType.extractMessage(message[0]);
            Assertions.assertThat(product.getProductName()).isEqualTo(message[1]);
            Assertions.assertThat(product.getValue()).isEqualTo(Long.valueOf(message[2]));
            Assertions.assertThat(product.getNumberOfItems()).isEqualTo(0);
        });
    }
}
