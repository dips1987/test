package com.jpmorgan.messagenotification.processor;

import com.jpmorgan.messagenotification.model.Product;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

/**
 * Test for MessageProcessor
 */
public class MessageProcessorTest {

    private MessageProcessor processor = new MessageProcessor();


    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();



    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void shouldLogMessageAfterEvery10thMessage() throws IOException{
        // give data
        String[] messages = {
                "apple at 20p",
                "apple at 20p",
                "apple at 10p",
                "apple at 10p",
                "apple at 10p",
                "apple at 10p",
                "apple at 10p",
                "20 sales of apples at 10p each",
                "10 sales of apples at 10p each",
                "10 sales of apples at 10p each",
                // repeat above section with 10 more
                "apple at 20p",
                "apple at 20p",
                "apple at 10p",
                "apple at 10p",
                "apple at 10p",
                "apple at 10p",
                "apple at 10p",
                "20 sales of apples at 10p each",
                "10 sales of apples at 10p each",
                "10 sales of apples at 10p each",
                // repeat 1st section with 10 more
                "apple at 20p",
                "apple at 20p",
                "apple at 10p",
                "apple at 10p",
                "apple at 10p",
                "apple at 10p",
                "apple at 10p",
                "20 sales of apples at 10p each",
                "10 sales of apples at 10p each",
                "10 sales of apples at 10p each",
        };

        for(int i=0; i<messages.length; i++) {
            processor.processMessage(messages[i]);
            int section = 1;
            if((i+1) % 10 == 0) {
                // create expected data to verify
                Product expectedProduct = new Product();
                // set expected number of items
                expectedProduct.setNumberOfItems(section*47);

                // set expected number of total value.
                long value = 20*2 + 10*5 + 20*10 + 10*10 + 10*10;
                expectedProduct.setValue(section*value);

                // set expected product name
                expectedProduct.setProductName("apple");


                // verify console logged with expected data after every 10th message.
                Assertions.assertThat(outContent.toString()).containsSequence(expectedProduct.toString());

                // for next section
                ++ section;
            }
        }
    }


    @Test
    public void shouldLogMessageAfterEvery10thMessageForAllThreeTypesOfMessage() throws IOException{
        // give data
        String[] messages = {
                "apple at 20p",
                "apple at 20p",
                "apple at 10p",
                "apple at 10p",
                "apple at 10p",
                "Subtract 20p apples",
                "Add 20p apples",
                "Add 20p apples",
                "20 sales of apples at 10p each",
                "10 sales of apples at 10p each",
                "10 sales of apples at 10p each",

        };

        for(int i=0; i<messages.length; i++) {
            processor.processMessage(messages[i]);

            if((i+1) % 10 == 0) {
                // create expected data to verify
                Product expectedProduct = new Product();
                // set expected number of items
                expectedProduct.setNumberOfItems(35);

                // set expected number of total value.
                long value = 20*2 + 10*3 + 20*2 - 20*1 +20*10 + 10*10;
                expectedProduct.setValue(value);

                // set expected product name
                expectedProduct.setProductName("apple");


                // verify console logged with expected data after every 10th message.
                Assertions.assertThat(outContent.toString()).containsSequence(expectedProduct.toString());


            }
        }
    }


    @Test
    public void shouldRefuseMessageIfLimitReached() throws IOException{
        // give data
        String[] messages = {
                "apple at 20p",
                "apple at 20p",
                "apple at 10p",
                "apple at 10p",
                "apple at 10p",
                "Subtract 20p apples",
                "Add 20p apples",
                "Add 20p apples",
                "20 sales of apples at 10p each",
                "10 sales of apples at 10p each",
                "10 sales of apples at 10p each",
                "apple at 20p",
                "apple at 20p",
                "apple at 10p",
                "apple at 10p",
                "apple at 10p",
                "Subtract 20p apples",
                "Add 20p apples",
                "Add 20p apples",
                "20 sales of apples at 10p each",
                "10 sales of apples at 10p each",
                "10 sales of apples at 10p each",
                "apple at 20p",
                "apple at 20p",
                "apple at 10p",
                "apple at 10p",
                "apple at 10p",
                "Subtract 20p apples",
                "Add 20p apples",
                "Add 20p apples",
                "20 sales of apples at 10p each",
                "10 sales of apples at 10p each",
                "10 sales of apples at 10p each",
                "apple at 20p",
                "apple at 20p",
                "apple at 10p",
                "apple at 10p",
                "apple at 10p",
                "Subtract 20p apples",
                "Add 20p apples",
                "Add 20p apples",
                "20 sales of apples at 10p each",
                "10 sales of apples at 10p each",
                "10 sales of apples at 10p each",
                "apple at 20p",
                "apple at 20p",
                "apple at 10p",
                "apple at 10p",
                "apple at 10p",
                "Subtract 20p apples",
                "Add 20p apples",
                "Add 20p apples"
        };

        for(int i=0; i<messages.length; i++) {
            processor.processMessage(messages[i]);

            if((i+1) == 51 || (i+1) == 52) {
                // verify console logged with expected data after every 10th message.
                Assertions.assertThat(outContent.toString())
                        .containsSequence("Limit Reached. current size=50");


            }
        }
    }

}
