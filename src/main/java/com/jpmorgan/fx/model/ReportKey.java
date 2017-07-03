package com.jpmorgan.fx.model;

import java.time.LocalDate;

/**
 * Report key used in group by operation.
 * Equals and hashcode methods used for group by operation.
 */
public class ReportKey {

    /**
     * action field in groupby operation.
     */
    private Action action;

    /**
     * settlement date in groupby
     */
    private LocalDate settlementDate;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public LocalDate getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(LocalDate settlementDate) {
        this.settlementDate = settlementDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportKey reportKey = (ReportKey) o;

        if (action != reportKey.action) return false;
        return settlementDate != null ? settlementDate.equals(reportKey.settlementDate) : reportKey.settlementDate == null;
    }

    @Override
    public int hashCode() {
        int result = action != null ? action.hashCode() : 0;
        result = 31 * result + (settlementDate != null ? settlementDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Data={" +
                "action=" + action +
                ", settlementDate=" + settlementDate +
                '}';
    }
}
