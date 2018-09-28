package com.mcaffee.ocdsim.model;

/**
 * Created by Mitch on 4/8/2016.
 */
public class TickEffect {

    private String description;

    // Effect constants
    public final static int EFFECT_NOTHING = 0;
    public final static int EFFECT_SKIP_TURN = 1;
    public final static int EFFECT_SKIP_TWO_TURNS = 7;
    public final static int EFFECT_SKIP_THREE_TURNS = 6;
    public final static int EFFECT_MOVE_FORWARD = 2;
    public final static int EFFECT_REMOVE_SYMBOL = 3;
    public final static int EFFECT_NEW_SYMBOL = 4;

    // The effect that this tick effect will have
    private int effect;

    public TickEffect(String description, int effect) {
        this.description = description;
        this.effect = effect;
    }

    public TickEffect(int effect) {
        this.effect = effect;
        if (effect == EFFECT_NOTHING) {
            this.description = "Nothing";
        } else if (effect == EFFECT_SKIP_TURN) {
            this.description = "Skip 1 turn";
        } else if (effect == EFFECT_SKIP_TWO_TURNS) {
            this.description = "Skip 2 turns";
        } else if (effect == EFFECT_SKIP_THREE_TURNS) {
            this.description = "Skip 3 turns";
        } else if (effect == EFFECT_MOVE_FORWARD) {
            this.description = "Move forward 1 tile";
        } else if (effect == EFFECT_REMOVE_SYMBOL) {
            this.description = "Remove a ritual from the player";
        } else if (effect == EFFECT_NEW_SYMBOL){
            this.description = "Gain a new ritual";
        }
    }

    public void applyEffect(Board board, Player player) {
        if(effect == EFFECT_NOTHING) {
            // Literally do nothing
        } else if(effect == EFFECT_SKIP_TURN) {
            player.addTurnsToWait(1);
        } else if(effect == EFFECT_SKIP_TWO_TURNS) {
            player.addTurnsToWait(2);
        } else if(effect == EFFECT_SKIP_THREE_TURNS) {
            player.addTurnsToWait(3);
        } else if(effect == EFFECT_MOVE_FORWARD) {
            board.movePlayer(player, 1);
        }
    }

    public String getDescription() {
        return description;
    }

    public int getEffect() {
        return effect;
    }
}
