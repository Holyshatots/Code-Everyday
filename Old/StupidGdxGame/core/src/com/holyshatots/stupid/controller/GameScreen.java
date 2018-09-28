package com.holyshatots.stupid.controller;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen
{
	final Drop game;
	
	Texture dropImage;
	Texture bucketImage;
	OrthographicCamera camera;
	Rectangle bucket;
	Array<Rectangle> raindrops;
	long lastDropTime;
	int dropsGathered;
	
	public GameScreen(final Drop game)
	{
		this.game = game;
		
		// load the images for the droplet and the bucket, 64x64 pixels each
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		
		// load the drop sound effect and the rain background "music"
//		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
//		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		
		// start the playback of the background music
//		rainMusic.setLooping(true);
//		rainMusic.play();
		
		// create a camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		// setup the rain bucket
		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;
		
		// setup the score
		
		
		raindrops = new Array<Rectangle>();
		spawnRaindrop();
	}
	
	private void spawnRaindrop()
	{
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800-64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
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
		
		// begin a new batch and draw the bucket and
		// all drops
		game.batch.begin();
		game.batch.draw(bucketImage, bucket.x, bucket.y);
		for(Rectangle raindrop: raindrops)
		{
			game.batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		game.font.draw(game.batch, "Score: " + Integer.toString(dropsGathered), 50, 450);
		game.batch.end();
		
		// Allows the user to control the bucket with mouse / touch
		if(Gdx.input.isTouched())
		{
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}
		
		// Allows the user to control with a keyboard
		if(Gdx.input.isKeyPressed(Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();
		
		// make sure the bucket stays within the screen limits
		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 800 - 64 ) bucket.x = 800 - 64;
		
		// Checks how much time has passed since we spawned a new raindrop
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();
		
		// Make the raindrops move and play a sound if it hits the bottom
		// of the screen
		Iterator<Rectangle> iter = raindrops.iterator();
		while(iter.hasNext())
		{
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(raindrop.y + 64 < 0)
			{
				// Raindrop touching bottom of screen
				iter.remove();
				dropsGathered--;
			}
			if(raindrop.overlaps(bucket)) 
			{
//				dropSound.play();
				iter.remove();
				dropsGathered++;
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
		// Clean up 
		dropImage.dispose();
		bucketImage.dispose();
//		dropSound.dispose();
//		rainMusic.dispose();
		
	}
}
