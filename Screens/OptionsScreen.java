package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Geometry;
import com.mygdx.game.MapGenerator;

public class OptionsScreen implements com.badlogic.gdx.Screen {
	private Geometry game;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private MapGenerator map;
	private Texture gameSpeedTexture;
	private Texture returnTexture;
	private Texture returnActiveTexture;
	private Texture oneTexture;
	private Texture twoTexture;
	private Texture threeTexture;
	private Texture fourTexture;
	private Texture fiveTexture;
	private Texture oneActiveTexture;
	private Texture twoActiveTexture;
	private Texture threeActiveTexture;
	private Texture fourActiveTexture;
	private Texture fiveActiveTexture;
	private float oneY = 300;
	private float twoY = 400;
	private float returnY = 100;

	public OptionsScreen(SpriteBatch batch, MapGenerator map, OrthographicCamera camera, Geometry game) {
		this.map = map;
		this.game = game;
		this.batch = batch;
		this.camera = camera;
		gameSpeedTexture = new Texture("buttons/game_speed.png");
		returnTexture = new Texture("buttons/return.png");
		returnActiveTexture = new Texture("buttons/return_active.png");
		oneTexture = new Texture("buttons/one.png");
		twoTexture = new Texture("buttons/two.png");
		threeTexture = new Texture("buttons/three.png");
		fourTexture = new Texture("buttons/four.png");
		fiveTexture = new Texture("buttons/five.png");
		oneActiveTexture = new Texture("buttons/one_active.png");
		twoActiveTexture = new Texture("buttons/two_active.png");
		threeActiveTexture = new Texture("buttons/three_active.png");
		fourActiveTexture = new Texture("buttons/four_active.png");
		fiveActiveTexture = new Texture("buttons/five_active.png");
	}

	public void show() {
	}

	public void render(float delta) {
		camera.update();
		camera.position.set(512, 360, 0);
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(16384);
		batch.begin();
		batch.draw(gameSpeedTexture, 512 - gameSpeedTexture.getWidth() / 2, 720 - oneY, gameSpeedTexture.getWidth(),
				gameSpeedTexture.getHeight());
		if ((map.playerSpeed == 6) || ((Gdx.input.getY() < twoY) && (Gdx.input.getY() > twoY - oneTexture.getHeight())
				&& (Gdx.input.getX() > 170) && (Gdx.input.getX() < 170 + oneTexture.getWidth()))) {
			batch.draw(oneActiveTexture, 170, 720 - twoY, oneTexture.getWidth(), oneTexture.getHeight());
			if (Gdx.input.isTouched())
				map.playerSpeed = 6;
		} else {
			batch.draw(oneTexture, 170, 720 - twoY, oneTexture.getWidth(), oneTexture.getHeight());
		}

		if ((map.playerSpeed == 9) || ((Gdx.input.getY() < twoY) && (Gdx.input.getY() > twoY - twoTexture.getHeight())
				&& (Gdx.input.getX() > 340) && (Gdx.input.getX() < 340 + twoTexture.getWidth()))) {
			batch.draw(twoActiveTexture, 340, 720 - twoY, twoTexture.getWidth(), twoTexture.getHeight());
			if (Gdx.input.isTouched())
				map.playerSpeed = 9;
		} else {
			batch.draw(twoTexture, 340, 720 - twoY, twoTexture.getWidth(), twoTexture.getHeight());
		}

		if ((map.playerSpeed == 12)
				|| ((Gdx.input.getY() < twoY) && (Gdx.input.getY() > twoY - threeTexture.getHeight())
						&& (Gdx.input.getX() > 510) && (Gdx.input.getX() < 510 + threeTexture.getWidth()))) {
			batch.draw(threeActiveTexture, 510, 720 - twoY, threeTexture.getWidth(), threeTexture.getHeight());
			if (Gdx.input.isTouched())
				map.playerSpeed = 12;
		} else {
			batch.draw(threeTexture, 510, 720 - twoY, threeTexture.getWidth(), threeTexture.getHeight());
		}

		if ((map.playerSpeed == 15) || ((Gdx.input.getY() < twoY) && (Gdx.input.getY() > twoY - fourTexture.getHeight())
				&& (Gdx.input.getX() > 680) && (Gdx.input.getX() < 680 + fourTexture.getWidth()))) {
			batch.draw(fourActiveTexture, 680, 720 - twoY, fourTexture.getWidth(), fourTexture.getHeight());
			if (Gdx.input.isTouched())
				map.playerSpeed = 15;
		} else {
			batch.draw(fourTexture, 680, 720 - twoY, fourTexture.getWidth(), fourTexture.getHeight());
		}

		if ((map.playerSpeed == 18) || ((Gdx.input.getY() < twoY) && (Gdx.input.getY() > twoY - fiveTexture.getHeight())
				&& (Gdx.input.getX() > 850) && (Gdx.input.getX() < 850 + fiveTexture.getWidth()))) {
			batch.draw(fiveActiveTexture, 850, 720 - twoY, fiveTexture.getWidth(), fiveTexture.getHeight());
			if (Gdx.input.isTouched())
				map.playerSpeed = 18;
		} else {
			batch.draw(fiveTexture, 850, 720 - twoY, fiveTexture.getWidth(), fiveTexture.getHeight());
		}

		if ((Gdx.input.getY() < returnY) && (Gdx.input.getY() > returnY - returnTexture.getHeight())
				&& (Gdx.input.getX() > 512 - gameSpeedTexture.getWidth() / 2)
				&& (Gdx.input.getX() < 512 + gameSpeedTexture.getWidth() / 2)) {
			batch.draw(returnActiveTexture, 512 - returnTexture.getWidth() / 2, 720 - returnY, returnTexture.getWidth(),
					returnTexture.getHeight());
			if (Gdx.input.isTouched())
				game.changeScreen(1);
		} else {
			batch.draw(returnTexture, 512 - returnTexture.getWidth() / 2, 720 - returnY, returnTexture.getWidth(),
					returnTexture.getHeight());
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
		gameSpeedTexture.dispose();
		returnTexture.dispose();
		returnActiveTexture.dispose();
		oneTexture.dispose();
		twoTexture.dispose();
		threeTexture.dispose();
		fourTexture.dispose();
		fiveTexture.dispose();
		oneActiveTexture.dispose();
		twoActiveTexture.dispose();
		threeActiveTexture.dispose();
		fourActiveTexture.dispose();
		fiveActiveTexture.dispose();
	}
}
