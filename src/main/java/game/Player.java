package game;

import java.util.HashMap;
import java.awt.image.BufferedImage;

public class Player extends Character {
    
    PlayerClasses classPlayed;    

    public Player(Position position, HashMap<State, BufferedImage> graphics, String objectName, int verticalHitBox, int horizontalHitBox, PlayerClasses classPlayed) {
        super(position, graphics, objectName, verticalHitBox, horizontalHitBox);
        this.classPlayed = classPlayed;
        this.xp = 0;        
        initCharacteristic();
    }

    protected void initCharacteristic(){     
        
        switch (classPlayed) {

            case MAGE:
                
                break;

            case WARRIOR:
            
                break;

            case ASSASSIN:
            
                break;

            case CLERIC:
            
                break;
        
            default:
                break;
        }
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
