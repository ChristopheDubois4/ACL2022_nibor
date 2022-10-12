package game;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Teacher extends Character implements Merchant{

    public Teacher(Position position, HashMap<State, BufferedImage> graphics, String objectName, int verticalHitBox, int horizontalHitBox) {
        super(position, graphics, objectName, verticalHitBox, horizontalHitBox);
    }

    @Override
    public String getDialogue() {
        return null;
    }

    @Override
    protected void initCharacteristic() {
        
    }

    @Override
    public void die() {
        
    }

    @Override
    public void draw() {
        
    }

    @Override
    public void move() {
        
    }
    
}
