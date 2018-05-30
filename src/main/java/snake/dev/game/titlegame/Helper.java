package snake.dev.game.titlegame;

import GameClient.Dot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import snake.dev.game.entities.creatures.SnakeDot;

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
        }
        return null;
    }

    public static String getJsonStringFromObject(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
