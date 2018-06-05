package GameGUI.com.snake.dev.game.titlegame;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class Helper {

    public static Object getObjectFromJsonString(int objectType,String jsonString){
        Gson gson = new Gson();
        switch (objectType){

            case ObjectType.SEVER_MESSAGE_OBJECT:{
                Type collectionType = new TypeToken<ServerMessage>(){}.getType();
                ServerMessage serverMessage = gson.fromJson(jsonString,collectionType);
                return serverMessage;
            }
            case  ObjectType.GAME_PLAY_OBJECT: {
                Type collectionType = new TypeToken<WrapList>(){}.getType();
                WrapList dots = gson.fromJson(jsonString,collectionType);
                return dots;
            }
            case ObjectType.LIST_UDP_PORT:{
                Type collectionType = new TypeToken<List<Integer>>(){}.getType();
                List<Integer> listUDPPort = gson.fromJson(jsonString, collectionType);
                return listUDPPort;
            }
            
            case ObjectType.LIST_CHARACTER_OBJECT_INFO:{
                Type collectionType = new TypeToken<List<CharacterObjInfo>>(){}.getType();
                List<CharacterObjInfo> listCharacterObjInfos = gson.fromJson(jsonString, collectionType);
                return listCharacterObjInfos;
            }
        }
        return null;
    }

    public static String getJsonStringFromObject(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
