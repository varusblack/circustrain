package actionCards;

import java.util.List;

import commands.CommandPerformance;
import commands.CommandToHire;

import performance.BankruptCircus;
import performance.Performance;
import performance.PerformanceDemand;
import player.Player;

import board.City;
import utiles.factoria.CollectionsFactory;
import utiles.factoria.readDataFromKeyBoard;
import card.CardImpl;

public abstract class ActionCardImpl extends CardImpl implements ActionCard {

	public ActionCardImpl(String name, String des) {
		super(name, des);
	}

	public void movePlayer(Player player,Integer move,Boolean onlyPerformance){
		Integer answer;
		List<City> adjCities = CollectionsFactory.createListFactory().createList();

		System.out.println("Selecciona la ciudad a la que quieres viajar:");

		if(!onlyPerformance){
			adjCities=player.getCity().maxMovement(move);
			for (int i=0;i<adjCities.size();i++){
				System.out.println("--> ["+i+"]"+ adjCities.get(i));
			}
			answer= readDataFromKeyBoard.takeParametersToIntegerTopValue("Option:", adjCities.size());
			player.moveCity(adjCities.get(answer));
		}
		else{
			adjCities=player.getCity().maxMovement(move);
			List<City> adjCitiesWithPerformance = CollectionsFactory.createListFactory().createList();
			for (City c: adjCities){
				if (c.hasPerfomance()){
					adjCitiesWithPerformance.add(c);
				}
			}
			for (int i=0; i< adjCitiesWithPerformance.size() ; i++){
				System.out.println("--> ["+i+"]"+ adjCitiesWithPerformance.get(i));
			}
			answer= readDataFromKeyBoard.takeParametersToIntegerTopValue("Option:", adjCitiesWithPerformance.size());
			player.moveCity(adjCities.get(answer));
		}
	}

	public void performPlayer(Player player){

		Integer answer;
		Performance p = player.getCity().getPerformance();
		if (p instanceof PerformanceDemand){
			System.out.println("Se hace saber que la ciudad de "+player.getCity()+" demanda urgentemente una Actuación : "+ p );
			answer = 0;
		}else if (p instanceof BankruptCircus){
			System.out.println("Por la calles de esta ciudad deambula un Circo en Bancarrota: "+ p + "\n");
			answer =1;
		} else {
			throw new IllegalArgumentException("No puedes instanciar otra cosa que no sea Performance or BankruptCircus");
		}
		if (answer ==0){
			if (p instanceof PerformanceDemand){
				CommandPerformance cp = new CommandPerformance(player,(PerformanceDemand) p);
				cp.execute();
			}
			else{
				System.out.print("This city has no performance\n");
			}
		}
		if (answer ==1){
			if(p instanceof BankruptCircus){
				CommandToHire ch = new CommandToHire((BankruptCircus)p, player);
				ch.execute();
			}
			else{
				System.out.print("This city has no BankruptCircus\n");
			}
		}
	}
}