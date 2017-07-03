package com.jpmorgan.fx.model;

import java.time.LocalDate;

/**
 * Class to construct key. This key will be used for group by operation.
 */
public class GenerateKey {

    /**
     * key for group by operation.
     */
    private ReportKey reportKey;

    /**
     * Constructor
     */
    public GenerateKey() {
        reportKey = new ReportKey();
    }


    /**
     * Build key
     */
    public ReportKey build() {
        return reportKey;
    }


    /**
     * key=Action field to includ in group by
     */
    public  GenerateKey withAction(Action action) {
        reportKey.setAction(action);
        return this;
    }

    /**
     *key=Settlement Date field to include in group by operation.
     */
    public GenerateKey withSettlementDate(LocalDate settlementDate) {
        reportKey.setSettlementDate(settlementDate);
        return this;
    }







}
