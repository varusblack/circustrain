package game;

import game.factory.GameFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

import commands.CommandPay;
import actionCards.ActionCard;
import player.Player;
import talent.Clown;
import talent.Talent;
import utiles.factoria.CollectionsFactory;
import utiles.factoria.readDataFromKeyBoard;

public abstract class TwoPlayersGame extends CircusTrainGame{

	protected Integer numberOfPlayers=2;
	
	
	public void finalMonth(){
		//Puntos de victoria segun el nº de talentos
		compareTalentsCountAndAddVictoryPoints();
		//Puntos de victoria segun actuacion maxima
		int comparatorPerformancePoints =playerList.get(0).getPerformanceMax().compareTo(playerList.get(1).getPerformanceMax());
		if(comparatorPerformancePoints>0)
			playerList.get(0).addVictoryPoints(4);
		if(comparatorPerformancePoints<0)
			playerList.get(1).addVictoryPoints(4);			
		//Robar talentos
		//En modo basico solo puede robar el que tenga menos puntos de victoria
	}

	public void gameOver() {
		finalWage();
		higherClownNumber();
		higherMoneyAmount();
		higherPerformancesNumber();
		noClownsNoAnimals();
	}
	
	
	
	//====================================== X =====================================

	protected void rotatePlayers(){
		if(numberOfPlayers!=1){
			List<Player> newPlayerList=CollectionsFactory.createListFactory().createList();
			for(int i=1;i<playerList.size();i++){
				newPlayerList.add(playerList.get(i));	
			}
			newPlayerList.add(playerList.get(0));
			playerList.clear();
			playerList.addAll(newPlayerList);
		}
	}
	private void compareTalentsCountAndAddVictoryPoints(){
		List<Map<Talent,Integer>> playersTalentsList=CollectionsFactory.createListFactory().createList();
		for(Player player:playerList){
			playersTalentsList.add(player.getTalents());
		}
		//TODO List<Player>
		Set<Talent> playerOneTalents= playersTalentsList.get(0).keySet();
		Set<Talent> playerTwoTalents=playersTalentsList.get(1).keySet();
		
		Map<Talent,Integer> playerOneTalentsMap=playersTalentsList.get(0);
		Map<Talent,Integer> playerTwoTalentsMap=playersTalentsList.get(1);

		//Por cada talento del player1 mira todos los del player2
		for(Talent playerOneTalent:playerOneTalents){
			for(Talent playerTwoTalent:playerTwoTalents){
				//Se comprueba si ambos jugadores tienen el mismo talento
				if(playerTwoTalentsMap.containsKey(playerOneTalent)){
					//Si existe se compara la cantidad de talentos
					int comparation=playerOneTalentsMap.get(playerOneTalent).compareTo(playerTwoTalentsMap.get(playerTwoTalent));
					//Si player1 tiene mas se le añaden tantos puntos de victoria como talentos tenga
					if(comparation>0){
						playerList.get(0).addVictoryPoints(playerOneTalentsMap.get(playerOneTalent));
					}
					//Si player2 tiene mas se le añaden tantos puntos de victoria como talentos tenga
					if(comparation<0){
						playerList.get(1).addVictoryPoints(playerTwoTalentsMap.get(playerTwoTalent));
					}
					break;
				}
			}
		}
	}

	protected void finalWage() {
		for(Player player: playerList){
			for(ActionCard actioncard : player.getActionCards()){
				if(actioncard.getIdCard() == 5){
					player.addVictoryPoints(-3);
					CommandPay cmp = new CommandPay(player, this, 2);
					cmp.execute();
					break;
				}
			}
		}		
	}
	
	protected void higherClownNumber(){
		Integer playerClownNumber=0;
		Integer maximumClownNumber=-1;
		Integer sameClownNumberTimes=0;
		Integer previousClownNumber=0;
		List<Integer> clownNumber=CollectionsFactory.createListFactory().createList();
		for(int i=0;i<playerList.size();i++){
			Player thisPlayer=playerList.get(i);
			Map<Talent,Integer> thisPlayerTalents = thisPlayer.getTalents();
			if(thisPlayerTalents.containsKey(Clown.class)){
				playerClownNumber=thisPlayer.getTalents().get(Clown.class);			
			}else{
				playerClownNumber=0;
			}
			clownNumber.add(playerClownNumber);
			if(maximumClownNumber<playerClownNumber){
				maximumClownNumber=playerClownNumber;
			}
			if(i>0){
				Player previousPlayer=playerList.get(i-1);
				if(previousPlayer.getTalents().containsKey(Clown.class)){
					previousClownNumber=thisPlayer.getTalents().get(Clown.class);			
				}else{
					previousClownNumber=0;
				}
			}
			if(maximumClownNumber==previousClownNumber){
				sameClownNumberTimes++;
			}
		}
		if(sameClownNumberTimes==0){
			Integer playerIndex= clownNumber.indexOf(maximumClownNumber);
			playerList.get(playerIndex).addVictoryPoints(3);
		}
	}
	
