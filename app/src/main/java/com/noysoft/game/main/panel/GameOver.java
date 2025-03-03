package com.noysoft.game.main.panel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.noysoft.game.R;

public class GameOver {

    private Context context;
    private int screenWidth;
    private int screenHeight;

    public GameOver(Context context, int screenWidth, int screenHeight) {
        this.context = context;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void draw(Canvas canvas) {
        String text = "Game Over";
        float x = (float)(screenWidth/2) - 400;
        float y = (float)(screenHeight/2);
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);
        paint.setColor(color);
        int textSize = 150;
        paint.setTextSize(textSize);
        canvas.drawText(text, x, y, paint);
    }
}
