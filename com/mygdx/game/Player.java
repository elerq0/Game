package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

@SuppressWarnings("serial")
public class Player
  extends Rectangle
{
  private Texture texture;
  public float velocityValue = 600;
  public boolean canJump = true;
  public float jumpVelocity;
 
  public Player(Texture texture)
  {
    this.texture = texture;
    width = texture.getWidth();
    height = texture.getHeight();
  }
  
  public void draw(SpriteBatch batch)
  {
    batch.draw(texture, x, y);
  }
  
  public void jump()
  {
    if (canJump) {
      jumpVelocity += velocityValue;
      canJump = false;
    }
  }
  
  public void textureChange(Texture texture)
  {
    this.texture = texture;
    width = texture.getWidth();
    height = texture.getHeight();
  }
}
