package com.jpmorgan.fx.consumer;

import com.jpmorgan.fx.model.Action;
import com.jpmorgan.fx.model.Instruction;
import com.jpmorgan.fx.report.FXReportGeneration;
import com.jpmorgan.fx.report.IReportGeneration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Consumer class to play with FX Report Generation.
 */
public class Main {

    public static void main(String[] args) {
        IReportGeneration reportGeneration = new FXReportGeneration();
        reportGeneration.generateReport(buildInstructions());
    }

    /**
     * Build dummy set of instructions.
     * @return list of dummy instructions.
     */

    private static List<Instruction> buildInstructions() {
     List<Instruction> instructions = new ArrayList<>();

        Instruction buy_instruction = buildInstruction("foo",
                "B", "SGP", "0.50",
                "2016-01-01", "2016-01-01",
                "200",
                "100.25");

        Instruction sell_instruction = buildInstruction("bar",
                "S", "AED", "0.22",
                "2016-01-01", "2016-01-05",
                "450",
                "100.25");

        instructions.add(buy_instruction);
        instructions.add(sell_instruction);

     return instructions;
    }


    private static Instruction buildInstruction(String entity,
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
}
