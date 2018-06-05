package GameGUI.com.snake.dev.game.gfx;

import GameGUI.com.snake.dev.define.SpritePath;
import GameGUI.com.snake.dev.define.SpriteType;
import GameGUI.com.snake.dev.game.model.Sprite;

public class Assets {
    public static Sprite brickwall;
    public static Sprite
            foodBlack,
            foodBlue,
            foodGreen,
            foodPink,
            foodRed,
            foodYellow;

    public static Sprite
            snakeHead,
            snakeDotBlack,
            snakeDotYellow,
            snakeDotRed,
            snakeDotPink,
            snakeDotGreen,
            snakeDotBlue;

    public static void init () {

        snakeHead      = new Sprite(SpriteType.SNAKE_HEAD,SpritePath.SNAKE_HEAD);
        snakeDotBlack  = new Sprite(SpriteType.SNAKE_DOT_BLACK,SpritePath.SNAKE_DOT_BLACK);
        snakeDotYellow = new Sprite(SpriteType.SNAKE_DOT_YELLOW,SpritePath.SNAKE_DOT_YELLOW);
        snakeDotRed    = new Sprite(SpriteType.SNAKE_DOT_RED,SpritePath.SNAKE_DOT_RED);
        snakeDotPink   = new Sprite(SpriteType.SNAKE_DOT_PINK,SpritePath.SNAKE_DOT_PINK);
        snakeDotGreen  = new Sprite(SpriteType.SNAKE_DOT_GREEN,SpritePath.SNAKE_DOT_GREEN);
        snakeDotBlue   = new Sprite(SpriteType.SNAKE_DOT_BLUE,SpritePath.SNAKE_DOT_BLUE);

        foodBlack      = new Sprite(SpriteType.FOOD_BLACK,SpritePath.FOOD_BLACK);
        foodBlue       = new Sprite(SpriteType.FOOD_BLUE,SpritePath.FOOD_BLUE);
        foodGreen      = new Sprite(SpriteType.FOOD_GREEN,SpritePath.FOOD_GREEN);
        foodPink       = new Sprite(SpriteType.FOOD_PINK,SpritePath.FOOD_PINK);
        foodRed        = new Sprite(SpriteType.FOOD_RED,SpritePath.FOOD_RED);
        foodYellow     = new Sprite(SpriteType.FOOD_YELLOW,SpritePath.FOOD_YELLOW);

        brickwall      = new Sprite(SpriteType.BRICK_WALL,SpritePath.BRICK_WALL);
    }

    public static Sprite getSpriteBySpriteType(SpriteType spriteType){
        switch (spriteType){
            case BRICK_WALL:{
                return brickwall;
            }
            case FOOD_BLACK:{
                return foodBlack;
            }
            case FOOD_BLUE:{
                return foodBlue;
            }
            case FOOD_GREEN:{
                return foodGreen;
            }
            case FOOD_PINK:{
                return  foodPink;
            }
            case FOOD_RED:{
                return foodRed;
            }
            case FOOD_YELLOW:{
                return foodYellow;
            }
            case SNAKE_DOT_BLACK:{
                return snakeDotBlack;
            }
            case SNAKE_DOT_BLUE:{
                return snakeDotBlue;
            }
            case SNAKE_DOT_GREEN:{
                return snakeDotGreen;
            }
            case SNAKE_DOT_PINK:{
                return snakeDotPink;
            }
            case SNAKE_DOT_RED:{
                return snakeDotRed;
            }
            case SNAKE_DOT_YELLOW:{
                return snakeDotYellow;
            }
            case SNAKE_HEAD:{
                return snakeHead;
            }
        }
        return null;
    }

    public static Sprite getSnakeDotByFoodType(SpriteType spriteType){
        switch (spriteType){
            case FOOD_BLACK:{
                return snakeDotBlack;
            }
            case FOOD_BLUE:{
                return  snakeDotBlue;
            }

            case FOOD_GREEN:{
                return snakeDotGreen;
            }

            case FOOD_PINK:{
                return snakeDotPink;
            }

            case FOOD_RED:{
                return snakeDotRed;
            }
            case FOOD_YELLOW:{
                return snakeDotYellow;
            }
        }

        return null;
    }

}
