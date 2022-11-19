package prefab.information;

import java.awt.image.BufferedImage;

import model.NiborPainter;

public class Visual {
			
	private BufferedImage image;
	private State state = null;
    
    private int x, y;
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
        
    public Visual(int x, int y, BufferedImage image) {
        
        this.x = x;
        this.y = y;
        this.image = image;
    }
    
    public void setX(int x) {
    	 this.x = x;
    } 
    
    public void setY(int y) {
   	 this.y = y;
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
        return x*NiborPainter.TILE_LENGTH - directionX*shift ;
    } 

    public int getY() {
        return NiborPainter.SCREEN_HEIGHT - NiborPainter.TILE_LENGTH*(y+1) + directionY*shift;
    } 

    public BufferedImage getBufferedImage() {
        return image;
    } 
    
    public void resetShift() {
    	shift = defaultShift;
    }
    
    public void updateMoveShift() {
    	//System.out.println("DIRECTION, SHIF =  "+directionX+","+shift);
    	if (shift <= 0) {
    		shift = defaultShift;
    	}
    	shift = shift - 6;
    	//System.out.println("POSITION X = "+(x*NiborPainter.TILE_LENGTH - directionX*shift));

    }
    
}
