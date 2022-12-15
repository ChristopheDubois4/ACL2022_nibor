package prefab.props;

import engine.Cmd;
import manager.WorldManager;
import prefab.entity.Enemy;
import prefab.entity.GameObject;
import prefab.entity.Player;
import prefab.information.Position;
import prefab.information.State;
import prefab.rendering.Animation;

public class TrappedBox extends GameObject implements UsableObject {

    private Enemy enemy;

    public TrappedBox(Position position, Animation animation,  int horizontalHitBox, int verticalHitBox, Enemy mob) throws CloneNotSupportedException {
        super(position, animation, horizontalHitBox, verticalHitBox, State.DEFAULT);
        this.enemy=mob;
    }

    @Override
    public void objectUse(Player user,Cmd cmd) throws Exception {
        WorldManager.testCombats();
        return;
    }
}
