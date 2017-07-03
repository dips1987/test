package com.jpmorgan.fx.report;

import com.jpmorgan.fx.model.Instruction;

import java.util.List;
import java.util.Set;

/**
 * Provides method to generate report
 */

public interface IReportGeneration {

   void generateReport(List<Instruction> instructionSet);
}