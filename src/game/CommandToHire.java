package game;

import game.factory.GameFactory;

import java.util.List;

import performance.BankruptCircus;
import player.Player;
import talent.Talent;
import utiles.factoria.CollectionsFactory;

import counter.BankruptCircusCounter;

import card.TypeTalentCard;

public class CommandToHire extends AbstractCommand{
	private BankruptCircus circus;
	private Player player;
	
	public CommandToHire (BankruptCircus circus, Player player){
		this.circus=circus;
		this.player=player;
	}
	
	public void execute(){
		List<Talent> newtalents=CollectionsFactory.createListFactory().createList();
		for(Talent t: circus.getTalents()){
			String answers="\n" + "1 : YES" + "\n" + "2 : NO";
			String question="Here is an unemployed talent: "+t.toString()+". Do you want to hire him?"+answers;
			String condition="1,2";
			String election= GameFactory.takeParametersToStringRestricted(question, condition);

			if(!election.equals("2")){
				Integer dice=GameFactory.throwDice();
				System.out.println("The dice has been thrown: "+dice);
				Integer repMaxValue=player.getHigherDiceScore();
				if((1<=dice) && (dice<=repMaxValue)){
					newtalents.add(t);
				}else{
					if(!((player.getMoney())<10)){	
						String answers1="\n" + "1 : YES" + "\n" + "2 : NO";
						String message1="You fail throwng the dice for hiring a talent,do you want to pay?"+answers1;
						String condition1="1,2";
						String failure=GameFactory.takeParametersToStringRestricted(message1, condition1);
						if(!failure.equals("2")){
							newtalents.add(t);
							player.addMoney(-10);							
						}						
					}
				}
			}
		}
		player.addTalent(newtalents);
	}
	

}

