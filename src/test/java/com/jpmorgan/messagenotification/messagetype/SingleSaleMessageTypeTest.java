package com.jpmorgan.messagenotification.messagetype;

import com.jpmorgan.messagenotification.model.Product;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Arrays;

/**
 * Tests for SingleSaleMessageType
 */
public class SingleSaleMessageTypeTest {

    private SingleSaleMessageType singleSaleMessageType = new SingleSaleMessageType();


    // TEST: correct format
    @Test
    public void shouldAcceptTheCorrectMessageFormat() {
        //given messages with correct format
        String[] messages = {"apple at 10p",
                            "banana at 20p",
                            "potato at 30p",
                            "banana 20p"};

        // then SingleSaleMessageType class accepts.
        Arrays.stream(messages).forEach(message -> {
            Assertions.assertThat(singleSaleMessageType.support(message)).isTrue();
        });
    }


    // Incorrect formats
    @Test
    public void shouldRejectsTheIncorrectCorrectMessageFormat() {
        //given messages with correct format
        String[] messages =
                {"apple at 10",
                "potato at p",
                "10p",
                "20 sales of apples at 10p each",
                "Add 20p apples"};

        // then SingleSaleMessageType class rejects.
        Arrays.stream(messages).forEach(message -> {
            Assertions.assertThat(singleSaleMessageType.support(message)).isFalse();
        });
    }

    @Test
    public void shouldExtractProductInfoFromTheCorrectMessageFormat() {
        //given messages with correct format.
        // create array with messages with its expcted product.
        String[][] messages_with_expectedProduct_info = {
                {"apple at 10p","apple", "10"},
                {"banana at 20p","banana", "20"},
                {"potato at 30p","potato", "30"},
        };


        // then SingleSaleMessageType class extracts Product info.
        Arrays.stream(messages_with_expectedProduct_info).forEach(message -> {
            Product product = singleSaleMessageType.extractMessage(message[0]);
            Assertions.assertThat(product.getProductName()).isEqualTo(message[1]);
            Assertions.assertThat(product.getValue()).isEqualTo(Long.valueOf(message[2]));
            Assertions.assertThat(product.getNumberOfItems()).isEqualTo(1);
        });
    }
}
