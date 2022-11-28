package manager;

import prefab.entity.Player;
import prefab.gui.InventoryHud;
import prefab.gui.StatsHud;
import prefab.gui.VitalResourcesHud;


/**
 * Créer les différents Huds
 */
public class HudCreator {

    Player player;


    InventoryHud inventoryHud;
    VitalResourcesHud vitalResourceshBar;
    StatsHud statsInfo;

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
        inventoryHud = new InventoryHud(player);

        vitalResourceshBar = new VitalResourcesHud(player);

        statsInfo = new StatsHud(player);
        
    }

    public InventoryHud getInventoryHud() {
        return inventoryHud;
    }

    
    public VitalResourcesHud getHealthBar() {
        return vitalResourceshBar;
    }

    public StatsHud getStatsInfo() {
        return statsInfo;
    }
}
