package GameClient;
import ConvertDataTransfer.Convert;
import com.google.gson.Gson;
import snake.dev.define.SpriteType;
import snake.dev.game.entities.creatures.SnakeDot;
import snake.dev.game.titlegame.Game;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

public class GameClient {

    private int port;
    private InetAddress ipAddress;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Socket socket;
    private Game game;
    private boolean isLogged = false;

    public GameClient(Game game, String strIpAdress, int port) {
        try {

            this.game = game;
            this.port = port;

            this.ipAddress = InetAddress.getByName(strIpAdress);
            this.socket = new Socket(ipAddress, port);

            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tranferData() {
        final Gson gson = new Gson();
        final Thread sendData = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {

                            Snake snake = Convert.convertSnakeClientToServer(game.getPlayer().getSnake());
                            String snakeJsonData = gson.toJson(snake);
                            dataOutputStream.writeUTF(snakeJsonData);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread readData = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String jsonData = null;
                    try {
                        jsonData = dataInputStream.readUTF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Map<String, Object> myMap = Convert.convertDataFormServer(jsonData);

                    String strJsonListSnakes = gson.toJson(myMap.get("listSnakes"));
                    List<Snake> snakes = Convert.getListSnakesFromJsonValue(strJsonListSnakes);

                    List<List<SnakeDot>> anotherSnakes = Convert.convertAnotherSnakesJSonToObject(snakes);
                    System.out.println("ANOTHER SNAKES : " + myMap.get("listSnakes"));
                    System.out.println("ANOTHER SNAKES LENGTH : " + anotherSnakes.size());

                    game.getPlayer().setAnotherSnakes(anotherSnakes);

                    String temp = gson.toJson(myMap.get("world"));
                    SpriteType[][] titles = Convert.convertWorldJsonStringToWorld(temp);
                    System.out.println(titles);
                    game.getWorld().setTitles(titles);

                    if (!isLogged) {
                        isLogged = !isLogged;
                        String snakeJsonValue = gson.toJson(myMap.get("snake"));
                        List<SnakeDot> snake = Convert.convertSnakeFromServerToClient(snakeJsonValue);
                        game.getPlayer().setSnake(snake);
                    }

                }
            }
        });

        sendData.start();
        readData.start();
    }


}
