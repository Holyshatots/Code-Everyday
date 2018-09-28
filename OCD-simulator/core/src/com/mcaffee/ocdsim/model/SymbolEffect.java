package com.mcaffee.ocdsim.model;

/**
 * Created by Mitch on 4/12/2016.
 */
public class SymbolEffect {

    private int symbol;
    private String description;

    private int effect;

    public SymbolEffect() {

    }

    public SymbolEffect(int symbol, String description, int effect) {
        this.symbol = symbol;
        this.description = description;
        this.effect = effect;
    }

    public boolean containsSymbol(TickSymbol tickSymbol) {
        return tickSymbol.getSymbol() == symbol;
    }

    public int getEffect() {
        return effect;
    }

    public String getDescription() {
        return description;
    }

    public int getSymbol() {
        return symbol;
    }

    public TickEffect getTickEffect() {
        return new TickEffect(effect);
    }
}
