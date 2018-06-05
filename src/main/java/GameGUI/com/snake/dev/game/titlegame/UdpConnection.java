package GameGUI.com.snake.dev.game.titlegame;

import GameGUI.com.snake.ConvertDataTransfer.Convert;
import GameGUI.com.snake.dev.game.entities.creatures.SnakeDot;
import MainGUI.com.RoomGame;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;
import java.util.Random;

public class UdpConnection implements Runnable {

    private Game game;
    private TcpConnection tcpConnection;
    private DatagramSocket datagramSocket;
    private int UDP_PORT;
    private byte[] buffer = new byte[1024 * 3];

    public UdpConnection(Game game, TcpConnection tcpConnection) {
        this.game = game;
        //this.roomGame = roomGame;
        this.tcpConnection = tcpConnection;
        UDP_PORT = 5000;

        List<Integer> listUDPPort = tcpConnection.getListUDPPort();
        boolean isAcceptedPort = true;
        for (int port : listUDPPort) {
            if (port == UDP_PORT) {
                isAcceptedPort = false;
                break;
            }
        }

        if (!isAcceptedPort) {
            while (!isAcceptedPort) {
                Random random = new Random();
                UDP_PORT = random.nextInt(65556);

                boolean isExist = false;
                for (int port : listUDPPort) {
                    if (port == UDP_PORT) {
                        isExist = true;
                        break;
                    }
                }

                if (isExist == false) {
                    isAcceptedPort = true;
                }
            }
        }

        System.out.println("UDP PORT :" + UDP_PORT);
    }

    @Override
    public void run() {

        try {
            if (UDP_PORT < 0 || UDP_PORT > 65535) {
                datagramSocket = new DatagramSocket();
                System.err.append(UDP_PORT + "is not possible");
            } else {
                datagramSocket = new DatagramSocket(UDP_PORT);
            }

            tcpConnection.sendIpIdPort(UDP_PORT, game.getRoomgameCode());
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);

            while (true) {
                String data;
                datagramSocket.receive(datagramPacket);
                ByteArrayInputStream bais = new ByteArrayInputStream(datagramPacket.getData());
                DataInputStream dataInputStream = new DataInputStream(bais);
                data = dataInputStream.readUTF();
               
                String[] datas = data.split("#");
                String gamePlayData = datas[0];
                String listCharacterObjectInfoData = datas[1];
               
                RoomGame.listFriends = (List<CharacterObjInfo>) Helper.getObjectFromJsonString(ObjectType.LIST_CHARACTER_OBJECT_INFO, listCharacterObjectInfoData);

                if (gamePlayData != null && !"".equals(gamePlayData)) {
                    //System.out.println("data[1] :" + listCharacterObjectInfoData);
                    WrapList gameplay = (WrapList) Helper.getObjectFromJsonString(ObjectType.GAME_PLAY_OBJECT, gamePlayData);

                    List<SnakeDot> snakeDots = Convert.convertListDotsToListSnakeDots(gameplay.dots);

                    if (game == null) {
                        System.out.println("GAME OBJECT NULL...");
                    } else {
                        if (game.getPlayer() != null) {
                            game.getPlayer().setGamePlay(snakeDots);
                        }
                    }
                }
                datagramPacket.setData(buffer);
                datagramPacket.setLength(buffer.length);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort(){
        return UDP_PORT;
    }
}
