package com.noysoft.game.main.panel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.noysoft.game.R;
import com.noysoft.game.main.GameDisplay;
import com.noysoft.game.main.object.Player;

/**
 * HealthBar class draw health status on top of the player
 */

public class HealthBar {

    private Player player;
    private int width;
    private int height;
    private int margin;
    private Paint borderPaint;
    private Paint healthPaint;

    public HealthBar(Context context, Player player) {
        this.player = player;
        this.width = 128;
        this.height = 20;
        this.margin = 5;
        this.borderPaint = new Paint();
        int borderColor = ContextCompat.getColor(context, R.color.grey);
        this.borderPaint.setColor(borderColor);
        this.healthPaint = new Paint();
        int healthColor = ContextCompat.getColor(context, R.color.green);
        this.healthPaint.setColor(healthColor);
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        float x = (float) player.getPositionX();
        float y = (float) player.getPositionY();
        float distanceToPlayer = 128;
        float healthPercentage = (float) player.getHealthPoints()/player.MAX_HEALTH_POINTS;
        // draw border
        float borderLeft, borderTop, borderRight, borderBottom;
        borderLeft = x - (float) width/2;
        borderRight = x + (float) width/2;
        borderBottom = y - distanceToPlayer;
        borderTop = borderBottom - height;
        canvas.drawRect(
            (float)gameDisplay.gameToDisplayCoordinateX(borderLeft),
            (float)gameDisplay.gameToDisplayCoordinateY(borderTop),
            (float)gameDisplay.gameToDisplayCoordinateX(borderRight) ,
            (float)gameDisplay.gameToDisplayCoordinateY(borderBottom),
            borderPaint
        );
        // draw health
        float healthLeft, healthTop, healthRight, healthBottom, healthWidth, healthHeight;
        healthWidth = width - 2*margin;
        healthHeight = height - 2*margin;
        healthLeft = borderLeft + margin;
        healthRight =  healthLeft + healthWidth*healthPercentage;
        healthBottom = borderBottom - margin;
        healthTop = healthBottom - healthHeight;
        canvas.drawRect(
            (float)gameDisplay.gameToDisplayCoordinateX(healthLeft),
            (float)gameDisplay.gameToDisplayCoordinateY(healthTop),
            (float)gameDisplay.gameToDisplayCoordinateX(healthRight),
            (float)gameDisplay.gameToDisplayCoordinateY(healthBottom),
            healthPaint
        );
    }
}
