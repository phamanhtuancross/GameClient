package snake.dev.game.model;

import snake.dev.define.SpriteType;
import snake.dev.game.controller.ImageLoader;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Sprite implements Serializable{
    private SpriteType spriteType;
    private BufferedImage spriteImage;

    public SpriteType getSpriteType() {
        return spriteType;
    }

    public void setSpriteType(SpriteType spriteType) {
        this.spriteType = spriteType;
    }

    public BufferedImage getSpriteImage() {
        return spriteImage;
    }

    public void setSpriteImage(BufferedImage spriteImage) {
        this.spriteImage = spriteImage;
    }

    public Sprite(Sprite sprite){
        this.spriteType = sprite.spriteType;
        this.spriteImage = sprite.spriteImage;
    }
    public Sprite(SpriteType _spriteType, String path){
        this.spriteType = _spriteType;
        this.spriteImage = ImageLoader.loadImage(path);
    }


}
