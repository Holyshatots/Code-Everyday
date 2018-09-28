package com.holyshatots.dogedodge.controller;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.holyshatots.dogedodge.model.DogeText;

public class GameScreen implements Screen
{
	final DogeDodge game;
	
	Texture dogeImage;
	Texture grumpyImage;
	Texture dogeCoinImage;
	Texture background;
	OrthographicCamera camera;
	Rectangle doge;
	Array<Rectangle> grumpies;
	Array<Rectangle> coins;
	Array<DogeText> dogeTextOnScreen;
	private long lastGrumpyDropTime;
	private long lastCoinDropTime;
	private int dogesDodged;
	private long lastDogeTextSpawn;
	private int difficulty;
	
	private final int SCREEN_WIDTH = 800;
	private final int SCREEN_HEIGHT = 480;
	
	private String[] dogeText = {"Wow much dodge",
	 							 "So coins",
	 							 "Many dodge",
	 							 "wow",
	 							 "cool",
	 							 "space",
	 							 "such scary",
	 							 "nice dodge",
	 							 "close"};
	
	public GameScreen(DogeDodge game)
	{
		this.game = game;
		
		// Load the images
		dogeImage = new Texture(Gdx.files.internal("doge_small.png"));
		grumpyImage = new Texture(Gdx.files.internal("grumpycat_small.png"));
		background = new Texture(Gdx.files.internal("earthfromspace.jpg"));
		dogeCoinImage = new Texture(Gdx.files.internal("dogecoin_small.png"));
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		doge = new Rectangle();
		// Place on the screen (x,y)
		doge.x = SCREEN_WIDTH / 2 - 64 / 2;
		doge.y = -5;
		// Size of doge
		doge.width = 64;
		doge.height = 84;
		
		dogesDodged = 0;
		lastDogeTextSpawn = 0;
		
		coins = new Array<Rectangle>();
		grumpies = new Array<Rectangle>();
		dogeTextOnScreen = new Array<DogeText>();
		
		// Fonts
	
	}
	
	private void spawnGrumpies()
	{
		Rectangle grumpyCat = new Rectangle();
		grumpyCat.x = MathUtils.random(0, SCREEN_WIDTH-64);
		grumpyCat.y = SCREEN_HEIGHT;
		grumpyCat.width = 64;
		grumpyCat.height = 64;
		grumpies.add(grumpyCat);
		// Clamp the difficulty
		if(difficulty < 55){
			difficulty++;
		}
		lastGrumpyDropTime = TimeUtils.nanoTime() - (difficulty * 5000000);
	}
	
	private void spawnCoin()
	{
		Rectangle coin = new Rectangle();
		coin.x = MathUtils.random(0, SCREEN_WIDTH-64);
		coin.y = SCREEN_HEIGHT;
		coin.width = 64;
		coin.height = 64;
		coins.add(coin);
		lastCoinDropTime = TimeUtils.nanoTime();
	}
	
	private void spawnDogeText()
	{	
		Random random = new Random();
		Color color = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat());
		DogeText text = new DogeText(dogeText[random.nextInt(dogeText.length)], random.nextInt(SCREEN_WIDTH - 10), random.nextInt(SCREEN_HEIGHT - 10), color);
		dogeTextOnScreen.add(text); 
		lastDogeTextSpawn = TimeUtils.nanoTime();
		if(dogeTextOnScreen.size >= 20)
		{
			dogeTextOnScreen.removeIndex(0);
		}
	}
	
	@Override
	public void render(float delta)
	{
		// Clear the screen with a dark blue color
			Gdx.gl.glClearColor(0,  0 , 0.2f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			// update the camera's matrices
			camera.update();
			
			// tell the Spritebatch to render in the coordinate
			// systme specified by the camera
			game.batch.setProjectionMatrix(camera.combined);
			
			// begin a new batch and draw the doge and
			// all grumpy cats
			game.batch.begin();
			game.batch.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			game.batch.draw(dogeImage, doge.x, doge.y);
			for(Rectangle coin : coins)
			{
				game.batch.draw(dogeCoinImage, coin.x, coin.y);
			}
			for(Rectangle grumpyCat: grumpies)
			{
				game.batch.draw(grumpyImage, grumpyCat.x, grumpyCat.y);
			}
			game.font.draw(game.batch, "Score: " + Integer.toString(dogesDodged), 50, 450);
			game.font.draw(game.batch, "Dodged: " + Integer.toString(difficulty), 50, 470);
			for(DogeText text : dogeTextOnScreen)
			{
				game.comicsansFont.setColor(text.getColor());
				game.comicsansFont.draw(game.batch, text.getText(), text.getX(), text.getY());
			}
			game.batch.end();
			
			// Allows the user to control the doge with mouse / touch
			if(Gdx.input.isTouched())
			{
				Vector3 touchPos = new Vector3();
				touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				camera.unproject(touchPos);
				doge.x = touchPos.x - 64 / 2;
			}
			
			// Allows the user to control with a keyboard
			if(Gdx.input.isKeyPressed(Keys.LEFT)) doge.x -= 200 * Gdx.graphics.getDeltaTime();
			if(Gdx.input.isKeyPressed(Keys.RIGHT)) doge.x += 200 * Gdx.graphics.getDeltaTime();
			
			// make sure doge stays within the screen limits
			if(doge.x < 0) doge.x = 0;
			if(doge.x > 800 - 64 ) doge.x = 800 - 64;
			
			// Checks how much time has passed since we spawned a new grumpy cat
			if(TimeUtils.nanoTime() - lastDogeTextSpawn > 5 * 100000000) spawnDogeText();
			if(TimeUtils.nanoTime() - lastGrumpyDropTime > 5 * 100000000) spawnGrumpies();
			if(TimeUtils.nanoTime() - lastCoinDropTime > 5 * 100000000) spawnCoin();
			
			// Make the grumpy cats move off the screen
			Iterator<Rectangle> iter = coins.iterator();
			while(iter.hasNext())
			{
				Rectangle coin = iter.next();
				coin.y -= 250 * Gdx.graphics.getDeltaTime();
				if(coin.y + 64 < 0)
				{
					// Coin touching bottom of screen
					iter.remove();
				}
				else if(coin.overlaps(doge))
				{
					iter.remove();
					dogesDodged++;
				}
			}
			
			iter = grumpies.iterator();
			while(iter.hasNext())
			{
				Rectangle grumpy = iter.next();
				grumpy.y -= 200 * Gdx.graphics.getDeltaTime();
				if(grumpy.y + 64 < 0)
				{
					// Grumpy cat touching bottom of screen
					iter.remove();
//					dogesDodged++;
				}
				else if(grumpy.overlaps(doge)) 
				{
					// Die
					iter.remove();
					game.setScreen(new DeadScreen(game, dogesDodged));
				}
			}
		
	}

	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
		
	}

}
