package snake.dev.game.entities.creatures;

import snake.dev.game.model.Sprite;

import java.io.Serializable;

public class SnakeDot implements Serializable {
    public float x,y;
    public Sprite sprite;

    public SnakeDot(float x, float y, Sprite sprite){
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }
}
