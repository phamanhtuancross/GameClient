package snake.dev.game.titlegame;
import ConvertDataTransfer.Convert;
import GameClient.Snake;
import snake.dev.game.display.Display;
import snake.dev.game.entities.creatures.Player;
import snake.dev.game.entities.creatures.SnakeDot;
import snake.dev.game.gfx.Assets;
import snake.dev.game.gfx.CameraGame;
import snake.dev.game.states.GameState;
import snake.dev.game.states.State;
import snake.dev.game.titlegame.worlds.World;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.List;

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
    private String SERVER_IP;
    private int SERVER_TCP_PORT;
    private int SERVER_UDP_PORT;
    private TcpConnection connection;
    private long ID;

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

    public Game(String title,String serverIp, int serverTcpPort, int serverUdpPort) {
        this.title = title;
        this.width = 900;
        this.height = 800;

        this.SERVER_IP = serverIp;
        this.SERVER_TCP_PORT = serverTcpPort;
        this.SERVER_UDP_PORT = serverUdpPort;

        keyManager = new KeyManager();
        Assets.init();
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);

        cameraGame = new CameraGame(this, 0, 0);
        world = new World(this,"");
        player = new Player(world,this,100,50);
        connection = new TcpConnection(this,serverIp,serverTcpPort);

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
            sendCharacter();

        }

        stop();
    }

    public void sendCharacter(){
        Snake snake = Convert.convertSnakeClientToServer(this.getPlayer().getSnake());
        snake.Id  = ID;
        connection.sendUpdateVersion(snake);
    }
    public synchronized void start(){


        if (isRunning) {
            return;
        }

        isRunning = true;
        thread = new Thread(this);
        thread.start();
        // gameClient.start();
      //  GameClient gameClient = new GameClient(this,"localhost",3000);
       // gameClient.tranferData();
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
        ID = connection.getIdFromServer();
        if(ID != -1) {
            List<SnakeDot> dots = GameHandler.generateSanke((int) ID);
            this.player.setSnake(dots);
            this.player.setId(ID);
            //this.SERVER_UDP_PORT = (int) ID;
        }

        new Thread(new UdpConnection(this,connection,SERVER_UDP_PORT)).start();
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
