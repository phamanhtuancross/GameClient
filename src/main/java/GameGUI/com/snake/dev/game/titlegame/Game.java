package GameGUI.com.snake.dev.game.titlegame;

import GameGUI.com.snake.ConvertDataTransfer.Convert;
import GameGUI.com.snake.GameClient.Snake;
import GameGUI.com.snake.dev.define.Define;
import GameGUI.com.snake.dev.game.display.Display;
import GameGUI.com.snake.dev.game.entities.creatures.Player;
import GameGUI.com.snake.dev.game.entities.creatures.SnakeDot;
import GameGUI.com.snake.dev.game.gfx.Assets;
import GameGUI.com.snake.dev.game.gfx.CameraGame;


import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferStrategy;
import java.text.AttributedString;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game implements Runnable {

    private Display display;
    private BufferStrategy bufferStrategy;
    private Graphics graphics;
    private String title;
    private int width, height;
    private Thread thread;
    private boolean isRunning = false;
    private KeyManager keyManager;
    private CameraGame cameraGame;
    // private World world;
    private Player player;
    private TcpConnection connection;
    private UdpConnection udpConnection;
    private boolean isWaitingForStarting = true;
    private boolean isRemovedRoomgame = false;

    public boolean isIsRemovedRoomgame() {
        return isRemovedRoomgame;
    }

    public void setIsRemovedRoomgame(boolean isRemovedRoomgame) {
        this.isRemovedRoomgame = isRemovedRoomgame;
    }

    
    private long ID;

    //roomgame information
    private String roomgameCode;
    private int idInRoomGame;

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public TcpConnection getConnection() {
        return connection;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
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

    public Player getPlayer() {
        return player;
    }

    public UdpConnection getUdpConnection() {
        return udpConnection;
    }

    public void setUdpConnection(UdpConnection udpConnection) {
        this.udpConnection = udpConnection;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public Game(String title) {
        this.title = title;
        this.width = 900;
        this.height = 800;

        // gameClient = new GameGUI.com.snake.GameClient("localhost",1234);
    }

    public String getRoomgameCode() {
        return roomgameCode;
    }

    public void setRoomgameCode(String roomgameCode) {
        this.roomgameCode = roomgameCode;
    }

    public int getIdInRoomGame() {
        return idInRoomGame;
    }

    public void setIdInRoomGame(int idInRoomGame) {
        this.idInRoomGame = idInRoomGame;
    }

    public Game() {
    }

    public void setConnection(TcpConnection connection) {
        this.connection = connection;
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

        boolean flag  = true;
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
            
            if(flag){
                boolean check = sendCharacter();
                if(check){
                    flag = false;
                    isRemovedRoomgame = true;
                }
            }
                    

        }

        stop();
    }

    public boolean sendCharacter() {
        Snake snake = Convert.convertSnakeClientToServer(this.getPlayer().getSnake());
        snake.Id = idInRoomGame;
        return connection.sendUpdateVersion(snake,roomgameCode);
    }

    public synchronized void start() {

        Assets.init();
        display = new Display(title, width, height);

        cameraGame = new CameraGame(this, 0, 0);
        //world = new World(this,"");u
        player = new Player(this, 100, 50);
        
        keyManager = new KeyManager();
        display.getFrame().addKeyListener(keyManager);

        isRemovedRoomgame = false;
        isRunning = true;
        isWaitingForStarting = true;
        timeWating = 10;
        
        thread = new Thread(this);
        thread.start();
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
        if (idInRoomGame != -1) {
            List<SnakeDot> dots = GameHandler.generateSanke(idInRoomGame);
            this.player.setSnake(dots);
            this.player.setId(idInRoomGame);
        }
    }

    public void renderWaitingTime() {
        for (int i = 0; i < 10; i++) {

        }
    }

    private void tick() {
        player.tick();
    }

    private int timeWating = 10;

    private void render() {

        bufferStrategy = display.getCanvas().getBufferStrategy();
        if (bufferStrategy == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }

        graphics = bufferStrategy.getDrawGraphics();
        graphics.clearRect(0, 0, width, height);

        //world.render(graphics);
        player.render(graphics);

        if (timeWating > 0) {
            timeWating--;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (graphics instanceof Graphics2D) {
                String scoreString = "TIME COUNTDOWS... " + timeWating;
                Graphics2D g2d = (Graphics2D) graphics;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Font font = new Font("Serif", Font.PLAIN, 30);
                AttributedString as1 = new AttributedString(scoreString);
                as1.addAttribute(TextAttribute.FONT, font);
                as1.addAttribute(TextAttribute.FOREGROUND, Color.red, 0, 6);
                as1.addAttribute(TextAttribute.FOREGROUND, Color.BLUE,7,9);
                g2d.drawString(as1.getIterator(), Define.BoardSize.WIDTH_SIZE / 2 - 50, Define.BoardSize.HEIGHT_SIZE / 2 - 50);

            }

        } else if (timeWating == 0 && isWaitingForStarting) {
            isWaitingForStarting = true;
        }
        
        if(isRemovedRoomgame){
            drawGameOVer(graphics);
        }
        // graphics.drawRect(200, 2002, 200, 200);

        bufferStrategy.show();
        graphics.dispose();
    }
    
    public void drawGameOVer(Graphics g){
         if (graphics instanceof Graphics2D) {
                String scoreString = "GAMEOVER";
                Graphics2D g2d = (Graphics2D) graphics;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Font font = new Font("Serif", Font.PLAIN, 70);
                AttributedString as1 = new AttributedString(scoreString);
                as1.addAttribute(TextAttribute.FONT, font);
                as1.addAttribute(TextAttribute.FOREGROUND, Color.red, 0, scoreString.length());
                g2d.drawString(as1.getIterator(), Define.BoardSize.WIDTH_SIZE / 2 - 250, Define.BoardSize.HEIGHT_SIZE / 2 + 50);

            }
    }

}
