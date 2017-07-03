package com.jpmorgan.messagenotification.messagetype;

import com.jpmorgan.messagenotification.model.Product;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Arrays;

/**
 * Tests for MutliSaleMessageType
 */
public class MutliSaleMessageTypeTest {

    private MutliSaleMessageType mutliSaleMessageType = new MutliSaleMessageType();


    // TEST: correct format
    @Test
    public void shouldAcceptTheCorrectMessageFormat() {
        //given messages with correct format
        String[] messages = {"20 sales of banana at 10p each",
                "20 sales of apples at 10p",
                "20 sales of apples 10p each",
                "20 apples 10p each",
                "20 apples 10p"};

        // then MutliSaleMessageType class accepts.
        Arrays.stream(messages).forEach(message -> {
            Assertions.assertThat(mutliSaleMessageType.support(message)).isTrue();
        });
    }


    // Incorrect formats
    @Test
    public void shouldRejectsTheIncorrectCorrectMessageFormat() {
        //given messages with correct format
        String[] messages = {"20 sales of banana at 10 each",
                "sales of banana at 10p each",
                "20 sales of banana at p each",
                "20 sales of banana at each",
                "20 at 10p each"
        };

        // then MutliSaleMessageType class rejects.
        Arrays.stream(messages).forEach(message -> {
            Assertions.assertThat(mutliSaleMessageType.support(message)).isFalse();
        });
    }

    @Test
    public void shouldExtractProductInfoFromTheCorrectMessageFormat() {
        //given messages with correct format.
        // create array with messages with its expcted product.
        String[][] messages_with_expectedProduct_info = {
                {"20 sales of banana at 10p each","banana", "10", "20"},
                {"30 sales of banana at 10p","banana", "10", "30"},
                {"20 sales of apples 15p each","apple", "15", "20"}
        };


        // then MutliSaleMessageType class extracts Product info.
        Arrays.stream(messages_with_expectedProduct_info).forEach(message -> {
            System.out.println(message[0]);
            Product product = mutliSaleMessageType.extractMessage(message[0]);
            Assertions.assertThat(product.getProductName()).isEqualTo(message[1]);
            Assertions.assertThat(product.getValue()).isEqualTo(Long.valueOf(message[2]));
            Assertions.assertThat(product.getNumberOfItems()).isEqualTo(Integer.parseInt(message[3]));
        });
    }
}
