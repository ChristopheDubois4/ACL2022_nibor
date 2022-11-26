package manager;

import java.util.ArrayList;
import java.util.List;

import engine.Command;
import prefab.entity.Character;
import prefab.entity.Enemy;
import prefab.entity.Player;
import prefab.equipment.Consumable;
import prefab.equipment.Item;
import prefab.gui.FightHud;

/**
 * gère les combats
 */
public class FightManager {

    private FightHud fightHud;

    // tableau des menus
    private String[] menu = {"ATTAQUES", "MAGIE", "CONSOMMABLES"};

    // informations pour les menus
    private boolean isMenuOpen = false;
    private int cursorMenu = 0;
    private int cursorSubMenu = 0;

    // information générales du combat
	private boolean isInFight = false;
	private int nbTurn = 0;
	
	// informations de l'ennemi
	private Enemy enemy;
    Object[] enemyOptions;

    //informations du joueur
    private Player player;
    Object[] playerOptions;
  

	/**
     * constructeur de la classe WorldManager
     * @param player le joueur
     * @param fightHud le hud de combat
     */
	public FightManager(Player player, FightHud fightHud) {
		this.player = player;
		this.fightHud = fightHud;
	}
	
    /**
     * lance un combat avec un ennemi
     * @param enemy l'ennemi
     */
	public void startNewFight(Enemy enemy)  {
		isInFight = true;
		this.enemy = enemy;
        fightHud.loadEnemy(enemy);
		fightHud.changeDisplayState();

        playerOptions = new Object[] {player.getAttacks(), player.getSpells(), getConsumables(player.getInventory())};
        enemyOptions = new Object[] {enemy.getAttacks(), enemy.getSpells(), getConsumables(enemy.getInventory())};
	}

    /**
     * récupère la liste des coordonées des consommables dans l'inventaire 
     * @return un tuple (i, j)
     */
    private List<Consumable> getConsumables(Item[][] playerInventory) {
        // liste de tuple (i, j)
        List<Consumable> consommables = new ArrayList<Consumable>();
        // ajout des coordonnées des consommables à la liste
        for (int j = 0; j < Character.inventoryLengthY; j++) {
            for (int i = 0; i < Character.inventoryLengthX; i++) {
                if ( playerInventory[i][j] instanceof Consumable) {
                    consommables.add((Consumable) playerInventory[i][j]);
                }     
            }
        }
        return consommables;
    }
    
	/**
     * fait evoluer le combat
     * @param command commande du joueur
     */
	public void evolve(Command command) {
        int shiftMenu = 0;
        switch (command.getKeyCommand()) {            
            case LEFT:
                isMenuOpen = false;
                break;
            case RIGHT:
                isMenuOpen = true;
                break;
            case UP:
                shiftMenu--;
                break;
            case DOWN:
                shiftMenu++;
                break;
            case USE:
                System.out.println("USE");
                break;
            default:
                return;
        }

        List<String> submenuNames = new ArrayList<String>(); 

        if (isMenuOpen) {   
            for (Object object : (List<Object>) playerOptions[cursorMenu]) {
                submenuNames.add(object.toString());
            }         
            cursorSubMenu += shiftMenu;            
            if (cursorSubMenu < 0) {
                cursorSubMenu = submenuNames.size() - 1 ;
            } else {
                try {
                    cursorSubMenu = cursorSubMenu % submenuNames.size();
                } catch (Exception e) {
                }
            }            
        } else {
            cursorSubMenu = 0;
            cursorMenu += shiftMenu;
            if (cursorMenu < 0) {
                cursorMenu = menu.length - 1;
            } else {
                cursorMenu = cursorMenu%3;
            }
        }
        
        // si le menu est vide, on informe le houeur avec un message
        if (submenuNames.size() == 0 && isMenuOpen) {
            isMenuOpen = false;
            fightHud.setMessageBox("Le menu '" + menu[cursorMenu] + "' est vide");
            return;
        }
        fightHud.setMessageBox("");
		fightHud.updateHud(cursorMenu, isMenuOpen, cursorSubMenu, submenuNames);
		
	}
	
	public boolean getIsInFight() {
		return isInFight;
	}
	
	private void finishFight() {
		isInFight = false;
		fightHud.changeDisplayState();
		
	}
	
	private void menuController(Command command) {
		
	}
	
	private void ennemyTurn() {
		
	}
	
	private void palyerTurn() {
		
	}
}
