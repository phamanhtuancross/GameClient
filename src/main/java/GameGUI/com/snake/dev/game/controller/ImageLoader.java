package GameGUI.com.snake.dev.game.controller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoader {
    public static BufferedImage loadImage(String path){
        BufferedImage bufferedImage = null;
        try{
            System.out.println("URl" + ImageLoader.class.getResource(path));
            bufferedImage = ImageIO.read(ImageLoader.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }
}
