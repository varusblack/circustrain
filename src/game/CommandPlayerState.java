package game;

import performance.PerformanceDemand;
import player.Player;

public class CommandPlayerState extends AbstractCommand{
	
	private Player player;
	
	public CommandPlayerState(Player player){
		this.player=player;
	}
	
	public void execute(){
		String messageForTwoWeeksPerformance="You must to performance "+player.getWeeksToPerformance()+" times more to gain points.";
		PerformanceDemand performance=(PerformanceDemand) player.getCity().getPerformance();
		String actionCardList=player.getActionCards().toString();
		System.out.println("You have the following action card/s: "+actionCardList);
		String discardActionCardList=player.getdiscartpile().toString();
		System.out.println("You have the following discarded action card/s: "+discardActionCardList);
		System.out.println("You have the following talents: "+player.getTalents().toString());
		System.out.println("Your reputation is: "+player.getReputation());
		System.out.println("Your higher dice score is: "+player.getHigherDiceScore());
		System.out.println("Your current position is: "+player.getCity().toString());
		System.out.println("Your higher performance score is: "+player.getPerformanceMax());
		System.out.println("You have "+player.getMoney()+" $");
		if(performance.isTwoWeeks()){			
			System.out.println("Rememeber that you are in a city that contains a Two Week Performance Demand."+"\n"+messageForTwoWeeksPerformance);
		}
	}

}