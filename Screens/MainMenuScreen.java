package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Geometry;

public class MainMenuScreen implements com.badlogic.gdx.Screen {
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

	public void show() {
	}

	public void render(float delta) {
		camera.update();
		camera.position.set(512, 360, 0);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(16384);
		batch.begin();
		if ((720 - Gdx.input.getY() > playY) && (720 - Gdx.input.getY() < playY + playButtonActiveTexture.getHeight())
				&& (Gdx.input.getX() > 512 - playButtonInactiveTexture.getWidth() / 2)
				&& (Gdx.input.getX() < 512 + playButtonInactiveTexture.getWidth() / 2)) {
			batch.draw(playButtonActiveTexture, 512 - playButtonInactiveTexture.getWidth() / 2, playY,
					playButtonInactiveTexture.getWidth(), playButtonInactiveTexture.getHeight());
			if (Gdx.input.isTouched())
				game.changeScreen(3);
		} else {
			batch.draw(playButtonInactiveTexture, 512 - playButtonInactiveTexture.getWidth() / 2, 500,
					playButtonInactiveTexture.getWidth(), playButtonInactiveTexture.getHeight());
		}
		if ((720 - Gdx.input.getY() > optionsY)
				&& (720 - Gdx.input.getY() < optionsY + optionsButtonActiveTexture.getHeight())
				&& (Gdx.input.getX() > 512 - optionsButtonInactiveTexture.getWidth() / 2)
				&& (Gdx.input.getX() < 512 + optionsButtonInactiveTexture.getWidth() / 2)) {
			batch.draw(optionsButtonActiveTexture, 512 - optionsButtonInactiveTexture.getWidth() / 2, 300,
					optionsButtonInactiveTexture.getWidth(), optionsButtonInactiveTexture.getHeight());
			if (Gdx.input.isTouched())
				game.changeScreen(2);
		} else {
			batch.draw(optionsButtonInactiveTexture, 512 - optionsButtonInactiveTexture.getWidth() / 2, 300,
					optionsButtonInactiveTexture.getWidth(), optionsButtonInactiveTexture.getHeight());
		}
		if ((720 - Gdx.input.getY() > exitY) && (720 - Gdx.input.getY() < exitY + exitButtonActiveTexture.getHeight())
				&& (Gdx.input.getX() > 512 - exitButtonInactiveTexture.getWidth() / 2)
				&& (Gdx.input.getX() < 512 + exitButtonInactiveTexture.getWidth() / 2)) {
			batch.draw(exitButtonActiveTexture, 512 - exitButtonInactiveTexture.getWidth() / 2, 100,
					exitButtonInactiveTexture.getWidth(), exitButtonInactiveTexture.getHeight());
			if (Gdx.input.isTouched())
				Gdx.app.exit();
		} else {
			batch.draw(exitButtonInactiveTexture, 512 - exitButtonInactiveTexture.getWidth() / 2, 100,
					exitButtonInactiveTexture.getWidth(), exitButtonInactiveTexture.getHeight());
		}

		batch.end();
		batch.setProjectionMatrix(camera.combined);
	}

	public void resize(int width, int height) {
	}

	public void pause() {
	}

	public void resume() {
	}

	public void hide() {
	}

	public void dispose() {
		playButtonActiveTexture.dispose();
		playButtonInactiveTexture.dispose();
		optionsButtonActiveTexture.dispose();
		optionsButtonInactiveTexture.dispose();
		exitButtonActiveTexture.dispose();
		exitButtonInactiveTexture.dispose();
	}
}
