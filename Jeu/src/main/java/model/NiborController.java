package model;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JLabel;


import engine.Cmd;
import engine.Command;
import engine.DrawingPanel;
import engine.GameController;


/**
 * controleur de type KeyListener
 */
public class NiborController implements GameController {
	
	/**
	 * commande en cours
	 */
	private Command commandeEnCours;
	private Command commandePrecedente;

	JLabel label;
	
	/**
	 * construction du controleur par defaut le controleur n'a pas de commande
	 */
	public NiborController(DrawingPanel panel) {
		this.commandeEnCours = new Command();
		this.commandePrecedente = new Command();
		panel.addMouseListener(new MouseClickHandler());
	}

	/**
	 * quand on demande les commandes, le controleur retourne la commande en
	 * cours
	 * 
	 * @return commande faite par le joueur
	 */
	public Command getCommand() {
		Command c = new Command();
		try {
			c = (Command) commandeEnCours.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		// si on relache une touche, on envoie la commande par défaut (pour ne rien faire)
		if (commandeEnCours.getActionType() == "released" )	{	
				commandeEnCours.setKeyCommand(Cmd.IDLE);
		}
		return c;
	}

	/**
	 * sauvegarde la commande précédente avant qu'elle ne soit ramplacée par la nouvelle
	 */
	public void saveFormerCommand() {
		try {
			commandePrecedente = (Command) commandeEnCours.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	@Override
	/**
	 * met a jour les commandes en fonction des touches appuyées
	 */
	public void keyPressed(KeyEvent e) {		
		saveFormerCommand();
		this.commandeEnCours.setKeyCommand(findKeyCommand(e.getKeyCode()), "pressed");
	}

	
	@Override
	/**
	 * met a jour les commandes quand le joueur relache une touche
	 */
	public void keyReleased(KeyEvent e) {
		saveFormerCommand();
		/**
		 * si la touche relachée est la même que la dernière touche appuyé 
		 * alors on met a jours la commande en cours
		 */
		if (commandePrecedente.getKeyCommand() == findKeyCommand(e.getKeyCode())) {
			this.commandeEnCours.setKeyCommand(findKeyCommand(e.getKeyCode()), "released");
		}
	}
	
	/**
	 * trouve la commande associée à une touche
	 * @param button code de la touche
	 * @return la commande 
	 */
	private Cmd findKeyCommand(int button) {
		
		switch (button) {
		// "Q" ou ← : GAUCHE
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_Q:
			return Cmd.LEFT;
		// "D" ou → : DROITE
		case KeyEvent.VK_RIGHT:	
		case KeyEvent.VK_D:
			return Cmd.RIGHT;
		// "Z" ou ↑ : HAUT
		case KeyEvent.VK_UP:	
		case KeyEvent.VK_Z:
			return Cmd.UP;
		// "S" ou ↓ : BAS
		case KeyEvent.VK_DOWN:	
		case KeyEvent.VK_S:
			return Cmd.DOWN;
		// "I" : INVENTAIRE
		case KeyEvent.VK_I:
			return Cmd.INVENTORY;
		// "E" : UTILISER
		case KeyEvent.VK_E:
			return Cmd.USE;
		// "ECHAPE" : FERMER
		case KeyEvent.VK_ESCAPE:
			return Cmd.CLOSE;
		}	
		return Cmd.IDLE;
	}
	

	@Override
	/**
	 * ne fait rien
	 */
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * (W I P)
	 * gère la détection des clics de souris
	 */
	private class MouseClickHandler extends MouseAdapter {
		// handle mouse-click event and determine which button was pressed
		@Override
		public void mousePressed(MouseEvent event) {
			Cmd cmd = findMouseCommand(event.getButton());
			commandeEnCours.setKeyCommand(cmd, "pressed");
			commandeEnCours.setClick(event.getX(), event.getY());
		}

		@Override
		public void mouseReleased(MouseEvent event) {
			Cmd cmd = findMouseCommand(event.getButton());
			commandeEnCours.setKeyCommand(cmd, "released");
			commandeEnCours.setClick(event.getX(), event.getY());
		}

		private Cmd findMouseCommand(int button) {
			// Clic GAUCHE
			if (button == MouseEvent.BUTTON1) {
				return Cmd.MOUSE_LEFT;
			}
			// Clic DROIT
			if (button == MouseEvent.BUTTON3) {
				return Cmd.MOUSE_RIGHT;
			}
			// Clic MOLETTE
			return Cmd.MOUSE_CENTER;		
		}
	}	
}
