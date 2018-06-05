package GameGUI.com.snake.dev.game.entities.creatures;

import GameGUI.com.snake.GameClient.Dot;
import GameGUI.com.snake.dev.define.Define;
import GameGUI.com.snake.dev.define.KeyDirectionType;
import GameGUI.com.snake.dev.define.SpriteType;
import GameGUI.com.snake.dev.game.gfx.Assets;
import GameGUI.com.snake.dev.game.model.Sprite;
import GameGUI.com.snake.dev.game.titlegame.Game;
import GameGUI.com.snake.dev.game.titlegame.worlds.World;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Player extends Creature {

    private Game game;
    private long Id;
    private int score = 0;
    private List<SnakeDot> snake = new ArrayList<SnakeDot>();
    private World world;
    private List<SnakeDot> gamePlay = null;

    public List<SnakeDot> getSnake() {
        return snake;
    }

    public void setSnake(List<SnakeDot> snake) {
        this.snake = snake;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public Player(Game game, float x, float y) {
        super(game, x, y);
        this.game = game;
        initSnake(x, y);
        // this.world = world;
    }

    public List<SnakeDot> getGamePlay() {
        return gamePlay;
    }

    public void setGamePlay(List<SnakeDot> gamePlay) {
        this.gamePlay = gamePlay;
    }

    public void initSnake(float x, float y) {
        float _x = x;
        float _y = y;
        // Assets.snakeHead.setSpriteImage(ImageRotation.rotateClockwise90(Assets.snakeHead.getSpriteImage()));
        snake.add(new SnakeDot(x, y, Assets.snakeDotYellow));

        for (int indexDot = 1; indexDot < this.size; indexDot++) {
            _x -= 16;
            SnakeDot dot = new SnakeDot(_x, _y, Assets.snakeDotGreen);
            snake.add(dot);
        }
    }

    private int currentSpaceUp = 0;
    private int currentSpaceDown = 0;
    private int currentSpaceLeft = 0;
    private int currentSpaceRight = 0;

    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean rightDirection = false;
    private boolean leftDirection = false;

    private void setValueForCurrentSpace(KeyDirectionType keyDirectionType) {
        if (keyDirectionType == KeyDirectionType.UP) {
            currentSpaceDown = 0;
            currentSpaceLeft = 0;
            currentSpaceRight = 0;
            currentSpaceUp += Define.SnakeSize.JUMP_SPACE_SIZE;
            return;
        }

        if (keyDirectionType == KeyDirectionType.DOWN) {
            currentSpaceDown += Define.SnakeSize.JUMP_SPACE_SIZE;
            currentSpaceLeft = 0;
            currentSpaceRight = 0;
            currentSpaceUp = 0;
            return;
        }
        if (keyDirectionType == KeyDirectionType.LEFT) {
            currentSpaceDown = 0;
            currentSpaceLeft += Define.SnakeSize.JUMP_SPACE_SIZE;
            currentSpaceRight = 0;
            currentSpaceUp = 0;
            return;
        }
        if (keyDirectionType == KeyDirectionType.RIGTH) {
            currentSpaceDown = 0;
            currentSpaceLeft = 0;
            currentSpaceRight += Define.SnakeSize.JUMP_SPACE_SIZE;
            currentSpaceUp += 0;
            return;
        }

    }

    public void move() {

        SnakeDot head = snake.get(0);
        if (game.getKeyManager() != null) {

            if (game.getKeyManager().upDirection && !downDirection) {

                upDirection = true;
                rightDirection = false;
                leftDirection = false;

                head.y -= Define.SnakeSize.JUMP_SPACE_SIZE;
                setValueForCurrentSpace(KeyDirectionType.UP);

            } else if (game.getKeyManager().downDriection && !upDirection) {
                downDirection = true;
                leftDirection = false;
                rightDirection = false;
                //head.y += GameGUI.com.snake.Define.SnakeSize.DOT_SIZE;
                //head.y += GameGUI.com.snake.Define.SnakeSize.DOT_SIZE;

                head.y += Define.SnakeSize.JUMP_SPACE_SIZE;
                setValueForCurrentSpace(KeyDirectionType.DOWN);
            } else if (game.getKeyManager().leftDirection && !rightDirection) {

                leftDirection = true;
                downDirection = false;
                upDirection = false;

                // head.x -= GameGUI.com.snake.Define.SnakeSize.DOT_SIZE;
                head.x -= Define.SnakeSize.JUMP_SPACE_SIZE;
                setValueForCurrentSpace(KeyDirectionType.LEFT);
            } else if (game.getKeyManager().rightDirection && !leftDirection) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;

                //head.x += GameGUI.com.snake.Define.SnakeSize.DOT_SIZE;
                head.x += Define.SnakeSize.JUMP_SPACE_SIZE;
                setValueForCurrentSpace(KeyDirectionType.RIGTH);
            } else if (game.getKeyManager().stopGame) {
                game.setRunning(false);
            }
        }

//        if(checkIsCollisionWithOtherSnake()){
//            System.out.println("stop ");
//            game.setRunning(false);
//            return;
//        }
        if (checkIsCollisionWithItSelf()) {
            //game.getConnection().sendExitRoomByCharacterInfo(game.getRoomgameCode(), game.getIdInRoomGame());
            game.setIsRemovedRoomgame(true);
            return;
        }
        if (conllisionWithItems()) {
            score++;
        }

        if (currentSpaceUp > Define.SnakeSize.DOT_SIZE
                || currentSpaceRight > Define.SnakeSize.DOT_SIZE
                || currentSpaceLeft > Define.SnakeSize.DOT_SIZE
                || currentSpaceDown > Define.SnakeSize.DOT_SIZE) {

            currentSpaceUp = 0;
            currentSpaceRight = 0;
            currentSpaceDown = 0;
            currentSpaceLeft = 0;

            for (int index = snake.size() - 1; index > 0; index--) {
                SnakeDot currentDot = snake.get(index);
                SnakeDot previousDot = snake.get(index - 1);

                currentDot.x = previousDot.x;
                currentDot.y = previousDot.y;
            }
        }

    }

    @Override
    public void tick() {
        if (!game.isIsRemovedRoomgame()) {
            move();
        }
    }

    @Override
    public void render(Graphics g) {
        if (!game.isIsRemovedRoomgame()) {
            for (int index = 0; index < snake.size(); index++) {
                SnakeDot dot = snake.get(index);
                g.drawImage(dot.sprite.getSpriteImage(), (int) dot.x, (int) dot.y, null);

            }
        }

        if (gamePlay != null) {
            for (int anotherSnakeIndex = 0; anotherSnakeIndex < this.gamePlay.size(); anotherSnakeIndex++) {
                SnakeDot dot = gamePlay.get(anotherSnakeIndex);
                g.drawImage(dot.sprite.getSpriteImage(), (int) dot.x, (int) dot.y, null);
            }
        }

        if (g instanceof Graphics2D) {
            String scoreString = "SCORE :" + score;
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Font font = new Font("Serif", Font.PLAIN, 30);
            AttributedString as1 = new AttributedString(scoreString);
            as1.addAttribute(TextAttribute.FONT, font);
            as1.addAttribute(TextAttribute.FOREGROUND, Color.red, 0, 6);
            g2d.drawString(as1.getIterator(), Define.BoardSize.WIDTH_SIZE / 2 - 50, 50);

        }

    }

    protected boolean conllisionWithItems() {

        if (snake == null || snake.size() == 0) {
            return false;
        }

        if (gamePlay == null) {
            return false;
        }

        SnakeDot head = this.snake.get(0);
        for (SnakeDot tile : gamePlay) {

            SpriteType spriteType = tile.sprite.getSpriteType();

            if (spriteType == SpriteType.SNAKE_DOT_BLACK
                    || spriteType == SpriteType.SNAKE_DOT_GREEN
                    || spriteType == SpriteType.SNAKE_DOT_BLUE
                    || spriteType == SpriteType.SNAKE_DOT_PINK
                    || spriteType == SpriteType.SNAKE_DOT_RED
                    || spriteType == SpriteType.SNAKE_DOT_YELLOW
                    || spriteType == SpriteType.BRICK_WALL) {
                continue;
            }
            int leftLocation = (int) head.x - 16;
            int rightLocationl = (int) head.x + 16;
            int aboveLocation = (int) head.y + 16;
            int belowLocation = (int) head.y - 16;

            if (leftLocation < tile.x && tile.x < rightLocationl
                    && belowLocation < tile.y && tile.y < aboveLocation) {
                SnakeDot dotPrefixLast = snake.get(this.snake.size() - 2);
                SnakeDot dotLast = snake.get(this.snake.size() - 1);
                int tempX = 0, tempY = 0;
                if (dotPrefixLast.x == dotLast.x) {
                    tempX = (int) dotLast.x;
                    if (dotLast.y < dotPrefixLast.y) {
                        tempY = (int) dotLast.y - 16;
                    } else {
                        tempY = (int) dotLast.y + 16;
                    }
                } else {
                    tempY = (int) dotLast.y;
                    if (dotLast.x < dotPrefixLast.x) {
                        tempX = (int) dotLast.x - 16;
                    } else {
                        tempX = (int) dotLast.x + 16;
                    }
                }
                Sprite sprite = Assets.getSnakeDotByFoodType(spriteType);
                SnakeDot dot = new SnakeDot(tempX, tempY, sprite);

                game.getConnection().sendUpdateMap(new Dot((int) tile.x, (int) tile.y, tile.sprite.getSpriteType()), game.getRoomgameCode());
                if (game.getConnection().checkIsWhenServerUpdateMapSucessfully()) {
                    this.snake.add(dot);
                }
                return true;
            }

        }
        return false;

    }


    public float distance(float xFirstPoint, float yFirstPoint, float xSecondPoint, float ySecondPoint) {
        return (float) Math.sqrt(Math.pow(xFirstPoint - xSecondPoint, 2) + Math.pow(yFirstPoint - ySecondPoint, 2));
    }

    public boolean checkIsCollisionWithItSelf() {

        SnakeDot head = snake.get(0);
        for (int dotIndex = 4; dotIndex < snake.size(); dotIndex++) {
            SnakeDot dot = snake.get(dotIndex);
            if (distance(head.x, head.y, dot.x, dot.y) < 16){
                return true;
            }
        }
        return false;
    }
    
    

}
