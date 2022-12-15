package prefab.props;

import engine.Cmd;
import prefab.information.Position;
import prefab.information.State;
import prefab.information.Stats;
import prefab.rendering.Animation;
import prefab.entity.GameObject;
import prefab.entity.Player;


/**
 * représente un piege s'actionnant par le passage du joueur
 */
public class Trap extends GameObject implements UsableObject{
    
    private int dammage;

    /**
     * constructeur de la classe Character heritant de GameObject
     * @throws CloneNotSupportedException
     */
    public Trap(Position position, Animation animation, int horizontalHitBox, int verticalHitBox) throws CloneNotSupportedException {
        super(position, animation, horizontalHitBox, verticalHitBox, State.DEFAULT);
    }

    public Trap(Position position, Animation animation, int horizontalHitBox, int verticalHitBox, int dammage) throws CloneNotSupportedException {
        this(position, animation, horizontalHitBox, verticalHitBox);
        this.dammage=dammage;
    }

    /**
    * le joueur utilise un piège
    * @param user l'utilisateur du piège
    * @return true ça fait mal
    */
    @Override
    public void objectUse(Player user,Cmd cmd) {
        user.takeDammage(this.dammage);
        System.out.println("Player PV : "+user.getCurrentStats().get(Stats.HP)+" sur "+user.getStats().get(Stats.HP)+"\n");
        return;
    }
}

