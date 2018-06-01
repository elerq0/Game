package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

@SuppressWarnings("serial")
public class BuildingStructure extends Rectangle {

	private Texture texture;
	public int mode;
	
	/**
	 * Konstruktor klasy. Ustawia teksture obiektu, jego szerokosc oraz wysokosc.
	 * @param texture 	obraz obiektu
	 */
	public BuildingStructure(Texture texture) {
		this.texture = texture;
		this.width = texture.getWidth();
		this.height = texture.getHeight();
	}
	
	/**
	 * Konstruktor klasy. Ustawia teksture obiektu, jego szczerokosc, wysokosc oraz parametr mode
	 * @param texture 	obraz obiektu
	 * @param mode		parametr mowiacy o typie mapy, pozwalajacy na zmiane grawitacji w odpowiednim momencie.
	 */
	
	public BuildingStructure(Texture texture, int mode) {
		this.texture = texture;
		this.mode = mode;
		this.width = texture.getWidth();
		this.height = texture.getHeight();
	}
	
	/**
	 * Metoda odpowiedzialna za wyœwietlenie tekstury obiektu
	 * @param batch 	umo¿liwia wyœwietlenie tekstury
	 */
	
	public void draw(SpriteBatch batch) {
		batch.draw(texture, x, y);
	}
}
