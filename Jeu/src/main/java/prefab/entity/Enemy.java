package prefab.entity;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import engine.Cmd;
import manager.WorldManager;
import prefab.equipment.Item;
import prefab.information.Position;
import prefab.rendering.Animation;
import prefab.rendering.CharacterAnimation;


/**
 * permet à un personnage de donner ses objets et son expérience au joueur
 */
public abstract class Enemy extends Character{

    double ATK_PROBA = 0.4; 
    double MAGIC_PROBA = 0.4; 
    double ITEM_PROBA = 0.2; 

    double range = 3;

    Enemy(Position position, Animation animation, int horizontalHitBox, int verticalHitBox, String name)
            throws FileNotFoundException, IOException, ParseException, Exception {
        super(position, animation, horizontalHitBox, verticalHitBox, name);
        initCharacteristic();
        
    }

    // REMPALCER PAR ALGO UZAWA
    public int calculateTrajectory() throws CloneNotSupportedException, Exception {
        
        int deltaY =  position.getY() - Player.getInstance().getPosition().getY();
        int deltaX =  position.getX() - Player.getInstance().getPosition().getX();

        if (deltaY*deltaY + deltaX*deltaX > 4*4) {
            return 4;
        }

        if (Math.abs(deltaY) > Math.abs(deltaX)) {
            if (deltaY > 0) {
                return 1;
            }
            if (deltaY < 0) {             
                return 0;
            }
        }
        if (deltaX > 0) {
            return 2;
        }
        if (deltaX < 0) {
            return 3;
        }
        return 4;
    }

    Cmd moves[] = {Cmd.UP, Cmd.DOWN, Cmd.LEFT, Cmd.RIGHT, Cmd.IDLE};
    int previousMove = 0;

    public Cmd getNextMove(boolean isBlocked) throws CloneNotSupportedException, Exception {
        
        if (((CharacterAnimation) animation).getPlayMoving()) {
            return Cmd.IDLE;
        }

        int move;
        if (isBlocked) {
            move = previousMove++%3;
        } else {
            move = calculateTrajectory();
        }         
        previousMove = move;
        return moves[move];
    }
    
    public void chooseAction(int action[], int consumablesSize) {

        // sélection du type d'action
        double typeSelection = Math.random();
        if (typeSelection <= ATK_PROBA) {
            action[0] = 0;
        } else if (typeSelection <= (ATK_PROBA + MAGIC_PROBA)) {
            action[0] = 1;
        } else {
            action[0] = 2;
        }
        
        // sélection de l'action
        int max = 0, min = 0;
        switch (action[0]) {
            case 0:
                max = getAttacks().size()-1;
                break;
            case 1:
                max = getSpells().size()-1;
                break;
            case 2:
                max = consumablesSize-1;
                break;
        }
        
        action[1] = min + (int)(Math.random() * ((max - min)));        
    }

    @Override
    public void die() {
        super.die();
    }
    
    public abstract Item dropItem();
    public abstract int dropXp();
    
}
