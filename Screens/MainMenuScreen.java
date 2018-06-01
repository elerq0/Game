package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Geometry;

public class MainMenuScreen implements Screen {

	private Geometry game;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Texture playButtonActiveTexture;
	private Texture playButtonInactiveTexture;
	private Texture optionsButtonActiveTexture;
	private Texture optionsButtonInactiveTexture;
	private Texture exitButtonActiveTexture;
	private Texture exitButtonInactiveTexture;

	private float playY = 500;
	private float optionsY = 300;
	private float exitY = 100;

	
	/**
	 * Konstruktor klasy.  Przekazuje odwo³ania do obiektów utworzonych w klasie g³ównej gry oraz inicjuje tekstury
	 * @param game		odwolanie na obiekt typu Geometry
	 * @param batch 	umozliwia wyswietlenie tekstury
	 * @param camera	odwolanie na obiekt typu OrthographicCamera
	 */
	public MainMenuScreen(SpriteBatch batch, Geometry game, OrthographicCamera camera) {
		this.game = game;
		this.batch = batch;
		this.camera = camera;
		playButtonActiveTexture = new Texture("buttons/play_button_active.png");
		playButtonInactiveTexture = new Texture("buttons/play_button_inactive.png");
		optionsButtonActiveTexture = new Texture("buttons/options_button_active.png");
		optionsButtonInactiveTexture = new Texture("buttons/options_button_inactive.png");
		exitButtonActiveTexture = new Texture("buttons/exit_button_active.png");
		exitButtonInactiveTexture = new Texture("buttons/exit_button_inactive.png");
	}

	@Override
	public void show() {
	}

	/**
	 * Wyswietlenie menu opcji
	 * @param delta	czas pomiedzy dwoma kolejnymi klatkami
	 */
	
	@Override
	public void render(float delta) {
		camera.update();
		camera.position.set(Geometry.width / 2, Geometry.height / 2, 0);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		if (Geometry.height - Gdx.input.getY() > playY
				&& Geometry.height - Gdx.input.getY() < playY + playButtonActiveTexture.getHeight()
				&& Gdx.input.getX() > Geometry.width / 2 - playButtonInactiveTexture.getWidth() / 2
				&& Gdx.input.getX() < Geometry.width / 2 + playButtonInactiveTexture.getWidth() / 2) {
			batch.draw(playButtonActiveTexture, Geometry.width / 2 - playButtonInactiveTexture.getWidth() / 2, playY,
					playButtonInactiveTexture.getWidth(), playButtonInactiveTexture.getHeight());
			if (Gdx.input.isTouched())
				game.changeScreen(3);
		} else {
			batch.draw(playButtonInactiveTexture, Geometry.width / 2 - playButtonInactiveTexture.getWidth() / 2, 500,
					playButtonInactiveTexture.getWidth(), playButtonInactiveTexture.getHeight());
		}
		if (Geometry.height - Gdx.input.getY() > optionsY
				&& Geometry.height - Gdx.input.getY() < optionsY + optionsButtonActiveTexture.getHeight()
				&& Gdx.input.getX() > Geometry.width / 2 - optionsButtonInactiveTexture.getWidth() / 2
				&& Gdx.input.getX() < Geometry.width / 2 + optionsButtonInactiveTexture.getWidth() / 2) {
			batch.draw(optionsButtonActiveTexture, Geometry.width / 2 - optionsButtonInactiveTexture.getWidth() / 2,
					300, optionsButtonInactiveTexture.getWidth(), optionsButtonInactiveTexture.getHeight());
			if (Gdx.input.isTouched())
				game.changeScreen(2);
		} else {
			batch.draw(optionsButtonInactiveTexture, Geometry.width / 2 - optionsButtonInactiveTexture.getWidth() / 2,
					300, optionsButtonInactiveTexture.getWidth(), optionsButtonInactiveTexture.getHeight());
		}
		if (Geometry.height - Gdx.input.getY() > exitY
				&& Geometry.height - Gdx.input.getY() < exitY + exitButtonActiveTexture.getHeight()
				&& Gdx.input.getX() > Geometry.width / 2 - exitButtonInactiveTexture.getWidth() / 2
				&& Gdx.input.getX() < Geometry.width / 2 + exitButtonInactiveTexture.getWidth() / 2) {
			batch.draw(exitButtonActiveTexture, Geometry.width / 2 - exitButtonInactiveTexture.getWidth() / 2, 100,
					exitButtonInactiveTexture.getWidth(), exitButtonInactiveTexture.getHeight());
			if (Gdx.input.isTouched())
				Gdx.app.exit();
		} else {
			batch.draw(exitButtonInactiveTexture, Geometry.width / 2 - exitButtonInactiveTexture.getWidth() / 2, 100,
					exitButtonInactiveTexture.getWidth(), exitButtonInactiveTexture.getHeight());

		}
		batch.end();
		batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	/**
	 * Czyszczenie pamieci
	 */
	
	@Override
	public void dispose() {
		 playButtonActiveTexture.dispose();
		 playButtonInactiveTexture.dispose();
		 optionsButtonActiveTexture.dispose();
		 optionsButtonInactiveTexture.dispose();
		 exitButtonActiveTexture.dispose();
		 exitButtonInactiveTexture.dispose();
	}
}
