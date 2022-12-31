package prefab.props;

import engine.Cmd;
import manager.WorldManager;
import prefab.information.Position;
import prefab.information.State;
import prefab.rendering.Animation;
import prefab.entity.GameObject;
import prefab.entity.Player;

/**
 * représente une tp ouvrable par le joueur
 */
public class Teleportation extends GameObject implements UsableObject{
    String nextLevel;
    private Position nextPosition;
    /**
     * constructeur de la classe Character heritant de GameObject
     * @throws CloneNotSuptpdException
     */
    public Teleportation(Position position, Animation animation, int verticalHitBox, int horizontalHitBox, String nextLevel, Position nextPosition) throws CloneNotSupportedException {
        super(position, animation, verticalHitBox, horizontalHitBox, State.DEFAULT);
        this.nextPosition = nextPosition;
        this.nextLevel = nextLevel;
    }

    @Override
    //manque la direction unique d'utlisation
    //manque la réapparition devant la tp de sortie
    public void objectUse(Player user,Cmd cmd) throws CloneNotSupportedException {
        

        WorldManager.currentLevel.unload();
        //le joueur utilise la tp pour se rendre au niveau suivant
        WorldManager.currentLevel = WorldManager.gameLevels.get(this.nextLevel);
        WorldManager.currentLevel.load();
        //on se deplace devant la tp de sortie
        user.setPosition(nextPosition);
        //on se dirige vers bas en sortant de la tp
        user.setState(State.IDLE_DOWN);

        return;
    }
}
