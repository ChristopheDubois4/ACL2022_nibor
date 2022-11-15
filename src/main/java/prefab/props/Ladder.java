package prefab.props;

import java.util.HashMap;

import prefab.information.Image;
import prefab.information.Position;
import prefab.information.State;
import prefab.entity.GameObject;
import prefab.entity.Player;

/**
 * représente une échelle utilisable par le joueur
 */
public class Ladder extends GameObject{

    /**
     * constructeur de la classe Ladder heritant de GameObject
     */
    public Ladder(Position position, HashMap<State, Image> graphics, String objectName, int horizontalHitBox, int verticalHitBox) {
        super(position, graphics, objectName, horizontalHitBox, verticalHitBox);
    }

    public Ladder(Position position, HashMap<State, Image> graphics, int verticalHitBox) {
        this(position, graphics, "Ladder",1, verticalHitBox);
    }


    /**
    * déplace le joueur de l'autre côté de l'échelle
    * @param user l'utilisateur de l'échelle
    * @return un boolean qui vaut :
    *   -> false si le joueur n'est pas placé au bord de l'échelle
    *   -> true sinon
    */
    @Override
    public boolean objectUse(Player user) {
        if (user.getPosition().getY()>=this.position.getY()+this.HitBox.getValue1()-1){
            user.getPosition().setY(this.position.getY()-1);
        }
        else if (user.getPosition().getY()<=this.position.getY()){
            user.getPosition().setY(this.position.getY()+this.HitBox.getValue1());
        }
        else {
            System.out.println("On ne peut pas prendre l'échelle au milieu\n");
            return false;
        }
        user.getPosition().setX(this.position.getX());

        System.out.println("Player : "+ user.getPosition() + "\n");
        return true;
    }
        
}
