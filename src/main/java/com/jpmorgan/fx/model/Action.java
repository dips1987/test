package com.jpmorgan.fx.model;

/**
 *  Trade action represents buy and sell
 */
public enum Action {

    Buy("B"), Sell("S");

    private String value;

     Action(String value) {
        this.value = value;
    }

    public static Action get(String value) {
         switch (value) {
             case "B": return Buy;
             case "S": return Sell;
             default: throw new RuntimeException("Invalid action");
         }
    }
}
