package prefab.entity;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import prefab.information.Position;
import prefab.information.State;

/**
 * présente un personnage particulié qui est marchant
 * (Nom à changer)
 */
public class Commercant1 extends Character implements Merchant{

    public Commercant1(Position position, HashMap<State, BufferedImage> graphics, String objectName, int verticalHitBox, int horizontalHitBox) {
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
