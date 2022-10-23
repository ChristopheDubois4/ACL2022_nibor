package prefab.information;

/**
 * represente la position d'un objet avec :
 * - ses coordonnées
 * - son état actuel
 * - son niveau de profondeur 
 * 
 * implemente l'interface Comparable<> pour comparer les position en
 * fonction de leurs 
 */
public class Position implements Comparable<Position> {
    private int x,y;
    private State state;
    private Layer layer;

     /**
     * constructeur de la classe Position
     * @param x la postion horizontale
     * @param y la position verticale 
     * @param state l'état de l'objet
     * @param layer niveau de profondeur pour le rendu graphique
     */
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

    /**
     * compare 2 positions
     * @param p
     * @return le resultat de la comparaison
     */
    @Override
    public int compareTo(Position p) {
        
        if (this.layer == Layer.BACKGROUND || p.getLayer() == Layer.FOREGROUND )
            return -1;

        if (this.layer == Layer.FOREGROUND || p.getLayer() == Layer.BACKGROUND)
            return 1;

        return this.y - p.getY();
    }
}
