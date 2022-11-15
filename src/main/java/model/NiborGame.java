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
	 */
	@Override
	public void evolve(Command commande) {
		if (commande.getKeyCommand() != Cmd.IDLE) {
			System.out.println("Execute "+commande.getKeyCommand());
		}
		worldManager.updateWorld(commande);
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
