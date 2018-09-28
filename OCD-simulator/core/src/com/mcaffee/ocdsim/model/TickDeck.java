package com.mcaffee.ocdsim.model;

import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mitch on 4/10/2016.
 */
public class TickDeck {

    private ArrayList<TickCard> deck;

    private Random rand;

    public TickDeck(ArrayList<TickCard> deck) {
        this.deck = deck;
        rand = new Random(TimeUtils.nanoTime());
    }

    public TickCard drawCard() {
        return deck.get(rand.nextInt(deck.size() - 1));
    }
}
