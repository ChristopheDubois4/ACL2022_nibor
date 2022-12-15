package prefab.information;

/**
 * les états possibles d'un objet physique sue la map
 * 
 * sert à enregistrer quel est l'image de l'objet à afficher 
 * en fonction de l'orientation et de l'action en cours
 */
public enum State {
    IDLE_UP,
    IDLE_DOWN,
    IDLE_LEFT,
    IDLE_RIGHT,
    WALK_UP,
    WALK_DOWN,
    WALK_LEFT,
    WALK_RIGHT,
    DEFAULT,
    FIGHT,
    DEAD,
    CLIMBING,
    OPEN,
    CLOSE
}
