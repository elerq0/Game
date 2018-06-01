package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class Physics {
       private MapGenerator map;
       private Player player;
       private Hud hud;
       public Music music;
       private Geometry game;
       private Texture cubeTexture, cubeTextureCrouch;

       private float gravity = -20;

       private boolean standardMode = true;
       private boolean invertedMode = false;
       private boolean changingMode = false;
       private boolean underwaterMode = false;
       private boolean isCrouching = false;
       private boolean canInvert = true;
       private boolean gamePaused = false;

       /**
        * Konstruktor klasy
        * @param map   odwolanie na obiekt typu MapGenerator
        * @param player        odwolanie na obiekt typu Player
        * @param hud   odwolanie na obiekt typu Hud
        * @param game  odwolanie na obiekt typu Geometry
        */

       public Physics(MapGenerator map, Player player, Hud hud, Geometry game) {
               this.map = map;
               this.player = player;
               this.hud = hud;
               this.game = game;
               init();
       }

       /**
        * Inicjowanie tekstur
        */

       private void init() {
               cubeTexture = new Texture("Cube.png");
               cubeTextureCrouch = new Texture("CubeCrouch.png");
       }

       /**
        * Metoda ostawiajaca pozycje gracza, czas, muzyke oraz wszystkie flagi do stanu poczatkowego
        */

       private void gameOver() {
               hud.gameOver();
               music.stop();
               music.dispose();
               musicChoose();
               player.canJump = true;
               canInvert = true;
               player.y = 0;
               player.x = 0;
               player.jumpVelocity = 0;
               if (underwaterMode) {
                       gravity = -20;
               }
               if (!standardMode)
                       setStandardGravity();
               standardMode = true;
               invertedMode = false;
               changingMode = false;
               underwaterMode = false;
      }

       /**
        * Wybieranie muzyki w sposob losowy
        */

       public void musicChoose() {
               int valueIterator = MathUtils.random(0, 2);
               switch (valueIterator) {
               case 0:
                       music = Gdx.audio.newMusic(Gdx.files.internal("personaSound.mp3"));
                       break;
               case 1:
                       music = Gdx.audio.newMusic(Gdx.files.internal("nierSound.mp3"));
                       break;
               case 2:
                       music = Gdx.audio.newMusic(Gdx.files.internal("Dragos.mp3"));
                       break;
               }
               music.setVolume((float) 0.05);
               music.play();
       }

       /**
        * Metoda okreslajaca dzialanie fizyki w grze oraz skotki zderzenia sie tekstury gracza z teksturami innych obiektow
        */

       public void physics() {

              player.x += map.playerSpeed;

               if (gamePaused)
                       gamePause();

               if (isCrouching) {
                       map.upperWallLevel = map.upperWallLevelMain + player.height;
                       player.canJump = false;
               }
               if (!isCrouching) {
                       map.upperWallLevel = map.upperWallLevelMain;
               }

               if (!underwaterMode) {
                       player.y += player.jumpVelocity * Gdx.graphics.getDeltaTime();
                       if (player.y >= map.bottomWallLevel && player.y <= map.upperWallLevel) {
                               player.jumpVelocity += gravity;
                       }
               }
               if (underwaterMode) {
                       if (gamePaused)
                               gravity = 0;
                       if (!gamePaused)
                               gravity = -3;
                       if (player.y >= map.bottomWallLevel && player.y <= map.waterLevel - player.height / 2) {
                               player.y -= gravity;
                       }
                       if (player.y >= map.waterLevel - player.height / 2 && player.y <= map.upperWallLevel) {
                               player.y += gravity;
                       }
               }

               for (BuildingStructure e : map.bottomWallArray) {
                       if (player.overlaps(e)) {
                               if (standardMode) {
                                       player.y = e.y + e.height;
                                       player.canJump = true;
                                       canInvert = true;
                                       player.jumpVelocity = 0;
                               } else
                                       gameOver();
                       }
               }

               for (BuildingStructure e : map.upperWallArray) {
                       if (player.overlaps(e)) {
                               if (invertedMode) {
                                       player.y = e.y - player.height;
                                       player.canJump = true;
                                       canInvert = true;
                                       player.jumpVelocity = 0;
                               } else
                                       gameOver();
                       }
               }

               for (BuildingStructure e : map.jumpablePlatformArray) {
                       if (isPlayerOnPlatform(e)) {
                               player.canJump = true;
                               canInvert = true;
                               player.jumpVelocity = 0;
                               if (!invertedMode)
                                       player.y = e.y + e.height;
                               else
                                       player.y = e.y - player.height;
                       }
                       if (player.overlaps(e) && !isPlayerOnPlatform(e) && !isCrouching) {
                               gameOver();
                       }
               }
               for (BuildingStructure e : map.killingPlatformArray) {
                       if (player.overlaps(e))
                               gameOver();
               }
               for (BuildingStructure e : map.lavaArray) {
                       if (player.overlaps(e))
                               gameOver();
               }

               for (BuildingStructure e : map.portalArray) {
                       if (player.overlaps(e)) {
                               if (!isCrouching)
                                       gravity = -20;
                               player.jumpVelocity = 0;
                               if (e.mode == 1) {
                                       if (invertedMode)
                                               setStandardGravity();
                                       standardMode = true;
                                       invertedMode = false;
                                       changingMode = false;
                                       underwaterMode = false;
                               } else if (e.mode == 2) {
                                       if (standardMode)
                                               setInvertedGravity();
                                       standardMode = false;
                                       invertedMode = true;
                                       changingMode = false;
                                       underwaterMode = false;
                               } else if (e.mode == 3) {
                                       standardMode = true;
                                       invertedMode = false;
                                       changingMode = true;
                                       underwaterMode = false;
                               } else if (e.mode == 4) {
                                       if (invertedMode)
                                               setStandardGravity();
                                       standardMode = false;
                                       invertedMode = false;
                                       changingMode = false;
                                       underwaterMode = true;
                                       gravity = -2;
                               }
                       }
               }
       }

       /**
        * Sterowanie
        */

       public void input() {

               if (!gamePaused) {
                       if (standardMode && !changingMode) {
                               if (Gdx.input.isKeyPressed(Keys.W)) {
                                       player.jump();
                               }
                               if (Gdx.input.isKeyJustPressed(Keys.S) && !isCrouching) {
                                       gravity *= 3;
                                       player.textureChange(cubeTextureCrouch);
                                       isCrouching = true;
                               }
                               if (!Gdx.input.isKeyPressed(Keys.S) && isCrouching) {
                                       gravity /= 3;
                                       player.textureChange(cubeTexture);
                                       isCrouching = false;
                               }
                       }

                       if (invertedMode && !changingMode) {
                               if (Gdx.input.isKeyPressed(Keys.S)) {
                                       player.jump();
                               }
                               if (Gdx.input.isKeyJustPressed(Keys.W) && !isCrouching) {
                                      gravity *= 3;
                                       player.textureChange(cubeTextureCrouch);
                                       isCrouching = true;
                               }
                               if (!Gdx.input.isKeyPressed(Keys.W) && isCrouching) {
                                       gravity /= 3;
                                       player.textureChange(cubeTexture);
                                       isCrouching = false;
                              }
                       }
                       if (changingMode) {
                               if (standardMode) {
                                       if (Gdx.input.isKeyPressed(Keys.W) && canInvert) {
                                               setInvertedGravity();
                                               canInvert = false;
                                               invertedMode = true;
                                              standardMode = false;
                                       }
                                       if (Gdx.input.isKeyPressed(Keys.S) && !isCrouching) {
                                               gravity *= 3;
                                               player.textureChange(cubeTextureCrouch);
                                               isCrouching = true;
                                       }
                                       if (!Gdx.input.isKeyPressed(Keys.S) && isCrouching) {
                                               gravity /= 3;
                                               player.textureChange(cubeTexture);
                                               isCrouching = false;
                                       }
                               }
                               if (invertedMode) {
                                       if (Gdx.input.isKeyPressed(Keys.S) && canInvert) {
                                               setStandardGravity();
                                               canInvert = false;
                                               invertedMode = false;
                                               standardMode = true;
                                       }
                                       if (Gdx.input.isKeyPressed(Keys.W) && !isCrouching) {
                                               gravity *= 3;
                                               player.textureChange(cubeTextureCrouch);
                                               isCrouching = true;
                                       }
                                       if (!Gdx.input.isKeyPressed(Keys.W) && isCrouching) {
                                               gravity /= 3;
                                               player.textureChange(cubeTexture);
                                               isCrouching = false;
                                       }
                               }
                       }

                       if (underwaterMode) {
                               if (Gdx.input.isKeyPressed(Keys.W))
                                       player.y += 6;
                               if (Gdx.input.isKeyPressed(Keys.S))
                                       player.y -= 6;
                       }

                       if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
                               // Gdx.app.exit();
                               game.changeScreen(1);
                       }

                       if (Gdx.input.isKeyJustPressed(Keys.R)) {
                               gameReset();
                               map.structureCreate();
                       }
               }

               if (Gdx.input.isKeyJustPressed(Keys.P)) {
                       gamePaused = !gamePaused;
                       hud.gamePaused = !hud.gamePaused;
               }
       }

       /**
        * Resetowanie gry.
        */

       public void gameReset() {
               gameOver();
               map.mapReset();
               hud.timeReset();
               gravity = -20;

               standardMode = true;
               invertedMode = false;
               changingMode = false;
               underwaterMode = false;
               isCrouching = false;
               canInvert = true;
       }

       /**
        * Pauza gry.
        */

       private void gamePause() {
               player.jumpVelocity -= gravity;
               player.x -= map.playerSpeed;
               player.y -= player.jumpVelocity * Gdx.graphics.getDeltaTime();
               if(underwaterMode) player.y += 0.05;
       }

       /**
        * Sprawdzenie czy tekstura gracza zderza sie z tekstura obiektru
        * @param e     badany obiekt
        * @return              wartosc logiczna zalezna od wzajemnego polozenia tekstury gracza i tekstury badanego obiektu
        */

       private boolean isPlayerOnPlatform(BuildingStructure e) {
               if (!invertedMode) {
                       if (!isCrouching)
                               return player.overlaps(e) && (player.y >= e.y + (float) 0.65 * e.height);
                       else
                               return player.overlaps(e) && (player.y >= e.y + (float) 0.1 * e.height);
               } else {
                       if (!isCrouching)
                               return player.overlaps(e) && (player.y <= e.y - (float) 0.35 * e.height);
                       else
                               return player.overlaps(e) && (player.y <= e.y - (float) 0.9 * e.height);
               }
       }

       /**
        * Ustawienie odwroconej grawitacji
        */

       private void setInvertedGravity() {
               player.jump();
               player.canJump = true;
               player.velocityValue = -600;
               gravity = 20;
       }

       /**
        * Ustawienie standardowej grawitacji
        */

       private void setStandardGravity() {
               player.jump();
               player.canJump = true;
               player.velocityValue = 600;
               gravity = -20;
       }

       /**
        * Czyszczenie pamieci
        */

       public void dispose() {
               music.dispose();
               cubeTexture.dispose();
               cubeTextureCrouch.dispose();
       }
}
