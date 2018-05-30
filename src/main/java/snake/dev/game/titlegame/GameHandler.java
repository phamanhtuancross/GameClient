package snake.dev.game.titlegame;

import GameClient.Dot;
import GameClient.Snake;
import com.google.gson.Gson;
import snake.dev.game.entities.creatures.SnakeDot;
import snake.dev.game.gfx.Assets;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GameHandler {
    public static List<SnakeDot>  generateSanke(int number){
        List<SnakeDot> snake = null;
        switch (number){
            case 0:{
                snake = generateSnakeAtTopLeftBoard();
                break;
            }
            case 1:{
                snake = generateSnakeAtTopRightBoard();
                break;
            }
            case 2:{
                snake = generateSnakeAtBottonLeftBoard();
                break;
            }
            case 3:{
                snake = generateSnakeAtBottonRightBoard();
                break;
            }
            default:{
                break;
            }
        }
        return snake;
    }

    private static List<SnakeDot> generateSnakeAtTopLeftBoard(){
        System.out.println("Calling function generateSnakeAtTopLeftBoard()....");
        List<SnakeDot> dots = new ArrayList<SnakeDot>();

        int x = StartSnakeLocation.SNAKE_TOP_LEFT_X_POSITION;
        int y = StartSnakeLocation.SNAKE_TOP_LEFT_Y_POSITION;

        SnakeDot heaDot = new SnakeDot(x,y,Assets.snakeDotRed);
        dots.add(heaDot);

        for(int dotIndex = 1; dotIndex < WorlSize.START_SNAKE_SIZE; dotIndex++){
            x -= 16;
            SnakeDot dot = new SnakeDot(x,y,Assets.snakeDotYellow);
            dots.add(dot);
        }
        return dots;
    }

    private static List<SnakeDot> generateSnakeAtTopRightBoard(){
        System.out.println("Calling function generateSnakeAtTopRightBoard()....");
        List<SnakeDot> dots = new ArrayList<SnakeDot>();

        int x = StartSnakeLocation.SNAKE_TOP_RIGHT_X_POSITION;
        int y = StartSnakeLocation.SNAKE_TOP_RIGHT_Y_POSITION;

        SnakeDot heaDot = new SnakeDot(x,y,Assets.snakeDotBlack);
        dots.add(heaDot);

        for(int dotIndex = 1; dotIndex < WorlSize.START_SNAKE_SIZE; dotIndex++){
            x += 16;
            SnakeDot dot = new SnakeDot(x,y,Assets.snakeDotYellow);
            dots.add(dot);
        }
        return dots;
    }

    private static List<SnakeDot> generateSnakeAtBottonLeftBoard(){
        List<SnakeDot> dots = new ArrayList<SnakeDot>();

        int x = StartSnakeLocation.SNAKE_BOTTON_LEFT_X_POSITION;
        int y = StartSnakeLocation.SNAKE_BOTTON_LEFT_Y_POSITION;

        SnakeDot heaDot = new SnakeDot(x,y,Assets.snakeDotBlue);
        dots.add(heaDot);

        for(int dotIndex = 1; dotIndex < WorlSize.START_SNAKE_SIZE; dotIndex++){
            x -= 16;
            SnakeDot dot = new SnakeDot(x,y,Assets.snakeDotYellow);
            dots.add(dot);
        }
        return dots;
    }

    private static List<SnakeDot> generateSnakeAtBottonRightBoard(){
        System.out.println("Calling function generateSnakeAtBottonRightBoard()...");
        List<SnakeDot> dots = new ArrayList<SnakeDot>();

        int x = StartSnakeLocation.SNAKE_BOTTON_RIGHT_X_POSITION;
        int y = StartSnakeLocation.SNAME_BOTTON_RIGHT_Y_POSITION;

        SnakeDot heaDot = new SnakeDot(x,y,Assets.snakeDotGreen);
        dots.add(heaDot);

        for(int dotIndex = 1; dotIndex < WorlSize.START_SNAKE_SIZE; dotIndex++){
            x += 16;
            SnakeDot dot = new SnakeDot(x,y,Assets.foodPink);
            dots.add(dot);
        }
        return dots;
    }
}
