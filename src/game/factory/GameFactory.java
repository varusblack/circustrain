package game.factory;

import game.AdvancedTwoPlayersGame;
import game.BasicSoloGame;
import game.TwoPlayersGame;
import game.TwoPlayersGameImpl;
import game.BasicTwoPlayersGame;

import java.util.List;
import java.util.Random;

import actionCards.ActionCard;
import actionCards.BasicMoveImpl;
import actionCards.FastMoveImpl;
import actionCards.HoldImpl;
import actionCards.OvernighterImpl;
import actionCards.RestImpl;
import actionCards.TravelImpl;
import actionCards.WagesImpl;
import bag.PerformanceBag;
import bag.PerformanceBagImpl;
import bag.TalentBag;
import bag.TalentBagImpl;
import board.Board;
import board.BoardImpl;
import player.Player;
import player.PlayerImpl;
import talent.AcrobatImpl;
import talent.BigCatImpl;
import talent.ClownImpl;
import talent.ElephantImpl;
import talent.FreakShowImpl;
import talent.HorseImpl;
import talent.HumanCannonballImpl;
import talent.Talent;
import utiles.factoria.CollectionsFactory;
import utiles.factoria.readDataFromKeyBoard;

public class GameFactory {
	
	//PARAMETROS: parametros de los distintos constructores. A partir de ahi se
	// podria poner el create con la ruta del archivo. Ejemplo: 
	// Board board=GameFactory.createBoard().create("C:\tablero.txt")
	
	public static Player createPlayer(String name,Boolean play_mode){
		return new PlayerImpl(name,play_mode);
	}
	
//	public static EventBag createEventBag(){
//		return new EventBagImpl(/*PARAMETROS*/);
//	}
	
	// A LA ESPERA DE UN CONVENIO PARA ESTO.
	
	
	public static PerformanceBag createPerformanceBag(){
		return new PerformanceBagImpl("/data/performancecfg.txt");
	}
	
	public static TalentBag createTalentBag(){
		return new TalentBagImpl();
		/*
		 * NOTA DE FRANCIS:
		 * el constructor TalentBagImpl no existía como tal, había creado un método llamado
		 * createInitialTalentBag(). Lo he modificado de nombre para que pudiera llamarse
		 * como está aquí.
		 */
	}
	
	public static Board createBoard(){
		return new BoardImpl("/data/boardcfg.txt");
	}
	
	public static Integer throwDice(){
		Random random = new Random();
		int tiradaDado = (Math.abs(random.nextInt()) % 6) + 1;
		return new Integer(tiradaDado);
	}
	
	public static Talent createTalent(String talentToCreate){
		talentToCreate=talentToCreate.toUpperCase();
		
		if(talentToCreate.equals("ACROBAT")){
			return new AcrobatImpl();
		}
		if(talentToCreate.equals("CLOWN")){
			return new ClownImpl();
		}
		if(talentToCreate.equals("BIG CAT")){
			return new BigCatImpl();
		}
		if(talentToCreate.equals("ELEPHANT")){
			return new ElephantImpl();
		}
		if(talentToCreate.equals("FREAK SHOW")){
			return new FreakShowImpl();
		}
		if(talentToCreate.equals("HORSE")){
			return new HorseImpl();
		}
		if(talentToCreate.equals("HUMAN CANNON BALL")){
			return new HumanCannonballImpl();
		}
		return null;
	}
	
	public static List<ActionCard> inicializateActionCards(TwoPlayersGame ctg, Player player){
		List<ActionCard> ac = CollectionsFactory.createListFactory().createList();
		ac.add(new TravelImpl(player));
		ac.add(new BasicMoveImpl(2, player));
		ac.add(new BasicMoveImpl(3, player));
		ac.add(new FastMoveImpl(player));
		ac.add(new WagesImpl(player,ctg));
		ac.add(new OvernighterImpl(player));
		ac.add(new HoldImpl(player));
		if(player.getPlay_Mode()){
			ac.add(new RestImpl(player,ctg));			
		}
		return ac;
	}
	
	public static TwoPlayersGame createCircusTrainGame(){

		TwoPlayersGame game=null;

		System.out.println("Welcome to Circus Train!");
		
		//Peticion de numero de jugadores
//		String askNumberOfPlayers="How many players are going to play: ";
//		String askNumberOfPlayersCondition="1,2";
//		numberOfPlayers=readDataFromKeyBoard.takeParametersToIntegerRestricted(askNumberOfPlayers,askNumberOfPlayersCondition);
		Integer numberOfPlayers=2;
		
		//Seleccion de modo de juego
//		String selectGameMode="Select game mode:"+"\n"+"1 : Basic mode"+"\n"+"2 : Advanced mode";
//		String selectGameModeCondition="1,2";
//		String gameMode=readDataFromKeyBoard.takeParametersToStringRestricted(selectGameMode,selectGameModeCondition);
		String gameMode="2";
		
		if(numberOfPlayers==1){
			if(gameMode=="1"){
				game=new BasicSoloGame();
			}else if(gameMode=="2"){
				game=new AdvancedTwoPlayersGame();
			}
		}else if (numberOfPlayers==2){
			if(gameMode=="1"){
				game=new BasicTwoPlayersGame();
			}else if(gameMode=="2"){
				game=new AdvancedTwoPlayersGame();
			}
		}
		
		return game;
	}
}
