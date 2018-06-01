package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.BuildingStructure;
import com.mygdx.game.Player;

public class MapGenerator {
    private SpriteBatch batch;
    private Player player;
    private Texture brickTexture;
    private Texture stoneTexture;
    private Texture lavaTexture;
    private Texture portalTexture;
    private Texture waterTexture;
    private Texture iceSpikeBotSmallTexture;
    private Texture iceSpikeBotBigTexture;
    private Texture iceSpikeTopBigTexture;
    private Texture iceBlockTexture;
    private Texture dirtTexture;
    private Texture ironBlockTexture;
    private Texture goldBlockTexture;
    public Array<BuildingStructure> jumpablePlatformArray;
    public Array<BuildingStructure> killingPlatformArray;
    public Array<BuildingStructure> bottomWallArray;
    public Array<BuildingStructure> upperWallArray;
    public Array<BuildingStructure> lavaArray;
    public Array<BuildingStructure> waterArray;
    public Array<BuildingStructure> fillerArray;
    public Array<BuildingStructure> portalArray;
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

    public MapGenerator(SpriteBatch batch, Player player) {
        this.batch = batch;
        this.player = player;
        init();
    }

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

    public void structureCreate() {
        drawStartingPlatform();
        int i = 0;
        while (i < mapRandomizer) {
            int typeIterator = MathUtils.random((int)0, (int)3);
            switch (typeIterator) {
                case 0: {
                    portalUsage(1);
                    mapStandardOne();
                    break;
                }
                case 1: {
                    portalUsage(1);
                    mapStandardTwo();
                    break;
                }
                case 2: {
                    portalUsage(4);
                    mapUnderwaterOne();
                    break;
                }
                case 3: {
                    portalUsage(3);
                    mapChangingOne();
                }
            }
            ++i;
        }
    }

    private void drawStartingPlatform() {
        int i = 0;
        while (i < 15) {
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
            if (nextMapType == 4 && enemyUpperLevel > enemyLevel + player.height * 7 && i % 4 == 0) {
                enemyUpperLevel -= 50;
            }
            floorLine = e.x;
            bottomWallArray.add(e);
            upperWallArray.add(g);
            fillBelow(e.y, e.x, stoneTexture, fillerArray);
            fillAbove(g.y, g.x, stoneTexture, fillerArray);
            ++i;
        }
        enemyLine = floorLine;
    }

    private void drawPortalRoom(int mode) {
        BuildingStructure e = new BuildingStructure(portalTexture, mode);
        e.y = enemyLevel;
        e.x = floorLine;
        portalArray.add(e);
        float k = e.y;
        while (k < enemyUpperLevel) {
            BuildingStructure o = new BuildingStructure(portalTexture, mode);
            k = o.y = k + o.height * 0.98f;
            o.x = e.x;
            portalArray.add(o);
        }
        enemyLine = floorLine;
    }

    private void mapStandardOne() {
        enemyLine = floorLine;
        int numberIterator = MathUtils.random((int)mapLength) + 100;
        int j = 0;
        while (j < numberIterator) {
            int lengthIterator = MathUtils.random((int)1, (int)4);
            if (j % 5 == 0) {
                BuildingStructure e = new BuildingStructure(brickTexture);
                int valueIterator = MathUtils.random((int)-1, (int)1);
                e.y = enemyLevel + (float)(valueIterator * 100);
                if (e.y < bottomWallLevel) {
                    e.y = enemyLevel + 100;
                    valueIterator = 1;
                }
                if (e.y > enemyUpperLevel - player.height - 200) {
                    e.y = enemyLevel - 100;
                    valueIterator = -1;
                }
                if (valueIterator == -1) {
                    enemyLineInc = 145;
                }
                if (valueIterator == 0) {
                    enemyLineInc = 350;
                }
                if (valueIterator == 1) {
                    enemyLineInc = 250;
                }
                enemyLineInc *= (float)(playerSpeed / 6);
                enemyLevel = e.y;
                e.x = enemyLine + enemyLineInc - (float)MathUtils.random((int)50);
                enemyLine = e.x + e.width;
                jumpablePlatformArray.add(e);
                fillBelow(e.y, e.x, brickTexture, killingPlatformArray);
                int k = 1;
                while (k <= lengthIterator) {
                    BuildingStructure o = new BuildingStructure(brickTexture);
                    o.y = e.y;
                    o.x = enemyLine;
                    enemyLine = o.x + o.width;
                    jumpablePlatformArray.add(o);
                    fillBelow(o.y, o.x, brickTexture, killingPlatformArray);
                    ++k;
                }
            }
            ++j;
        }
        float j2 = floorLine;
        while (j2 < enemyLine) {
            BuildingStructure e = new BuildingStructure(lavaTexture);
            BuildingStructure g = new BuildingStructure(brickTexture);
            e.y = bottomWallLevel - e.height;
            e.x = floorLine + e.width;
            g.y = enemyUpperLevel + player.height;
            g.x = e.x;
            floorLine = e.x;
            lavaArray.add(e);
            upperWallArray.add(g);
            j2 = floorLine;
            fillBelow(e.y, e.x, lavaTexture, lavaArray);
            fillAbove(g.y, g.x, brickTexture, fillerArray);
        }
    }

