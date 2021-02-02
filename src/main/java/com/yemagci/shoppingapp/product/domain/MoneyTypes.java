package com.yemagci.shoppingapp.product.domain;

public enum MoneyTypes {
    USD("Dolar","$"),
    EUR("Euro","€"),
    TL("Lira","TL");
    private String label;//Euro, Dolar
    private String symbol;//$,€
     MoneyTypes(String label, String symbol){
        this.label=label;
        this.symbol=symbol;
    }
    public String getSymbol(){
         return symbol;
    }
}
