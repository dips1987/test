package com.jpmorgan.fx.process;

import com.jpmorgan.fx.model.*;

import static java.util.stream.Collectors.summingDouble;
import static org.assertj.core.api.Assertions.*;

import org.assertj.core.util.Maps;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;

/**
 * Tests for FX Report
 */
public class FXReportComputationTest {

    // static data
    private static final String WORKING_DATE_FOR_OTHER_CURRENCIES_03 = "2013-07-03";
    private static final String WORKING_DATE_FOR_OTHER_CURRENCIES_04 = "2013-07-04";

    private static final String NON_WORKING_DATE_FOR_OTHER_CURRENCIES_02 = "2013-07-02";

    private static final String WORKING_DATE_FOR_AED_CURRENCY_04 = "2013-07-05";
    private static final String WORKING_DATE_FOR_AED_CURRENCY_05 = "2013-07-05";

    private static final String NON_WORKING_DATE_FOR_AER_CURRENCY_01 = "2013-07-01";

    FXReportComputation fxReportComputation = new FXReportComputation();

    private Instruction buildInstruction(String entity,
                                         String action,
                                  String currency,
                                         String fxRate,
                                         String instructionDate, //"2016-08-16";
                                         String settlementDate,
                                         String units,
                                         String pricePerUnit) {
        Instruction instruction = new Instruction();
        instruction.setAction(Action.get(action));
        instruction.setCurrency(currency);
        instruction.setEntity(entity);
        instruction.setFxRate(Double.valueOf(fxRate));
        instruction.setInstructionDate(LocalDate.parse(instructionDate));
        instruction.setSettlementDate(LocalDate.parse(settlementDate));
        instruction.setUnits(Long.valueOf(units));
        instruction.setPerunitPrice(Double.valueOf(pricePerUnit));

        return instruction;
    }


    private Double expectedAmount(Instruction... instructions) {
        return Arrays.stream(instructions).collect(summingDouble(instruction ->
                instruction.getPerunitPrice() * instruction.getUnits() * instruction.getFxRate()));
    }


    @Test
    public void shouldReturnTotalAmountForOutgoingOnlyForAWorkingSettlementDate() {
        // given buy instructions only
        Instruction buy_instruction_1_for_working_day_03 = buildInstruction("foo_min_1",
                "B", "SGP", "0.40",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_03,
                "200",
                "100.25");

        Instruction buy_instruction_2_for_working_day_03 = buildInstruction("foo_min_2",
                "B", "SGP", "0.50",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_03,
                "200",
                "100.25");

        // instruction with max amount
        Instruction buy_instruction_3_for_working_day_03 = buildInstruction("foo_max",
                "B", "SGP", "0.60",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_03,
                "200",
                "100.25");

        // pass set of instructions
        List<Instruction> instructions = new ArrayList<>();
        instructions.add(buy_instruction_1_for_working_day_03);
        instructions.add(buy_instruction_2_for_working_day_03);
        instructions.add(buy_instruction_3_for_working_day_03);

        // call process
        Map<ReportKey, Double> totalAmountPerActionEveryday =
                fxReportComputation.computeAmount(instructions);

        // then amount is summed for all three buy instruments with same settlement dat
        assertThat(totalAmountPerActionEveryday).isNotNull();
        assertThat(totalAmountPerActionEveryday).hasSize(1);

        // create expected data
        ReportKey reportKey = new GenerateKey().
                withAction(Action.Buy).
                withSettlementDate(LocalDate.parse(WORKING_DATE_FOR_OTHER_CURRENCIES_03)).build();

        // assert expected result.
        assertThat(totalAmountPerActionEveryday).containsEntry(reportKey,
                expectedAmount(buy_instruction_1_for_working_day_03,
                        buy_instruction_2_for_working_day_03, buy_instruction_3_for_working_day_03));

    }


