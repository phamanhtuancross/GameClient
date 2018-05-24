package snake.dev.game.titlegame;
import GameClient.GameClient;
import snake.dev.game.display.Display;
import snake.dev.game.entities.creatures.Player;
import snake.dev.game.gfx.Assets;
import snake.dev.game.gfx.CameraGame;
import snake.dev.game.states.GameState;
import snake.dev.game.states.State;
import snake.dev.game.titlegame.worlds.World;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {

    private Display display;
    private BufferStrategy bufferStrategy;
    private Graphics graphics;
    private String title;
    private int width, height;
    private Thread thread;
    private boolean isRunning = false;
    private GameState gameState;
    private State menuState;
    private KeyManager keyManager;
    private CameraGame cameraGame;
    private World world;
    private Player player;

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public GameState getGameState() {
        return gameState;
    }

    public CameraGame getCameraGame() {
        return cameraGame;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public World getWorld() {
        return world;
    }

    public Player getPlayer() {
        return player;
    }

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        keyManager = new KeyManager();
        Assets.init();
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);

        cameraGame = new CameraGame(this, 0, 0);
        world = new World(this,"");
        player = new Player(world,this,100,50);
        // gameClient = new GameClient("localhost",1234);

    }

    @Override
    public void run() {
        init();

        int FPS = 60;
        double timePerTick = 1000000000 / FPS;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;
        while (isRunning) {


            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if (delta >= 1) {
                tick();
                render();
                ticks++;
                delta--;
            }

            if (timer >= 1000000000) {
                System.out.println("Ticks and frame :" + ticks);
                ticks = 0;
                timer = 0;
            }
        }

        stop();
    }

    public synchronized void start(){


        if (isRunning) {
            return;
        }

        isRunning = true;
        thread = new Thread(this);
        thread.start();
        // gameClient.start();
        GameClient gameClient = new GameClient(this,"localhost",3000);
        gameClient.tranferData();
    }

    public synchronized void stop() {
        System.out.println("Calling function stop()...");
        if (!isRunning) {
            return;
        }

        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void init() {


        // gameState = new GameState(this);
        //menuState = new MenuState(this);

        //State.setState(gameState);
    }


    private void tick() {
//        if (State.getState() != null) {
//            State.getState().tick();
//        }
        world.tick();
        player.tick();
    }

    private void render() {

        bufferStrategy = display.getCanvas().getBufferStrategy();
        if (bufferStrategy == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }

        graphics = bufferStrategy.getDrawGraphics();
        graphics.clearRect(0, 0, width, height);

        world.render(graphics);
        player.render(graphics);

        bufferStrategy.show();
        graphics.dispose();
    }

}
