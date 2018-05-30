package snake.dev.game.titlegame;

import GameClient.Snake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TcpConnection {

    private String SERVER_IP;
    private int SERVER_PORT;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private Socket socket;

    public TcpConnection(Game game, String ip, int tcpPort) {
        this.SERVER_IP = ip;
        this.SERVER_PORT = tcpPort;

        try {
            socket = new Socket(ip, tcpPort);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendIpIdPort(int port){

        try {
            ServerMessage serverMessage = new ServerMessage(MessageType.GET_IP_ID_PORT);
            serverMessage.port = port;
            String serverMessageString = Helper.getJsonStringFromObject(serverMessage);
            dataOutputStream.writeUTF(serverMessageString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public long getIdFromServer() {

        //System.out.println(dataInputStream.readUTF());
        long id = -1L;
        try {
            ServerMessage serverMessage = new ServerMessage(MessageType.GET_ID);
            String serverMessaageJson = Helper.getJsonStringFromObject(serverMessage);
            dataOutputStream.writeUTF(serverMessaageJson);
            id = Long.parseLong(dataInputStream.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void sendUpdateVersion(Snake snake){
        try{
            ServerMessage serverMessage = new ServerMessage(MessageType.SEND_MAIN_CHARACTER);
            serverMessage.character = snake;
            String jsonString = Helper.getJsonStringFromObject(serverMessage);
            dataOutputStream.writeUTF(jsonString);
        }
        catch (IOException e){

        }
    }
}