	protected void higherMoneyAmount(){
		List<Integer> playersMoney=CollectionsFactory.createListFactory().createList();
		Integer sameMoneyTimes=0;
		Integer maximumMoney=-1;
		Integer previousMoney=0;
		for(int i=0;i<playerList.size();i++){
			if(i>0){
				previousMoney=playerList.get(i-1).getMoney();
			}
			Player thisPlayer=playerList.get(i);
			playersMoney.add(thisPlayer.getMoney());
			if(maximumMoney<thisPlayer.getMoney()){
				maximumMoney=thisPlayer.getMoney();
			}
			if(previousMoney==thisPlayer.getMoney()){
				sameMoneyTimes++;
			}
		}
		if(sameMoneyTimes==0){
			Integer playerIndex=playersMoney.indexOf(maximumMoney);
			playerList.get(playerIndex).addVictoryPoints(3);
		}
	}
	
	protected void higherPerformancesNumber(){
		List<Integer> playersNumberOfPerformances=CollectionsFactory.createListFactory().createList();
		Integer sameNumberOfPerformancesTimes=0;
		Integer maximumNumberOfPerformances=-1;
		Integer previousNumberOfPerformances=0;
		for(int i=0;i<playerList.size();i++){
			if(i>0){
				previousNumberOfPerformances=playerList.get(i-1).getPerfomancesUsed().size();
			}
			Player thisPlayer=playerList.get(i);
			playersNumberOfPerformances.add(thisPlayer.getPerfomancesUsed().size());
			if(maximumNumberOfPerformances<thisPlayer.getPerfomancesUsed().size()){
				maximumNumberOfPerformances=thisPlayer.getPerfomancesUsed().size();
			}
			if(previousNumberOfPerformances==thisPlayer.getPerfomancesUsed().size()){
				sameNumberOfPerformancesTimes++;
			}
		}
		if(sameNumberOfPerformancesTimes==0){
			Integer playerIndex=playersNumberOfPerformances.indexOf(maximumNumberOfPerformances);
			playerList.get(playerIndex).addVictoryPoints(3);
		}
	}
	

	protected void results(){
		// TODO DOCUMENTAR CAMBIO
		String winner="";
		Integer maxVictoryPoints=0;
		Integer maxPerformancePoints=0;
		Integer victoryPointsDrawGame=0;
		Integer performancePointsDrawGame=0;
		Integer previousPlayerVictoryPoints=0;
		Integer previousPlayerPerformancePoints=0;
		List<Integer> playerVictoryPoints=CollectionsFactory.createListFactory().createList();
		List<Integer> playerPerformancePoints=CollectionsFactory.createListFactory().createList();
		for(int i=0;i<playerList.size();i++){
			Player player=playerList.get(i);
			if(i>0){
				previousPlayerVictoryPoints=playerList.get(i-1).getVictoryPoints();
				previousPlayerPerformancePoints=playerList.get(i-1).getPerformanceMax();
			}
			playerVictoryPoints.add(player.getVictoryPoints());
			playerPerformancePoints.add(player.getPerformanceMax());
			if(maxVictoryPoints<player.getVictoryPoints()){
				maxVictoryPoints=player.getVictoryPoints();				
			}
			if(maxPerformancePoints<player.getPerformanceMax()){
				maxPerformancePoints=player.getPerformanceMax();
			}
			if(player.getVictoryPoints()==previousPlayerVictoryPoints){
				victoryPointsDrawGame=1;
			}
			if(player.getPerformanceMax()==previousPlayerPerformancePoints){
				performancePointsDrawGame=1;
			}
			
		}
		
		if(victoryPointsDrawGame==1){
			if(performancePointsDrawGame==1){
				winner="There's no winner."+"\n"+"============= DRAW GAME =============";
			}else{
				Integer winnerIndex=playerPerformancePoints.indexOf(maxPerformancePoints);
				Player winnerPlayer=playerList.get(winnerIndex);
				winner="Here's your winner! The winner is"+"\n"+"============= "+winnerPlayer.getName()+" =============";
			}
		}else{
			Integer winnerIndex=playerVictoryPoints.indexOf(maxVictoryPoints);
			Player winnerPlayer=playerList.get(winnerIndex);
			winner="Here's your winner! The winner is"+"\n"+"============= "+winnerPlayer.getName()+" =============";
		}
		System.out.println(winner+"\n"+"\n"+"---=== Thank you for playing Train Circus! ===---");
	}
	public void noClownsNoAnimals(){
		for(Player thisPlayer:playerList){
			Map<Talent,Integer> thisPlayerTalents = thisPlayer.getTalents();
			if(!thisPlayerTalents.containsKey(talent.Clown.class)){
				thisPlayer.addVictoryPoints(-3);
			}			
			Boolean noAnimals=(!thisPlayerTalents.containsKey(talent.BigCat.class))&&
			(!thisPlayerTalents.containsKey(talent.Elephant.class))&&
			(!thisPlayerTalents.containsKey(talent.Horse.class));
			if(noAnimals){
				thisPlayer.addVictoryPoints(-3);
			}			
		}
	}



	protected void setPlayersNames() {
		for(int i=0;i<2;i++){
			String name=readDataFromKeyBoard.takeParametersToString("Player name: ");
			Player player=GameFactory.createPlayer(name);
			player.addTalent(theClown);
			talentBag.removeTalent(clown);
			player.addActionCards(GameFactory.inicializateActionCards(this, player));
			playerList.add(player);
		}
	}	
}