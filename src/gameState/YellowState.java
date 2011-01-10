package gameState;

import game.CircusTrainGame;

import java.util.List;

import performance.Performance;
import player.Player;

public class YellowState extends GameStateImpl implements GameState {

	public YellowState(CircusTrainGame game){
		super(game);
		completeBoard();
	}
	public void completeBoard() {
		completeBoardPerformances(10);
	}
	public Performance getPerformance(){
		List<Performance> bag=game.getPerformanceBag().getYellowBag();
		return getPerformanceInList(bag);
	}
	public void discardPilePunishment(Player player) {
		player.addReputation(2);
	}

}
