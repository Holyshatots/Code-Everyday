package com.mcaffee.ocdsim.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the game board of OCD Simulator ( Name could change ).
 *
 * Created by Mitch on 4/8/2016.
 */
public class Board {
    // The number of gameTiles high the path of gameTiles will be
    private final int BOARD_TILE_HEIGHT = 5;

    private ArrayList<Texture> symbolImages;

    // The textures for the gameTiles
    private Texture normalTileImage;
    private Texture startTileImage;
    private Texture stopTileImage;
    private Texture tickTileImage;

    // The gameTiles of the board
    private ArrayList<GameTile> gameTiles;

    // The images for the possible player image colors
    private Texture bluePlayerImage;
    private Texture yellowPlayerImage;
    private Texture redPlayerImage;
    private Texture greenPlayerImage;

    // All of the dice faces
    private ArrayList<Texture> diceImages;
    // The game die. Shared by all players
    private Die die;

    // All of the players in the game
    private ArrayList<Player> players;

    // The font object to draw to the screen
    private BitmapFont font;

    // The texture for the message box background
    private Texture messageBoxImage;
    // Representation of the message box
    private MessageBox messageBox;

    private TickDeck tickDeck;

    // The pixel size of the game board
    private int height;
    private int width;

    // Size of each tile
    private float tileWidth;
    private float tileHeight;

    // The state of the game
    private GameState state;

    public Board(ArrayList<GameTile> gameTiles, int height, int width, ArrayList<Player> players, ArrayList<TickCard> tickDeck) {
        // Given arguments
        this.gameTiles = gameTiles;
        this.players = players;
        this.height = height;
        this.width = width;

        // Setup the font
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        // Setup the symbols
        symbolImages = new ArrayList<Texture>();

        symbolImages.add(new Texture(Gdx.files.internal("inverted_icons/glyphicons-54-alarm-inv.png")));
        symbolImages.add(new Texture(Gdx.files.internal("inverted_icons/glyphicons-678-open-water-inv.png")));
        symbolImages.add(new Texture(Gdx.files.internal("inverted_icons/glyphicons-157-show-thumbnails-inv.png")));
        symbolImages.add(new Texture(Gdx.files.internal("inverted_icons/glyphicons-4-user-inv.png")));
        symbolImages.add(new Texture(Gdx.files.internal("inverted_icons/glyphicons-25-parents-inv.png")));
        symbolImages.add(new Texture(Gdx.files.internal("inverted_icons/glyphicons-681-door-inv.png")));
        symbolImages.add(new Texture(Gdx.files.internal("inverted_icons/glyphicons-13-heart-inv.png")));
        symbolImages.add(new Texture(Gdx.files.internal("inverted_icons/glyphicons-542-cat-inv.png")));
        symbolImages.add(new Texture(Gdx.files.internal("inverted_icons/glyphicons-84-random-inv.png")));
        symbolImages.add(new Texture(Gdx.files.internal("inverted_icons/glyphicons-52-eye-open-inv.png")));
        symbolImages.add(new Texture(Gdx.files.internal("inverted_icons/glyphicons-17-bin-inv.png")));

        // Dogs for now ( TESTING ONLY )
        /*
        symbolImages.add(new Texture(Gdx.files.internal("inverted_icons/glyphicons-1-glass-inv.png")));
        symbolImages.add(new Texture(Gdx.files.internal("inverted_icons/glyphicons-2-leaf-inv.png")));
        symbolImages.add(new Texture(Gdx.files.internal("inverted_icons/glyphicons-16-print-inv.png")));
        symbolImages.add(new Texture(Gdx.files.internal("inverted_icons/glyphicons-3-dog-inv.png")));
        */

        if(symbolImages.get(0) == null) {
            throw new NullPointerException();
        }

        // Setup the gameTiles
        tileWidth = 32;
        tileHeight = 32;
        normalTileImage = new Texture(Gdx.files.internal("Tile.png"));
        startTileImage = new Texture(Gdx.files.internal("Tile_Start.png"));
        stopTileImage = new Texture(Gdx.files.internal("Tile_Stop.png"));
        tickTileImage = new Texture(Gdx.files.internal("Tile_Tick.png"));
        setupTiles();

        // Setup the tick deck
        this.tickDeck = new TickDeck(tickDeck);

        // Setup the players
        bluePlayerImage = new Texture(Gdx.files.internal("Player_lblue.png"));
        yellowPlayerImage = new Texture(Gdx.files.internal("Player_yellow.png"));
        redPlayerImage = new Texture(Gdx.files.internal("Player_red.png"));
        greenPlayerImage = new Texture(Gdx.files.internal("Player_green.png"));
        setupPlayers();

        // Setup the message box
        messageBoxImage = new Texture(Gdx.files.internal("MessageBox.png"));
        messageBox = new MessageBox();

        // Setup the die
        diceImages = new ArrayList<Texture>();
        die = new Die();
        for(int i=0; i<=6; i++ ) {
            diceImages.add(new Texture(Gdx.files.internal("dice_0" + i + ".png")));
        }

        // Setup the game state
        state = new GameState();

        // Sets the die to the first player
        die.setToPlayer(players.get(0));
    }

