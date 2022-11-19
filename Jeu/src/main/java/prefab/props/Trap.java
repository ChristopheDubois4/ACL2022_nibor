package prefab.props;

import java.util.HashMap;

import prefab.information.Position;
import prefab.information.State;
import prefab.information.Stats;
import prefab.entity.GameObject;
import prefab.entity.Player;
import java.awt.image.BufferedImage;


/**
 * représente un piege s'actionnant par le passage du joueur
 */
public class Trap extends GameObject{
    
    private int dammage;

    /**
     * constructeur de la classe Character heritant de GameObject
     */
    public Trap(Position position, HashMap<State,BufferedImage > graphics, String objectName, int horizontalHitBox, int verticalHitBox) {
        super(position, graphics, objectName, horizontalHitBox, verticalHitBox);
    }

    public Trap(Position position, HashMap<State, BufferedImage> graphics,  int horizontalHitBox, int verticalHitBox, int dammage) {
        super(position, graphics, "Trap", horizontalHitBox, verticalHitBox);
        this.dammage=dammage;
    }

    /**
    * le joueur utilise un piège
    * @param user l'utilisateur du piège
    * @return true ça fait mal
    */
    @Override
    public boolean objectUse(Player user) {
        user.takeDammage(this.dammage);
        System.out.println("Player PV : "+user.getCurrentStats().get(Stats.HP)+" sur "+user.getStats().get(Stats.HP)+"\n");
        return true;
    }

}

