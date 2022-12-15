package prefab.entity;

import java.util.HashMap;

import engine.Cmd;
import prefab.information.Position;
import prefab.information.State;
import java.awt.image.BufferedImage;

/**
 * présente un personnage particulié qui est marchant
 * (Nom à changer)
 */
public class Commercant1 extends Character implements Merchant{
	
	protected int ligne;

    public Commercant1(Position position, HashMap<State, BufferedImage> graphics, String objectName, int horizontalHitBox, int verticalHitBox, int ligne) {
        super(position, graphics, objectName, horizontalHitBox, verticalHitBox);
        state =  State.IDLE_DOWN;
        this.ligne = ligne;
    }

    @Override
    public int getDialogue(int i) {
    	switch (i) {
    	
    	case 1:
    		System.out.println("Bonjour, voici le tout premier dialogue du jeu. \n");
    		i = i+1;
    		break;
    	
    	case 2:
    		System.out.println("Pour continuer le dialogue, rentrez encore en collision avec moi. \n");
    		i = i+1;
    		break;
    	
    	case 3:
    		System.out.println("C'est tout ce que j'avais � enseigner, le dialogue va maintenant se r�p�ter. \n");
    		i = 1;
    		break;
    	}
    	return (i);
    }

    @Override
    protected void initCharacteristic() {
        
    }
    
    @Override
    public void objectUse(Player user, Cmd cmd) {
    	this.ligne = getDialogue(this.ligne);
    }
    
    @Override
    public void die() {
        
    }
    
}