    /**
     * Tiles are drawn in rows of 8, down one, and then goes the other direction
     *
     * XXXXXXXX
     *        X
     * XXXXXXXX
     * X
     * XXXXXXXX
     *        X
     * XXXXXXXX
     */
    public void setupTiles() {
        int row = 0;
        int col = 0;
        boolean top = true;
        GameTile prev = null;

        for(GameTile gameTile : gameTiles) {
            // Set the position of the gameTile
            gameTile.setX(col * 64);
            gameTile.setY(height - (row * 64));

            // Calculate the position of the next gameTile
            if(((row < BOARD_TILE_HEIGHT - 1 && top) || (row > 0 && !top)) && col % 2 == 0) {
                if(top) {
                    // Normal column going down
                    row++;
                } else {
                    row--;
                }

            } else if(col % 2 != 0)  {
                if(top) {
                    // Last column on regular row going down
                    row = 0;
                    col++;
                } else {
                    row = BOARD_TILE_HEIGHT - 1;
                    col++;
                }

            } else {
                // Single gameTile row
                if(top) {
                    row = BOARD_TILE_HEIGHT - 1;
                } else {
                    row = 0;
                }
                top = !top;
                col++;
            }

            // Create the linked list of gameTiles
            if(prev != null) {
                gameTile.setPrevGameTile(prev);
                prev.setNextGameTile(gameTile);
            }
            prev = gameTile;
        }
    }

    /**
     * Set the variables of the players to the initial states
     */
    private void setupPlayers() {
        GameTile startGameTile = gameTiles.get(0);
        for(int i=0; i<players.size(); i++) {
            Player player = players.get(i);
            player.setCurrentGameTile(startGameTile);
            player.setY(20);
            player.setX(i * 90 + 10);
        }
    }

    /**
     * Update the game board and redraw the assets on it
     * @param batch
     */
    public void update(SpriteBatch batch) {
        drawTiles(batch);
        drawPlayers(batch);
        drawMessageBox(batch);
        drawDice(batch);
        updateTimers();
    }

    /**
     * Draw the gameTiles on the board
     * @param batch
     */
    private void drawTiles(SpriteBatch batch) {
        for(GameTile gameTile : gameTiles) {
            // Draw the appropriate gameTile image
            int tileType = gameTile.getTileType();
            if(tileType == GameTile.NORMAL_TILE) {
                batch.draw(normalTileImage, gameTile.x, gameTile.y);
            } else if(tileType == GameTile.START_TILE) {
                batch.draw(startTileImage, gameTile.x, gameTile.y);
            } else if(tileType == GameTile.STOP_TILE) {
                batch.draw(stopTileImage, gameTile.x, gameTile.y);
            } else if(tileType == GameTile.TICK_TILE) {
                batch.draw(tickTileImage, gameTile.x, gameTile.y);
            }

            // Draw the players on the gameTile if applicable
            boolean[] playersOnTile = gameTile.getPlayersOnTile();
            for(int i=0; i<playersOnTile.length; i++) {
                if(playersOnTile[i]) {
                    Player player = players.get(i);
                    gameTile.drawPlayer(batch, getPlayerImage(player), i);
                }
            }
        }
    }

