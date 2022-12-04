package prefab.information;

import java.awt.image.BufferedImage;

import manager.WorldManager;
import model.NiborPainter;

public class Visual {
			
	private BufferedImage image;    
    /**
     * si true : symetrie verticale de l'image
     * sinon false
     */
    private boolean mirorV;
    /**
     * si true : symetrie horizontale de l'image
     * sinon false
     */
    private boolean mirorH;

    
    private int x, y;
    private int deltaX = 0 , deltaY = 0;
    /**
     * direction horizontale
     * gauche : -1
     * droite : 1
     * immobile : 0
     */
    private int directionX = 0;
    /**
     * direction horizontale
     * bas : -1
     * haut : 1
     * immobile : 0
     */
    private int directionY = 0;
    /**
     * deplacement
     */
    private static final int defaultShift = 60;  
    private int shift = defaultShift;   
        

    public Visual(int x, int y, int deltaX, int deltaY, BufferedImage image, boolean mirorV, boolean mirorH) {
        this.x = x;
        this.y = y;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.image = image;
        this.mirorV = mirorV;
        this.mirorH = mirorH;
    }
    
    public Visual(int x, int y, int deltaX, int deltaY, BufferedImage image) {
        this(x, y, deltaX, deltaY, image, false, false);
    }

    public Visual(int x, int y, BufferedImage image, boolean mirorV, boolean mirorH) {
        this(x, y, 0, 0, image, mirorV, mirorH);
    }

    public Visual(int x, int y, BufferedImage image) {
        this(x, y, image, false, false);
    }
        
    public void setX(int x) {
    	 this.x = x;
    } 
        
    public void setY(int y) {
   	    this.y = y;
   } 

   public void setMirorV(boolean mirorV) {
    this.mirorV = mirorV;
} 

   public void setDeltaPos(int deltaX, int deltaY) {
    this.deltaX = deltaX;
    this.deltaY = deltaY;
} 

    public void setBufferedImage(BufferedImage image) {
    	this.image = image;
    } 
    
    public void setDirection(int directionX, int directionY) {
    	this.directionX = directionX;
    	this.directionY = directionY;
    } 
    
    // On places de positons pas des déplacements

    public int getX() {
        int deltaIfMiror = 0;
        if (mirorV) {
            deltaIfMiror = image.getWidth(); 
        }
        return x*NiborPainter.TILE_LENGTH - directionX*shift + deltaX + deltaIfMiror;
    } 

    public int getY() {
        return NiborPainter.SCREEN_HEIGHT - NiborPainter.TILE_LENGTH*(y+1) + directionY*shift - deltaY;
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
        return image.getHeight();
    }
    
    public void resetShift() {
    	shift = defaultShift;
    }
    
    public void updateMoveShift() {
    	if (shift <= 0) {
    		shift = defaultShift;
    	}
    	shift = shift - (int) defaultShift/WorldManager.IMAGES_PER_MOVE;
    }
}
