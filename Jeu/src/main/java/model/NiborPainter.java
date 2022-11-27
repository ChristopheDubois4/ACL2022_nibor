package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.BasicStroke;

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

	
    public static final int SCREEN_WIDTH = 1620;
	public static final int SCREEN_HEIGHT = 900;
	public static final int TILE_LENGTH = 60;

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
			Visual visual = visuals.get(i);
			g.drawImage(visual.getBufferedImage(), visual.getX(), visual.getY() , null);
		}
		
		worldPainter.drawHuds(g);

		visuals = worldPainter.getFrontVisuals();

		//affichages images (devant les Huds)
		for(int i=0; i<visuals.size();i++){
			Visual visual = visuals.get(i);
			g.drawImage(visual.getBufferedImage(), visual.getX(), visual.getY() , null);
		}

		g.setStroke(new BasicStroke(1));

		if (true) {
			return;
		}

		//affichage quadrillage
		 for (int i = 60; i <900; i= i+60) {
		 	g.drawLine(0, i, 1620, i);
		 }
		 for (int i = 60; i <1620; i= i+60) {
		 	g.drawLine(i, 0, i, 900);
		 }


	}
}
