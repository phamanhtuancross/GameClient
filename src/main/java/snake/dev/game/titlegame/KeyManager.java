package snake.dev.game.titlegame;

import snake.dev.define.KeyDirectionType;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    public boolean upDirection,downDriection,leftDirection,rightDirection;
    public boolean stopGame;
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode){
            case KeyEvent.VK_RIGHT: case KeyEvent.VK_D:{
                System.out.println("Right direction...");
                setKeyPressed(KeyDirectionType.RIGTH);
                break;
            }

            case KeyEvent.VK_LEFT: case KeyEvent.VK_A:{
                System.out.println("Left direction...");
                setKeyPressed(KeyDirectionType.LEFT);
                break;
            }

            case KeyEvent.VK_UP: case KeyEvent.VK_W:{
                System.out.println("Up direction....");
                setKeyPressed(KeyDirectionType.UP);
                break;
            }

            case KeyEvent.VK_DOWN: case KeyEvent.VK_S:{
                System.out.println("Down direction....");
                setKeyPressed(KeyDirectionType.DOWN);
                break;
            }

            case KeyEvent.VK_SPACE:{
                System.out.println("Space direction....");
                stopGame = !stopGame;
                break;
            }
            default:{
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void setKeyPressed(KeyDirectionType keyDirectionType){
        upDirection = false;
        downDriection = false;
        rightDirection = false;
        leftDirection = false;
        stopGame = false;

        if(keyDirectionType == KeyDirectionType.UP){
            upDirection = true;
            return;
        }

        if(keyDirectionType == KeyDirectionType.DOWN){
            downDriection = true;
            return;
        }

        if(keyDirectionType == KeyDirectionType.LEFT){
            leftDirection = true;
            return;
        }

        if(keyDirectionType == KeyDirectionType.RIGTH){
            rightDirection = true;
            return;
        }

    }
}
