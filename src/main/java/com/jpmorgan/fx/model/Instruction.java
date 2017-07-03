package com.jpmorgan.fx.model;

import java.time.LocalDate;

/**
 * Instruction to hold values of instruction message.
 */
public class Instruction {

    /**
     * holds entity value
     */
    private String entity;

    /**
     * hold trade action
     */
    private Action action;

    /**
     * holds foreign exchange rate
     */
    private Double fxRate;

    /**
     * holds currency
     */
    private String currency;

    /**
     * instruction date
     */
    private LocalDate instructionDate;

    /**
     * settlement date received in a message.
     */
    private LocalDate settlementDate;

    /**
     * number of units buy/sell
     */
    private long units;

    /**
     * per unit price.
     */
    private double perunitPrice;

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Double getFxRate() {
        return fxRate;
    }

    public void setFxRate(double fxRate) {
        this.fxRate = fxRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getInstructionDate() {
        return instructionDate;
    }

    public void setInstructionDate(LocalDate instructionDate) {
        this.instructionDate = instructionDate;
    }

    public LocalDate getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(LocalDate settlementDate) {
        this.settlementDate = settlementDate;
    }

    public long getUnits() {
        return units;
    }

    public void setUnits(long units) {
        this.units = units;
    }

    public double getPerunitPrice() {
        return perunitPrice;
    }

    public void setPerunitPrice(double perunitPrice) {
        this.perunitPrice = perunitPrice;
    }


    @Override
    public String toString() {
        return "Instruction{" +
                "entity='" + entity + '\'' +
                ", action=" + action +
                ", fxRate=" + fxRate +
                ", currency='" + currency + '\'' +
                ", instructionDate=" + instructionDate +
                ", settlementDate=" + settlementDate +
                ", units=" + units +
                ", perunitPrice=" + perunitPrice +
                '}';
    }
}
