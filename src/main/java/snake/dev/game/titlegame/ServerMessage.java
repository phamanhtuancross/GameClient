package snake.dev.game.titlegame;

import GameClient.Snake;

import java.io.Serializable;

public class ServerMessage implements Serializable{

    public int messageType;
    public int id;
    public int port;
    public Snake character;

    public ServerMessage(){}

    public ServerMessage(int messageType){
        this.messageType = messageType;
    }

}
