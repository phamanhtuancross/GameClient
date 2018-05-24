package snake.dev.game.entities.creatures;

import snake.dev.define.Define;
import snake.dev.define.KeyDirectionType;
import snake.dev.define.SpriteType;
import snake.dev.game.gfx.Assets;
import snake.dev.game.model.Sprite;
import snake.dev.game.titlegame.Game;
import snake.dev.game.titlegame.worlds.World;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;


public class Player extends Creature {

    private Game game;
    private int score = 0;
    private List<SnakeDot> snake = new ArrayList<SnakeDot>();
    private World world;

    public List<SnakeDot> getSnake() {
        return snake;
    }

    public void setSnake(List<SnakeDot> snake) {
        this.snake = snake;
    }

    public Player(World world, Game game, float x, float y) {
        super(game,x, y);
        this.game = game;
        initSnake(x,y);
        this.world = world;
        System.out.println("WORLD DATA FORM SERVER IN PLAYER : " + world );
        System.out.println("SNAKE DATA FORM SERVER IN PLAYER : " + snake.get(1).x);
    }


    public void initSnake(float x, float y){
        float _x = x;
        float _y = y;
       // Assets.snakeHead.setSpriteImage(ImageRotation.rotateClockwise90(Assets.snakeHead.getSpriteImage()));
        snake.add(new SnakeDot(x,y,Assets.snakeDotYellow));

        for(int indexDot = 1; indexDot < this.size; indexDot++){
            _x -= 16;
            SnakeDot dot = new SnakeDot(_x,_y,Assets.snakeDotGreen);
            snake.add(dot);
        }
    }

    private int currentSpaceUp = 0;
    private int currentSpaceDown = 0;
    private int currentSpaceLeft = 0;
    private int currentSpaceRight = 0;

    private void setValueForCurrentSpace(KeyDirectionType keyDirectionType){
       if(keyDirectionType == KeyDirectionType.UP){
           currentSpaceDown = 0;
           currentSpaceLeft = 0;
           currentSpaceRight = 0;
           currentSpaceUp += Define.SnakeSize.JUMP_SPACE_SIZE;
           return;
       }

        if(keyDirectionType == KeyDirectionType.DOWN){
            currentSpaceDown += Define.SnakeSize.JUMP_SPACE_SIZE;
            currentSpaceLeft = 0;
            currentSpaceRight = 0;
            currentSpaceUp = 0;
            return;
        }
        if(keyDirectionType == KeyDirectionType.LEFT){
            currentSpaceDown = 0;
            currentSpaceLeft += Define.SnakeSize.JUMP_SPACE_SIZE;
            currentSpaceRight = 0;
            currentSpaceUp = 0;
            return;
        }
        if(keyDirectionType == KeyDirectionType.RIGTH){
            currentSpaceDown = 0;
            currentSpaceLeft = 0;
            currentSpaceRight += Define.SnakeSize.JUMP_SPACE_SIZE;
            currentSpaceUp += 0;
            return;
        }

    }

