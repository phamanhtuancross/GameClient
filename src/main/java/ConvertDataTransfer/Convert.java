package ConvertDataTransfer;

import GameClient.Dot;
import GameClient.Snake;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

    public static List<SnakeDot> convertSnakeToSnakeDotObjet(Snake snake){
        List<SnakeDot> snakeDots = new ArrayList<SnakeDot>();
        for(Dot dot : snake.dots){
            Sprite sprite = Assets.getSpriteBySpriteType(dot.spriteType);
            SnakeDot snakeDot = new SnakeDot(dot.x,dot.y,sprite);
            snakeDots.add(snakeDot);
        }
        return snakeDots;
    }

    public static Snake convertSnakeClientToServer(List<SnakeDot> snakeClient){
        Snake snakeServer = new Snake();
        for(SnakeDot snakeDot : snakeClient) {
            Dot dot = new Dot((int) snakeDot.x, (int) snakeDot.y, snakeDot.sprite.getSpriteType());
            snakeServer.dots.add(dot);
        }
        return snakeServer;
    }

    public static List<List<SnakeDot>> convertAnotherSnakesJSonToObject(List<Snake> snakes){
        List<List<SnakeDot>> anotherSnakes = new ArrayList<List<SnakeDot>>();
        if(snakes != null) {
            for (Snake snake : snakes) {
                //System.out.println("TEST : " + snake);
                List<SnakeDot> _snake = convertSnakeToSnakeDotObjet(snake);
                anotherSnakes.add(_snake);
            }
        }
        return anotherSnakes;
    }

    public static List<Snake> getListSnakesFromJsonValue(String jsonValue){

        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<Snake>>(){}.getType();
        List<Snake> snakes = gson.fromJson(jsonValue,collectionType);
        return snakes;
    }
}
