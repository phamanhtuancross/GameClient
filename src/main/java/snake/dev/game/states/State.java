package snake.dev.game.states;

import snake.dev.game.titlegame.Game;

import java.awt.*;

public abstract class State {

    private static State currentState = null;
    protected Game game;

    public static void setState(State state){
        currentState = state;
    }

    public static State getState(){
        return currentState;
    }

    public State(Game game){
        this.game = game;
    }
    public abstract void tick();
    public abstract void render(Graphics g);
}
