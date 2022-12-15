package prefab.rendering;

import java.awt.image.BufferedImage;

import model.NiborPainter;

public final class Visual {

	private final BufferedImage image;
    /**
     * si true : symetrie verticale de l'image
     * sinon false
     */
    private final boolean mirorV;
    /**
     * si true : symetrie horizontale de l'image
     * sinon false
     */
    private final boolean mirorH;
    private final int x, y;

    private Visual(int x, int y, BufferedImage image, boolean mirorV, boolean mirorH) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.mirorV = mirorV;
        this.mirorH = mirorH;
    }

    public static Visual createWithExactCoord(int x, int y, BufferedImage image, boolean mirorV, boolean mirorH) throws Exception {
        if (image == null) {
            throw new NullPointerException("l'image est nulle");
        }
        return new Visual(x, y, image, mirorV, mirorH);
    }

    public static Visual createWithExactCoord(int x, int y, BufferedImage image) throws Exception {
        return createWithExactCoord(x, y, image, false, false);
    }

    public static Visual createWithGameCoord(int x, int y, int deltaX, int deltaY, BufferedImage image,  boolean mirorV, boolean mirorH) throws Exception {
        x = x*NiborPainter.TILE_LENGTH + deltaX;
        y = NiborPainter.SCREEN_HEIGHT - (y+1)*NiborPainter.TILE_LENGTH - deltaY;
        return createWithExactCoord(x, y, image, mirorV, mirorH);
    }

    public static Visual createWithGameCoord(int x, int y, int deltaX, int deltaY, BufferedImage image) throws Exception {
        return createWithGameCoord(x, y, deltaX, deltaY, image, false, false);
    } 

    public static Visual createWithGameCoord(int x, int y, BufferedImage image, boolean mirorV, boolean mirorH) throws Exception {
        return createWithGameCoord(x, y, 0, 0, image, mirorV, mirorH);
    }

    public static Visual createWithGameCoord(int x, int y, BufferedImage image) throws Exception {
        return createWithGameCoord(x, y, image, false, false);
    }

  

    public int getX() {
        int deltaIfMiror = 0;
        if (mirorV) {
            deltaIfMiror = image.getWidth();
        }
        return x + deltaIfMiror;
    }

    public int getY() {
        int deltaIfMiror = 0;
        if (mirorH) {
            deltaIfMiror = image.getWidth();
        }
        return y + deltaIfMiror;
    }

    public BufferedImage getBufferedImage() {
        return image;
    }

    public int getWidth() {
        if (mirorV) {
            return - image.getWidth();
        }
        return image.getWidth();
    }

    public int getHeight() {
        if (mirorH) {
            return - image.getHeight();
        }
        return image.getHeight();
    }
}
