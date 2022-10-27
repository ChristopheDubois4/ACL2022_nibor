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
public class Position implements Comparable<Position> {
    private int x,y;    
    private Layer layer;

    /**
     * La taille choisie de la fenêtre graphique implique que :
     *  - Les valeurs possibles de x vont de 0 à 14
     *  - Les valeurs possibles de y vont de 0 à 26
     */
    private static final int xMin = 0, xMax = 26;
    private static final int yMin = 0, yMax = 14;

     /**
     * constructeur de la classe Position
     * @param x la postion horizontale
     * @param y la position verticale 
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.layer = Layer.DEFAULT;
    }

    /**
     * constructeur surchargé de la classe Position
     * @param layer niveau de profondeur pour le rendu graphique
     */
    public Position(int x, int y, Layer layer) {
        this.x = x;
        this.y = y;
        this.layer = layer;
    }

    /**
     * retourne un tuple de taille 2 qui contient les coordonnées
     * @return (x, y)
     */
    public Pair<Integer, Integer> getCoordinate() {
        return new Pair<Integer, Integer>(this.x, this.y);
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
    * permet d'effectuer un déplacement en modifiant les coordonnées
    * @param x déplacement horizontale à ajouter
    * @param y déplacement verticale à ajouter
    * @return un boolean qui vaut :
    *   -> false si le déplacement dépasse les limites de la fenêtre
    *   -> true sinon
    */
    public boolean addToXY(int x, int y) {
        // vérification du respect des limites sur l'axe horizontale de la fenêtre
        if ( (this.x + x > xMax ) || (this.x + x < xMin )) {
            return false;
        }   
        // vérification du respect des limites sur l'axe vertical de la fenêtre 
        if ( (this.y + y > yMax ) || (this.y + y < yMin )) {
            return false;
        }       
        this.x = this.x + x;
        this.y = this.y + y;
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
        return this.y - p.getY();
    }

    @Override
    public String toString() {
        return "(" + x +"," + y + ")";
    }
}
