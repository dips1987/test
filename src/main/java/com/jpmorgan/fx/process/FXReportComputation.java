package com.jpmorgan.fx.process;

import com.jpmorgan.fx.model.*;
import com.jpmorgan.fx.support.TradableDays;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.*;

/**
 * This class carries out computation and returns the result based on key which represents group by option.
 *
 */
public class FXReportComputation {

    /**
     * This method computes total amount based on key
     * which is a combination of Action (Incoming/Outgoing) and working settlement
     * @param instructions - set of instructions
     * @return - total amount map for both incoming/outgoing and
     * for each settlement day exists in a data set.
     */
    public Map<ReportKey,Double> computeAmount(List<Instruction> instructions) {

        Map<ReportKey,Double> result =
                instructions.stream().collect
                                (groupingBy(instruction -> new GenerateKey().
                                                withAction(instruction.getAction()).
                                                withSettlementDate(
                                                        getTradeDay(instruction.getCurrency(),
                                                                instruction.getSettlementDate())).build(),
                                        summingDouble(this::calculateAmount))
                );



        return result;
    }


    /**
     * This method ranks entities based on action as key.
     * @param instructions - set of instructions
     * @return - ranking map for both incoming and outgoing operations if available in data set.
     */
    public Map<ReportKey,Optional<Instruction>> computeRank(List<Instruction> instructions) {


        Map<ReportKey, Optional<Instruction>> map =
                instructions.stream().
                        collect
                        (groupingBy(instruction -> new GenerateKey().
                                        withAction(instruction.getAction()).build(),
                                maxBy(Comparator.comparing(this::calculateAmount)))
                        );



        return map;
    }


    /**
     * compute amount based on units * price per unit * fx rate
     * @param instruction - instruction
     * @return calculated amount.
     */
    private double calculateAmount(Instruction instruction) {
        return instruction.getPerunitPrice() * instruction.getUnits() * instruction.getFxRate();
    }


    /**
     * Calculate trade date based on setllement date and currency i.e.
     * returns the next working date for a non working settlement date.
     * @param currency - currency to determine non working days
     * @param settlementDate - date to verify if falls on non working day.
     * @return working date based on input parameters.
     */
    private LocalDate getTradeDay(String currency, LocalDate settlementDate) {
        return new TradableDays().getTradableDate(currency, settlementDate);
    }



}
