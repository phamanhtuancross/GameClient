package snake.dev.game.gfx;

import snake.dev.game.entities.Entity;
import snake.dev.game.titlegame.Game;

public class CameraGame {
    private float xOffset,yOffset;
    private Game game;

    public CameraGame(Game game,float xOffset, float yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.game = game;
    }

    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }

    public void move(float xAmt, float yAmt){
        xOffset += xAmt;
        yOffset += yAmt;
    }

    public void centerOnEntity(Entity e){
        xOffset = e.getX() - game.getWidth()/2;
        yOffset = e.getY() - game.getHeight()/2;
    }
}