    @Test
    public void shouldReturnTotalAmountForOutgoingOnlyForMultipleWorkingSettlementDates() {
        // given buy instructions only
        Instruction buy_instruction_1_for_working_day_03 = buildInstruction("foo_min_1",
                "B", "SGP", "0.40",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_03,
                "200",
                "100.25");

        Instruction buy_instruction_2_for_working_day_04 = buildInstruction("foo_min_2",
                "B", "SGP", "0.50",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_04,
                "200",
                "100.25");

        // instruction with max amount
        Instruction buy_instruction_3_for_working_day_03 = buildInstruction("foo_max",
                "B", "SGP", "0.60",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_03,
                "200",
                "100.25");


        List<Instruction> instructions = new ArrayList<>();
        instructions.add(buy_instruction_1_for_working_day_03);
        instructions.add(buy_instruction_2_for_working_day_04);
        instructions.add(buy_instruction_3_for_working_day_03);


        Map<ReportKey, Double> totalAmountPerActionEveryday =
                fxReportComputation.computeAmount(instructions);

        assertThat(totalAmountPerActionEveryday).isNotNull();
        assertThat(totalAmountPerActionEveryday).hasSize(2);

        // create expected buy instrument for settlement date 2017-07-03
        ReportKey reportKeyForSettlementDate_02 = new GenerateKey().
                withAction(Action.Buy)
                .withSettlementDate(LocalDate.parse(WORKING_DATE_FOR_OTHER_CURRENCIES_03)).build();

        // verify buy instrument with settlement date 03
        assertThat(totalAmountPerActionEveryday).containsEntry(reportKeyForSettlementDate_02,
                expectedAmount(buy_instruction_1_for_working_day_03,
                        buy_instruction_3_for_working_day_03));

        // create expected data for settlement date 2017-07-03
        ReportKey reportKeyForSettlementDate_03 = new GenerateKey().
                withAction(Action.Buy).
                withSettlementDate(LocalDate.parse(WORKING_DATE_FOR_OTHER_CURRENCIES_04)).build();
        ;
        // assert expected result.
        assertThat(totalAmountPerActionEveryday).containsEntry(reportKeyForSettlementDate_03,
                expectedAmount(buy_instruction_2_for_working_day_04));

    }


    @Test
    public void shouldReturnTotalAmountForBothIncomingAndOutgoingOnlyForAWorkingSettlementDate() {
        // given buy instructions only
        Instruction buy_instruction_1_for_working_day_03 = buildInstruction("foo_min_1",
                "B", "SGP", "0.40",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_03,
                "200",
                "100.25");

        Instruction buy_instruction_2_for_working_day_03 = buildInstruction("foo_min_2",
                "B", "SGP", "0.50",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_03,
                "200",
                "100.25");

        // instruction with max amount
        Instruction sell_instruction_1_for_working_day_03 = buildInstruction("foo_max",
                "S", "SGP", "0.60",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_03,
                "200",
                "100.25");


        List<Instruction> instructions = new ArrayList<>();
        instructions.add(buy_instruction_1_for_working_day_03);
        instructions.add(buy_instruction_2_for_working_day_03);
        instructions.add(sell_instruction_1_for_working_day_03);


        Map<ReportKey, Double> totalAmountPerActionEveryday =
                fxReportComputation.computeAmount(instructions);

        assertThat(totalAmountPerActionEveryday).isNotNull();
        assertThat(totalAmountPerActionEveryday).hasSize(2);

        // create expected data for settlement date 2016-01-02
        ReportKey reportKeyForBuy = new GenerateKey().
                withAction(Action.Buy)
                .withSettlementDate(LocalDate.parse(WORKING_DATE_FOR_OTHER_CURRENCIES_03)).build();
        ;
        // assert expected result.
        assertThat(totalAmountPerActionEveryday).containsEntry(reportKeyForBuy,
                expectedAmount(buy_instruction_1_for_working_day_03, buy_instruction_2_for_working_day_03));

        // create expected data for settlement date 2016-01-03
        ReportKey reportKeyForSell = new GenerateKey().
                withAction(Action.Sell)
                .withSettlementDate(LocalDate.parse(WORKING_DATE_FOR_OTHER_CURRENCIES_03)).build();
        ;
        // assert expected result.
        assertThat(totalAmountPerActionEveryday).containsEntry(reportKeyForSell,
                expectedAmount(sell_instruction_1_for_working_day_03));

    }

