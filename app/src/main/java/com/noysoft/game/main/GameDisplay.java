package com.noysoft.game.main;

import android.graphics.Rect;

import com.noysoft.game.main.object.GameObject;

public class GameDisplay {

    public final Rect DISPLAY_RECT;
    private double gameToDisplayCoordinateOffsetX;
    private double gameToDisplayCoordinateOffsetY;
    private double displayCenterX;
    private double displayCenterY;
    private double gameCenterX;
    private double gameCenterY;
    private GameObject centerObject;
    private int widthPixels;
    private int heightPixels;

    public GameDisplay(GameObject centerObject, int widthPixels, int heightPixels) {
        this.centerObject = centerObject;
        this.widthPixels = widthPixels;
        this.heightPixels = heightPixels;
        DISPLAY_RECT = new Rect(0, 0, widthPixels, heightPixels);
        displayCenterX = widthPixels/2.0;
        displayCenterY = heightPixels/2.0;
    }

    public void update() {
        gameCenterX = centerObject.getPositionX();
        gameCenterY = centerObject.getPositionY();
        gameToDisplayCoordinateOffsetX = displayCenterX - gameCenterX;
        gameToDisplayCoordinateOffsetY = displayCenterY - gameCenterY;
    }

    public double gameToDisplayCoordinateX(double positionX) {
        return positionX + gameToDisplayCoordinateOffsetX;
    }

    public double gameToDisplayCoordinateY(double positionY) {
        return positionY + gameToDisplayCoordinateOffsetY;
    }

    public Rect getGameRect() {
        return new Rect(
            (int)(gameCenterX - widthPixels/2),
            (int)(gameCenterY - heightPixels/2),
            (int)(gameCenterX + widthPixels/2),
            (int)(gameCenterY + heightPixels/2)
        );
    }
}
