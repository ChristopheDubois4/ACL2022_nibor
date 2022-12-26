package prefab.rendering;

import java.awt.image.BufferedImage;

import model.NiborPainter;
import prefab.information.Layer;

public final class Visual implements Comparable<Visual>{

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
    // Coordonnées de l'animation
    private final int x, y;
    // Layer
    private final Layer layer;

    private Visual(int x, int y, BufferedImage image, boolean mirorV, boolean mirorH, Layer layer) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.mirorV = mirorV;
        this.mirorH = mirorH;
        this.layer = layer;
    }

    public static Visual createWithExactCoord(int x, int y, BufferedImage image, boolean mirorV, boolean mirorH, Layer layer) throws Exception {
        if (image == null) {
            throw new NullPointerException("l'image est nulle");
        }
        return new Visual(x, y, image, mirorV, mirorH, layer);
    }

    public static Visual createWithExactCoord(int x, int y, BufferedImage image, boolean mirorV, boolean mirorH) throws Exception {
        if (image == null) {
            throw new NullPointerException("l'image est nulle");
        }
        return new Visual(x, y, image, mirorV, mirorH, Layer.DEFAULT);
    }

    public static Visual createWithExactCoord(int x, int y, BufferedImage image) throws Exception {
        return createWithExactCoord(x, y, image, false, false, Layer.DEFAULT);
    }

    public static Visual createWithGameCoord(int x, int y, int deltaX, int deltaY, BufferedImage image,  boolean mirorV, boolean mirorH, Layer layer) throws Exception {
        x = x*NiborPainter.TILE_LENGTH + deltaX;
        y = NiborPainter.SCREEN_HEIGHT - (y+1)*NiborPainter.TILE_LENGTH - deltaY;
        return createWithExactCoord(x, y, image, mirorV, mirorH, layer);
    }

    public static Visual createWithGameCoord(int x, int y, int deltaX, int deltaY, BufferedImage image,  boolean mirorV, boolean mirorH) throws Exception {
        x = x*NiborPainter.TILE_LENGTH + deltaX;
        y = NiborPainter.SCREEN_HEIGHT - (y+1)*NiborPainter.TILE_LENGTH - deltaY;
        return createWithExactCoord(x, y, image, mirorV, mirorH, Layer.DEFAULT);
    }

    public static Visual createWithGameCoord(int x, int y, int deltaX, int deltaY, BufferedImage image, Layer layer) throws Exception {
        return createWithGameCoord(x, y, deltaX, deltaY, image, false, false, layer);
    } 

    public static Visual createWithGameCoord(int x, int y, int deltaX, int deltaY, BufferedImage image) throws Exception {
        return createWithGameCoord(x, y, deltaX, deltaY, image, false, false);
    } 

    public static Visual createWithGameCoord(int x, int y, BufferedImage image, boolean mirorV, boolean mirorH, Layer layer) throws Exception {
        return createWithGameCoord(x, y, 0, 0, image, mirorV, mirorH, layer);
    }

    public static Visual createWithGameCoord(int x, int y, BufferedImage image, boolean mirorV, boolean mirorH) throws Exception {
        return createWithGameCoord(x, y, 0, 0, image, mirorV, mirorH);
    }

    public static Visual createWithGameCoord(int x, int y, BufferedImage image, Layer layer) throws Exception {
        return createWithGameCoord(x, y, image, false, false, layer);
    }

    public static Visual createWithGameCoord(int x, int y, BufferedImage image) throws Exception {
        return createWithGameCoord(x, y, image, false, false);
    }

    // AVEC LAYER
    

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

    public Layer getLayer() {
        return layer;
    }

    @Override
    public int compareTo(Visual o) {
        if (this.layer == Layer.BACKGROUND || o.getLayer() == Layer.FOREGROUND ) {
            return -1;
        }
        if (this.layer == Layer.FOREGROUND || o.getLayer() == Layer.BACKGROUND) {
            return 1;
        }
        return this.getY() + this.getHeight() - o.getY() - o.getHeight();
    }
}
