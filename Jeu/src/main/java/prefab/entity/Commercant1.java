package prefab.entity;

import prefab.information.Position;
import prefab.rendering.Animation;


/**
 * présente un personnage particulié qui est marchant
 * (Nom à changer)
 */
public class Commercant1 extends Character implements Merchant{

    public Commercant1(Position position, Animation animation, int horizontalHitBox, int verticalHitBox, String name) throws CloneNotSupportedException {
        super(position, animation, horizontalHitBox, verticalHitBox, name);
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
