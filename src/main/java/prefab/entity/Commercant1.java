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

    public Commercant1(Position position, HashMap<State, BufferedImage> graphics, String objectName, int horizontalHitBox, int verticalHitBox) {
        super(position, graphics, objectName, horizontalHitBox, verticalHitBox);
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
    
}
