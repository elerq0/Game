package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class MapGenerator {
	private SpriteBatch batch;
	private Player player;

	private Texture brickTexture, stoneTexture, lavaTexture, portalTexture, waterTexture, iceSpikeBotSmallTexture,
			iceSpikeBotBigTexture, iceSpikeTopBigTexture, iceBlockTexture, dirtTexture, ironBlockTexture,
			goldBlockTexture;
	public Array<BuildingStructure> jumpablePlatformArray, killingPlatformArray, bottomWallArray, upperWallArray,
			lavaArray, waterArray, fillerArray, portalArray;

	public int playerSpeed = 12;
	private int mapLength = 20;
	private int mapRandomizer = 20;
	private int lastMapType = 0;
	private int nextMapType = 0;
	

	
	private float enemyLine = 300;
	private float enemyLineInc = 0;
	private float enemyLevel = 0;
	private float enemyUpperLevel = 550;
	public float waterLevel = 500;
	private float floorLine = -250;
	public float bottomWallLevel = 0;
	public float upperWallLevel = 570;
	public float upperWallLevelMain = 570;
	
	/**
	 * Konstruktor klasy. Przekazuje odwo³ania do obiektów utworzonych w klasie g³ównej gry.
	 * @param batch 	umo¿liwia wyswietlenie tekstury
	 * @param player	odwolanie do obiektu gracza
	 */

	public MapGenerator(SpriteBatch batch, Player player) {
		this.batch = batch;
		this.player = player;
		init();
	}
	
	/**
	 * Metoda inicjujaca tekstury oraz listy obiektow.
	 */

	public void init() {
		stoneTexture = new Texture("stone.png");
		brickTexture = new Texture("brick.png");
		dirtTexture = new Texture("dirt.png");
		ironBlockTexture = new Texture("ironBlock.png");
		goldBlockTexture = new Texture("goldBlock.png");
		lavaTexture = new Texture("lava.png");
		waterTexture = new Texture("water.png");
		iceSpikeBotSmallTexture = new Texture("iceSpikeBotSmall.png");
		iceSpikeBotBigTexture = new Texture("iceSpikeBotBig.png");
		iceSpikeTopBigTexture = new Texture("iceSpikeTopBig.png");
		iceBlockTexture = new Texture("iceBlock.png");
		portalTexture = new Texture("void.png");

		jumpablePlatformArray = new Array<BuildingStructure>();
		killingPlatformArray = new Array<BuildingStructure>();
		bottomWallArray = new Array<BuildingStructure>();
		upperWallArray = new Array<BuildingStructure>();
		lavaArray = new Array<BuildingStructure>();
		waterArray = new Array<BuildingStructure>();
		fillerArray = new Array<BuildingStructure>();
		portalArray = new Array<BuildingStructure>();

	}

	/**
	 * Fukcja tworzaca mape w sposob losowy.
	 */
	
	public void structureCreate() {
		drawStartingPlatform();
		for (int i = 0; i < mapRandomizer; i++) {
			int typeIterator = MathUtils.random(0, 3);
			switch (typeIterator) {
			case 0:
				portalUsage(1);
				mapStandardOne();
				break;
			case 1:
				portalUsage(1);
				mapStandardTwo();
				break;
			case 2:
				portalUsage(4);
				mapUnderwaterOne();
				break;
			case 3:
				portalUsage(3);
				mapChangingOne();
				break;
			}
		}
	}
	
	/**
	 * Metoda tworzaca jeden z typow mapy - platforme startowa.
	 */
	
	private void drawStartingPlatform() {
		for (int i = 0; i < 15; i++) {
			BuildingStructure e = new BuildingStructure(stoneTexture);
			BuildingStructure g = new BuildingStructure(stoneTexture);
			e.y = enemyLevel - e.height;
			e.x = floorLine + e.width;
			g.y = enemyUpperLevel + player.height;
			g.x = e.x;
			if (enemyLevel > 200 && i % 4 == 0) {
				enemyLevel -= 50;
			}
			if (nextMapType == 1 && enemyUpperLevel < 450 && i % 4 == 0) {
				enemyUpperLevel += 50;
			}
			if (nextMapType == 3 && enemyUpperLevel < 550 && i % 4 == 0) {
				enemyUpperLevel += 50;
			}
			if (nextMapType == 3 && enemyLevel > 100 && i % 4 == 0) {
				enemyLevel -= 50;
			}
			if (nextMapType == 4 && enemyUpperLevel > (enemyLevel + player.height * 7) && i % 4 == 0) {
				enemyUpperLevel -= 50;
			}
			floorLine = e.x;
			bottomWallArray.add(e);
			upperWallArray.add(g);
			fillBelow(e.y, e.x, stoneTexture, fillerArray);
			fillAbove(g.y, g.x, stoneTexture, fillerArray);
		}
		enemyLine = floorLine;
	}
	
	/**
	 * Metoda tworzaca jeden z typow mapy - platforme umozliwiajaca zmiane grawitacji.
	 * @param mode
	 */

	private void drawPortalRoom(int mode) {
		BuildingStructure e = new BuildingStructure(portalTexture, mode);
		e.y = enemyLevel;
		e.x = floorLine;
		portalArray.add(e);
		for (float k = e.y; k < enemyUpperLevel;) {
			BuildingStructure o = new BuildingStructure(portalTexture, mode);
			o.y = k + o.height * (float) 0.98;
			k = o.y;
			o.x = e.x;
			portalArray.add(o);
		}
		enemyLine = floorLine;
	}
	
	/**
	 * Metoda tworzaca jeden z typow mapy - ceglane wieze rozdzielone lawa
	 */

	private void mapStandardOne() {
		enemyLine = floorLine;
		int numberIterator = MathUtils.random(mapLength) + 100;
		for (int j = 0; j < numberIterator; j++) {
			int lengthIterator = MathUtils.random(1, 4);
			if (j % 5 == 0) {
				BuildingStructure e = new BuildingStructure(brickTexture);
				int valueIterator = MathUtils.random(-1, 1);
				e.y = enemyLevel + valueIterator * 100;
				if (e.y < bottomWallLevel) {
					e.y = enemyLevel + 100;
					valueIterator = 1;
				}
				if (e.y > enemyUpperLevel - player.height - 200) {
					e.y = enemyLevel - 100;
					valueIterator = -1;
				}
				if (valueIterator == -1)
					enemyLineInc = 145;
				if (valueIterator == 0)
					enemyLineInc = 350;
				if (valueIterator == 1)
					enemyLineInc = 250;
				enemyLineInc *= playerSpeed / 6;
				enemyLevel = e.y;
				e.x = enemyLine + enemyLineInc - MathUtils.random(50);
				enemyLine = e.x + e.width;
				jumpablePlatformArray.add(e);
				fillBelow(e.y, e.x, brickTexture, killingPlatformArray);
				for (int k = 1; k <= lengthIterator; k++) {
					BuildingStructure o = new BuildingStructure(brickTexture);
					o.y = e.y;
					o.x = enemyLine;
					enemyLine = o.x + o.width;
					jumpablePlatformArray.add(o);
					fillBelow(o.y, o.x, brickTexture, killingPlatformArray);
				}
			}
		}
		for (float j = floorLine; j < enemyLine;) {
			BuildingStructure e = new BuildingStructure(lavaTexture);
			BuildingStructure g = new BuildingStructure(brickTexture);
			e.y = bottomWallLevel - e.height;
			e.x = floorLine + e.width;
			g.y = enemyUpperLevel + player.height;
			g.x = e.x;
			floorLine = e.x;
			lavaArray.add(e);
			upperWallArray.add(g);
			j = floorLine;
			fillBelow(e.y, e.x, lavaTexture, lavaArray);
			fillAbove(g.y, g.x, brickTexture, fillerArray);
		}
	}
	
	/**
	 * Metoda tworzaca jeden z typow mapy - plansza z kolcami do ominiecia
	 */

	private void mapStandardTwo() {
		enemyLine = floorLine + 200;
		boolean extraTrap;
		int numberIterator = MathUtils.random(mapLength / 10) + 10;
		for (int j = 0; j < numberIterator; j++) {
			int lengthIterator = MathUtils.random(1, 3);
			boolean valueIterator = MathUtils.randomBoolean();
			if (valueIterator) {
				extraTrap = true;
				for (int k = 0; k < lengthIterator; k++) {
					BuildingStructure e = new BuildingStructure(iceSpikeTopBigTexture);
					e.y = enemyLevel + player.height / (float) 1.75;
					e.x = enemyLine;
					enemyLine = e.x + e.width;
					jumpablePlatformArray.add(e);
					if (lengthIterator == 1 && extraTrap) {
						valueIterator = MathUtils.randomBoolean((float) 0.2);
						if (valueIterator) {
							extraTrap = false;
							valueIterator = MathUtils.randomBoolean();
							if (valueIterator)
								fillAbove(e.y + e.height / 2, e.x, iceBlockTexture, killingPlatformArray);
							else {
								BuildingStructure o = new BuildingStructure(iceSpikeBotSmallTexture);
								o.y = enemyLevel;
								o.x = e.x;
								killingPlatformArray.add(o);
							}
						}
					}
					if (lengthIterator == 2 && extraTrap) {
						valueIterator = MathUtils.randomBoolean((float) 0.4);
						if (valueIterator) {
							extraTrap = false;
							valueIterator = MathUtils.randomBoolean();
							if (valueIterator)
								fillAbove(e.y + e.height / 2, e.x, iceBlockTexture, killingPlatformArray);
							else {
								BuildingStructure o = new BuildingStructure(iceSpikeBotSmallTexture);
								o.y = enemyLevel;
								o.x = e.x;
								killingPlatformArray.add(o);
							}
						}
					}
					if (lengthIterator == 3 && extraTrap) {
						valueIterator = MathUtils.randomBoolean((float) 0.6);
						if (valueIterator) {
							extraTrap = false;
							valueIterator = MathUtils.randomBoolean();
							if (valueIterator)
								fillAbove(e.y + e.height / 2, e.x, iceBlockTexture, killingPlatformArray);
							else {
								BuildingStructure o = new BuildingStructure(iceSpikeBotSmallTexture);
								o.y = enemyLevel;
								o.x = e.x;
								killingPlatformArray.add(o);
							}
						}
					}
				}
			} else
				for (int k = 0; k < lengthIterator; k++) {
					BuildingStructure e;
					valueIterator = MathUtils.randomBoolean();
					if (!valueIterator && lengthIterator < 3)
						e = new BuildingStructure(iceSpikeBotBigTexture);
					else
						e = new BuildingStructure(iceSpikeBotSmallTexture);
					e.y = enemyLevel;
					e.x = enemyLine;
					enemyLine = e.x + e.width;
					killingPlatformArray.add(e);
				}
			enemyLine += 300 * playerSpeed / 6 - MathUtils.random(50);
		}
		for (float j = floorLine; j < enemyLine;) {
			BuildingStructure e = new BuildingStructure(iceBlockTexture);
			BuildingStructure g = new BuildingStructure(iceBlockTexture);
			e.y = enemyLevel - e.height;
			e.x = floorLine + e.width;
			g.y = enemyUpperLevel + player.height;
			g.x = e.x;
			floorLine = e.x;
			bottomWallArray.add(e);
			upperWallArray.add(g);
			j = floorLine;
			fillBelow(e.y, e.x, iceBlockTexture, fillerArray);
			fillAbove(g.y, g.x, iceBlockTexture, fillerArray);
		}
	}
	
	/*
	 * Metoda tworzaca jeden z typow mapy - plansze z wystajacymi z gory badz z dolu wiezami
	 */

	private void mapChangingOne() {
		enemyLine = floorLine + 50;
		int numberIterator = MathUtils.random(mapLength) + 5;
		for (int i = 0; i < numberIterator; i++) {
			BuildingStructure e = new BuildingStructure(goldBlockTexture);
			int lengthIterator = MathUtils.random(1, 2);
			int valueIterator = MathUtils.random(0, 1);
			if (valueIterator == 0)
				e.y = enemyLevel;
			if (valueIterator == 1)
				e.y = enemyUpperLevel - 20;
			e.x = enemyLine;
			for (int j = 1; j <= lengthIterator; j++) {
				BuildingStructure g = new BuildingStructure(goldBlockTexture);
				if (valueIterator == 0)
					g.y = enemyLevel + j * g.height;
				if (valueIterator == 1)
					g.y = enemyUpperLevel - 20 - j * g.height;
				g.x = e.x;
				killingPlatformArray.add(g);
			}
			if (MathUtils.randomBoolean((float) 0.3)) {
				BuildingStructure n = new BuildingStructure(goldBlockTexture);
				if (valueIterator == 1)
					n.y = enemyLevel + e.height*2;
				if (valueIterator == 0)
					n.y = enemyUpperLevel - 20 - e.height*2;
				n.x = e.x;
				jumpablePlatformArray.add(n);
				for (int j = 1; j <= lengthIterator; j++) {
					BuildingStructure g = new BuildingStructure(goldBlockTexture);
					if (valueIterator == 1)
						g.y = n.y + j * g.height;
					if (valueIterator == 0)
						g.y = n.y - j * g.height;
					g.x = e.x;
					killingPlatformArray.add(g);
				}
			}
			enemyLine = e.x + e.width;
			killingPlatformArray.add(e);

			enemyLine += MathUtils.random(300) + 150 + playerSpeed * 20;
		}
		for (float j = floorLine; j < enemyLine;) {
			BuildingStructure e = new BuildingStructure(ironBlockTexture);
			BuildingStructure g = new BuildingStructure(ironBlockTexture);
			e.y = enemyLevel - e.height;
			e.x = floorLine + e.width;
			g.y = enemyUpperLevel + player.height;
			g.x = e.x;
			floorLine = e.x;
			bottomWallArray.add(e);
			upperWallArray.add(g);
			j = floorLine;
			fillBelow(e.y, e.x, ironBlockTexture, fillerArray);
			fillAbove(g.y, g.x, ironBlockTexture, fillerArray);
		}
	}
	/**
	 * Metoda tworzaca jeden z typow mapy - podziemny tunel
	 */

	private void mapUnderwaterOne() {
		enemyLine = floorLine + 50;
		int valueIterator;
		int lengthIterator = MathUtils.random(1, 4);
		int numberIterator = MathUtils.random(mapLength) + 20;
		for (int i = 0; i < numberIterator; i++) {
			BuildingStructure e = new BuildingStructure(waterTexture);
			valueIterator = MathUtils.random(-1, 1);
			e.y = enemyLevel + valueIterator * 50;
			if (e.y < bottomWallLevel) {
				e.y = enemyLevel + 50;
				valueIterator = 1;
			}
			if (e.y > upperWallLevel - player.height - 200) {
				e.y = enemyLevel - 50;
				valueIterator = -1;
			}
			e.x = enemyLine;
			enemyLevel = e.y;
			enemyLine = e.x + e.width;
			enemyUpperLevel = e.y + player.height * 5;
			waterArray.add(e);
			for (int l = 1; l <= 5; l++) {
				BuildingStructure k = new BuildingStructure(waterTexture);
				k.y = e.y + k.height * l;
				k.x = e.x;
				waterArray.add(k);
			}
			lengthIterator = MathUtils.random(playerSpeed / 2 - 3, playerSpeed / 2 - 1);
			for (int j = 0; j < lengthIterator; j++) {
				BuildingStructure g = new BuildingStructure(waterTexture);
				g.y = enemyLevel;
				g.x = enemyLine;
				enemyLine = g.x + g.width;
				waterArray.add(g);
				for (int l = 1; l <= 5; l++) {
					BuildingStructure k = new BuildingStructure(waterTexture);
					k.y = g.y + k.height * l;
					k.x = g.x;
					waterArray.add(k);
				}
				fillBelow(g.y, g.x, dirtTexture, killingPlatformArray);
				fillAbove(enemyUpperLevel, g.x, dirtTexture, killingPlatformArray);
			}
			fillBelow(e.y, e.x, dirtTexture, killingPlatformArray);
			fillAbove(enemyUpperLevel, e.x, dirtTexture, killingPlatformArray);
		}
		floorLine = enemyLine - 50;
	}
	
	/**
	 *  Wyswietlenie tekstur wszystkich obiektow.
	 */

	public void draw() {

		// for (Portal e : portalArray) {
		// e.draw(batch);
		// }
		for (BuildingStructure e : fillerArray) {
			e.draw(batch);
		}
		// for (BuildingStructure e : waterArray) {
		// e.draw(batch);
		// }
		for (BuildingStructure e : lavaArray) {
			e.draw(batch);
		}
		for (BuildingStructure e : bottomWallArray) {
			e.draw(batch);
		}
		for (BuildingStructure e : upperWallArray) {
			e.draw(batch);
		}
		for (BuildingStructure e : killingPlatformArray) {
			e.draw(batch);
		}
		for (BuildingStructure e : jumpablePlatformArray) {
			e.draw(batch);
		}

	}
	
	/**
	 *  Metoda decydujaca, czy potrzeba tworzyc platforme zmieniajaca grawitacje przed nastepna mapa.
	 * @param mode 	Okreslenie typu mapy
	 */

	private void portalUsage(int mode) {
		if (lastMapType != mode)
			drawStartingPlatform();
		nextMapType = mode;
		if (lastMapType != mode) {
			drawPortalRoom(mode);
			drawStartingPlatform();
		}
		lastMapType = mode;
	}
	
	/**
	 * Wypelnienie obszaru ponizej obiektem o wybranej teksturze
	 * @param height	wartosc wspolrzednej y 
	 * @param width		wartosc wspolrzednej x
	 * @param texture	obraz obiektu
	 * @param array		lista obiektow
	 */

	private void fillBelow(float height, float width, Texture texture, Array<BuildingStructure> array) {
		for (float k = height; k > -80;) {
			BuildingStructure o = new BuildingStructure(texture);
			o.y = k - o.height;
			k = o.y;
			o.x = width;
			array.add(o);
		}
	}
	
	/**
	 * Wypelnienie obszaru powyzej obiektem o wybranej teksturze
	 * @param height	wartosc wspolrzednej y 
	 * @param width		wartosc wspolrzednej x
	 * @param texture	obraz obiektu
	 * @param array		lista obiektow
	 */

	private void fillAbove(float height, float width, Texture texture, Array<BuildingStructure> array) {
		for (float k = height; k < 650;) {
			BuildingStructure o = new BuildingStructure(texture);
			o.y = k + o.height;
			k = o.y;
			o.x = width;
			array.add(o);
		}
	}
	
	/**
	 * Resetowanie wszystkich zmiennych oraz list
	 */
	
	public void mapReset() {
		jumpablePlatformArray.clear();
		killingPlatformArray.clear();
		bottomWallArray.clear();
		upperWallArray.clear();
		lavaArray.clear();
		waterArray.clear();
		fillerArray.clear();
		portalArray.clear();
		
		lastMapType = 0;
		nextMapType = 0;
		enemyLine = 300;
		enemyLevel = 0;
		enemyUpperLevel = 550;
		floorLine = -250;
	}

	/**
	 * Czyszczenie pamieci
	 */
	
	public void dispose() {
		stoneTexture.dispose();
		brickTexture.dispose();
		dirtTexture.dispose();
		ironBlockTexture.dispose();
		goldBlockTexture.dispose();
		lavaTexture.dispose();
		waterTexture.dispose();
		iceSpikeBotSmallTexture.dispose();
		iceSpikeBotBigTexture.dispose();
		iceSpikeTopBigTexture.dispose();
		iceBlockTexture.dispose();
		portalTexture.dispose();
	}
}