    @Test
    public void shouldReturnTotalAmountForBothIncomingAndOutgoingOnlyForANonWorkingSettlementDate() {
        // given buy instructions only
        Instruction buy_instruction_min_1 = buildInstruction("foo_min_1",
                "B", "SGP", "0.40",
                "2017-07-01", "2017-07-01",
                "200",
                "100.25");

        Instruction buy_instruction_min_2 = buildInstruction("foo_min_2",
                "B", "SGP", "0.50",
                "2017-06-30", "2017-06-30",
                "200",
                "100.25");

        // instruction with max amount
        Instruction buy_instruction_max = buildInstruction("foo_max",
                "S", "SGP", "0.60",
                "2017-06-30", "2017-06-30",
                "200",
                "100.25");


        List<Instruction> instructions = new ArrayList<>();
        instructions.add(buy_instruction_min_1);
        instructions.add(buy_instruction_min_2);
        instructions.add(buy_instruction_max);


        Map<ReportKey, Double> totalAmountPerActionEveryday =
                fxReportComputation.computeAmount(instructions);


        System.out.print(totalAmountPerActionEveryday);

        assertThat(totalAmountPerActionEveryday).isNotNull();
        assertThat(totalAmountPerActionEveryday).hasSize(3);

        // create expected data for settlement date 2016-01-02
        ReportKey reportKeyForBuyAndNonWorkingDay = new GenerateKey().
                withAction(Action.Buy).withSettlementDate(LocalDate.parse("2017-07-03")).build();
        ;
        // assert expected result.
        assertThat(totalAmountPerActionEveryday).containsEntry(reportKeyForBuyAndNonWorkingDay,
                expectedAmount(buy_instruction_min_1));


        // create expected data for settlement date 2017-06-30
        ReportKey reportKeyForBuyForWorkingDay = new GenerateKey().
                withAction(Action.Buy).withSettlementDate(LocalDate.parse("2017-06-30")).build();
        ;
        // assert expected result.
        assertThat(totalAmountPerActionEveryday).containsEntry(reportKeyForBuyForWorkingDay,
                expectedAmount(buy_instruction_min_2));

        // create expected data for settlement date 2016-01-03
        ReportKey reportKeyForSell = new GenerateKey().
                withAction(Action.Sell).withSettlementDate(LocalDate.parse("2017-06-30")).build();
        ;
        // assert expected result.
        assertThat(totalAmountPerActionEveryday).containsEntry(reportKeyForSell,
                expectedAmount(buy_instruction_max));

    }

