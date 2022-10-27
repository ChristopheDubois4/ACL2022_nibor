package start;

import model.PacmanPainter;

import engine.GameEngineGraphical;
import model.PacmanController;
import model.PacmanGame;

/**
 * lancement du moteur avec le jeu
 */
public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		originalCode();
		
		test_Partie_Dubois();
	}

	public static void originalCode() throws InterruptedException{

		// creation du jeu particulier et de son afficheur
		PacmanGame game = new PacmanGame("helpFilePacman.txt");
		PacmanPainter painter = new PacmanPainter();
		PacmanController controller = new PacmanController();

		// classe qui lance le moteur de jeu generique
		GameEngineGraphical engine = new GameEngineGraphical(game, painter, controller);
		engine.run();
	}

	/**
	 * Tests pour la partie de Dubois
	 */
	public static void test_Partie_Dubois() {
		
	}

}
