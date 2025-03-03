package com.noysoft.game.main.graphics;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.noysoft.game.main.GameDisplay;

public class Sprite {

    private SpriteSheet spriteSheet;
    private Rect rect;

    public Sprite(SpriteSheet spriteSheet, Rect rect) {
        this.spriteSheet = spriteSheet;
        this.rect = rect;
    }

    public void drawPlayer(Canvas canvas, int positionX, int positionY) {
        canvas.drawBitmap(
                spriteSheet.getPlayerBitmap(),
                rect,
                new Rect(
                    positionX-64,
                    positionY-64,
                    positionX+192,
                    positionY+192
                ),
                null
        );
    }

    public void draw(Canvas canvas, int positionX, int positionY) {
        canvas.drawBitmap(
            spriteSheet.getBitmap(),
            rect,
            new Rect(
                positionX,
                positionY,
                positionX+128,
                positionY+128
            ),
            null
        );
    }

    public int getWidth() {
        return rect.width();
    }

    public int getHeight() {
        return rect.height();
    }
}
