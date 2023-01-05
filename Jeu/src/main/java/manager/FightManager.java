package manager;

import java.util.ArrayList;
import java.util.List;

import engine.Cmd;
import engine.Command;
import prefab.competence.Attack;
import prefab.competence.Spell;
import prefab.entity.Character;
import prefab.entity.Enemy;
import prefab.entity.Ghoul;
import prefab.entity.Player;
import prefab.equipment.Consumable;
import prefab.equipment.Effect;
import prefab.equipment.Item;
import prefab.gui.FightHud;
import prefab.information.State;
import prefab.information.Stats;

/**
 * <b>[SINGLETON]</b>
 * <p>gère les combats
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
	private Enemy enemy;
    public List<int[]> enemyConsumables;

    //informations du joueur
    private Player player;
    private List<int[]> playerConsumables;


	/**
     * constructeur de la classe FightManager
     */
	private FightManager() {}

    /**
     * initialise le fightManager
     * @param player le joueur
     * @param fightHud le hud de combat
     * @throws Exception
     * @throws CloneNotSupportedException
     */
    public void initFightManager() throws CloneNotSupportedException, Exception {
        submenusNames = new ArrayList[3];
        
        this.player = Player.getInstance();
		this.fightHud = FightHud.getInstance();
    }

    public static FightManager getInstance() {
        return INSTANCE;
    }

    /**
     * lance un combat avec un ennemi
     * @param enemy l'ennemi
     */
	public void startNewFight(Enemy enemy) {

        for (int i = 0; i < 3; i++) {
            submenusNames[i] = new ArrayList<String>();
        }
        this.turn = 0;
        fightHud.setMessageBox("");


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

        fightHud.loadEnemy(enemy);
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
    private boolean useConsumable(int pos, Character launcher, Character target) {

        System.out.println("AAAAAAAAAAAAAA");

        List<int[]> characConsumables;
        boolean isPlayer = launcher instanceof Player;

        characConsumables = isPlayer ? playerConsumables : enemyConsumables;

        int[] consumablePos = characConsumables.get(pos);
        Consumable consumable = (Consumable) launcher.getInventory()[consumablePos[0]][consumablePos[1]];

        System.out.println("BBBBBBBBBBBBBBBBBB");

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
            System.out.println("BBBBBBBBBBBBBBBBBB");

        	// si c'est au tour du joueur on affiche un message
        	if (turn == 0) {
	        	fightHud.setMessageBox("Impossible d'utiliser '" + consumable.toString() + "'");
        	}
            return false;
        }
        fightHud.setMessageBox(launcher.toString() + " utilise '" + consumable.toString() +"'");
        System.out.println(launcher.toString() + " DELTE '" + consumable.toString() +"'");

        launcher.deleteItem(consumablePos);
        characConsumables.remove(pos);

        if (isPlayer) {
            submenusNames[2].remove(consumable.toString());
        }
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
        Spell spell = launcher.getSpells().get(pos);
        if (!launcher.lauchSpell(target, spell)) {
        	// si c'est au tour du joueur on affiche un message
        	if (turn == 0) {
        		fightHud.setMessageBox("Pas assez de mana");
        	}
        	return false;
        }
        fightHud.setMessageBox(launcher.toString() + " utilise '" + spell.toString() + "'");
        return true;
    }


    private boolean playAction(int typeOfAction, int pos, Character launcher, Character target) {

        if (selectedTarget == 0) {
            selectedTarget = 1;
            target = launcher;
        }
        boolean succes = false;
        switch (typeOfAction) {
            case 0:
            	succes = useAttack(pos, launcher, target);
                // Animation, ect...
                break;
            case 1:
                succes = useSpell(pos, launcher, target);
                if (!succes) {
                    selectingTarget = false;
                    selectedTarget = 1;
                }
            	// Animation, ect...
                break;
            case 2:
                succes = useConsumable(pos, launcher, target);
                // Animation, ect...
                break;

            default:
                break;
        }
        return succes;
    }

    private void nextTurn() throws InterruptedException, CloneNotSupportedException {

        this.turn = (this.turn + 1)%4;
       
        if (turn > 1) {
            return;
        }

        Effect.applyEffects(player);
        Effect.applyEffects(enemy);

        if (!player.getIsAlive()) {
        	fightHud.setMessageBox("Vous avez perdu ... ");
        	// ... some delay
            this.turn = 5;
        	finishFight();
        	return;
        }
        if (!enemy.getIsAlive()) {
        	victory();
        }
    }

    private void victory() {
    	Item itemDrop = ((Enemy) enemy).dropItem();
    	if (itemDrop != null) {
    		fightHud.setMessageBox("Vous récupérez '" + itemDrop.toString() + "'");
    		player.addItem(itemDrop);
    	} else {
            fightHud.setMessageBox("Vous avez gagné !!! ");
        }
        // ... some delay
        this.turn = 5;
    }



	/**
     * fait evoluer le combat
     * @param command commande du joueur
	 * @throws InterruptedException
	 * @throws CloneNotSupportedException
     */
	public void evolve(Command command) throws InterruptedException, CloneNotSupportedException {

        // a remplacer par une neum EX truc == enum.NEW_TURN
        if (turn == 5) {
            finishFight();
            return;
        }
        if (turn == 3) {
            newTurn();
            return;
        }
        if (turn == 2) {
            endTurn();
            return;
        }
        if (turn == 1) {
            enemyTurn();        
            return;
        }
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
	}

    private void endTurn() throws InterruptedException, CloneNotSupportedException {

        Thread.sleep(1000);
        player.restoreEnergy(10, Stats.STAMINA);
        enemy.restoreEnergy(10, Stats.STAMINA);
        fightHud.setMessageBox("Fin du tour : +10 de stamina");
        nextTurn();
    }

    private void newTurn() throws InterruptedException, CloneNotSupportedException {
        
        Thread.sleep(1000);
        fightHud.setMessageBox("");
        nextTurn();
    }


    /**
     * permet au joueur de jouer son tour
     * @param command la commande du joueur
     * @throws InterruptedException
     * @throws CloneNotSupportedException
     */
	private void palyerTurn(Command command) throws InterruptedException, CloneNotSupportedException {
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
                        selectingTarget = false;
                        selectedTarget = 1;
                        // Animation
                        fightHud.updateHud(-1, false, 0, null, -1);
                        nextTurn();

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


	private void enemyTurn() throws InterruptedException, CloneNotSupportedException {

        Thread.sleep(1000);       

        System.out.println( "---> enemyTurn");

        int action[] = new int[2];
        
        do {            
            enemy.chooseAction(action, enemyConsumables.size());
        } 
        while (!playAction( action[0],  action[1], enemy, player));

        fightHud.updateHud(0, false, 0, null, -1);
        nextTurn();
        // Animation
        
	}

	public boolean getIsInFight() {
		return isInFight;
	}

    public boolean allowKeyLocker() {   
        return allowKeyLocker;
    }

	public void finishFight() throws InterruptedException {

        Thread.sleep(1500);

        player.restoreEnergy(1000, Stats.STAMINA);

		isInFight = false;
        this.player.setIsInFight(true);
        this.enemy.setIsInFight(true);

        enemy.stopAnimation();
       
        player.setState(State.IDLE_DOWN);
        WorldManager.removeObject(enemy);
		fightHud.changeDisplayState();

	}
	
	private void menuController(Command command) {
		
	}
	
	private void ennemyTurn() {
		
	}
	
	private void palyerTurn() {
	}
}
