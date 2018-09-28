package com.mcaffee.ocdsim.model;

/**
 * Represents an OCD tick that a player can have.
 *
 * Created by Mitch on 4/8/2016.
 */
public class TickCard {
    // The description of the tick
    private String description;
    // The effect that the tick has
    private int effect;

    // If this is tick removal card
    private boolean removeCard;

    public TickCard(){}

    public TickCard(int effect) {
        this("", effect);
    }

    public TickCard(String description, int effect) {
        this.description = description;
        this.effect = effect;
    }

    public TickEffect getEffect() {
        return new TickEffect(effect);
    }

    public String getDescription() {
        return description;
    }

    public boolean isRemoveCard() {
        return effect == TickEffect.EFFECT_REMOVE_SYMBOL;
    }
}
