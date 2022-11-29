package prefab.props;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import engine.Cmd;
import manager.FightManager;
import manager.WorldManager;
import prefab.entity.Enemy;
import prefab.entity.GameObject;
import prefab.entity.Player;
import prefab.information.Position;
import prefab.information.State;

public class TrappedBox extends GameObject{
    private Enemy enemy;

    public TrappedBox(Position position, HashMap<State, BufferedImage> graphics,  int horizontalHitBox, int verticalHitBox, Enemy mob) {
        super(position, graphics, "Box", horizontalHitBox, verticalHitBox);
        this.enemy=mob;
    }
    public void objectUse(Player user,Cmd cmd) {
        WorldManager.testCombats();
        return;
    }
}
