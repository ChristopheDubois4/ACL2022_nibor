package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.util.List;


import engine.GamePainter;
import manager.WorldPainter;
import prefab.information.Visual;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * afficheur graphique pour le game
 * 
 */
public class NiborPainter implements GamePainter {

	/**
	 * la taille des cases
	 */
	protected static final int WIDTH = 1620;
	protected static final int HEIGHT = 900;

	private WorldPainter worldPainter;
	/**
	 * appelle constructeur parent
	 * 
	 * @param game
	 *            le jeutest a afficher
	 */
	public NiborPainter(WorldPainter worldPainter) {
		this.worldPainter = worldPainter;
	}

	/**
	 * methode  redefinie de Afficheur retourne une image du jeu
	 */
	@Override
	public void draw(BufferedImage im) {

		Graphics2D g = (Graphics2D) im.getGraphics();
		
		//liste d'images avec leurs coordonnees
		List<Visual> visuals = worldPainter.getVisuals();

		//affichages images
		for(int i=0; i<visuals.size();i++){
			Visual image = visuals.get(i);
			g.drawImage(image.getBufferedImage(), image.getX()*60, HEIGHT - image.getY()*60 - 60 , null);
		}

		//affichage quadrillage
		g.setColor(Color.blue);
		for (int i = 60; i <900; i= i+60) {
			g.drawLine(0, i, 1620, i);
		}
		for (int i = 60; i <1620; i= i+60) {
			g.drawLine(i, 0, i, 900);
		}

	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

}