    private void mapStandardTwo() {
        enemyLine = floorLine + 200;
        int numberIterator = MathUtils.random((int)(mapLength / 10)) + 10;
        int j = 0;
        while (j < numberIterator) {
            int k;
            BuildingStructure e;
            int lengthIterator = MathUtils.random((int)1, (int)3);
            boolean valueIterator = MathUtils.randomBoolean();
            if (valueIterator) {
                boolean extraTrap = true;
                k = 0;
                while (k < lengthIterator) {
                    BuildingStructure o;
                    e = new BuildingStructure(iceSpikeTopBigTexture);
                    e.y = enemyLevel + player.height / 1.75f;
                    e.x = enemyLine;
                    enemyLine = e.x + e.width;
                    jumpablePlatformArray.add(e);
                    if (lengthIterator == 1 && extraTrap && (valueIterator = MathUtils.randomBoolean((float)0.2f))) {
                        extraTrap = false;
                        valueIterator = MathUtils.randomBoolean();
                        if (valueIterator) {
                            fillAbove(e.y + e.height / 2, e.x, iceBlockTexture, killingPlatformArray);
                        } else {
                            o = new BuildingStructure(iceSpikeBotSmallTexture);
                            o.y = enemyLevel;
                            o.x = e.x;
                            killingPlatformArray.add(o);
                        }
                    }
                    if (lengthIterator == 2 && extraTrap && (valueIterator = MathUtils.randomBoolean((float)0.4f))) {
                        extraTrap = false;
                        valueIterator = MathUtils.randomBoolean();
                        if (valueIterator) {
                            fillAbove(e.y + e.height / 2, e.x, iceBlockTexture, killingPlatformArray);
                        } else {
                            o = new BuildingStructure(iceSpikeBotSmallTexture);
                            o.y = enemyLevel;
                            o.x = e.x;
                            killingPlatformArray.add(o);
                        }
                    }
                    if (lengthIterator == 3 && extraTrap && (valueIterator = MathUtils.randomBoolean((float)0.6f))) {
                        extraTrap = false;
                        valueIterator = MathUtils.randomBoolean();
                        if (valueIterator) {
                            fillAbove(e.y + e.height / 2, e.x, iceBlockTexture, killingPlatformArray);
                        } else {
                            o = new BuildingStructure(iceSpikeBotSmallTexture);
                            o.y = enemyLevel;
                            o.x = e.x;
                            killingPlatformArray.add(o);
                        }
                    }
                    ++k;
                }
            } else {
                k = 0;
                while (k < lengthIterator) {
                    valueIterator = MathUtils.randomBoolean();
                    e = !valueIterator && lengthIterator < 3 ? new BuildingStructure(iceSpikeBotBigTexture) : new BuildingStructure(iceSpikeBotSmallTexture);
                    e.y = enemyLevel;
                    e.x = enemyLine;
                    enemyLine = e.x + e.width;
                    killingPlatformArray.add(e);
                    ++k;
                }
            }
            enemyLine += (float)(300 * playerSpeed / 6 - MathUtils.random((int)50));
            ++j;
        }
        float j2 = floorLine;
        while (j2 < enemyLine) {
            BuildingStructure e = new BuildingStructure(iceBlockTexture);
            BuildingStructure g = new BuildingStructure(iceBlockTexture);
            e.y = enemyLevel - e.height;
            e.x = floorLine + e.width;
            g.y = enemyUpperLevel + player.height;
            g.x = e.x;
            floorLine = e.x;
            bottomWallArray.add(e);
            upperWallArray.add(g);
            j2 = floorLine;
            fillBelow(e.y, e.x, iceBlockTexture, fillerArray);
            fillAbove(g.y, g.x, iceBlockTexture, fillerArray);
        }
    }

