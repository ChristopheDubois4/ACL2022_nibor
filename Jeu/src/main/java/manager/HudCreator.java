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


    InventoryHud inventory;
    VitalResourcesHud healthBar;
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
        inventory = new InventoryHud(player);
        healthBar = new HealthBar(player);
        BufferedImage backgroundsImages = getImageFromJSON("inventory");
        BufferedImage backgroundsImages2 = getImageFromJSON("vitalresources");
        BufferedImage backgroundsImages3 = getImageFromJSON("stats");

        inventory = new InventoryHud(player, backgroundsImages);
        
        healthBar = new VitalResourcesHud(player,backgroundsImages2);

        statsInfo = new StatsHud(player,backgroundsImages3);
        
    }

    public InventoryHud getInventory() {
        return inventory;
    }
    
    public VitalResourcesHud getHealthBar() {
        return healthBar;
    }
}
