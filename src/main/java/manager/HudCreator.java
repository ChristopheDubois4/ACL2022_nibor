package manager;

import prefab.entity.Player;
import prefab.gui.InventoryHud;

/**
 * Créer les différents Huds
 */
public class HudCreator {

    Player player;

    InventoryHud inventory;

    public HudCreator(Player player) {
        this.player = player;
        initHuds();
        testSrpint1();
    }

    private void initHuds() {
    }
    
    /**
     * methode temporaire
     * 
     * Méthode pour tester des fonctionnalitées liées au sprint 1
     */
    private void testSrpint1() {       
        inventory = new InventoryHud(player);
        
    }

    public InventoryHud getInventory() {
        return inventory;
    }
}
