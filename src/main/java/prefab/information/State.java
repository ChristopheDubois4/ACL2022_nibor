package prefab.information;

/**
 * les états possibles d'un objet physique sue la map
 * 
 * sert à enregistrer quel est l'image de l'objet à afficher 
 * en fonction de l'orientation et de l'action en cours
 */
public enum State {
    IdleUP,
    IdleDOWN,
    IdleLEFT,
    IdleRIGHT,
    WalkUP,
    WalkDOWN,
    WalkLEFT,
    WalkRIGHT,
    DEFAULT,
    CLIMBING
}
