package radua.ui.display.insulator;

import java.awt.Color;

import radua.ui.logic.basics.MColor;

public class ColorTranslator 
{
	public static Color TranslateColor(MColor mColor)
	{
		switch (mColor) {
			case BLACK:
				return Color.BLACK; 
			case BLUE:
				return Color.BLUE;
			case CYAN:
				return Color.CYAN;
			case GREEN:
				return Color.GREEN;
			case GREY:
				return Color.GRAY;
			case MAGENTA:
				return Color.MAGENTA;
			case ORANGE:
				return Color.ORANGE;
			case PINK:
				return Color.PINK;
			case RED:
				return Color.RED;
			case WHITE:
				return Color.WHITE;
			case YELLOW:
				return Color.YELLOW;
		}
		return Color.BLACK;
	}
}
