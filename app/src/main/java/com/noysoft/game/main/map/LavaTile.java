package com.noysoft.game.main.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.noysoft.game.main.graphics.Sprite;
import com.noysoft.game.main.graphics.SpriteSheet;

public class LavaTile extends Tile {

    private Sprite sprite;

    public LavaTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        sprite = spriteSheet.getLavaSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}
