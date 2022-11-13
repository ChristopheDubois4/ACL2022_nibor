package start;

import model.NiborPainter;
import prefab.information.State;

import engine.DrawingPanel;
import engine.GameEngineGraphical;
import manager.WorldManager;
import model.NiborController;
import model.NiborGame;

/**
 * lancement du moteur avec le jeu
 */
public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		originalCode();
		
		test_Partie_Dubois();
	}

	public static void originalCode() throws InterruptedException{

		//test_Partie_Dubois();
		WorldManager worldManager = new WorldManager();

		NiborPainter painter = new NiborPainter(worldManager);
		
		// creation du jeu particulier et de son afficheur
		NiborGame game = new NiborGame(worldManager);


		

		/**
		 * creation du pannel pour pouvoir le donnée au controller (détection des clics de souris)
		 * et à l'engine (pour l'interface graphique)
		 */
		DrawingPanel panel = new DrawingPanel(painter);

		NiborController controller = new NiborController(panel);

		// classe qui lance le moteur de jeu generique
		GameEngineGraphical engine = new GameEngineGraphical(game, panel, controller);
		engine.run();
	}

	/**
	 * Tests pour la partie de Dubois
	 */
	public static void test_Partie_Dubois() {
	
	}

	

}