    @Test
    public void shouldReturnTotalAmountForBothIncomingOutgoingOnlyForMultipleWorkingSettlementDates() {
        // given buy instructions only
        Instruction buy_instruction_with_settlement_date_03 = buildInstruction("foo_min_1",
                "B", "SGP", "0.40",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_03,
                "200",
                "100.25");

        Instruction buy_instruction_with_settlement_date_04 = buildInstruction("foo_min_2",
                "B", "SGP", "0.50",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_04,
                "200",
                "100.25");

        // instruction with max amount
        Instruction sell_instruction_with_settlement_date_03 = buildInstruction("foo_max",
                "S", "SGP", "0.60",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_03,
                "200",
                "100.25");


        // instruction with max amount
        Instruction sell_instruction_with_settlement_date_04 = buildInstruction("foo_max",
                "S", "SGP", "0.60",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_04,
                "200",
                "100.25");

        List<Instruction> instructions = new ArrayList<>();
        instructions.add(buy_instruction_with_settlement_date_03);
        instructions.add(buy_instruction_with_settlement_date_04);
        instructions.add(sell_instruction_with_settlement_date_03);
        instructions.add(sell_instruction_with_settlement_date_04);


        Map<ReportKey, Double> totalAmountPerActionEveryday =
                fxReportComputation.computeAmount(instructions);

        assertThat(totalAmountPerActionEveryday).isNotNull();
        assertThat(totalAmountPerActionEveryday).hasSize(4);

        // create expected data for settlement date 2016-01-02
        ReportKey buyReportKeyForSettlementDate_03 = new GenerateKey().
                withAction(Action.Buy)
                .withSettlementDate(LocalDate.parse(WORKING_DATE_FOR_OTHER_CURRENCIES_03)).build();
        ;
        // assert expected result.
        assertThat(totalAmountPerActionEveryday).containsEntry(buyReportKeyForSettlementDate_03,
                expectedAmount(buy_instruction_with_settlement_date_03));


        // create expected data for settlement date 2016-01-03
        ReportKey buyReportKeyForSettlementDate_04 = new GenerateKey().
                withAction(Action.Buy)
                .withSettlementDate(LocalDate.parse(WORKING_DATE_FOR_OTHER_CURRENCIES_04)).build();
        ;
        // assert expected result.
        assertThat(totalAmountPerActionEveryday).containsEntry(buyReportKeyForSettlementDate_04,
                expectedAmount(buy_instruction_with_settlement_date_04));


        // create expected data for settlement date 2016-01-03
        ReportKey sellReportKeyForSettlementDate_03 = new GenerateKey().
                withAction(Action.Sell)
                .withSettlementDate(LocalDate.parse(WORKING_DATE_FOR_OTHER_CURRENCIES_03)).build();
        ;
        // assert expected result.
        assertThat(totalAmountPerActionEveryday).containsEntry(sellReportKeyForSettlementDate_03,
                expectedAmount(sell_instruction_with_settlement_date_03));

        // create expected data for settlement date 2016-01-03
        ReportKey sellReportKeyForSettlementDate_04 = new GenerateKey().
                withAction(Action.Sell).withSettlementDate(LocalDate.parse(WORKING_DATE_FOR_OTHER_CURRENCIES_04)).build();
        ;
        // assert expected result.
        assertThat(totalAmountPerActionEveryday).containsEntry(sellReportKeyForSettlementDate_04,
                expectedAmount(sell_instruction_with_settlement_date_04));

    }

    @Test
    public void shouldReturnMaxForOutgoingOnlyForAWorkingSettlementDate() {
        // given buy instructions only
        Instruction buy_instruction_1_with_settlement_date_03 = buildInstruction("foo_min_1",
                "B", "SGP", "0.40",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_03,
                "200",
                "100.25");

        Instruction buy_instruction_2_with_settlement_date_03 = buildInstruction("foo_min_2",
                "B", "SGP", "0.50",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_03,
                "200",
                "100.25");

        // instruction with max amount
        Instruction buy_instruction_3_with_settlement_date_03_and_max_value = buildInstruction("foo_max",
                "B", "SGP", "0.60",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_03,
                "200",
                "100.25");


        List<Instruction> instructions = new ArrayList<>();
        instructions.add(buy_instruction_1_with_settlement_date_03);
        instructions.add(buy_instruction_2_with_settlement_date_03);
        instructions.add(buy_instruction_3_with_settlement_date_03_and_max_value);


        Map<ReportKey, Optional<Instruction>> rankOutput =
                fxReportComputation.computeRank(instructions);

       assertThat(rankOutput).isNotNull();
       assertThat(rankOutput).hasSize(1);

       // create expected data
        ReportKey reportKey = new GenerateKey().
                withAction(Action.Buy).build();
               Optional expectedInstrument = Optional.of(buy_instruction_3_with_settlement_date_03_and_max_value);
        // assert expected result.
       assertThat(rankOutput).containsEntry(reportKey, expectedInstrument);

    }


