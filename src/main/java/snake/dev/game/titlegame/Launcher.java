package snake.dev.game.titlegame;

import GameClient.GameClient;

import snake.dev.define.Define;

public class Launcher {
    public static void main(String[] args) {
        Game game = new Game("SNAKE SOCKET IO","localhost",1234,5004);
        game.start();
    }
}
