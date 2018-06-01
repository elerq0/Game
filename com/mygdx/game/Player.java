package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

@SuppressWarnings("serial")
public class Player extends Rectangle{

	private Texture texture;
	public float velocityValue = 600;
	
	public boolean canJump = true;
	public float jumpVelocity;
	
	/**
	 * Konstruktor klasy Player. Ustawia teksture obiektu, jego szerokosc oraz wysokosc.
	 * @param texture 	obraz obiektu.
	 */
	
	public Player(Texture texture) {
		this.texture = texture;
		this.width = texture.getWidth();
		this.height = texture.getHeight();
	}
	
	/**
	 * Metoda odpowiedzialna za wyświetlenie tekstury obiektu
	 * @param batch 	umożliwia wyświetlenie tekstury
	 */
	
	public void draw(SpriteBatch batch) {
		batch.draw(texture, x, y);
	}
	
	/**
	 * Metoda umożliwiająca wykonanie skoku przez gracza.
	 */
	
	public void jump() {
		if(canJump) {
			jumpVelocity +=velocityValue;
			canJump = false;
		}
	}
	
	/**
	 * Pozwala na zmiane tekstury.
	 * @param texture	 obraz obiektu.
	 */
	
	public void textureChange(Texture texture) {
		this.texture = texture;
		this.width = texture.getWidth();
		this.height = texture.getHeight();
	}
}
