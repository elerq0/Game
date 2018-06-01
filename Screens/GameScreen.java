package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Hud;
import com.mygdx.game.MapGenerator;
import com.mygdx.game.Physics;
import com.mygdx.game.Player;

public class GameScreen implements com.badlogic.gdx.Screen
{
  private OrthographicCamera camera;
  private SpriteBatch batch;
  private Player player;
  private Hud hud;
  private MapGenerator map;
  private Physics physics;
  private int backgroundR = 0;
  private int backgroundG = 0;
  private int backgroundB = 0;
  
  public GameScreen(SpriteBatch batch, Player player, Hud hud, MapGenerator map, Physics physics, OrthographicCamera camera)
  {
    this.batch = batch;
    this.player = player;
    this.hud = hud;
    this.map = map;
    this.physics = physics;
    this.camera = camera;
  }
  
  public void show() {}
  
  public void render(float delta)
  {
    Gdx.gl.glClearColor(backgroundR / 255, backgroundG / 255, backgroundB / 255, 1);
    Gdx.gl.glClear(16384);
    batch.begin();
    map.draw();
    player.draw(batch);
    batch.end();
    hud.stage.draw();
    update();
    batch.setProjectionMatrix(camera.combined);
  }
  

  private void update()
  {
    camera.update();
    camera.position.set(player.x + 300 + player.getWidth() / 2, 300, 0);
    physics.input();
    physics.physics();
    hud.updateTime(Gdx.graphics.getDeltaTime());
  }
  

  public void hide()
  {
    physics.gameReset();
    physics.music.stop();
  }
  
  public void dispose() {}
  
  public void resize(int width, int height) {}
  
  public void pause() {}
  
  public void resume() {}
}
