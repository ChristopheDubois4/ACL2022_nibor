package prefab.rendering;

import java.awt.image.BufferedImage;

import manager.Utilities;
import model.NiborPainter;

public final class Sprite {
    
    private final BufferedImage[] images;
    private final int numberOfImages;
    private boolean mirorV;
    private boolean mirorH;


    /** changement d'image tout les "animationSpeed*10" ms */
    private int animationSpeed;

    private Sprite(BufferedImage spriteSheet, int animationSpeed, boolean mirorV, boolean mirorH, int lengthX) {
        this.mirorV = mirorV;
        this.mirorH = mirorH;
        System.out.println("spriteSheet.getWidth()"+spriteSheet.getWidth());
        System.out.println("lengthX"+lengthX);

        numberOfImages = (int) (spriteSheet.getWidth() / lengthX);
        this.animationSpeed  = animationSpeed;
        images = new BufferedImage[numberOfImages];
        for (int i = 0; i < numberOfImages; i++) {
             images[i] = spriteSheet.getSubimage(i*lengthX, 0, lengthX, spriteSheet.getHeight());
        }
    }

    // ________________________________________
    // __________ CREATE BIG SPRITES __________

    public static Sprite createMirorBigSprite(BufferedImage spriteSheet, int animationSpeed, int lengthX, boolean mirorV, boolean mirorH) throws Exception {
        if (spriteSheet == null) {
            throw new NullPointerException("la Sprite Sheet est nulle");
        }
        return new Sprite(spriteSheet, animationSpeed, mirorV, mirorH, lengthX);
    }

    public static Sprite createMirorBigSprite(String pathStr, int animationSpeed, int lengthX, boolean mirorV, boolean mirorH) throws Exception {
        BufferedImage spriteSheet = Utilities.getImage(pathStr);
        return createMirorBigSprite(spriteSheet, animationSpeed, lengthX, mirorV, mirorH);
    }
    
    public static Sprite createBigSprite(BufferedImage spriteSheet, int animationSpeed, int lengthX) throws Exception {
        return createMirorBigSprite(spriteSheet, animationSpeed, lengthX, false, false);
    }

    public static Sprite createBigSprite(String pathStr, int animationSpeed, int lengthX) throws Exception {
        return createMirorBigSprite(pathStr, animationSpeed, lengthX, false, false);
    }

    public static Sprite createBigSprite(String pathStr, int animationSpeed) throws Exception {
        BufferedImage spriteSheet = Utilities.getImage(pathStr);
        return createMirorBigSprite(spriteSheet, animationSpeed, spriteSheet.getWidth(), false, false);
    }

    // ___________________________________________
    // __________ CREATE NORMAL SPRITES __________
    
    public static Sprite createMirorSprite(BufferedImage spriteSheet, int animationSpeed, boolean mirorV, boolean mirorH) throws Exception {
        return createMirorBigSprite(spriteSheet, animationSpeed, 60, mirorV, mirorH);
    }

    public static Sprite createMirorSprite(String pathStr, int animationSpeed, boolean mirorV, boolean mirorH) throws Exception {
        return createMirorBigSprite(pathStr, animationSpeed, 60, mirorV, mirorH);
    }
    
    public static Sprite createSprite(BufferedImage spriteSheet, int animationSpeed) throws Exception {
        return createBigSprite(spriteSheet, animationSpeed, 60);
    }

    public static Sprite createSprite(String pathStr, int animationSpeed) throws Exception {
        return createBigSprite(pathStr, animationSpeed, 60);
    }


    public BufferedImage getImage(int index) {
        return images[index];
    }
    public int getAnimationSpeed() {
        return animationSpeed;
    }

    public int getNumberOfImages() {
        return numberOfImages;
    }

    public boolean getMirorV() {
        return mirorV;   
    }

    public boolean getMirorH() {
        return mirorH;
    }

}
