package prefab.information;

import java.awt.image.BufferedImage;

public class Visual {

    private int x, y;
    private BufferedImage image;
    
    public Visual(int x, int y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public int getX() {
        return x;
    } 

    public int getY() {
        return y;
    } 

    public BufferedImage getBufferedImage() {
        return image;
    } 
}
