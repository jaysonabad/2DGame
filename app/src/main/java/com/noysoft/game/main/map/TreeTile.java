package com.noysoft.game.main.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.noysoft.game.main.graphics.Sprite;
import com.noysoft.game.main.graphics.SpriteSheet;

public class TreeTile extends Tile {

    private final Sprite grassSprite;
    private final Sprite treeSprite;

    public TreeTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        grassSprite = spriteSheet.getGrassSprite();
        treeSprite = spriteSheet.getTreeSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        grassSprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
        treeSprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}
