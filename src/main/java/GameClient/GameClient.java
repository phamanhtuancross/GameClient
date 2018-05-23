package GameClient;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class GameClient {
    private int port;
    private InetAddress  ipAddress;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Socket socket;

    public GameClient(String strIpAdress, int port){
        try {
            this.port = port;
            ipAddress = InetAddress.getByName(strIpAdress);
            socket = new Socket(ipAddress,port);

            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

        }
        catch (UnknownHostException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
    }

    public void tranferData(){
        Thread sendData = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dataOutputStream.writeUTF("bo may da vao roi ne");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread readData = new Thread(new Runnable() {
            @Override
            public void run() {
                String jsonData = null;
                try {
                    jsonData = dataInputStream.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("SERVER > " + jsonData);
            }
        });

        sendData.start();
        readData.start();
    }


}
