package com.mcaffee.ocdsim.model;

/**
 * Created by Mitch on 4/8/2016.
 */
public class TickSymbol {
    public final static int CLOCK = 0;
    public final static int WATER = 1;
    public final static int ORDER = 2;
    public final static int DOOR = 3;
    public final static int PEOPLE = 4;
    public final static int EVERYONE = 5;
    public final static int HEART = 6;
    public final static int CLOVER = 7;
    public final static int BRAIN = 8;
    public final static int EYES = 9;
    public final static int TRASHCAN = 10;

    private int symbol;

    private String description;
    private String imageName;

    public TickSymbol() {
    }

    public TickSymbol(int symbol) {
        this.symbol = symbol;
    }

    public int getSymbol() {
        return symbol;
    }
}
