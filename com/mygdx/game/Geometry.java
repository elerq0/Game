package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Screens.GameScreen;
import Screens.MainMenuScreen;
import Screens.OptionsScreen;

public class Geometry extends Game {
	
	public static final int width = 1024;
	public static final int height = 720;
	
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Player player;
	private Hud hud;
	private MapGenerator map;
	private Physics physics;
	private MainMenuScreen mainMenuScreen;
	private GameScreen gameScreen;
	private OptionsScreen optionsScreen;

	public float deltaTime;
	

	private Texture cubeTexture;

	/**
	 * Inicjowanie wszystkich obiektow
	 */
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Geometry.width, Geometry.height);
		hud = new Hud(batch);
		cubeTexture = new Texture("Cube.png");
		player = new Player(cubeTexture);
		map = new MapGenerator(batch, player);
		physics = new Physics(map, player, hud, this);
		mainMenuScreen = new MainMenuScreen(batch, this, camera);
		this.setScreen(mainMenuScreen);
	}
	
	/**
	 * Wyswietlenie odpowiedniego screenu
	 */
	
	@Override
	public void render() {
		deltaTime = Gdx.graphics.getDeltaTime();
		if(this.screen == gameScreen) gameScreen.render(deltaTime);
		if(this.screen == mainMenuScreen) mainMenuScreen.render(deltaTime);
		if(this.screen == optionsScreen) optionsScreen.render(deltaTime);
	}
	
	/**
	 * Zmiana screenu
	 * @param screen 	Zmienna okreslajaca wybrany screen
	 */

	public void changeScreen(int screen) {
		switch (screen) {
		case 1:
			if (mainMenuScreen == null)
				mainMenuScreen = new MainMenuScreen(batch, this, camera);
			this.setScreen(mainMenuScreen);
			break;
		case 2:
			if (optionsScreen == null)
				optionsScreen = new OptionsScreen(batch, map, camera, this);
			this.setScreen(optionsScreen);
			break;
		case 3:
			if (gameScreen == null)
				gameScreen = new GameScreen(batch, player, hud, map, physics, camera);
				this.setScreen(gameScreen);
				map.structureCreate();
				physics.musicChoose();
			break;
		}
	}
	
	/**
	 * Czyszczenie pamieci
	 */
	
	@Override
	public void dispose() {
		batch.dispose();
		cubeTexture.dispose();
		map.dispose();
		physics.dispose();
		mainMenuScreen.dispose();
		optionsScreen.dispose();
		gameScreen.dispose();
	}
}
