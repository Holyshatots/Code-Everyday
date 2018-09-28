package com.mcaffee.ocdsim.model;

/**
 * Created by Mitch on 4/8/2016.
 */
public class GameState {
    // The possible states that the game can be in
    public final static int START_SCREEN = 1;
    public final static int PLAYER_SELECTION = 2;
    public final static int TICKS_ASSIGNMENT = 3;
    public final static int WAITING_PLAYER_ROLL_DICE = 10;
    public final static int ROLL_DICE = 4;
    public final static int MOVE_CHARACTER = 5;
    public final static int APPLYING_EFFECT_OF_SYMBOL = 6;
    public final static int NEW_TICK = 7;
    public final static int REMOVE_TICK = 8;
    public final static int WIN = 9;

    // The defined amount of time that the MOVE_CHARACTER state is put in
    public final static long MOVE_CHARACTER_TIMEOUT = 300000000;

    private int currentPlayersTurn;

    private int currentState;

    private int currentDieFace;

    private long moveCharacterStart;

    private boolean newMessage;

    public GameState() {
        this.currentPlayersTurn = 1;
        currentDieFace = 1;
        currentState = WAITING_PLAYER_ROLL_DICE;
        newMessage = false;
    }

    public int getPlayersTurn() {
        return currentPlayersTurn;
    }

    public void setPlayersTurn(int player) {
        this.currentPlayersTurn = player;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        newMessage = true;
        this.currentState = currentState;
    }

    public int getCurrentDieFace() {
        return currentDieFace;
    }

    public void setMoveCharacterStart(long start) {
        moveCharacterStart = start;
    }

    public boolean checkMoveCharacterTimeout(long currentTime) {
        if(currentTime - moveCharacterStart > MOVE_CHARACTER_TIMEOUT) {
            moveCharacterStart = 0;
            return true;
        } else {
            return false;
        }
    }

    public boolean getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(boolean newMessage) {
        this.newMessage = newMessage;
    }

}