    /**
     * Draw the players below the game board
     * @param batch
     */
    private void drawPlayers(SpriteBatch batch) {
        for(Player player : players) {
            // Draw the symbols that the player has
            ArrayList<TickSymbol> symbols = player.getSymbols();
            int symbolRow = 0;
            int symbolCol = 0;
            for(int i=0; i<symbols.size() - 1; i++) {
                if(symbolCol >= 2) {
                    symbolCol = 0;
                    symbolRow++;
                }

                TickSymbol symbol = symbols.get(i);
                if (symbol != null) {
                    batch.draw(symbolImages.get(symbol.getSymbol()), player.x + (symbolCol * 35), player.y + 100 + (symbolRow * 30));
                }

                symbolCol++;
            }

            // Draw the name above the player
            font.draw(batch, player.getName(),(float) player.x + 3,(float) player.y + 64 + 30);

            // Draw the player image
            Texture playerImage = getPlayerImage(player);
            batch.draw(playerImage, player.x, player.y);
        }
    }

    /**
     * Draw the die in the appropriate place and animate the rolling
     * @param batch
     */
    private void drawDice(SpriteBatch batch) {
        if(state.getCurrentState() == state.ROLL_DICE) {
            if (die.checkFaceChange(TimeUtils.nanoTime())) {
                // Face changed to the next state so move character
                state.setMoveCharacterStart(TimeUtils.nanoTime());
                state.setCurrentState(state.MOVE_CHARACTER);
                Player player = currentPlayer();
                movePlayer(player, die.getCurrentFace());

                // Check the effect of the current tile
                GameTile newGameTile = player.getCurrentGameTile();
                if (newGameTile.getTileType() == newGameTile.NORMAL_TILE) {
                    // Print out this tile's description
                    messageBox.displayTileDescription(player, newGameTile);

                    // Normal tile so check the symbols for matches
                    ArrayList<Integer> tileSymbols = newGameTile.getSymbols();

                    ArrayList<SymbolEffect> matchingSymbolEffects = newGameTile.checkSymbols(player.getSymbols());
                    for (SymbolEffect currSymbolEffect : matchingSymbolEffects) {
                        // Apply the cards effect
                        TickEffect toApply = currSymbolEffect.getTickEffect();
                        toApply.applyEffect(this, player);
                        messageBox.applySymbolEffectMessage(player, currSymbolEffect);
                    }
                } else if (newGameTile.getTileType() == newGameTile.TICK_TILE) {
                    // Tick GameTile so draw a new card
                    TickCard drawnCard = tickDeck.drawCard();
                    if (drawnCard.isRemoveCard()) {
                        // This is a remove card so randomly remove a symbol from the player's hand
                        TickSymbol removedSymbol = player.removeRandomSymbol();
                        messageBox.removeSymbolMessage(removedSymbol, player);
                    } else if(drawnCard.getEffect().getEffect() == TickEffect.EFFECT_NEW_SYMBOL) {
                        // Add a new tick symbol to the player
                        boolean changed = player.addTickSymbol(randomSymbol());
                        if (changed) {
                            messageBox.newTickMessage(drawnCard, player);
                        } else {
                            // The player already has this card
                            messageBox.alreadyHasTickMessage(drawnCard, player);
                        }
                    } else {
                        // This is any other kind of tick card so apply it's effect
                        TickEffect effectToApply = drawnCard.getEffect();
                        effectToApply.applyEffect(this, player);
                    }
                } else if (newGameTile.getTileType() == newGameTile.STOP_TILE) {
                    state.setCurrentState(state.WIN);
                }
            }

            batch.draw(diceImages.get(die.getCurrentFace()), die.x, die.y);
        } else if(state.getCurrentState() == state.WAITING_PLAYER_ROLL_DICE) {
            batch.draw(diceImages.get(0), die.x, die.y);
        } else if(state.getCurrentState() != state.WIN){
            batch.draw(diceImages.get(die.getCurrentFace()), die.x, die.y);
        }
    }

    private ArrayList<TickEffect> intsToTickEffect(ArrayList<Integer> tickEffects) {
        ArrayList<TickEffect> created = new ArrayList<TickEffect>();
        for(Integer currSymbol : tickEffects ) {
            created.add(new TickEffect(currSymbol));
        }
        return created;
    }

