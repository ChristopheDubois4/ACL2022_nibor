package prefab.information;

/**
 * represente la position d'un objet avec :
 * - ses coordonnées
 * - son état actuel
 * - son niveau de profondeur 
 */
public class Position {
    int x,y;
    State state;
    Layer layer;

    public Position(int x, int y, State state, Layer layer) {
        this.x = x;
        this.y = y;
        this.state = state;
        this.layer = layer;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public State getState() {
        return state;
    }

    public Layer getLayer() {
        return layer;
    }
}
