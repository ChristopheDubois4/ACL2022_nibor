package prefab.props;

import engine.Cmd;
import prefab.entity.Player;

public interface UsableObject {

    public void objectUse(Player user, Cmd cmd) throws Exception;
}
