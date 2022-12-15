package model;

import engine.Cmd;
import engine.Command;
import engine.Game;
import manager.WorldManager;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 *         Version avec personnage qui peut se deplacer. A completer dans les
 *         versions suivantes.
 * 
 */
public class NiborGame implements Game {

	private WorldManager worldManager;
	/**
	 * constructeur avec fichier source pour le help
	 * 
	 */
	public NiborGame(WorldManager worldManager) {
		this.worldManager = worldManager;
	}

	/**
	 * faire evoluer le jeu suite a une commande
	 * 
	 * @param commande
	 * @throws Exception
	 * @throws CloneNotSupportedException
	 */
	@Override
	public void evolve(Command commande) throws Exception {
		if (commande.getKeyCommand() != Cmd.IDLE) {
			//System.out.println("Execute "+commande.getKeyCommand());
		}
		try {
			worldManager.updateWorld(commande);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * verifier si le jeu est fini
	 */
	@Override
	public boolean isFinished() {
		// le jeu n'est jamais fini
		return false;
	}

}
