package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import javax.imageio.ImageIO;

import engine.GamePainter;
import manager.WorldPainter;
import prefab.information.Visual;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * afficheur graphique pour le game
 * 
 */
public class PacmanPainter implements GamePainter {

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
	public PacmanPainter(WorldPainter worldPainter) {
		this.worldPainter = worldPainter;
	}

	/**
	 * methode  redefinie de Afficheur retourne une image du jeu
	 */
	@Override
	public void draw(BufferedImage im) {
		//test affichage mais pas de transparance 
		Graphics2D g = (Graphics2D) im.getGraphics();
		
		//coordonnees

		List<Visual> visuals = worldPainter.getVisuals();

	

		int x1=7,y1=7;
		int x2=7,y2=7;
		BufferedImage im1 =null;
		BufferedImage im2 =null;




		Path chemin = Paths.get("src/main/ressources/images/items/box.png");
		Path chemin2 = Paths.get("src/main/ressources/images/characters/player/idleDown.png");
    

		//lecture chemin mais doit etre dans le meme package (pb)
		try {
			im1 = ImageIO.read(new File(chemin.toAbsolutePath().toString()) );
			im2 = ImageIO.read(new File(chemin2.toAbsolutePath().toString()) );
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		//affichage quadrillage
		g.setColor(Color.blue);
		for (int i = 60; i <900; i= i+60) {
			g.drawLine(0, i, 1620, i);
		}
		for (int i = 60; i <1620; i= i+60) {
			g.drawLine(i, 0, i, 900);
		}

	
		//affichage images
		g.drawImage(im1, x1*60, y1*60, null);
		g.drawImage(im2, x2*60+1, y2*60-10, null);
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
