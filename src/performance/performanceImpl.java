package performance;

import java.awt.Color;

public class performanceImpl implements Performance {
	
	public performanceImpl(Color cardColor, String description) {
		super();
		this.cardColor = cardColor;
		this.description = description;
	}

	private Color cardColor;
	private String description,name;
	@Override
	
	
	public Color getColor() {
		// TODO Auto-generated method stub
		return cardColor;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return description;
	}


	public String toString(){
		return "La carta con descripcion "+ this.getDescription()+" y color "+this.getColor().toString();
	}
}
