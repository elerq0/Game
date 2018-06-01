package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Hud;
import com.mygdx.game.MapGenerator;
import com.mygdx.game.Physics;
import com.mygdx.game.Player;

public class GameScreen implements Screen {

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Player player;
	private Hud hud;
	private MapGenerator map;
	private Physics physics;

	private int backgroundR = 0;
	private int backgroundG = 0;
	private int backgroundB = 0;

	/**
	 * Konstruktor klasy
	 * @param batch 	umozliwia wyswietlenie tekstury
	 * @param player	odwolanie na obiekt
	 * @param hud		odwolanie na obiekt
	 * @param map		odwolanie na obiekt
	 * @param physics	odwolanie na obiekt
	 * @param camera	odwolanie na obiekt
	 */
	public GameScreen(SpriteBatch batch, Player player, Hud hud, MapGenerator map, Physics physics, OrthographicCamera camera) {
		this.batch = batch;
		this.player = player;
		this.hud = hud;
		this.map = map;
		this.physics = physics;
		this.camera = camera;
	}

	@Override
	public void show() {}

	/**
	 * Wyswietlenie przebiegu rozgrywki
	 * @param delta	czas pomiedzy dwoma kolejnymi klatkami
	 */
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor((float) backgroundR / 255, (float) backgroundG / 255, (float) backgroundB / 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		map.draw();
		player.draw(batch);
		batch.end();
		hud.stage.draw();
		update();
		batch.setProjectionMatrix(camera.combined);
	}

	/**
	 * Konstrola fizyki, sterowania oraz kamery
	 */
	
	private void update() {
		camera.update();
		camera.position.set(player.x + 300 + player.getWidth() / 2, 300, 0);
		physics.input();
		physics.physics();
		hud.updateTime(Gdx.graphics.getDeltaTime());
	}

	/**
	 * Resetowanie ustawien gry do stanu poczatkowego
	 */
	
	@Override
	public void hide() {
		physics.gameReset();
		physics.music.stop();
	}

	@Override
	public void dispose() {}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

}
