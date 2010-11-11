package board;

import java.util.Set;

import performance.Performance;

public interface City {
	
	public String getName();
	public void setName(String name);
	
	public Boolean getCanada();
	
	public Boolean hasPerfomance();
	public Performance getPerformance();
	public void setPerfomance(Performance perfomance);
	
	public Set<City> maxMovement(Integer jump);
	public Set<City> exactMovement(Integer jump);
	
	public String toString();
}
