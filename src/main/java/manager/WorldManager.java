package manager;

/**
 * gère le monde dans lequel le joueur évolue
 */
public class WorldManager {
    
    LevelManager levelManager;

    /**
     * constructeur de la classe WorldManager
     */
    public WorldManager() {
        levelManager = new LevelManager();
    }
}
