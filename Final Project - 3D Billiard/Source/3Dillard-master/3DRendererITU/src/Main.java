import engine.BilliardGame;
import game.EightBallGame;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		BilliardGame game = new EightBallGame();
		//BilliardGame game = new Test();
		game.run();

	}

}