    /**
     * Draw the message box with the appropriate message for the state
     * @param batch
     */
    private void drawMessageBox(SpriteBatch batch) {
        // Message box background image
        batch.draw(messageBoxImage, messageBox.x, messageBox.y);

        if(state.getNewMessage()) {
            // Set the message for the message box
            if(state.getCurrentState() == state.WAITING_PLAYER_ROLL_DICE) {
                messageBox.waitingPlayerMessage(currentPlayer());
            } else if(state.getCurrentState() == state.ROLL_DICE) {
                messageBox.rollingDiceMessage(currentPlayer());
            } else if(state.getCurrentState() == state.MOVE_CHARACTER) {
                messageBox.moveCharacterMessage(currentPlayer(), die.getCurrentFace());
            } else if(state.getCurrentState() == state.WIN) {
                messageBox.winMessage(currentPlayer());
            }
            state.setNewMessage(false);
        }


        // Message box message
        font.draw(batch, messageBox.getMessage(), messageBox.MESSAGE_X, messageBox.MESSAGE_Y, messageBox.MESSAGE_WIDTH, Align.left, true);
    }

    /**
     * Update any timers that are holding back the game state
     */
    private void updateTimers(){
        if(state.getCurrentState() == state.MOVE_CHARACTER && state.checkMoveCharacterTimeout(TimeUtils.nanoTime())) {
            state.setCurrentState(state.WAITING_PLAYER_ROLL_DICE);

            // Cycle to next players turn
            nextPlayersTurn();
        }
    }

    /**
     * Cycles to the next players turn and set's the die above that player
     */
    private void nextPlayersTurn() {
        // Cycle through the players decreasing the number of turns that they are waiting
        int index  = state.getPlayersTurn();
        if (index >= players.size()) {
            index = 1;
        } else {
            index += 1;
        }
        Player nextPlayer = players.get(index - 1);
        while(nextPlayer.decreaseTurnsToWait()) {
            if (index >= players.size()) {
                index = 1;
            } else {
                index += 1;
            }
            messageBox.playerSkippedMessage(nextPlayer);
            nextPlayer = players.get(index - 1);
        }

        // Somebody is not waiting
        state.setPlayersTurn(index);
        die.setToPlayer(currentPlayer());
    }

    /**
     * Check if the dice object was pressed
     * @param touchPos
     */
    public void checkDicePressed(Vector3 touchPos) {
        Rectangle touchPosRectangle = new Rectangle();
        touchPosRectangle.x = touchPos.x;
        touchPosRectangle.y = touchPos.y;
        touchPosRectangle.width = 10;
        touchPosRectangle.height = 10;
        if(state.getCurrentState() == state.WAITING_PLAYER_ROLL_DICE && die.overlaps(touchPosRectangle)) {
            state.setCurrentState(state.ROLL_DICE);
        }
    }

    /**
     * Returns the player object of the current player's turn
     * @return
     */
    private Player currentPlayer() {
        return players.get(state.getPlayersTurn() - 1);
    }

    /**
     * Returns the player image based on the player object
     * @param player
     * @return
     */
    private Texture getPlayerImage(Player player) {
        int color = player.getColor();
        if(color == Player.BLUE) {
            return bluePlayerImage;
        } else if(color == Player.YELLOW) {
            return yellowPlayerImage;
        } else if(color == Player.GREEN) {
            return greenPlayerImage;
        } else {
            return redPlayerImage;
        }
    }

    /**
     * Move the player object from the source tile to the destination tile
     * @param player
     * @param source
     * @param destination
     */
    private void movePlayer(Player player, GameTile source, GameTile destination) {
        int playerIndex = players.indexOf(player);
        source.setPlayerOnTile(playerIndex, false);
        destination.setPlayerOnTile(playerIndex, true);
        player.setCurrentGameTile(destination);
    }

    /**
     * Move the player the given number of spaces ahead or to the end of the path.
     * @param player
     * @param spaces
     */
    public void movePlayer(Player player, int spaces) {
        GameTile currentGameTile = player.getCurrentGameTile();
        int destination = gameTiles.indexOf(currentGameTile) + spaces;
        GameTile destinationGameTile;
        if(destination >= gameTiles.size() - 1) {
            destinationGameTile = gameTiles.get(gameTiles.size() - 1);
        } else {
            destinationGameTile = gameTiles.get(destination);
        }
        movePlayer(player, currentGameTile, destinationGameTile);
    }

    private TickSymbol randomSymbol() {
        Random rand = new Random(TimeUtils.nanoTime());
        int selection = rand.nextInt(symbolImages.size() - 1);
        TickSymbol symbol = new TickSymbol(selection);
        return symbol;
    }
}
