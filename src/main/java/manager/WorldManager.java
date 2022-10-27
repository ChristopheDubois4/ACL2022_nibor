package manager;

import org.javatuples.Pair;

import engine.Cmd;
import prefab.entity.GameObject;
import prefab.information.Position;

/**
 * gère le monde dans lequel le joueur évolue
 */
public class WorldManager {
    
    LevelManager levelManager;

    // SIMULE LE JOUEUR POUR L INSTANT
    GameObject Player;

    /**
     * constructeur de la classe WorldManager
     */
    public WorldManager() {
        levelManager = new LevelManager();
        
        Position p1 = new Position(10, 10);
        Player = new GameObject(p1, null, "PLAYER", 1, 1);
        System.out.println("Player : "+ Player.getPositon() + "\n");
    }

    /**
     * met à jour les données du monde
     * @param cmd commande du joueur
     */
    public void updateWorld(Cmd cmd) {
        if (cmd != Cmd.IDLE) {
            movePlayer(cmd);
        }
    }

    /**
     * deplace le joueur
     * @param cmd commande du joueur
     */
    private void movePlayer(Cmd cmd) {        
        // calcul du déplacement à effectuer
        int x = 0, y = 0;
        switch (cmd) {            
            case LEFT:
                x -= 1;
                break;
            case RIGHT:
                x += 1;
                break;
            case UP:
                y += 1;
                break;
            case DOWN:
                y -= 1;
                break;
            default:
                break;
        }

        // si le mouvement amène en dehors de la fenêtre, on sort de la fonction 
        if (!Player.move(x, y)) {
            return;
        } 
        /** 
         * test s'il y a un obstacle qui empêche le joueur de se déplacer 
         * et si oui retourne l'object bloquant
         */
        Pair<Boolean, GameObject> check = levelManager.checkMove(Player);
        // si il n'y a pas d'obstacle, on sort de la fonction
        if (check.getValue0()) {
            System.out.println("Player : "+ Player.getPositon() + "\n");
            return;
        }
        // sinon on remet le joueur à sa position avant le déplacement
        Player.move(-x, -y);
        System.out.println("MOVEMENT IMPOSSIBLE \nObjet de la collision : "+ check.getValue1() + "\n");
        System.out.println("Player : "+ Player.getPositon() + "\n");
    }
}
