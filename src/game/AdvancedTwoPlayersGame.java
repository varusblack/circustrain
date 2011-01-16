package game;


import java.util.List;

import player.Player;
import utiles.factoria.CollectionsFactory;
import utiles.factoria.readDataFromKeyBoard;
import actionCards.ActionCard;
import actionCards.RestImpl;

public class AdvancedTwoPlayersGame extends TwoPlayersGame {
	

	
	@Override
	public void finalMonth(){
		//Puntos de victoria segun el nº de talentos
		comparePlayersAndAddVictoryPoints();			
//		//Robar talentos
//		stealTalentsSelector();
	}
	@Override
	public void gameOver(){
		super.gameOver();
		higherReputation();
	}	
	
	private void higherReputation(){
		List<Integer> playersReputation=CollectionsFactory.createListFactory().createList();
		Integer sameReputationTimes=0;
		Integer maximumReputation=-1;
		Integer previousReputation=0;
		for(int i=0;i<playerList.size();i++){
			if(i>0){
				previousReputation=playerList.get(i-1).getReputation();
			}
			Player thisPlayer=playerList.get(i);
			playersReputation.add(thisPlayer.getReputation());
			if(maximumReputation<thisPlayer.getReputation()){
				maximumReputation=thisPlayer.getReputation();
			}
			if(previousReputation==thisPlayer.getReputation()){
				sameReputationTimes++;
			}
		}
		if(sameReputationTimes==0){
			Integer playerIndex=playersReputation.indexOf(maximumReputation);
			playerList.get(playerIndex).addVictoryPoints(3);
		}
	}
	
	@Override
	protected void selectCase(Player player){
		String action1="[1] Jugar una carta de acción";
		String action2="[2] Jugar una carta de acción ya descartada";		
		String action3="[3] Pedir dinero al banco";

		String askBasicAction = "¿Qué vas a hacer?:" + "\n" + action1;
		String askAllActions = askBasicAction + "\n" + action2+"\n" + action3;
		String askActionsDiscardedNoMoney = askBasicAction + "\n" + action2;
		String askActionsNoDiscardedMoney = askBasicAction + "\n" + action3;

		String askBasicActionCondition = "1";
		String askAllActionsCondition = "1,2,3";
		String askActionsDiscardedNoMoneyCondition = "1,2";
		String askActionsNoDiscardedMoneyCondition = "1,3";

		String action="";
		
		
		if(!this.getMonth().equals("AUGUST") || !this.getMonth().equals("SEPTEMBER")){
			//Si puede perder 2 puntos de reputacion y tiene cartas descartadas,podra optar a jugar una carta de accion de su mano,
			//jugar una carta de accion descartada o a obtener dinero del banco
			if(player.getReputation()<=6 && !player.getdiscartpile().isEmpty()){
				action=readDataFromKeyBoard.takeParametersToStringRestricted(askAllActions, askAllActionsCondition);

			}else{	
				//Si solo puede perder 1 punto de reputacion solo podra optar a jugar una carta de accion de su mano o a coger dinero del banco
				if(player.getReputation()<=7){
					action=readDataFromKeyBoard.takeParametersToStringRestricted(askActionsNoDiscardedMoney, askActionsNoDiscardedMoneyCondition);

				}else{
					action=readDataFromKeyBoard.takeParametersToStringRestricted(askBasicAction, askBasicActionCondition);
				}
			}						
		}else{
			//Si puede perder 4 puntos de victoria, podra optar a jugar una carta de accion de su mano o a jugar una carta de accion descartada
			if(player.getVictoryPoints()>=4){
				action=readDataFromKeyBoard.takeParametersToStringRestricted(askActionsDiscardedNoMoney, askActionsDiscardedNoMoneyCondition);
			}else{
				action=readDataFromKeyBoard.takeParametersToStringRestricted(askBasicAction, askBasicActionCondition);
			}
		}
		this.setFollowingAction(action);
	}
	
	
	
	@Override
	protected void executeCase(Player player) {
		if(getFollowingAction().equals("1")){
			List<ActionCard> actionCards=player.getActionCards();
			Integer selectedCard=selectCard(actionCards);
			player.getdiscartpile().add(actionCards.get(selectedCard));
			player.getActionCards().remove(actionCards.get(selectedCard));
		}else{
			if(getFollowingAction().equals("2")){
				selectCard(player.getdiscartpile());
				gameState.discardPilePunishment(player);
			}
			if(getFollowingAction().equals("3")){
				player.collectMoney();
			}
		}
	}
	
	protected void refreshToFire(Player player){
		if(gameState.getMonth().equals("AUGUST") || gameState.getMonth().equals("SEPTEMBER")){			
			if(player.getVictoryPoints()<3){
				player.addVictoryPoints(-player.getVictoryPoints());
				assert player.getVictoryPoints()!=0:"Puntos de victoria no son 0";
			}else{
				player.addVictoryPoints(-3);
				assert player.getVictoryPoints()>=0:"Puntos de victoria negativos";
			}
		}else{
			if(player.getReputation()<7){
				player.addReputation(1);
				assert player.getReputation()<7:"Reputacion fuera de rango";
			}
		}
	}
}
