package radua.tracks.logic.models;

import java.awt.Color;

import radua.ui.logic.models.BasicModel;

public class Train extends BasicModel
{
	private static final long serialVersionUID = 6584766422193674722L;
	
	
	public Train(double x, double y) {
		super(x, y, 40, 40, Color.CYAN);
	}
}
