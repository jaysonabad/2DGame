package com.noysoft.game.main.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.noysoft.game.R;
import com.noysoft.game.main.GameLoop;

/**
 * Enemy class is a class that extends circles.
 * It functions as an AI that always moves in the direction of the player.
 */

public class Enemy extends Circle {
    private static final double SPEED_PIXELS_PER_SECOND = Player.SPEED_PIXELS_PER_SECOND - 200;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private static final double SPAWNS_PER_MINUTE = 59;
    private static final double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE/60.0;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS/SPAWNS_PER_SECOND;
    private static double updatesUntilNextSpawn = UPDATES_PER_SPAWN ;
    private Player player;

    public Enemy(Context context, Player player, double positionX, double positionY, double radius) {
        super(context, ContextCompat.getColor(context, R.color.teal_200), positionX, positionY, radius);
        this.player = player;
    }

    public Enemy(Context context, Player player) {
        super(
            context,
            ContextCompat.getColor(context, R.color.teal_200),
            Math.random()*1000,
            Math.random()*1000,
            30
        );
        this.player = player;
    }

    public static boolean readyToSpawn() {
        if(updatesUntilNextSpawn <= 0) {
            updatesUntilNextSpawn += UPDATES_PER_SPAWN;
            return true;
        } else {
            updatesUntilNextSpawn--;
            return false;
        }
    }

    @Override
    public void update() {
        double distanceToPlayerX = player.getPositionX() - positionX;
        double distanceToPlayerY = player.getPositionY() - positionY;

        double distanceToPlayer = GameObject.getDistanceBetweenObjects(this, player);
        
        double directionX = distanceToPlayerX/distanceToPlayer;
        double directionY = distanceToPlayerY/distanceToPlayer;

        if(distanceToPlayer > 0) {
            velocityX = directionX*MAX_SPEED;
            velocityY = directionY*MAX_SPEED;
        } else {
            velocityX = 0;
            velocityY = 0;
        }

        positionX += velocityX;
        positionY += velocityY;
    }
}
