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

	JLabel label;
	
	/**
	 * construction du controleur par defaut le controleur n'a pas de commande
	 */
	public NiborController(DrawingPanel panel) {
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
		Command c = new Command();
		try {
			c = (Command) commandeEnCours.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		commandeEnCours.setKeyCommand(Cmd.IDLE);
		return c;
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
			case KeyEvent.VK_E:
				cmd = Cmd.USE;
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
	 * (W I P)
	 * gère la détection des clics de souris
	 */
	private class MouseClickHandler extends MouseAdapter {
		// handle mouse-click event and determine which button was pressed
		@Override
		public void mousePressed(MouseEvent event) {
			Cmd cmd = findCommand(event.getButton());
			commandeEnCours.setKeyCommand(cmd, "pressed");
			commandeEnCours.setClick(event.getX(), event.getY());
		}

		@Override
		public void mouseReleased(MouseEvent event) {
			Cmd cmd = findCommand(event.getButton());
			commandeEnCours.setKeyCommand(cmd, "release");
			commandeEnCours.setClick(event.getX(), event.getY());
		}

		private Cmd findCommand(int button) {
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
