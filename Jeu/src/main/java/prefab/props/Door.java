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
        //le joueur utilise la porte pour se rendre au niveau suivant
        WorldManager.currentLevel = WorldManager.gameLevels.get(this.nextLevel);
        user.setPosition(nextPosition);
        return;
    }
}
