package com.jpmorgan.fx.report;

import com.jpmorgan.fx.model.Instruction;
import com.jpmorgan.fx.model.ReportKey;
import com.jpmorgan.fx.process.FXReportComputation;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Report generation class - calls computation class and logs results.
 */
public class FXReportGeneration implements IReportGeneration {


    /**
     * Generates report for set on instructions.
     * @param instructions - set of instructions.
     */
    public void generateReport(List<Instruction> instructions) {
        FXReportComputation fxReport = new FXReportComputation();
        printTotalAmount(fxReport.computeAmount(instructions));
        printEntitiesBasedOnRanking(fxReport.computeRank(instructions));

    }


    /**
     * prints total amount for each settlement tradable date and for both incoming and outgoing operations.
     *
     */
    private void printTotalAmount(Map<ReportKey,Double> totalAmountResult) {
        totalAmountResult.forEach(((reportKey, aDouble) -> {
           System.out.print("Action:- "+reportKey.getAction());
            System.out.print("  Settlement Date:- "+reportKey.getSettlementDate());
            System.out.println("  Total amount:- "+aDouble);
            System.out.println();

        }));

    }

    /**
     *
     * Prints rankings for both incoming and outgoing.
     */
    private void printEntitiesBasedOnRanking(Map<ReportKey,Optional<Instruction>> rankEntitiesResult) {
        rankEntitiesResult.forEach(((reportKey, optional) -> {
            System.out.print("Action:- "+reportKey.getAction());
            System.out.println("  Rank:- "+optional.get().getEntity());
            System.out.println();

        }));

    }
}
