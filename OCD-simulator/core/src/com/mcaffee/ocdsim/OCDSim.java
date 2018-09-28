package com.mcaffee.ocdsim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.TimeUtils;
import com.mcaffee.ocdsim.model.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * The main application controller that contains everything.
 */
public class OCDSim extends ApplicationAdapter {
	// Height and Width of the window
	private static final int HEIGHT = 700;
	private static final int WIDTH = 703;

	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Board board;

	private Json json;
	
	@Override
	public void create () {
		json = new Json();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);

		batch = new SpriteBatch();

		ArrayList<TickSymbol> symbols = parseSymbols();

		// Create the test tick cards
		ArrayList<TickCard> cards = parseCards();

		// Add some testing players
		ArrayList<Player> players = createTestPlayers(symbols);

		// Create the test gameTiles
		ArrayList<GameTile> gameTiles = parseTiles(players.size());

		// Initialize a new game board
		board = new Board(gameTiles, HEIGHT - 100, 400, players, cards);
		board.setupTiles();
	}

	private ArrayList<Player> createTestPlayers(ArrayList<TickSymbol> tickSymbols) {
		ArrayList<Player> players = new ArrayList<Player>();

		players.add(new Player("Player 1", Player.BLUE, randomSymbolAmount(tickSymbols)));
		players.add(new Player("Player 2", Player.GREEN, randomSymbolAmount(tickSymbols)));
		players.add(new Player("Player 3", Player.YELLOW, randomSymbolAmount(tickSymbols)));
		players.add(new Player("Player 4", Player.RED, randomSymbolAmount(tickSymbols)));

		return players;
	}

	private ArrayList<TickCard> randomCardAmount(ArrayList<TickCard> tickCards) {
		Random rand = new Random();
		ArrayList<TickCard> initialCards = new ArrayList<TickCard>();
		int numOfCards = rand.nextInt(tickCards.size() - 1);
		for(int i=0; i<numOfCards; i++) {
			initialCards.add(tickCards.get(rand.nextInt(tickCards.size() - 1)));
		}
		return initialCards;
	}

	private ArrayList<TickSymbol> parseSymbols() {
		ArrayList<JsonValue> list = json.fromJson(ArrayList.class, Gdx.files.internal("data/symbols.json"));
		ArrayList<TickSymbol> toReturn = new ArrayList<TickSymbol>();
		for(JsonValue v : list) {
			toReturn.add(json.readValue(TickSymbol.class, v));
		}
		return toReturn;
	}

	private ArrayList<GameTile> parseTiles(int playerCount) {
		ArrayList<JsonValue> list = json.fromJson(ArrayList.class, Gdx.files.internal("data/tiles.json"));
		ArrayList<GameTile> tiles = new ArrayList<GameTile>();
		for(JsonValue v : list) {
			tiles.add(json.readValue(GameTile.class, v));
		}
		GameTile startingTile = tiles.get(0);
		for(int i=0; i<playerCount; i++) {
			startingTile.setPlayerOnTile(i, true);
		}
		return tiles;
	}

	private ArrayList<TickCard> parseCards() {
		ArrayList<JsonValue> list = json.fromJson(ArrayList.class, Gdx.files.internal("data/cards.json"));
		ArrayList<TickCard> toReturn = new ArrayList<TickCard>();
		for(JsonValue v : list) {
			toReturn.add(json.readValue(TickCard.class, v));
		}
		return toReturn;
	}

	/**
	 * Returns a random amount of symbols
	 * @param symbols
	 * @return
     */
	private ArrayList<TickSymbol> randomSymbolAmount(ArrayList<TickSymbol> symbols) {
		Random rand = new Random(TimeUtils.nanoTime());
		ArrayList<TickSymbol> toReturn = new ArrayList<TickSymbol>();
		int numOfSymbols = rand.nextInt(symbols.size() - 1);
		for(int i=0; i<numOfSymbols; i++) {
			TickSymbol tempSymbol = symbols.get(rand.nextInt(symbols.size()));
			if(!toReturn.contains(tempSymbol)) {
				toReturn.add(tempSymbol);
			}
		}
		return toReturn;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		// Draw the game board
        board.update(batch);
        batch.end();

		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			board.checkDicePressed(touchPos);
		}
	}

	@Override
	public void dispose() {
		// dispose of all native resources
	}
}
