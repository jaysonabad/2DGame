package com.noysoft.game.main.map;

import static com.noysoft.game.main.map.MapLayout.NUMBER_OF_COLUMN_TILES;
import static com.noysoft.game.main.map.MapLayout.NUMBER_OF_ROW_TILES;
import static com.noysoft.game.main.map.MapLayout.TILE_HEIGHT_PIXELS;
import static com.noysoft.game.main.map.MapLayout.TILE_WIDTH_PIXELS;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.noysoft.game.main.GameDisplay;
import com.noysoft.game.main.graphics.SpriteSheet;

public class Tilemap {

    private final MapLayout mapLayout;
    private Tile[][] tilemap;
    private SpriteSheet spriteSheet;
    private Bitmap mapBitmap;

    public Tilemap(SpriteSheet spriteSheet) {
        mapLayout = new MapLayout();
        this.spriteSheet = spriteSheet;
        initializeTilemap();
    }

    private void initializeTilemap() {
        int[][] layout = mapLayout.getLayout();
        tilemap = new Tile[NUMBER_OF_ROW_TILES][NUMBER_OF_COLUMN_TILES];
        for(int row = 0; row < NUMBER_OF_ROW_TILES; row++) {
            for(int column = 0; column < NUMBER_OF_COLUMN_TILES; column++) {
                tilemap[row][column] = Tile.getTile(
                        layout[row][column],
                        spriteSheet,
                        getRectByIndex(row, column)
                );
            }
        }

        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        mapBitmap = Bitmap.createBitmap(
        NUMBER_OF_COLUMN_TILES*TILE_WIDTH_PIXELS,
        NUMBER_OF_ROW_TILES*TILE_HEIGHT_PIXELS,
            config
        );

        Canvas mapCanvas = new Canvas(mapBitmap);
        for(int row = 0; row < NUMBER_OF_ROW_TILES; row++) {
            for(int column = 0; column < NUMBER_OF_COLUMN_TILES; column++) {
                tilemap[row][column].draw(mapCanvas);
            }
        }

    }

    private Rect getRectByIndex(int row, int column) {
        return new Rect(
                column*TILE_WIDTH_PIXELS,
                row*TILE_HEIGHT_PIXELS,
                (column+1)*TILE_WIDTH_PIXELS,
                (row+1)*TILE_HEIGHT_PIXELS
        );
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        canvas.drawBitmap(
            mapBitmap,
            gameDisplay.getGameRect(),
            gameDisplay.DISPLAY_RECT,
            null
        );
    }
}
