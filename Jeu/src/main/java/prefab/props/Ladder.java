package prefab.props;

import engine.Cmd;
import prefab.information.Position;
import prefab.information.State;
import prefab.rendering.Animation;
import prefab.entity.GameObject;
import prefab.entity.Player;


/**
 * représente une échelle utilisable par le joueur
 */
public class Ladder extends GameObject implements UsableObject{

    /**
     * constructeur de la classe Ladder heritant de GameObject
     * @throws CloneNotSupportedException
     */
    public Ladder(Position position, Animation animation, int verticalHitBox, int horizontalHitBox) throws CloneNotSupportedException {
        super(position, animation, horizontalHitBox, verticalHitBox, State.DEFAULT);
    }

    public Ladder(Position position, Animation animation, int verticalHitBox) throws CloneNotSupportedException {
        this(position, animation, verticalHitBox, 1);
    }


    /**
    * déplace le joueur de l'autre côté de l'échelle
    * @param user l'utilisateur de l'échelle
    * @return un boolean qui vaut :
    *   -> false si le joueur n'est pas placé au bord de l'échelle
    *   -> true sinon
     * @throws CloneNotSupportedException
    */
    @Override
    public void objectUse(Player user, Cmd cmd) throws CloneNotSupportedException {
        Position userP = user.getPosition();
        Position thisP = getPosition();
        if (userP.getY()> thisP.getY() + getHitBox().getValue1()-1){
            user.setPosition(Position.createPosition(userP.getX(), thisP.getY()-1, userP.getLayer()));
        }
        else if (userP.getY()<thisP.getY()){
            user.setPosition( Position.create(userP.getX(), thisP.getY() + (int) getHitBox().getValue1()) );
        }
        else {
            System.out.println("On ne peut pas prendre l'échelle sur côté\n");
            return;
        }
        userP.setX(thisP.getX());
        System.out.println("Player : "+ userP + "\n");
    }
}
