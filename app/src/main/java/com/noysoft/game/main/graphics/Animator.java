package com.noysoft.game.main.graphics;

import android.graphics.Canvas;

import com.noysoft.game.main.GameDisplay;
import com.noysoft.game.main.object.Player;

public class Animator {

    private Sprite[] playerSpriteArray;
    private int idxNotMovingFrame = 0;
    private int idxMovingFrame = 1;
    private int frameBuffer;
    private static final int MAX_FRAME_BUFFER = 5;

    public Animator(Sprite[] playerSpriteArray) {
        this.playerSpriteArray = playerSpriteArray;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay, Player player) {
        switch (player.getPlayerState().getState()) {
            case NOT_MOVING:
                drawFrame(canvas, gameDisplay, player, playerSpriteArray[idxNotMovingFrame]);
                break;
            case STARTED_MOVING:
                frameBuffer = MAX_FRAME_BUFFER;
                drawFrame(canvas, gameDisplay, player, playerSpriteArray[idxMovingFrame]);
                break;
            case IS_MOVING:
                frameBuffer--;
                if(frameBuffer == 0) {
                    frameBuffer = MAX_FRAME_BUFFER;
                    toggleIdxMovingFrame();
                }
                drawFrame(canvas, gameDisplay, player, playerSpriteArray[idxMovingFrame]);
                break;
            default:
                break;
        }
    }

    private void toggleIdxMovingFrame() {
        if(idxMovingFrame == 1) {
            idxMovingFrame = 2;
        } else {
            idxMovingFrame = 1;
        }
    }

    public void drawFrame(Canvas canvas, GameDisplay gameDisplay, Player player, Sprite sprite) {
        sprite.drawPlayer(
            canvas,
            (int)gameDisplay.gameToDisplayCoordinateX(player.getPositionX()) - sprite.getWidth()/2,
            (int)gameDisplay.gameToDisplayCoordinateY(player.getPositionY()) - sprite.getHeight()/2
        );
    }
}
