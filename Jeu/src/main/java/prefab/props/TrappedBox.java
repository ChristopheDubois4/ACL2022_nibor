package prefab.props;

import java.awt.image.BufferedImage;
import java.util.HashMap;


import engine.Cmd;
import manager.FightManager;
import manager.WorldManager;
import prefab.entity.Character;
import prefab.entity.GameObject;
import prefab.entity.Player;
import prefab.information.Position;
import prefab.information.State;
import prefab.rendering.Animation;

public class TrappedBox extends GameObject implements UsableObject {

    private Character enemy;

    public TrappedBox(Position position, Animation animation,  int horizontalHitBox, int verticalHitBox, Character mob) throws CloneNotSupportedException {
        super(position, animation, horizontalHitBox, verticalHitBox, State.DEFAULT);
        this.enemy=mob;
    }

    @Override
    public void objectUse(Player user,Cmd cmd) throws Exception {
        enemy.startAnimation();
        FightManager.getInstance().startNewFight(enemy);
        return;
    }
}
