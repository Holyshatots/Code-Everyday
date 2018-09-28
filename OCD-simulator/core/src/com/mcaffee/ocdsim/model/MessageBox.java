package com.mcaffee.ocdsim.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * A box in the game that displays information about the game's state to the players.
 *
 * Created by Mitch on 4/8/2016.
 */
public class MessageBox extends Rectangle {
    // Dimensions of the box and offset for placement on the screen
    public static final int OFFSET = 180;
    public static final int HEIGHT = 0;
    public static final int WIDTH = 200;
    // The maximum number of messages that are displayed at once
    private static final int MAX_MESSAGES = 7;

    // Location of the text message that appears in the message box
    public static final int MESSAGE_X = OFFSET + WIDTH + 10;
    public static final int MESSAGE_Y = 288 + HEIGHT;
    public static final int MESSAGE_WIDTH = WIDTH + 80;

    // A queue of messages where the first is the newest message
    private Queue<String> messages;

    public MessageBox() {
        this.x = WIDTH + OFFSET;
        this.y = HEIGHT;
        this.messages = new Queue<String>();
    }

    /**
     * Sets a new message in the message box. If the queue is full, will remove
     * the oldest message
     * @param message
     */
    public void setMessage(String message) {
        if(messages.size > MAX_MESSAGES) {
            messages.removeLast();
        }
        messages.addFirst(message);
    }

    /**
     * Returns the list of messages to display with new lines between them
     * @return
     */
    public String getMessage() {
        String message = "";
        Iterator iter = messages.iterator();
        while(iter.hasNext()) {
            message += iter.next() + "\n";
        }
        return message;
    }

    public void waitingPlayerMessage(Player player) {
        setMessage("Waiting for " + player.getName() + " to roll.");
    }

    public void rollingDiceMessage(Player player) {
        setMessage(player.getName() + " is rolling.");
    }

    public void moveCharacterMessage(Player player, int spaces) {
        setMessage(player.getName() + " moved " + spaces + " spaces.");
    }

    public void applyEffectOfSymbolMessage(Player player, TickEffect tickEffect) {
        setMessage(player.getName() + " had effect '" + tickEffect.getDescription() + "' applied to them.");
    }

    public void applySymbolEffectMessage(Player player, SymbolEffect symbolEffect) {
        setMessage(player.getName() + " had '" + symbolEffect.getDescription() + "' applied to them which resulted in the effect '" + symbolEffect.getTickEffect().getDescription() + "'");
    }

    public void displayTileDescription(Player player, GameTile tile) {
        setMessage(player.getName() + " landed on the tile '" + tile.getDescription() + "'");
    }

    public void playerSkippedMessage(Player player) {
        setMessage(player.getName() + " skipped.");
    }

    public void newTickMessage(TickCard tickCard, Player player) {
        setMessage(player.getName() + " just gained a new ritual '" + tickCard.getDescription() + "'.");
    }

    public void alreadyHasTickMessage(TickCard tickCard, Player player) {
        setMessage(player.getName() + " drew card '" + tickCard.getDescription() + "' but already has this ritual.");
    }

    public void removeTickMessage(TickCard tickCardRemoved, Player player) {
        if(tickCardRemoved == null) {
            // There are no ticks to be removed
            setMessage(player.getName() + " would have a ritual removed but has none left!");
        } else {
            setMessage(player.getName() + " just had the ritual '" + tickCardRemoved.getDescription() + "' removed.");
        }
    }

    public void removeSymbolMessage(TickSymbol symbol, Player player) {
        setMessage(player.getName() + " just had a ritual symbol removed from them.");
    }

    public void winMessage(Player winningPlayer) {
        setMessage(winningPlayer.getName() + " wins!");
    }
}
