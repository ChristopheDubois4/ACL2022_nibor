package start;

import model.NiborPainter;

import engine.DrawingPanel;
import engine.GameEngineGraphical;
import manager.WorldManager;
import model.NiborController;
import model.NiborGame;

/**
 * lancement du moteur avec le jeu
 */
public class Main {

	public static void main(String[] args) throws Exception {
						
		WorldManager worldManager = new WorldManager();
		NiborPainter painter = new NiborPainter(worldManager);		
		// creation du jeu particulier et de son afficheur
		NiborGame game = new NiborGame(worldManager);
		
		// creation du pannel pour pouvoir le donnée au controller (détection des clics de souris)
		//et à l'engine (pour l'interface graphique)
		 
		DrawingPanel panel = new DrawingPanel(painter);
		NiborController controller = new NiborController(panel);
		// classe qui lance le moteur de jeu generique
		GameEngineGraphical engine = new GameEngineGraphical(game, panel, controller);
		engine.run();		
		
	}
}
