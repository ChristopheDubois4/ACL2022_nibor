package game;

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
