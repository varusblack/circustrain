package game.factory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

import bag.EventBag;
import bag.PerformanceBag;
import bag.TalentBag;
import bag.TalentBagImpl;
import board.Board;
import board.BoardImpl;
import player.Player;
import player.PlayerImpl;

public class GameFactory {
	
	//PARAMETROS: parametros de los distintos constructores. A partir de ahi se
	// podria poner el create con la ruta del archivo. Ejemplo: 
	// Board board=GameFactory.createBoard().create("C:\tablero.txt")
	
	public static Player createPlayer(String name,Boolean play_mode, Boolean firstp){
		return new PlayerImpl(name,play_mode,firstp);
	}
	
	public static EventBag createEventBag(){
		return new EventBagImpl(/*PARAMETROS*/);
	}
	
	public static PerformanceBag createPerformanceBag(){
		return new PerformanceBagImpl(PARAMETROS);
	}
	
	public static TalentBag createTalentBag(){
		return new TalentBagImpl(PARAMETROS);
	}
	
	public static Board createBoard(){
		return new BoardImpl();
	}
	
	public static String takeParametersToString(String message){
		String line=null;
		try {
			System.out.print(message.toString());
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			line = br.readLine();
		} catch (Exception e) {
		}
		return line;
	}
	
	public static Integer takeParametersToInteger(String message){
		Integer number=-1;
		try {
			System.out.print(message.toString());
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String linea = br.readLine();
			number = Integer.parseInt(linea);
		} catch (Exception e) {
		}
		return number;
	}
	
	public static Integer throwDice(){
		Random random = new Random();
		int tiradaDado = (Math.abs(random.nextInt()) % 6) + 1;
		return new Integer(tiradaDado);
	}
}
