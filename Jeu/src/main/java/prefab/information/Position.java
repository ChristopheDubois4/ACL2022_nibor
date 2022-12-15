package prefab.information;

import org.javatuples.Pair;

/**
 * represente la position d'un objet avec :
 * - ses coordonnées
 * - son état actuel
 * - son niveau de profondeur 
 * 
 * implemente l'interface Comparable<> pour comparer les position en
 * fonction de leurs 
 */
public final class Position implements Comparable<Position>, Cloneable{

    /**
     * La taille choisie de la fenêtre graphique implique que :
     *  - Les valeurs possibles de x vont de 0 à 14
     *  - Les valeurs possibles de y vont de 0 à 26
     */
    private static final int xMin = 0, xMax = 26;
    private static final int yMin = 0, yMax = 14;

    private final int x,y;    
    private final Layer layer;

    /**
     * constructeur surchargé de la classe Position
     * @param x la postion horizontale
     * @param y la position verticale 
     * @param layer niveau de profondeur pour le rendu graphique
     */
    private Position(int x, int y, Layer layer) {
        this.x = x;
        this.y = y;
        this.layer = layer;
    }

    public static Position createPosition(int x, int y, Layer layer) {
        return new Position(x, y, layer);
    }

    public static Position create(int x, int y) {
        return createPosition(x, y, Layer.DEFAULT);
            }

    /**
     * retourne un tuple de taille 2 qui contient les coordonnées
     * @return (x, y)
     */
    public Pair<Integer, Integer> getCoordinate() {
        return new Pair<Integer, Integer>(this.x, this.y);
    }

    public Position setX(int newX) {
        return new Position(newX, y, layer);
    }

    public Position setY(int newYy) {
        return new Position(x, newYy, layer);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Layer getLayer() {
        return layer;
    }


    /**
     * permet d'effectuer un déplacement sur la position actuelle
     * @param deltaX déplacement horizontale à ajouter
     * @param deltaY déplacement verticale à ajouter
     * @return la nouvelle position
     */
    public Position addToXY(int deltaX, int deltaY) {     
        return new Position(x + deltaX, y + deltaY, layer);
    }

    public boolean authorizedPosition(int deltaX, int deltaY) {
         // vérification du respect des limites sur l'axe horizontale de la fenêtre
         if ( (this.x + deltaX > xMax ) || (this.x + deltaX < xMin )) {
            return false;
        }   
        // vérification du respect des limites sur l'axe vertical de la fenêtre 
        if ( (this.y + deltaY > yMax ) || (this.y + deltaY < yMin )) {
            return false;
        } 
        return true;
    }
    
    /**
     * compare 2 positions
     * @param p postion avec laquelle on compare
     * @return le resultat de la comparaison
     */
    @Override
    public int compareTo(Position p) {        
        if (this.layer == Layer.BACKGROUND || p.getLayer() == Layer.FOREGROUND ) {
            return -1;
        }
        if (this.layer == Layer.FOREGROUND || p.getLayer() == Layer.BACKGROUND) {
            return 1;
        }
        return p.getY() - this.y;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "(" + x +"," + y + ")";
    }
}
