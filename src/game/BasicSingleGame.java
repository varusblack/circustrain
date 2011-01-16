package game;

import java.util.List;

import actionCards.ActionCard;
import actionCards.RestImpl;
import player.Player;

public class BasicSingleGame extends OnePlayerGame {
	
	
	@Override
	protected void selectCase(Player p){
		//Si la unica opción que puede usar es jugar una carta de acción, ¿para que preguntarle lo que quiere hacer, ir directamente a preguntarle que carta quiere jugar
		
		
//		String action1="1 : Play one Action card from my hand";
//		String askBasicAction = "What are you going to do:" + "\n" + action1;
//		String askBasicActionCondition = "1";
		String action="1";
//		action=readDataFromKeyBoard.takeParametersToStringRestricted(askBasicAction, askBasicActionCondition);
		this.setFollowingAction(action);
	}

	@Override
	protected void executeCase(Player player) {
		List<ActionCard> actionCards=player.getActionCards();
		Integer selectedCard=selectCard(actionCards);
		player.getdiscartpile().add(actionCards.get(selectedCard));
		player.getActionCards().remove(actionCards.get(selectedCard));
	}
	
	protected void refreshToFire(Player player){
		//No hace nada en modo básico
	}
	
}
