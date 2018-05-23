package snake.dev.game.entities.creatures;

import snake.dev.game.entities.Entity;
import snake.dev.game.titlegame.Game;

public abstract class Creature extends Entity {

    protected int size;
    public Creature(Game game, float x, float y){
        super(game,x,y);
        size = 10;
    }

}
