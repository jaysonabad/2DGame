package com.noysoft.game.main.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.noysoft.game.R;
import com.noysoft.game.main.GameLoop;

public class Spell extends Circle {

    private static final double SPEED_PIXELS_PER_SECOND = 800.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    public Spell(Context context, Player spellcaster) {
        super(
            context,
            ContextCompat.getColor(context, R.color.orange),
            spellcaster.getPositionX() - 40,
            spellcaster.getPositionY() - 40,
            25
        );
        velocityX = spellcaster.getDirectionX()*MAX_SPEED;
        velocityY = spellcaster.getDirectionY()*MAX_SPEED;
    }

    @Override
    public void update() {
        positionX += velocityX;
        positionY += velocityY;
    }
}