    @Test
    public void shouldReturnMaxForOutgoingOnlyForMultipleWorkingSettlementDates() {
        // given buy instructions only
        Instruction buy_instruction_1_with_settlement_date_03 = buildInstruction("foo_min_1",
                "B", "SGP", "0.40",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_03,
                "200",
                "100.25");

        // differen settlement date
        Instruction buy_instruction_2_with_settlement_date_04 = buildInstruction("foo_min_2",
                "B", "SGP", "0.50",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_04,
                "200",
                "100.25");

        // instruction with max amount
        Instruction buy_instruction_3_with_settlement_date_03_and_max_value = buildInstruction("foo_max",
                "B", "SGP", "0.60",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_03,
                "200",
                "100.25");


        List<Instruction> instructions = new ArrayList<>();
        instructions.add(buy_instruction_1_with_settlement_date_03);
        instructions.add(buy_instruction_2_with_settlement_date_04);
        instructions.add(buy_instruction_3_with_settlement_date_03_and_max_value);


        Map<ReportKey, Optional<Instruction>> rankOutput =
                fxReportComputation.computeRank(instructions);


        // then verify settlement date has no significance. max is calculated based on amount.
        assertThat(rankOutput).isNotNull();
        assertThat(rankOutput).hasSize(1);

        // create expected data
        ReportKey reportKey = new GenerateKey().
                withAction(Action.Buy).build();
        Optional expectedInstrument = Optional.of(buy_instruction_3_with_settlement_date_03_and_max_value);
        // assert expected result.
        assertThat(rankOutput).containsEntry(reportKey, expectedInstrument);

    }


    @Test
    public void shouldReturnMaxForBothIncomingAndOutgoingForASettlementDate() {
        // given buy and sell instructions only
        Instruction buy_instruction = buildInstruction(" buy_min_1",
                "B", "SGP", "0.40",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_03,
                "200",
                "100.25");

        // instruction with max amount
        Instruction buy_instruction_with_max_value = buildInstruction("buy_max",
                "B", "SGP", "0.60",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_03,
                "200",
                "100.25");

        Instruction sell_instruction = buildInstruction("sell_min",
                "S", "SGP", "0.40",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_03,
                "200",
                "100.25");

        // instruction with max amount
        Instruction sell_instruction_with_max_value = buildInstruction("sell_max",
                "S", "SGP", "0.60",
                "2016-01-01", WORKING_DATE_FOR_OTHER_CURRENCIES_03,
                "200",
                "100.25");


        List<Instruction> instructions = new ArrayList<>();
        instructions.add(buy_instruction);
        instructions.add(buy_instruction_with_max_value);
        instructions.add(sell_instruction);
        instructions.add(sell_instruction_with_max_value);


        Map<ReportKey, Optional<Instruction>> rankOutput =
                fxReportComputation.computeRank(instructions);


        // then verify with expected.
        assertThat(rankOutput).isNotNull();
        assertThat(rankOutput).hasSize(2);


        // create expected buy
        ReportKey reportKeyForBuy = new GenerateKey().
                withAction(Action.Buy).build();
        Optional expectedBuyInstrument = Optional.of(buy_instruction_with_max_value);
        // assert expected result.
        assertThat(rankOutput).containsEntry(reportKeyForBuy, expectedBuyInstrument);

        // create expected sell
        ReportKey reportKeyForSell = new GenerateKey().
                withAction(Action.Sell).build();
        Optional expectedSellInstrument = Optional.of(sell_instruction_with_max_value);
        // assert expected result.
        assertThat(rankOutput).containsEntry(reportKeyForSell, expectedSellInstrument);

    }


}
