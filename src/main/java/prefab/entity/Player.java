package prefab.entity;

import java.util.HashMap;

import prefab.information.PlayerClasses;
import prefab.information.Position;
import prefab.information.State;

import java.awt.image.BufferedImage;


/**
 * représente le joueur, un personnage particulié du jeu que l'on contrôle
 */

public class Player extends Character {
    
    PlayerClasses classPlayed;    

    /**
     * constructeur de la classe Player heritant de Character
     * @param classPlayed la classe de combattant du joueur
     */
    public Player(Position position, HashMap<State, BufferedImage> graphics, String objectName, int horizontalHitBox, int verticalHitBox, PlayerClasses classPlayed) {
        super(position, graphics, objectName, horizontalHitBox, verticalHitBox);
        this.classPlayed = classPlayed;
        this.xp = 0;        
        initCharacteristic();
    }

    /**
     * méthode qui initailise les charactéristiques par défaut du joueur 
     * selon la classe qu'il à choisi
     */
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
    public void draw() {

    }
    
    @Override
    public void die() {
        
    }
    
}
