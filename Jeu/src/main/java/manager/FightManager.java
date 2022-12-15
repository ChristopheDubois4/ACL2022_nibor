package manager;

import java.util.ArrayList;
import java.util.List;

import engine.Cmd;
import engine.Command;
import prefab.competence.Attack;
import prefab.competence.Spell;
import prefab.entity.Character;
import prefab.entity.Enemy;
import prefab.entity.Mob1;
import prefab.entity.Player;
import prefab.equipment.Consumable;
import prefab.equipment.Effect;
import prefab.equipment.Item;
import prefab.gui.FightHud;

/**
 * gère les combats
 */
public class FightManager {

    private static final FightManager INSTANCE = new FightManager();

    private FightHud fightHud;

    // tableau des menus
    private String[] menu = {"ATTAQUES", "MAGIE", "CONSOMMABLES"};
    private List<String>[] submenusNames;

    // informations pour les menus
    private boolean isMenuOpen = false;
    private int cursorMenu = 0;
    private int cursorSubMenu = 0;

    // selection d'une cible
    private boolean selectingTarget = false;
    private int selectedTarget = 1;

    // information générales du combat
	private boolean isInFight = false;
    private boolean allowKeyLocker = false;
	private int turn = 0;

	// informations de l'ennemi
	private Character enemy;
    public List<int[]> enemyConsumables;

    //informations du joueur
    private Player player;
    private List<int[]> playerConsumables;


	/**
     * constructeur de la classe WorldManager
     * @param player le joueur
     * @param fightHud le hud de combat
     */
	private FightManager() {}

    public void initFightManager(Player player, FightHud fightHud) {
        submenusNames = new ArrayList[3];
        for (int i = 0; i < 3; i++) {
            submenusNames[i] = new ArrayList<String>();
        }
        this.player = player;
		this.fightHud = fightHud;
    }

    public static FightManager getInstance() {
        return INSTANCE;
    }

    /**
     * lance un combat avec un ennemi
     * @param enemy l'ennemi
     */
	public void startNewFight(Character enemy) {

        this.playerConsumables = getConsumables(player.getInventory(), true);
		for (Attack atk : player.getAttacks()) {
			submenusNames[0].add(atk.toString());
		}
		for (Spell spell : player.getSpells()) {
			submenusNames[1].add(spell.toString());
		}
		this.enemy = enemy;
		this.enemyConsumables = getConsumables(enemy.getInventory(), false);

        isInFight = true;
        this.player.setIsInFight(true);
        this.enemy.setIsInFight(true);

		fightHud.changeDisplayState(0);
	}

      /**
     * récupère la liste des coordonées des consommables dans l'inventaire
     * @return un tuple (i, j)
     */
    private List<int[]> getConsumables(Item[][] characterInventory, boolean isPlayer) {
        // liste de tuple (i, j)
        List<int[]> consommables = new ArrayList<>();
        // ajout des coordonnées des consommables à la liste
        for (int i = 0; i < characterInventory.length; i++) {
            for (int j = 0; j < characterInventory[0].length; j++) {
                if ( characterInventory[i][j] instanceof Consumable) {
                    consommables.add(new int[] {i, j});
                    if (isPlayer) {
                        submenusNames[2].add(characterInventory[i][j].toString());
                    }
                }
            }
        }
        return consommables;
    }

    /**
     * utilise un consomable
     * @param characConsumables la liste des positions des consommables du lanceur
     * @param pos la position du consommable en question
     * @param launcher le lanceur
     * @param target la cible
     * @return
     *      -> true si l'objet a pu être utilisé
     *      -> false sinon
     */
    private boolean useConsumable(List<int[]> characConsumables, int pos, Character launcher, Character target) {

        int[] consumablePos = characConsumables.get(pos);
        Consumable consumable = (Consumable) launcher.getInventory()[consumablePos[0]][consumablePos[1]];

        /**
         * si le consommable se lance
         * 	-> l'adversaire utilise le consommable
         * sinon le personnage qui joue essaye d'utiliser le consommable
         * 	-> si il ne peut pas, on affiche le message au joueur
         */
        if ( consumable.getIsThrowable()) {
        	target.useConsumable(consumable);
        }
        // sinon si l'objet ne peut pas être ui
        else if (!launcher.useConsumable(consumable)) {
        	// si c'est au tour du joueur on affiche un message
        	if (turn == 0) {
	        	fightHud.setMessageBox("Impossible d'utiliser '" + consumable.toString() + "'");
        	}
            return false;
        }
        fightHud.setMessageBox(launcher.toString() + " utilise '" + consumable.toString() +"'");
        launcher.deleteItem(consumablePos);
        characConsumables.remove(pos);
        return true;
    }


    /**
     * utilise une attaque
     * @param pos la position de l'attque dans la liste
     * @param launcher le lanceur
     * @param target la cible
     * @return
     *      -> true si lattaque a pu être utilisé
     *      -> false sinon
     */
    private boolean useAttack(int pos, Character launcher, Character target) {

        String[] attackName = new String[1];
        if (!launcher.attack(target, pos, attackName)) {
        	// si c'est au tour du joueur on affiche un message
        	if (turn == 0) {
        		fightHud.setMessageBox("Pas assez de stamina");
        	}
        	return false;
        }
        fightHud.setMessageBox(launcher.toString() + " utilise '" + attackName[0] + "'");
        return true;
    }