    private void mapChangingOne() {
        BuildingStructure e;
        enemyLine = floorLine + 50;
        int numberIterator = MathUtils.random((int)mapLength) + 5;
        int i = 0;
        while (i < numberIterator) {
            e = new BuildingStructure(goldBlockTexture);
            int lengthIterator = MathUtils.random((int)1, (int)2);
            int valueIterator = MathUtils.random((int)0, (int)1);
            if (valueIterator == 0) {
                e.y = enemyLevel;
            }
            if (valueIterator == 1) {
                e.y = enemyUpperLevel - 20;
            }
            e.x = enemyLine;
            int j = 1;
            while (j <= lengthIterator) {
                BuildingStructure g = new BuildingStructure(goldBlockTexture);
                if (valueIterator == 0) {
                    g.y = enemyLevel + (float)j * g.height;
                }
                if (valueIterator == 1) {
                    g.y = enemyUpperLevel - 20 - (float)j * g.height;
                }
                g.x = e.x;
                killingPlatformArray.add(g);
                ++j;
            }
            if (MathUtils.randomBoolean((float)0.3f)) {
                BuildingStructure n = new BuildingStructure(goldBlockTexture);
                if (valueIterator == 1) {
                    n.y = enemyLevel + e.height * 2;
                }
                if (valueIterator == 0) {
                    n.y = enemyUpperLevel - 20 - e.height * 2;
                }
                n.x = e.x;
                jumpablePlatformArray.add(n);
                int j2 = 1;
                while (j2 <= lengthIterator) {
                    BuildingStructure g = new BuildingStructure(goldBlockTexture);
                    if (valueIterator == 1) {
                        g.y = n.y + (float)j2 * g.height;
                    }
                    if (valueIterator == 0) {
                        g.y = n.y - (float)j2 * g.height;
                    }
                    g.x = e.x;
                    killingPlatformArray.add(g);
                    ++j2;
                }
            }
            enemyLine = e.x + e.width;
            killingPlatformArray.add(e);
            enemyLine += (float)(MathUtils.random((int)300) + 150 + playerSpeed * 20);
            ++i;
        }
        float j = floorLine;
        while (j < enemyLine) {
            e = new BuildingStructure(ironBlockTexture);
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

    private void mapUnderwaterOne() {
        enemyLine = floorLine + 50;
        int lengthIterator = MathUtils.random((int)1, (int)4);
        int numberIterator = MathUtils.random((int)mapLength) + 20;
        int i = 0;
        while (i < numberIterator) {
            BuildingStructure e = new BuildingStructure(waterTexture);
            int valueIterator = MathUtils.random((int)-1, (int)1);
            e.y = enemyLevel + (float)(valueIterator * 50);
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
            int l = 1;
            while (l <= 5) {
                BuildingStructure k = new BuildingStructure(waterTexture);
                k.y = e.y + k.height * (float)l;
                k.x = e.x;
                waterArray.add(k);
                ++l;
            }
            lengthIterator = MathUtils.random((int)(playerSpeed / 2 - 3), (int)(playerSpeed / 2 - 1));
            int j = 0;
            while (j < lengthIterator) {
                BuildingStructure g = new BuildingStructure(waterTexture);
                g.y = enemyLevel;
                g.x = enemyLine;
                enemyLine = g.x + g.width;
                waterArray.add(g);
                int l2 = 1;
                while (l2 <= 5) {
                    BuildingStructure k = new BuildingStructure(waterTexture);
                    k.y = g.y + k.height * (float)l2;
                    k.x = g.x;
                    waterArray.add(k);
                    ++l2;
                }
                fillBelow(g.y, g.x, dirtTexture, killingPlatformArray);
                fillAbove(enemyUpperLevel, g.x, dirtTexture, killingPlatformArray);
                ++j;
            }
            fillBelow(e.y, e.x, dirtTexture, killingPlatformArray);
            fillAbove(enemyUpperLevel, e.x, dirtTexture, killingPlatformArray);
            ++i;
        }
        floorLine = enemyLine - 50;
    }

    public void draw() {
        for (BuildingStructure e : fillerArray) {
            e.draw(batch);
        }
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

    private void portalUsage(int mode) {
        if (lastMapType != mode) {
            drawStartingPlatform();
        }
        nextMapType = mode;
        if (lastMapType != mode) {
            drawPortalRoom(mode);
            drawStartingPlatform();
        }
        lastMapType = mode;
    }

    private void fillBelow(float height, float width, Texture texture, Array<BuildingStructure> array) {
        float k = height;
        while (k > -80) {
            BuildingStructure o = new BuildingStructure(texture);
            k = o.y = k - o.height;
            o.x = width;
            array.add(o);
        }
    }

    private void fillAbove(float height, float width, Texture texture, Array<BuildingStructure> array) {
        float k = height;
        while (k < 650) {
            BuildingStructure o = new BuildingStructure(texture);
            k = o.y = k + o.height;
            o.x = width;
            array.add(o);
        }
    }

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
