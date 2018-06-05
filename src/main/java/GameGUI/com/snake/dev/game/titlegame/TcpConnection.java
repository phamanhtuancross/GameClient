package GameGUI.com.snake.dev.game.titlegame;

import GameGUI.com.snake.GameClient.Dot;
import GameGUI.com.snake.GameClient.Snake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TcpConnection {

    private String SERVER_IP;
    private int SERVER_PORT;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private Socket socket;

    public TcpConnection(String ip, int tcpPort) {
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

    public void sendIpIdPort(int port,String code){

        try {
            ServerMessage serverMessage = new ServerMessage(MessageType.GET_IP_ID_PORT);
            serverMessage.port = port;
            serverMessage.data = code;
            
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

    public boolean sendUpdateVersion(Snake snake,String code){
       boolean result = false;
        try{
            ServerMessage serverMessage = new ServerMessage(MessageType.SEND_MAIN_CHARACTER);
            serverMessage.character = snake;
            serverMessage.data = code;
            String jsonString = Helper.getJsonStringFromObject(serverMessage);
            dataOutputStream.writeUTF(jsonString);
            result = dataInputStream.readBoolean();
           
        }
        catch (IOException e){

        }
        return result;
    }

    public void sendUpdateMap(Dot dot,String code){
        try{
            ServerMessage serverMessage = new ServerMessage(MessageType.CHANGE_MAP_BY_TITLE);
            Snake title = new Snake();
            title.dots.add(dot);
            serverMessage.character = title;
            serverMessage.data = code;
            String jsonString = Helper.getJsonStringFromObject(serverMessage);
            dataOutputStream.writeUTF(jsonString);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }   
    
    
    public boolean checkIsWhenServerUpdateMapSucessfully(){
        try {
            boolean isSucessfully = dataInputStream.readBoolean();
            return isSucessfully;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void sendUserName(String userName,int Id){
        try {
            ServerMessage serverMessage = new ServerMessage(MessageType.SEND_MAIN_CHARACTER_NAME);
            serverMessage.data = userName;
            serverMessage.id = Id;
            String jsonString = Helper.getJsonStringFromObject(serverMessage);
            dataOutputStream.writeUTF(jsonString);
        } catch (IOException ex) {
            Logger.getLogger(TcpConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public List<Integer> getListUDPPort(){
        try {
            ServerMessage serverMessage = new ServerMessage(MessageType.GET_LIST_UDP_PORT);
            String jsonString = Helper.getJsonStringFromObject(serverMessage);
            dataOutputStream.writeUTF(jsonString);
            
            String listUDPPortString = dataInputStream.readUTF();
            return (List<Integer>) Helper.getObjectFromJsonString(ObjectType.LIST_UDP_PORT, listUDPPortString);
        } catch (IOException ex) {
            Logger.getLogger(TcpConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
       
    }
    
    public String createGameRoom(String username,int updPort){
        
        String code = "";
        try {
            ServerMessage serverMessage = new ServerMessage(MessageType.CREATE_GAME_ROOM);
            serverMessage.data = username;
            serverMessage.port = updPort;
            String jsonString = Helper.getJsonStringFromObject(serverMessage);
    
            dataOutputStream.writeUTF(jsonString);
            code = dataInputStream.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(TcpConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return code;
    }
    
    public List<CharacterObjInfo> getListPlayerInGame(String code){
        
        List<CharacterObjInfo> result = null;
        try {
            
            //create object for sending data
            ServerMessage serverMessage = new ServerMessage(MessageType.GET_PLAYER_IN_GAME_ROOM);
            serverMessage.data = code;
            System.out.println("CODE of room sednd oto server: " + code);
          
            //sending json data string
            String jsonString = Helper.getJsonStringFromObject(serverMessage);
            dataOutputStream.writeUTF(jsonString);

            //get data from server
            String data = dataInputStream.readUTF();
            System.out.println("Data get form server :" + data);
            result = (List<CharacterObjInfo>) Helper.getObjectFromJsonString(ObjectType.LIST_CHARACTER_OBJECT_INFO, data);
            
            return result;
        } catch (IOException ex) {
            Logger.getLogger(TcpConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public int joinToGameRoomByGameRoomCode(String gameRoomCode,String userName,int UPD_PORT){
        int idIndRoomGame = -1;
        try {      
            
            ServerMessage serverMessage = new ServerMessage(MessageType.JOIN_GAME_ROOM);
            serverMessage.port = UPD_PORT;
            String data  = gameRoomCode+"#"+userName;
            serverMessage.data = data;
            String jsonString = Helper.getJsonStringFromObject(serverMessage);
            dataOutputStream.writeUTF(jsonString);

            idIndRoomGame = dataInputStream.readInt();
           
        } catch (IOException ex) {
            Logger.getLogger(TcpConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return idIndRoomGame;
    }
    
    
    public boolean checkIsRoomMasterStartGame(String roomCode){     
        boolean isRoomGameRunning = false;
      
        try {
            ServerMessage serverMessage = new ServerMessage(MessageType.GET_IS_ROOM_MASTER_START_GAME);
            serverMessage.data = roomCode;
            String jsonString = Helper.getJsonStringFromObject(serverMessage);
            dataOutputStream.writeUTF(jsonString);
            isRoomGameRunning = dataInputStream.readBoolean();
           
        } catch (IOException ex) {
            Logger.getLogger(TcpConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return isRoomGameRunning;
    }
    
    public void sendStartRoomGameByRoomCode(String roomCode){
        try {
            ServerMessage serverMessage = new ServerMessage(MessageType.SEND_START_ROOM_GAME);
            serverMessage.data = roomCode;
            String jsonString = Helper.getJsonStringFromObject(serverMessage);
            dataOutputStream.writeUTF(jsonString);
        } catch (IOException ex) {
            Logger.getLogger(TcpConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    }

    public void sendDisconnectOnServer(){
        
        try {
            ServerMessage serverMessage = new ServerMessage();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(TcpConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendExitRoomByCharacterInfo(String roomCode,int idInRoomGame){
        try {
            ServerMessage serverMessage = new ServerMessage(MessageType.SEND_LEAVE_ROOM);
            serverMessage.id = idInRoomGame;
            serverMessage.data = roomCode;
            String jsonString = Helper.getJsonStringFromObject(serverMessage);
            dataOutputStream.writeUTF(jsonString);
            
        } catch (IOException ex) {
            Logger.getLogger(TcpConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void sendExitProgram(int userId){
        try {
            ServerMessage serverMessage = new ServerMessage(MessageType.SEND_DISCONECTING);
            serverMessage.id = userId;
            String jsonString = Helper.getJsonStringFromObject(serverMessage);
            dataOutputStream.writeUTF(jsonString);
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
             
        } catch (IOException ex) {
            Logger.getLogger(TcpConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void sendRemovedCharacter(String roomCode, int idInRoomGame) {
        ServerMessage serverMessage = new ServerMessage(MessageType.SEND_REMOVED_GAME);
        serverMessage.data = roomCode;
        serverMessage.id = idInRoomGame;
        String jsonString = Helper.getJsonStringFromObject(serverMessage);
        try {
            dataOutputStream.writeUTF(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}