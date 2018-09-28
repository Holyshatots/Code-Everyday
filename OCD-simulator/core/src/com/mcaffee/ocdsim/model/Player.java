package com.mcaffee.ocdsim.model;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a player in the game
 *
 * Created by Mitch on 4/8/2016.
 */
public class Player extends Circle {
    // Mapping of the color to an pre generated image
    public final static int BLUE = 1;
    public final static int RED = 2;
    public final static int YELLOW = 3;
    public final static int GREEN = 4;

    // Color of the player's token
    private int color;
    // Name of the player
    private String name;
    // The current tile of the player
    private GameTile currentGameTile;

    private int turnsToWait;
    private boolean canRollTwice;

    // The symbols that the player has
    private ArrayList<TickSymbol> symbols;

    private Random rand;

    public Player(String name, int color) {
        this(name, color, new ArrayList<TickSymbol>());
    }

    public Player(String name, int color, ArrayList<TickSymbol> tickSymbols) {
        this.color = color;
        this.name = name;
        rand = new Random(TimeUtils.nanoTime());
        symbols = tickSymbols;
        turnsToWait = 0;
        this.canRollTwice = false;
    }

    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public GameTile getCurrentGameTile() {
        return currentGameTile;
    }

    public void setCurrentGameTile(GameTile gameTile) {
        currentGameTile = gameTile;
    }

    public boolean addTickSymbol(TickSymbol symbol) {
        if(!symbols.contains(symbol)) {
            symbols.add(symbol);
            return true;
        }
        return false;
    }

    public boolean checkSymbol(Integer symbolToFind) {
        for(TickSymbol symbol : symbols ) {
            if(symbol.getSymbol() == symbolToFind) {
                return true;
            }
        }
        return false;
    }

    public boolean checkSymbols(ArrayList<Integer> tickSymbols) {
        for(TickSymbol symbol : symbols ) {
            if(checkSymbol(symbol.getSymbol())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<TickSymbol> getSymbols() {
        return symbols;
    }

    /**
     * Removes a random tick symbol from the player's hand or nothing if the player's hand is empty
     */
    public TickSymbol removeRandomSymbol() {
        if(!symbols.isEmpty()) {
            // Remove a random card
            return symbols.remove(rand.nextInt(symbols.size()));
        } else {
            return null;
        }
    }

    /**
     * Returns the number of turns that the player still has to wait
     * @return
     */
    public int getTurnsToWait() {
        return turnsToWait;
    }

    /**
     * Add on to the number of turns the player has to wait
     * @param turns
     */
    public void addTurnsToWait(int turns) {
        turnsToWait += turns;
    }

    public boolean getCanRollTwice() {
        return true;
    }

    public void setCanRollTwice(boolean canRollTwice) {
        this.canRollTwice = canRollTwice;
    }

    public boolean decreaseTurnsToWait() {
        if(turnsToWait > 0) {
            turnsToWait -= 1;
            return true;
        }
        return false;
    }
}
