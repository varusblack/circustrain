package board;

import performance.VictoryPoints;
import performance.VictoryPointsImpl;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Board Tablero=new BoardImpl("/data/boardcfg.txt");
		VictoryPoints vp=new VictoryPointsImpl("red","PV1",2); 
		City c=Tablero.addPerfomanceInRandomCity(vp);
		System.out.println(c);
		System.out.println(Tablero.getCities());

	}
}
