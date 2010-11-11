package bag;

import java.awt.Color;
import java.util.List;
import performance.Performance;

public interface PerformanceBag extends Performance {

	/*This method must be static and private*/public List<Performance> createGreenBag();
	/*This method must be static and private*/public List<Performance> createYellowBag();
	/*This method must be static and private*/public List<Performance> createRedBag();
	
	
	public Performance getPerformance(Color color);//This method will be random, over a concrete color
	public Performance removePerformance(Performance p);
	public Performance addPerformance(Performance e);
}
