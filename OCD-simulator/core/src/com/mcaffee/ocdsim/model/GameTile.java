package com.mcaffee.ocdsim.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

/**
 * A tile on the board
 *
 * Created by Mitch on 4/8/2016.
 */
public class GameTile extends Rectangle {
    // The type of tile
    public final static int NORMAL_TILE = 1;
    public final static int TICK_TILE = 2;
    public final static int START_TILE = 3;
    public final static int STOP_TILE = 4;
    private final static int PLAYER_IMAGE_SCALE = 3;

    private int tileType;

    // What is the description of this square. Will be displayed to the users on landing on it.
    private String description;

    // The previous tile or null if the starting tile
    private GameTile prevGameTile;
    // The next tile or null if the end tile
    private GameTile nextGameTile;

    // Index maps with Board's player array index, true means they are on the tile
    private boolean[] playersOnTile;

    // The mapping of the symbol to the effect of the symbol on this tile
    private ArrayList<SymbolEffect> symbolEffects;

    /**
     * Initializes the GameTile with the minimal parameters
     * @param description
     * @param tileType
     */
    public GameTile() {
        playersOnTile = new boolean[]{false,false,false,false};
        symbolEffects = new ArrayList<SymbolEffect>();
    }

    public GameTile(String description, int tileType) {
        this(description, tileType, null, null, 0, new ArrayList<SymbolEffect>());
    }

    public GameTile(String description, int tileType, ArrayList<SymbolEffect> symbolEffects) {
        this(description, tileType, null, null, 0, symbolEffects);
    }

    public GameTile(String description, int tileType, GameTile prevGameTile, GameTile nextGameTile, int numOfPlayers) {
        this(description, tileType, prevGameTile, nextGameTile, numOfPlayers, new ArrayList<SymbolEffect>());
    }

    public GameTile(String description, int tileType, GameTile prevGameTile, GameTile nextGameTile, int numOfPlayers, ArrayList<SymbolEffect> symbolEffects) {
        if(tileType == START_TILE) {
            // Put the players on the starting tile at the beginning
            playersOnTile = new boolean[4];
            for(int i=0; i<numOfPlayers; i++) {
                playersOnTile[i] = true;
            }
        } else {
            // Normal tiles don't start with any players
            playersOnTile = new boolean[]{false,false,false,false};
        }
        this.description = description;
        this.tileType = tileType;
        this.prevGameTile = prevGameTile;
        this.nextGameTile = nextGameTile;
        this.symbolEffects = symbolEffects;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTileType() {
        return this.tileType;
    }

    public void setTileType(int tileType) {
        this.tileType = tileType;
    }

    public GameTile getPrevGameTile() {
        return prevGameTile;
    }

    public GameTile getNextGameTile() {
        return nextGameTile;
    }

    public void setPrevGameTile(GameTile prevGameTile) {
        this.prevGameTile = prevGameTile;
    }

    public void setNextGameTile(GameTile nextGameTile) {
        this.nextGameTile = nextGameTile;
    }

    public boolean[] getPlayersOnTile() {
        return playersOnTile;
    }

    public void setPlayerOnTile(int playerIndex, boolean onTile) {
        playersOnTile[playerIndex] = onTile;
    }

    public void setPlayersOnTile(boolean[] playersOnTile) {
        this.playersOnTile = playersOnTile;
    }

    /**
     * Draw a scaled down version of the player images that need to be drawn on this tile
     * @param batch
     * @param playerTexture
     * @param playerNum
     */
    public void drawPlayer(SpriteBatch batch, Texture playerTexture, int playerNum) {
        if(playerNum == 0) {
            batch.draw(playerTexture, x + 10, y + 10, playerTexture.getWidth() / PLAYER_IMAGE_SCALE, playerTexture.getHeight() / PLAYER_IMAGE_SCALE, 0, 0, playerTexture.getWidth(), playerTexture.getHeight(), false, false);
        } else if(playerNum == 1) {
            batch.draw(playerTexture, x + 30, y + 10, playerTexture.getWidth() / PLAYER_IMAGE_SCALE, playerTexture.getHeight() / PLAYER_IMAGE_SCALE, 0, 0, playerTexture.getWidth(), playerTexture.getHeight(), false, false);
        } else if(playerNum == 2) {
            batch.draw(playerTexture, x + 10, y + 30, playerTexture.getWidth() / PLAYER_IMAGE_SCALE, playerTexture.getHeight() / PLAYER_IMAGE_SCALE, 0, 0, playerTexture.getWidth(), playerTexture.getHeight(), false, false);
        } else if(playerNum == 3) {
            batch.draw(playerTexture, x + 30, y + 30, playerTexture.getWidth() / PLAYER_IMAGE_SCALE, playerTexture.getHeight() / PLAYER_IMAGE_SCALE, 0, 0, playerTexture.getWidth(), playerTexture.getHeight(), false, false);
        }
    }

    public boolean containsSymbol(TickSymbol symbol) {
        return getEffect(symbol) != -1;
    }

    public int getEffect(TickSymbol symbol) {
        for(SymbolEffect effect : symbolEffects) {
            if(effect.containsSymbol(symbol)) {
                return effect.getEffect();
            }
        }
        return -1;
    }

    public ArrayList<SymbolEffect> checkSymbols(ArrayList<TickSymbol> symbols) {
        ArrayList<SymbolEffect> matchingSymbolEffects = new ArrayList<SymbolEffect>();
        for(SymbolEffect symbolEffect : symbolEffects ) {
            int symbolToCheck = symbolEffect.getSymbol();
            for(TickSymbol symbol : symbols) {
                if(symbol.getSymbol() == symbolToCheck) {
                    matchingSymbolEffects.add(symbolEffect);
                }
            }
        }
        return matchingSymbolEffects;
    }



    public boolean containsSymbol(ArrayList<TickSymbol> symbols) {
        for(TickSymbol symbol : symbols) {
            if(containsSymbol(symbol)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Integer> getSymbols() {
        ArrayList<Integer> symbols = new ArrayList<Integer>();
        for(SymbolEffect effect : symbolEffects) {
            symbols.add(effect.getSymbol());
        }
        return symbols;
    }
}
