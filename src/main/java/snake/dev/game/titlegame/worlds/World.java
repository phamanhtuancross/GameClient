package snake.dev.game.titlegame.worlds;

import snake.dev.define.Define;
import snake.dev.define.SpriteType;
import snake.dev.game.gfx.Assets;
import snake.dev.game.model.Sprite;
import snake.dev.game.titlegame.Game;

import java.awt.*;
import java.util.ArrayList;

public class World {
    private int width,height;
    private SpriteType[][] titles;
    private Game game;

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public SpriteType[][] getTitles() {
        return titles;
    }

    public World(Game game, String path){
        this.game = game;
        width = Define.WorldSize.WIDTH_SIZE;
        height = Define.WorldSize.HEIGHT_SIZE;
        loadWord(path);
    }

    public Game getGame(){
        return game;
    }
    public void tick(){

    }

    public void render(Graphics g){

        for(int y = 0; y < 20; y++){
            for(int x = 0; x < 20; x++) {
                if (titles[x][y] == SpriteType.BRICK_WALL) {
                    g.drawImage(Assets.brickwall.getSpriteImage(), (int) (x * 32 - game.getCameraGame().getxOffset()), (int) (y * 32 - game.getCameraGame().getyOffset()), 3, 3, null);
                } else {
                    SpriteType spriteType  = titles[x][y];
                    Sprite sprite = null;

                    switch (spriteType){
                        case FOOD_GREEN:{
                            sprite = Assets.foodGreen;
                            break;
                        }
                        case FOOD_PINK:{
                            sprite = Assets.foodPink;
                            break;
                        }
                        case FOOD_BLACK:{
                            sprite = Assets.foodBlack;
                            break;
                        }
                        case FOOD_RED:{
                            sprite = Assets.foodRed;
                            break;
                        }
                        case FOOD_BLUE:{
                            sprite = Assets.foodBlue;
                            break;
                        }
                        case FOOD_YELLOW:{
                            sprite = Assets.foodYellow;
                            break;
                        }
                    }
                    g.drawImage(sprite.getSpriteImage(), (int) (x * 32 - game.getCameraGame().getxOffset()), (int) (y * 32 - game.getCameraGame().getyOffset()), null);
                }
            }
        }

    }

    public void setTitles(SpriteType[][]titles ) {
        this.titles = titles;
    }

    private void loadWord(String path){
        titles = new SpriteType[width][height];

        for(int x  = 0; x < width; x ++){
            for(int y = 0; y < height; y++){
                titles[x][y] = SpriteType.BRICK_WALL;
            }
        }
        titles[1][4] = SpriteType.FOOD_GREEN;
        titles[2][10] = SpriteType.FOOD_PINK;
        titles[4][15] = SpriteType.FOOD_BLUE;
        titles[5][5] = SpriteType.FOOD_YELLOW;
        titles[6][8] = SpriteType.FOOD_BLACK;
        titles[9][10] = SpriteType.FOOD_RED;

    }

}
