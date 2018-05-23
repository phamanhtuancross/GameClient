package snake.dev.game.gfx;

import snake.dev.define.SpritePath;
import snake.dev.define.SpriteType;
import snake.dev.game.model.Sprite;

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

}
