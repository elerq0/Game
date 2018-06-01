package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class BuildingStructure extends Rectangle {
	private Texture texture;
	public int mode;

	public BuildingStructure(Texture texture) {
		this.texture = texture;
		width = texture.getWidth();
		height = texture.getHeight();
	}

	public BuildingStructure(Texture texture, int mode) {
		this.texture = texture;
		this.mode = mode;
		width = texture.getWidth();
		height = texture.getHeight();
	}

	public void draw(SpriteBatch batch) {
		batch.draw(texture, x, y);
	}
}
