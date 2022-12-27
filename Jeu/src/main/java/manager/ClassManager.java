package manager;


import engine.Cmd;
import engine.Command;
import prefab.entity.Player.PlayerClasses;
import prefab.gui.ClassHud;

/**
 * gère la selection de classe
 */
public class ClassManager {

    private static final ClassManager INSTANCE = new ClassManager();

    private ClassHud classHud;

    // Les classes jouables
    private PlayerClasses[] menu = {PlayerClasses.WARRIOR, PlayerClasses.ASSASSIN, PlayerClasses.ARCHER, PlayerClasses.MAGE};

    // informations pour les menus
    private int cursorMenu = 0;

    // information générales du combat
	private boolean isChoosingClass = false;
    private boolean allowKeyLocker = false;

    //informations du joueur
    private  PlayerClasses classPlayed;



	/**
     * constructeur de la classe WorldManager
     * @param classHud le hud de combat
     */
	private ClassManager() {}

    public void initClassManager() {
		this.classHud = ClassHud.getInstance();
    }

    public static ClassManager getInstance() {
        return INSTANCE;
    }

    /**
     * lance la selection de class
     */
	public void startClassSelection() {
        this.isChoosingClass = true;
        classHud.changeDisplayState();
	}


    /**
     * permet au joueur de jouer son tour
     * @param command la commande du joueur
     */
	private void playerChoice(Command command) {
        
        System.out.println(menu[cursorMenu]);
        int shiftMenu = 0;
        switch (command.getKeyCommand()) {
            case LEFT:
                shiftMenu--;
                break;
            case RIGHT:
                shiftMenu++;
                break;
            case USE:
                finishClass(menu[cursorMenu]);
                break;
            default:
                return;
        }
        cursorMenu += shiftMenu;
        if (cursorMenu < 0) {
            cursorMenu = menu.length - 1;
        } else {
            cursorMenu = cursorMenu%4;
        }
        classHud.updateHud(cursorMenu);
	}


	public boolean getIsChoosingClass() {
		return isChoosingClass;
	}

    public boolean allowKeyLocker() {   
        return allowKeyLocker;
    }

	public void finishClass(PlayerClasses classSelected) {
        this.classPlayed = classSelected;
		isChoosingClass = false;
		classHud.changeDisplayState();
	}

    public void evolve(Command command) {
        if (command.getKeyCommand() == Cmd.IDLE) {
            allowKeyLocker = false;
            return;
        }
        allowKeyLocker = true;
        playerChoice(command);
        return;
    }

    public PlayerClasses getClassPlayed() {
        return classPlayed;
    }

    public void classPlayedIsInit() {
        classPlayed = null;
    }
}
