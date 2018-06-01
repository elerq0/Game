package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud {
	public Stage stage;
	public Viewport viewport;

	private int worldTimer;
	private float timeCounter;
	private int lastTime;
	private int bestTime;
	public boolean gamePaused = false;

	Label timeLabel;
	Label lastTimeLabel;
	Label bestTimeLabel;
	Label timeFontLabel;
	Label lastTimeFontLabel;
	Label bestTimeFontLabel;

	/**
	 * Konstruktor klasy
	 * @param batch	umozliwia wyswietlenie tekstury
	 */
	
	public Hud(SpriteBatch batch) {
		worldTimer = 0;
		timeCounter = 0;
		lastTime = 0;
		bestTime = 0;

		viewport = new FitViewport(600, 600, new OrthographicCamera());
		stage = new Stage(viewport, batch);

		Table table = new Table();
		table.left().top();
		table.setFillParent(true);

		timeLabel = new Label(String.format("%04d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		lastTimeLabel = new Label(String.format("%04d", lastTime), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		bestTimeLabel = new Label(String.format("%04d", lastTime), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		timeFontLabel = new Label("Time: ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		lastTimeFontLabel = new Label("Last Time:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		bestTimeFontLabel = new Label("Best Time:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

		table.add(bestTimeFontLabel).pad(10, 10, 0, 0);
		table.add(bestTimeLabel).pad(10, 10, 0, 0);
		table.row();
		table.add(lastTimeFontLabel).pad(10, 10, 0, 0);
		table.add(lastTimeLabel).pad(10, 10, 0, 0);
		table.row();
		table.add(timeFontLabel).pad(10, 10, 0, 0);
		table.add(timeLabel).pad(10, 10, 0, 0);
		stage.addActor(table);
	}

	/**
	 * Aktualizacja czasu
	 * @param deltaTime	czas pomiedzy dwoma kolejnymi klatkami
	 */
	
	public void updateTime(float deltaTime) {
		if (!gamePaused) {
			timeCounter += deltaTime;
			if (timeCounter > 1) {
				worldTimer++;
				timeLabel.setText(String.format("%04d", worldTimer));
				timeCounter = 0;
			}
		}
	}
	
	/**
	 * Zresetowanie czasu biezacego, ustawienie czasu ostatniego oraz najlepszego
	 */

	public void gameOver() {
		lastTime = worldTimer;
		lastTimeLabel.setText(String.format("%04d", lastTime));
		if (lastTime > bestTime) {
			bestTime = lastTime;
			bestTimeLabel.setText(String.format("%04d", lastTime));
		}
		worldTimer = 0;
	}
	
	/**
	 * Resetowanie czasow
	 */

	public void timeReset() {
		lastTime = 0;
		bestTime = 0;
		lastTimeLabel.setText(String.format("%04d", lastTime));
		bestTimeLabel.setText(String.format("%04d", lastTime));
	}
}
