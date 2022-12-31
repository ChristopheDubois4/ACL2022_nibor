package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.BasicStroke;
import java.util.ArrayList;
import java.util.List;

import engine.GamePainter;
import manager.WorldPainter;
import prefab.rendering.Visual;

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
	 * @throws Exception
	 */
	@Override
	public void draw(BufferedImage im) {


		Graphics2D g = (Graphics2D) im.getGraphics();
				
		
		
		//liste d'images avec leurs coordonnees
		List<Visual> visuals;

		visuals = new ArrayList<Visual>();
		try {
			visuals = worldPainter.getVisuals();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(visuals.size());
		//affichages images
		for(int i=0; i<visuals.size();i++){
			Visual visual = visuals.get(i);
			g.drawImage(visual.getBufferedImage(), visual.getX(), visual.getY(), visual.getWidth(), visual.getHeight(),  null);
		}
		
		worldPainter.drawHuds(g);

		try {
			visuals = worldPainter.getFrontVisuals();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		//affichages images (devant les Huds)
		for(int i=0; i<visuals.size();i++){
			Visual visual = visuals.get(i);
			g.drawImage(visual.getBufferedImage(), visual.getX(), visual.getY(), visual.getWidth(), visual.getHeight(),  null);
		}
		/**
		 * invertion horizontale d'une image
		 * g.drawImage(bufferedImage, x + width, y, -width, height, null);
		 */

		g.setStroke(new BasicStroke(1));

		//affichage quadrillage
		//for (int i = 60; i <900; i= i+60) {
		//	g.drawLine(0, i, 1620, i);
		//}
		//for (int i = 60; i <1620; i= i+60) {
		//	g.drawLine(i, 0, i, 900);
		//}


	}
}
