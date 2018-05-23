package snake.dev.game.titlegame;

import GameClient.GameClient;
import snake.dev.define.Define;

public class Launcher {
    public static void main(String[] args) {
//        Game game = new Game("SNAKE SOCKET IO",900,900);
//        game.start();
//
        GameClient gameClient = new GameClient("localhost",3000);
        gameClient.tranferData();
    }
}