    public  void move(){
        if(
                currentSpaceUp > Define.SnakeSize.DOT_SIZE
                        || currentSpaceRight > Define.SnakeSize.DOT_SIZE
                        || currentSpaceLeft > Define.SnakeSize.DOT_SIZE
                        || currentSpaceDown > Define.SnakeSize.DOT_SIZE){

            currentSpaceUp = 0;
            currentSpaceRight = 0;
            currentSpaceDown = 0;
            currentSpaceLeft = 0;

            for(int index = snake.size() - 1; index > 0; index --){
                SnakeDot currentDot = snake.get(index);
                SnakeDot previousDot  = snake.get(index - 1);

                currentDot.x = previousDot.x;
                currentDot.y = previousDot.y;
            }
        }
        SnakeDot head = snake.get(0);
        if(game.getKeyManager().upDirection){
            //head.y -= Define.SnakeSize.DOT_SIZE;
            head.y -= Define.SnakeSize.JUMP_SPACE_SIZE;
            setValueForCurrentSpace(KeyDirectionType.UP);
        }

        if(game.getKeyManager().downDriection){
            //head.y += Define.SnakeSize.DOT_SIZE;
            //head.y += Define.SnakeSize.DOT_SIZE;
            head.y += Define.SnakeSize.JUMP_SPACE_SIZE;
            setValueForCurrentSpace(KeyDirectionType.DOWN);
        }

        if(game.getKeyManager().leftDirection){
            // head.x -= Define.SnakeSize.DOT_SIZE;
            head.x -= Define.SnakeSize.JUMP_SPACE_SIZE;
            setValueForCurrentSpace(KeyDirectionType.LEFT);
        }

        if(game.getKeyManager().rightDirection){
            //head.x += Define.SnakeSize.DOT_SIZE;
            head.x += Define.SnakeSize.JUMP_SPACE_SIZE;
            setValueForCurrentSpace(KeyDirectionType.RIGTH);
        }

        if(game.getKeyManager().stopGame){
            game.setRunning(false);
        }
        if (conllisionWithItems((int)head.x,(int)head.y)){
            score++;
        }
    }
    @Override
    public void tick() {
        move();
    }
    @Override
    public void render(Graphics g) {

       for(int index = 0; index < snake.size(); index++){
            SnakeDot dot = snake.get(index);
            g.drawImage(dot.sprite.getSpriteImage(),(int)dot.x,(int)dot.y,null);
           if(g instanceof Graphics2D)
           {
               String scoreString = "SCORE :" + score;
               Graphics2D g2d = (Graphics2D)g;
               g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
               Font font = new Font("Serif", Font.PLAIN, 30);
               AttributedString as1 = new AttributedString(scoreString);
               as1.addAttribute(TextAttribute.FONT, font);
               as1.addAttribute(TextAttribute.FOREGROUND, Color.red, 0, 6);
               g2d.drawString(as1.getIterator(), Define.BoardSize.WIDTH_SIZE/2 -  50, 50);

           }
       }
    }

    protected boolean conllisionWithItems(int xPosition, int yPosition){
        int x = (int)Math.round((double)xPosition/32);
        int y = (int)Math.round((double)yPosition/32);

        if(x < 0 ||  y < 0 || x >= world.getTitles().length || y >= world.getTitles()[0].length){
            return false;
        }
        if(world.getTitles()[x][y] != SpriteType.BRICK_WALL){
            Sprite sprite  = null;
            SpriteType spriteType = world.getTitles()[x][y];

            switch (spriteType){
                case FOOD_GREEN:{
                    sprite = Assets.snakeDotGreen;
                    break;
                }
                case FOOD_PINK:{
                    sprite = Assets.snakeDotPink;
                    break;
                }
                case FOOD_BLUE:{
                    sprite = Assets.snakeDotBlue;
                    break;
                }
                case FOOD_RED:{
                    sprite = Assets.snakeDotRed;
                    break;
                }
                case FOOD_YELLOW:{
                    sprite = Assets.snakeDotYellow;
                    break;
                }
                case FOOD_BLACK:{
                    sprite = Assets.snakeDotBlack;
                    break;
                }
                default:{
                    break;
                }
            }

            world.getTitles()[x][y] = SpriteType.BRICK_WALL;
            SnakeDot dotPrefixLast = snake.get(snake.size() -2);
            SnakeDot dotLast = snake.get(snake.size() -1);
            float tempX = 0, tempY = 0;
            if(dotPrefixLast.x == dotLast.x){
                tempX = dotLast.x;
                if(dotLast.y < dotPrefixLast.y){
                    tempY =  dotLast.y - 16;
                }else{
                    tempY = dotLast.y + 16;
                }
            }
            else{
                tempY = dotLast.y;
                if(dotLast.x < dotPrefixLast.x){
                    tempX  = dotLast.x - 16;
                }
                else{
                    tempX = dotLast.x + 16;
                }
            }
            SnakeDot dot = new SnakeDot(tempX,tempY,sprite);
            snake.add(dot);
            return true;
        }
        return false;
    }
}
