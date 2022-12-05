package prefab.props;

import java.util.HashMap;

import engine.Cmd;
import manager.WorldManager;
import prefab.information.Position;
import prefab.information.State;
import prefab.entity.GameObject;
import prefab.entity.Player;

import java.awt.image.BufferedImage;


/**
 * représente une porte ouvrable par le joueur
 */
public class Door extends GameObject{
    String nextLevel;
    private Position nextPosition;
    /**
     * constructeur de la classe Character heritant de GameObject
     */
    public Door(Position position, HashMap<State, BufferedImage> graphics, int verticalHitBox, int horizontalHitBox, String nextLevel, Position nextPosition) {
        super(position, graphics, verticalHitBox, horizontalHitBox);
        this.nextPosition = nextPosition;
        this.nextLevel = nextLevel;
    }

    @Override
    //manque la direction unique d'utlisation
    //manque la réapparition devant la porte de sortie
    public void objectUse(Player user,Cmd cmd) {
        
        //on prend la porte lorsqu'on est en dessous
        if (user.getPosition().getY()<this.position.getY()){
            //le joueur utilise la porte pour se rendre au niveau suivant
            WorldManager.currentLevel = WorldManager.gameLevels.get(this.nextLevel);
            //on se deplace devan la porte de sortie
            user.setPosition(nextPosition);
            //on se dirige vers bas en sortant de la porte
            user.setState(State.IDLE_DOWN);
        }
        else {
            System.out.println("On ne peut pas prendre la porte sur côté\n");
            return;
        }

        return;
    }
}
