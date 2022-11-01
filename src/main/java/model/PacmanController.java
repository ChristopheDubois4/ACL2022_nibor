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
public class PacmanController implements GameController {

	/**
	 * commande en cours
	 */
	private Command commandeEnCours;

	JLabel label;
	
	/**
	 * construction du controleur par defaut le controleur n'a pas de commande
	 */
	public PacmanController(DrawingPanel panel) {
		this.commandeEnCours = new Command();
		panel.addMouseListener(new MouseClickHandler());
	}

	/**
	 * quand on demande les commandes, le controleur retourne la commande en
	 * cours
	 * 
	 * @return commande faite par le joueur
	 */
	public Command getCommand() {
		return this.commandeEnCours;
	}

	@Override
	/**
	 * met a jour les commandes en fonction des touches appuyées
	 */
	public void keyPressed(KeyEvent e) {
		Cmd cmd = Cmd.IDLE;
		switch (e.getKeyCode()) {
			// "Q" ou ← : GAUCHE
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_Q:
				cmd = Cmd.LEFT;
				break;	
			// "D" ou → : DROITE
			case KeyEvent.VK_RIGHT:	
			case KeyEvent.VK_D:
				cmd = Cmd.RIGHT;
				break;	
			// "Z" ou ↑ : HAUT
			case KeyEvent.VK_UP:	
			case KeyEvent.VK_Z:
				cmd = Cmd.UP;
				break;
			// "S" ou ↓ : BAS
			case KeyEvent.VK_DOWN:	
			case KeyEvent.VK_S:
				cmd = Cmd.DOWN;
				break;
			// "I" : INVENTAIRE
			case KeyEvent.VK_I:
			cmd = Cmd.INVENTORY;
			break;
		}
		this.commandeEnCours.setKeyCommand(cmd);
	}

	@Override
	/**
	 * met a jour les commandes quand le joueur relache une touche
	 */
	public void keyReleased(KeyEvent e) {
		this.commandeEnCours.setKeyCommand(Cmd.IDLE);
	}

	@Override
	/**
	 * ne fait rien
	 */
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * WORK IN PROGRESS
	 */
	private class MouseClickHandler extends MouseAdapter {
		// handle mouse-click event and determine which button was pressed
		@Override
		public void mousePressed(MouseEvent event) {
			Cmd cmd = findCommand(event.getButton());
			commandeEnCours.setKeyCommand(cmd, "pressed");
			commandeEnCours.setClick(event.getX(), event.getY());
		}

		private Cmd findCommand(int button) {
			// Clic GAUCHE
			if (button == MouseEvent.BUTTON1) {
				return Cmd.mouseLEFT;
			}
			// Clic DROIT
			if (button == MouseEvent.BUTTON2) {
				return Cmd.mouseRIGHT;
			}
			// Clic MOLETTE
			return Cmd.mouseCENTER;		
		}
	}	
}
