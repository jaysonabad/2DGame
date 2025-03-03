package com.noysoft.game.main.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.noysoft.game.R;

public class SpriteSheet {

    private static final int SPRITE_WIDTH_PIXELS = 64;
    private static final int SPRITE_HEIGHT_PIXELS = 64;
    private Bitmap bitmap;
    private Bitmap playerBitmap;

    public SpriteSheet(Context context) {

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_sheet, bitmapOptions);
        playerBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.primedirective, bitmapOptions);
    }

    public Sprite[] getPlayerSpriteArray() {
        Sprite[] spriteArray = new Sprite[3];
        spriteArray[0] = new Sprite(this, new Rect(0, 0, 128, 128));
        spriteArray[1] = new Sprite(this, new Rect(128, 0, 256, 128));
        spriteArray[2] = new Sprite(this, new Rect(256, 0, 384, 128));
        return spriteArray;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
    public Bitmap getPlayerBitmap() {
        return playerBitmap;
    }

    public Sprite getWaterSprite() {
        return getSpriteByIndex(1, 0);
    }

    public Sprite getLavaSprite() {
        return getSpriteByIndex(1, 1);
    }

    public Sprite getGroundSprite() {
        return getSpriteByIndex(1, 2);
    }

    public Sprite getGrassSprite() {
        return getSpriteByIndex(1, 3);
    }

    public Sprite getTreeSprite() {
        return getSpriteByIndex(1, 4);
    }

    private Sprite getSpriteByIndex(int row, int column) {
        return new Sprite(this,
                new Rect(
                    column*SPRITE_WIDTH_PIXELS,
                    row*SPRITE_HEIGHT_PIXELS,
                    (column+1)*SPRITE_WIDTH_PIXELS,
                    (row+1)*SPRITE_HEIGHT_PIXELS
                )
        );
    }
}
