package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Geometry;
import com.mygdx.game.MapGenerator;

public class OptionsScreen implements Screen {

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

	/**
	 * Konstruktor klasy.  Przekazuje odwo³ania do obiektów utworzonych w klasie g³ównej gry oraz inicjuje tekstury
	 * @param batch 	umozliwia wyswietlenie tekstury
	 * @param map		odwolanie na obiekt typu MapGenerator
	 * @param camera	odwolanie na obiekt typu OrthographicCamera
	 * @param game		odwolanie na obiekt typu Geometry
	 */
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
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(gameSpeedTexture, Geometry.width / 2 - gameSpeedTexture.getWidth() / 2, Geometry.height - oneY,
				gameSpeedTexture.getWidth(), gameSpeedTexture.getHeight());
		if (map.playerSpeed == 6 || (Gdx.input.getY() < twoY && Gdx.input.getY() > twoY - oneTexture.getHeight()
				&& Gdx.input.getX() > Geometry.width / 6 * 1
				&& Gdx.input.getX() < Geometry.width / 6 * 1 + oneTexture.getWidth())) {
			batch.draw(oneActiveTexture, Geometry.width / 6 * 1, Geometry.height - twoY, oneTexture.getWidth(),
					oneTexture.getHeight());
			if (Gdx.input.isTouched())
				map.playerSpeed = 6;
		} else {
			batch.draw(oneTexture, Geometry.width / 6 * 1, Geometry.height - twoY, oneTexture.getWidth(),
					oneTexture.getHeight());
		}

		if (map.playerSpeed == 9 || (Gdx.input.getY() < twoY && Gdx.input.getY() > twoY - twoTexture.getHeight()
				&& Gdx.input.getX() > Geometry.width / 6 * 2
				&& Gdx.input.getX() < Geometry.width / 6 * 2 + twoTexture.getWidth())) {
			batch.draw(twoActiveTexture, Geometry.width / 6 * 2, Geometry.height - twoY, twoTexture.getWidth(),
					twoTexture.getHeight());
			if (Gdx.input.isTouched())
				map.playerSpeed = 9;
		} else {
			batch.draw(twoTexture, Geometry.width / 6 * 2, Geometry.height - twoY, twoTexture.getWidth(),
					twoTexture.getHeight());
		}

		if (map.playerSpeed == 12 || (Gdx.input.getY() < twoY && Gdx.input.getY() > twoY - threeTexture.getHeight()
				&& Gdx.input.getX() > Geometry.width / 6 * 3
				&& Gdx.input.getX() < Geometry.width / 6 * 3 + threeTexture.getWidth())) {
			batch.draw(threeActiveTexture, Geometry.width / 6 * 3, Geometry.height - twoY, threeTexture.getWidth(),
					threeTexture.getHeight());
			if (Gdx.input.isTouched())
				map.playerSpeed = 12;
		} else {
			batch.draw(threeTexture, Geometry.width / 6 * 3, Geometry.height - twoY, threeTexture.getWidth(),
					threeTexture.getHeight());
		}

		if (map.playerSpeed == 15 || (Gdx.input.getY() < twoY && Gdx.input.getY() > twoY - fourTexture.getHeight()
				&& Gdx.input.getX() > Geometry.width / 6 * 4
				&& Gdx.input.getX() < Geometry.width / 6 * 4 + fourTexture.getWidth())) {
			batch.draw(fourActiveTexture, Geometry.width / 6 * 4, Geometry.height - twoY, fourTexture.getWidth(),
					fourTexture.getHeight());
			if (Gdx.input.isTouched())
				map.playerSpeed = 15;
		} else {
			batch.draw(fourTexture, Geometry.width / 6 * 4, Geometry.height - twoY, fourTexture.getWidth(),
					fourTexture.getHeight());
		}

		if (map.playerSpeed == 18 || (Gdx.input.getY() < twoY && Gdx.input.getY() > twoY - fiveTexture.getHeight()
				&& Gdx.input.getX() > Geometry.width / 6 * 5
				&& Gdx.input.getX() < Geometry.width / 6 * 5 + fiveTexture.getWidth())) {
			batch.draw(fiveActiveTexture, Geometry.width / 6 * 5, Geometry.height - twoY, fiveTexture.getWidth(),
					fiveTexture.getHeight());
			if (Gdx.input.isTouched())
				map.playerSpeed = 18;
		} else {
			batch.draw(fiveTexture, Geometry.width / 6 * 5, Geometry.height - twoY, fiveTexture.getWidth(),
					fiveTexture.getHeight());
		}

		if (Gdx.input.getY() < returnY && Gdx.input.getY() > returnY - returnTexture.getHeight()
				&& Gdx.input.getX() > Geometry.width / 2 - gameSpeedTexture.getWidth() / 2
				&& Gdx.input.getX() < Geometry.width / 2 + gameSpeedTexture.getWidth() / 2) {
			batch.draw(returnActiveTexture, Geometry.width / 2 - returnTexture.getWidth() / 2,
					Geometry.height - returnY, returnTexture.getWidth(), returnTexture.getHeight());
			if (Gdx.input.isTouched())
				game.changeScreen(1);
		} else {
			batch.draw(returnTexture, Geometry.width / 2 - returnTexture.getWidth() / 2, Geometry.height - returnY,
					returnTexture.getWidth(), returnTexture.getHeight());
		}

		batch.end();
		batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	/**
	 * Czyszczenie pamieci
	 */
	
	@Override
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
