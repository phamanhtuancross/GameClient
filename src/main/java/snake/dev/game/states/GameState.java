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

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
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
