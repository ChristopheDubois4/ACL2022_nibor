package game;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

public class NrjStudent extends Character implements Enemy{

    public NrjStudent(Position position, HashMap<State, BufferedImage> graphics, String objectName, int verticalHitBox, int horizontalHitBox) {
        super(position, graphics, objectName, verticalHitBox, horizontalHitBox);
    }

    @Override
    public List<Item> dropItems() {
        return null;
    }

    @Override
    public int dropXp() {
        return 0;
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
