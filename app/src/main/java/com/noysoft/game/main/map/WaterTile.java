package com.noysoft.game.main.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.noysoft.game.main.graphics.Sprite;
import com.noysoft.game.main.graphics.SpriteSheet;

public class WaterTile extends Tile {
    private Sprite sprite;

    public WaterTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        sprite = spriteSheet.getWaterSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}
