package com.holyshatots.stupid.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Drop extends Game
{
	Texture dropImage;
	Texture bucketImage;
	BitmapFont font;
//	private Sound dropSound;
//	private Music rainMusic;
	
	private OrthographicCamera camera;
	SpriteBatch batch;
	
	private Rectangle bucket;
	
	// Imported from libgdx because it produces less garbage in various ways
	private Array<Rectangle> raindrops;
	
	// Storing in nanoseconds
	private long lastDropTime;
	
	@Override
	public void create()
	{
		batch = new SpriteBatch();
		// use default Arial font
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render()
	{
		super.render();
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
		batch.dispose();
		font.dispose();
	}
	


}
