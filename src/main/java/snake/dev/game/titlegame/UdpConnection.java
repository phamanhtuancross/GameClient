package snake.dev.game.titlegame;

import ConvertDataTransfer.Convert;
import GameClient.Dot;
import org.omg.CORBA.DATA_CONVERSION;
import snake.dev.game.entities.creatures.SnakeDot;
import sun.tools.asm.Cover;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;

public class UdpConnection implements Runnable{

    private Game game;
    private TcpConnection tcpConnection;
    private DatagramSocket datagramSocket;
    private final int UDP_PORT;
    private byte[] buffer = new byte[1024 * 3];

    public UdpConnection(Game game,TcpConnection tcpConnection,int clientPortUdp){
        this.game = game;
        this.tcpConnection = tcpConnection;
        UDP_PORT = clientPortUdp;
    }


    @Override
    public void run() {

            try {
                if(UDP_PORT < 0 || UDP_PORT > 65535) {
                    datagramSocket = new DatagramSocket();
                    System.err.append(UDP_PORT + "is not possible");
                }
                else{
                    datagramSocket = new DatagramSocket(UDP_PORT);
                }

                tcpConnection.sendIpIdPort(UDP_PORT);
                DatagramPacket datagramPacket = new DatagramPacket(buffer,buffer.length);

                while (true){
                    String data;
                    datagramSocket.receive(datagramPacket);
                    ByteArrayInputStream bais = new ByteArrayInputStream(datagramPacket.getData());
                    DataInputStream dataInputStream = new DataInputStream(bais);
                    data = dataInputStream.readUTF();
                    WrapList gameplay = (WrapList)Helper.getObjectFromJsonString(ObjectType.GAME_PLAY_OBJECT,data);
                   List<SnakeDot> snakeDots = Convert.convertListDotsToListSnakeDots(gameplay.dots);
                    game.getPlayer().setAnotherSnakes(snakeDots);
                   // System.out.println("Data :" + dots);
                    datagramPacket.setData(buffer);
                    datagramPacket.setLength(buffer.length);
                }
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