    private boolean useSpell(int pos, Character launcher, Character target) {
        if (selectedTarget == 0) {
            target = launcher;
        }
        String[] spellName = new String[1];
        if (!launcher.lauchSpell(target, pos, spellName)) {
        	// si c'est au tour du joueur on affiche un message
        	if (turn == 0) {
        		fightHud.setMessageBox("Pas assez de mana");
        	}
        	return false;
        }
        fightHud.setMessageBox(launcher.toString() + " utilise '" + spellName[0] + "'");
        return true;
    }


    private boolean playAction(int typeOfAction, int pos, Character launcher, Character target) {
        if (selectedTarget == 0) {
            selectedTarget = 1;
            target = launcher;
        }
        boolean succes = false;
        switch (cursorMenu) {
            case 0:
            	succes = useAttack(cursorSubMenu, launcher, target);
                // Animation, ect...
                break;
            case 1:
                succes = useSpell(cursorSubMenu, launcher, target);
                if (!succes) {
                    selectingTarget = false;
                    selectedTarget = 1;
                }
            	// Animation, ect...
                break;
            case 2:
                succes = useConsumable(playerConsumables, cursorSubMenu, launcher, target);
                // Animation, ect...
                break;

            default:
                break;
        }
        return succes;
    }

    private void nextTurn() {

        Effect.applyEffects(player);
        Effect.applyEffects(enemy);

        if (!player.getIsAlive()) {
        	fightHud.setMessageBox("Vous avez perdu ... ");
        	// ... some delay
        	finishFight();
        	return;
        }
        if (!enemy.getIsAlive()) {
        	victory();
        }

        this.turn = (this.turn + 1)%2;
    }

    private void victory() {
    	Item itemDrop = ((Enemy) enemy).dropItem();
    	if (itemDrop != null) {
    		fightHud.setMessageBox("Vous récupérez '" + itemDrop.toString() + "'");
    		player.addItem(itemDrop);
    	}
        // ... some delay
        finishFight();
    }



	/**
     * fait evoluer le combat
     * @param command commande du joueur
     */
	public void evolve(Command command) {

        if (command.getKeyCommand() == Cmd.IDLE) {
            allowKeyLocker = false;
            return;
        }
		if (turn == 0) {
            System.out.println( "---> evolve");
            allowKeyLocker = true;
			palyerTurn(command);
            return;
		}
        enemyTurn();        
	}


    /**
     * permet au joueur de jouer son tour
     * @param command la commande du joueur
     */
	private void palyerTurn(Command command) {
        int shiftMenu = 0;
        switch (command.getKeyCommand()) {
            case LEFT:
                if (selectingTarget) {
                    selectedTarget--;
                } else {
                    isMenuOpen = false;
                }
                break;
            case RIGHT:
                if (selectingTarget) {
                    selectedTarget = (selectedTarget+1)%2;
                } else {
                    isMenuOpen = true;
                }
                break;
            case UP:
                if (!selectingTarget) {
                    shiftMenu--;
                }
                break;
            case DOWN:
                if (!selectingTarget) {
                    shiftMenu++;
                }
                break;
            case USE:
                if (isMenuOpen) {
                    // si le menu Magie est sélectionné
                    if (cursorMenu == 1 && !selectingTarget) {
                        selectingTarget = true;
                        fightHud.setMessageBox("Sélectionner une cible ");
                    }
                    else if (playAction(cursorMenu, cursorSubMenu, player, enemy)) {
                        isMenuOpen = false;
                        nextTurn();
                        // Animation
                        fightHud.updateHud(-1, false, 0, null, -1);
                        return;
                    }
                }
                break;
            case CLOSE:
                selectingTarget = false;
                selectedTarget = 1;
                fightHud.setMessageBox("");
                break;
            default:
                return;
        }

        if (isMenuOpen) {
            cursorSubMenu += shiftMenu;          
            if (cursorSubMenu < 0) {
                cursorSubMenu = submenusNames[cursorMenu].size() - 1 ;
            } else {
                try {
                    cursorSubMenu = cursorSubMenu % submenusNames[cursorMenu].size();
                } catch (Exception e) {
                }
            }
            if (selectedTarget < 0) {
                selectedTarget = 1;
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
        if (submenusNames[cursorMenu].size() == 0 && isMenuOpen) {
            isMenuOpen = false;
            fightHud.setMessageBox("Le menu '" + menu[cursorMenu] + "' est vide");
            return;
        }

        int target = -1;
        if (selectingTarget) {
            target = selectedTarget;
        } 
        fightHud.updateHud(cursorMenu, isMenuOpen, cursorSubMenu, submenusNames[cursorMenu], target);
	}


	private void enemyTurn() {
		fightHud.updateHud(-1, false, 0, null, -1);
        System.out.println( "---> enemyTurn");
        while (true) {
            
        }
	}

	public boolean getIsInFight() {
		return isInFight;
	}

    public boolean allowKeyLocker() {   
        return allowKeyLocker;
    }

	public void finishFight() {
		isInFight = false;
        this.player.setIsInFight(true);
        this.enemy.setIsInFight(true);
		fightHud.changeDisplayState();

	}

}
