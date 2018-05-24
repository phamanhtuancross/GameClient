package ConvertDataTransfer;

import GameClient.Dot;
import GameClient.Snake;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import snake.dev.define.SpriteType;
import snake.dev.game.entities.creatures.SnakeDot;
import snake.dev.game.gfx.Assets;
import snake.dev.game.model.Sprite;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Convert {
    public static SpriteType[][] convertWorldJsonStringToWorld(String jsonValue){
        Gson gson = new Gson();
        Type type = new TypeToken<SpriteType[][]>(){}.getType();
        SpriteType[][] titles = gson.fromJson(jsonValue,type);
        System.out.println("TITLES : " + titles);;
        return titles;
    }

    public static Map<String,Object>  convertDataFormServer(String jsonValue){
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String,Object>>(){}.getType();
        Map<String,Object> myMap = gson.fromJson(jsonValue,type);
        System.out.println("WORLD : "+ myMap.get("world"));
        System.out.println("SNAKE : " + myMap.get("snake"));
        return  myMap;
    }

    public static  List<SnakeDot> convertSnakeFromServerToClient(String jsonValue){
        System.out.println("Calling function convertSnakeFromServerToClient()....");

        Gson gson = new Gson();
        Type collectionType = new TypeToken<Snake>(){}.getType();
        Snake sankeServer = gson.fromJson(jsonValue,collectionType);
        List<SnakeDot> snake = new ArrayList<SnakeDot>();
        for(Dot dot : sankeServer.dots){
            Sprite sprite = Assets.getSpriteBySpriteType(dot.spriteType);
            SnakeDot snakeDot = new SnakeDot(dot.x,dot.y,sprite);
            snake.add(snakeDot);
        }
        return snake;
    }

    public static Snake convertSnakeClientToServer(List<SnakeDot> snakeClient){
        Snake snakeServer = new Snake();
        for(SnakeDot snakeDot : snakeClient) {
            Dot dot = new Dot((int) snakeDot.x, (int) snakeDot.y, snakeDot.sprite.getSpriteType());
            snakeServer.dots.add(dot);
        }
        return snakeServer;
    }



}
