package snake.dev.game.states;

import snake.dev.game.entities.creatures.Player;
import snake.dev.game.titlegame.Game;
import snake.dev.game.titlegame.worlds.World;

import java.awt.*;

public class GameState extends State{

    private Player player;
    private World world;
    public GameState(Game game){
        super(game);
        world = new World(game,"");
        player = new Player(world,game,100,50);
    }

    @Override
    public void tick() {
        world.tick();
        player.tick();

    }

    @Override
    public void render(Graphics g) {
        world.render(g);
        player.render(g);
    }
}
