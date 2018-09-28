package com.holyshatots.dogedodge.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class DeadScreen implements Screen
{
	final DogeDodge game;
	OrthographicCamera camera;
	Texture background;
	
	int score;
	
	public DeadScreen(DogeDodge game, int score)
	{
		this.game = game;
		this.score = score;
		
		background = new Texture(Gdx.files.internal("earthfromspace.jpg"));
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
	}
	
	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(1, 0, 0,  1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		game.batch.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		game.font.draw(game.batch, "You died", 100, 150);
		game.font.draw(game.batch, "Tap anywhere to try again. Use the arrow keys to move.", 100, 100);
		game.batch.end();
		
		if (Gdx.input.isTouched())
		{
			game.setScreen(new GameScreen(game));
			dispose();
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
