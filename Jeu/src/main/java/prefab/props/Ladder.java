package prefab.props;

import java.util.HashMap;

import engine.Cmd;
import prefab.information.Position;
import prefab.information.State;
import prefab.entity.GameObject;
import prefab.entity.Player;
import java.awt.image.BufferedImage;


/**
 * représente une échelle utilisable par le joueur
 */
public class Ladder extends GameObject{

    /**
     * constructeur de la classe Ladder heritant de GameObject
     */

    public Ladder(Position position, HashMap<State, BufferedImage> graphics, int verticalHitBox) {
        super(position, graphics, 1, verticalHitBox);
    }


    /**
    * déplace le joueur de l'autre côté de l'échelle
    * @param user l'utilisateur de l'échelle
    * @return un boolean qui vaut :
    *   -> false si le joueur n'est pas placé au bord de l'échelle
    *   -> true sinon
    */
    @Override
    public void objectUse(Player user, Cmd cmd) {
        if (user.getPosition().getY()>this.position.getY()+this.HitBox.getValue1()-1){
            user.getPosition().setY(this.position.getY()-1);
        }
        else if (user.getPosition().getY()<this.position.getY()){
            user.getPosition().setY(this.position.getY()+this.HitBox.getValue1());
        }
        else {
            System.out.println("On ne peut pas prendre l'échelle sur côté\n");
            return;
        }
        user.getPosition().setX(this.position.getX());
        System.out.println("Player : "+ user.getPosition() + "\n");
    }
}
