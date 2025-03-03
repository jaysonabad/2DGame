package com.noysoft.game.main.object;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;
import com.noysoft.game.R;
import com.noysoft.game.main.GameDisplay;
import com.noysoft.game.main.GameLoop;
import com.noysoft.game.main.graphics.Animator;
import com.noysoft.game.main.graphics.Sprite;
import com.noysoft.game.main.panel.HealthBar;
import com.noysoft.game.main.panel.Thumbstick;
import com.noysoft.game.main.Utils;

/**
 * Player is an abstract class the extends the Circle class.
 * This class updates the position and velocity of the player.
 */

public class Player extends Circle {
    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    public static final int MAX_HEALTH_POINTS = 10;
    private int healthPoints;

    private Thumbstick thumbstick;
    private HealthBar healthBar;
    private Animator animator;
    private PlayerState playerState;

    public Player(Context context, Thumbstick thumbstick,
                  double positionX, double positionY,
                  double radius, Animator animator) {
        super(
            context,
            ContextCompat.getColor(context, R.color.grey),
            positionX,
            positionY,
            radius
        );
        this.thumbstick = thumbstick;
        this.healthBar = new HealthBar(context,this);
        this.healthPoints = MAX_HEALTH_POINTS;
        this.animator = animator;
        this.playerState = new PlayerState(this);
    }

    public void update() {
        // assign player velocity
        velocityX = thumbstick.getActuatorX()*MAX_SPEED;
        velocityY = thumbstick.getActuatorY()*MAX_SPEED;

        // update player position
        positionX += velocityX;
        positionY += velocityY;

        // update direction
        if(velocityX != 0 || velocityY != 0) {
            // normalize velocity to get direction (unit vector of velocity)
            double distance = Utils.getDistanceBetweenPoints(0, 0, velocityX, velocityY);
            directionX = velocityX / distance;
            directionY = velocityY / distance;
        }

        playerState.update();
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        animator.draw(canvas, gameDisplay, this);
        healthBar.draw(canvas, gameDisplay);
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        if(healthPoints >= 0) {
            this.healthPoints = healthPoints;
        }
    }

    public PlayerState getPlayerState() {
        return playerState;
    }
}
