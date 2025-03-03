package com.noysoft.game.main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.noysoft.game.R;
import com.noysoft.game.main.graphics.Animator;
import com.noysoft.game.main.graphics.SpriteSheet;
import com.noysoft.game.main.map.Tilemap;
import com.noysoft.game.main.object.Enemy;
import com.noysoft.game.main.panel.GameOver;
import com.noysoft.game.main.object.Player;
import com.noysoft.game.main.object.Circle;
import com.noysoft.game.main.object.Spell;
import com.noysoft.game.main.panel.Performance;
import com.noysoft.game.main.panel.Thumbstick;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private Tilemap tilemap;
    private GameLoop gameLoop;
    private Player player;
    private Thumbstick thumbstick;
    private int width;
    private int height;
    private int thumbstickPointerId = 0;
    private int numberOfSpellsToCast = 0;

    private List<Enemy> enemyList = new ArrayList<Enemy>();
    private List<Spell> spellList = new ArrayList<Spell>();
    private GameOver gameOver;
    private Performance performance;
    private GameDisplay gameDisplay;

    public Game(Context context) {
        super(context);
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        // initialize game metrics
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        width = metrics.widthPixels + 228;
        height = metrics.heightPixels;
        // initialize game loop
        gameLoop = new GameLoop(this, surfaceHolder);
        // initialize game panels
        performance = new Performance(context, gameLoop);
        gameOver = new GameOver(context, width, height);
        thumbstick = new Thumbstick((width/6), (height-300), 70, 40);
        // initialize game objects
        SpriteSheet spriteSheet = new SpriteSheet(context);
        Animator animator = new Animator(spriteSheet.getPlayerSpriteArray());
        player = new Player(
            context, thumbstick,
            (width/2.0) - 15, (height/2.0) - 15, 30,
            animator
        );
        // initialize GameDisplay
        gameDisplay = new GameDisplay(player, width, height);

        // initialize tilemap
        tilemap = new Tilemap(spriteSheet);

        setFocusable(true);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        Log.d("Game.java", "surfaceCreated()");
        if(gameLoop.getState().equals(Thread.State.TERMINATED)) {
            gameLoop = new GameLoop(this, surfaceHolder);
        }
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.d("Game.java", "surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        Log.d("Game.java", "surfaceDestroyed()");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("Game.java", "onTouchEvent()");
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if(thumbstick.getIsPressed()) {
                    // thumbstick was pressed before this event -> cast spell
                    numberOfSpellsToCast++;
                } else if(thumbstick.isPressed(event.getX(), event.getY())) {
                    // thumbstick is pressed in this event -> setIsPressed(true)
                    thumbstickPointerId = event.getPointerId(event.getActionIndex());
                    thumbstick.setIsPressed(true);
                } else {
                    // thumbstick was not previously, and is not pressed in this event -> cast spell
                    numberOfSpellsToCast++;
                }
                return true;

            case MotionEvent.ACTION_MOVE:
                // thumbstick was pressed previously and is now moved
                if(thumbstick.getIsPressed()) {
                    thumbstick.setActuator(event.getX(), event.getY());
                }
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if(thumbstickPointerId == event.getPointerId(event.getActionIndex())) {
                    // thumbstick was let go of -> setIsPressed(false) and resetActuator
                    thumbstick.setIsPressed(false);
                    thumbstick.resetActuator();
                }
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Log.d("Game.java", "draw()");

        // Draw the Tilemap
        tilemap.draw(canvas, gameDisplay);

        // Draw the Player
        player.draw(canvas, gameDisplay);

        for(Enemy enemy : enemyList) {
            enemy.draw(canvas, gameDisplay);
        }

        for(Spell spell : spellList) {
            spell.draw(canvas, gameDisplay);
        }

        // draw game panels
        thumbstick.draw(canvas);
        performance.draw(canvas);
        if(player.getHealthPoints() <= 0) {
            gameOver.draw(canvas);
        }
    }

    public void update() {
        Log.d("Game.java", "update()");
        // stop updating the game if the player is dead
        if(player.getHealthPoints() <= 0) {
            return;
        }

        thumbstick.update();
        player.update();
        // add enemy to enemy spawn list
        if(Enemy.readyToSpawn()) {
            enemyList.add(new Enemy(getContext(), player));
        }
        // update the state of each enemy in the list
        while(numberOfSpellsToCast > 0) {
            spellList.add(new Spell(getContext(), player));
            numberOfSpellsToCast--;
        }
        for(Enemy enemy : enemyList) {
            enemy.update();
        }
        // update the state of each spell in the list
        for(Spell spell : spellList) {
            spell.update();
        }

        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        while(iteratorEnemy.hasNext()) {
            Circle enemy = iteratorEnemy.next();
            if(Circle.isColliding(enemy, player)) {
                iteratorEnemy.remove();
                player.setHealthPoints(player.getHealthPoints() - 1);
                continue;
            }
            Iterator<Spell> iteratorSpell = spellList.iterator();
            while(iteratorSpell.hasNext()) {
                Circle spell = iteratorSpell.next();
                if(Circle.isColliding(spell, enemy)) {
                    iteratorSpell.remove();
                    iteratorEnemy.remove();
                    break;
                }
            }
        }
        gameDisplay.update();
    }

    public void pause() {
        gameLoop.stopLoop();
    }
}
