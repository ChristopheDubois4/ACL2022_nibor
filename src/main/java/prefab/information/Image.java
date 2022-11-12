package prefab.information;

import java.awt.image.BufferedImage;

public class Image {
    
    private BufferedImage graphics;
    private int lenghtX;
    private int lenghtY;

    public Image(BufferedImage graphics, int lenghtX, int lenghtY) {
        this.graphics = graphics;
        this.lenghtX = lenghtX;
        this.lenghtY = lenghtY;
    }

    public BufferedImage getBufferedImage() {
        return graphics;
    }

    public int getLenghtX() {
        return lenghtX;
    }

    public int getLenghtY() {
        return lenghtY;
    }
}
