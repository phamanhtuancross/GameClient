package GameGUI.com.snake.dev.game.entities;

import GameGUI.com.snake.dev.game.titlegame.Game;

import java.awt.*;

public abstract class Entity {
    private  Game game;
    protected float x, y;
//    protected Rectangle bounds

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Entity(Game game, float x, float y) {
        this.x = x;
        this.y = y;
        this.game = game;
//        bounds = new Rectangle(0,0,16,16);
    }

    public abstract void tick();
    public abstract void render(Graphics g);
}
