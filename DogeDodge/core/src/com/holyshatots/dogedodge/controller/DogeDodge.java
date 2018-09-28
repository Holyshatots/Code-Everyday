package com.holyshatots.dogedodge.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class DogeDodge extends Game
{
	BitmapFont font;
	BitmapFont comicsansFont;
	SpriteBatch batch;
	Texture grumpyCatImage;
	Texture dogeImage;
	
	OrthographicCamera camera;
	
	Rectangle doge;
	
	// Imported from libgdx because it produces less garbage in various ways
	Array<Rectangle> grumpies;
	
	// Storing in nanoseconds
	long lastDropTime;
	
	@Override
	public void create()
	{
		font = new BitmapFont();
		comicsansFont = new BitmapFont(Gdx.files.internal("LDFComicSans-18.fnt"), Gdx.files.internal("comic_sans.png"), false);
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render()
	{
		super.render();
	}
	
	@Override
	public void dispose()
	{
		font.dispose();
		batch.dispose();
	}
}
