/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.enumeration.pneumatics;

/**
 *
 * @author User
 */
public enum PneumaticDiaphArea 
{
	Unknown,				// - 0			- "Unknown"
	CM_52,					// - 52			- "8 sq in (52 sq cm)"
	CM_71,					// - 71			- "11 sq in (71 sq cm)"
	CM_115,					// - 115		- "17.9 sq in (115 sq cm)"
	CM_126,					// - 126		- "19.6 inches2 (126 cm2)"
	CM_230;					// - 230		- "35.8 sq in (230 sq cm)"// - 230		- "35.8 sq in (230 sq cm)"

	
	public static PneumaticDiaphArea fromInt(int type) {
		switch (type) {
			case 52:
				return CM_52;
				
			case 71:
				return CM_71;
				
			case 115:
				return CM_115;
				
			case 126:
				return CM_126;
				
			case 230:
				return CM_230;
		}
		
		return Unknown;
	}
	
	
	public static PneumaticDiaphArea fromString(String type) {
		if (type.equalsIgnoreCase("8 sq in \n(52 sq cm)")) {
			return CM_52;
		}
		else if (type.equalsIgnoreCase("11 sq in \n(71 sq cm)")) {
			return CM_71;
		}
		else if (type.equalsIgnoreCase("17.9 sq in \n(115 sq cm)")) {
			return CM_115;
		}
		else if (type.equalsIgnoreCase("19.6 inches2 (126 cm2)")) {
			return CM_126;
		}
		else if (type.equalsIgnoreCase("35.8 sq in (230 sq cm)")) {
			return CM_230;
		}
		
		return Unknown;		
	}
	
	
	public int toFlag() {
		switch (this) {
			case CM_52:
				return 52;
				
			case CM_71:
				return 71;
				
			case CM_115:
				return 115;
				
			case CM_126:
				return 126;
				
			case CM_230:
				return 230;
		}
		
		return 0;
	}
	
	
	@Override
	public String toString() {
		switch (this) {
			case CM_52:
				return "8 sq in (52 sq cm)";
				
			case CM_71:
				return "11 sq in (71 sq cm)";
				
			case CM_115:
				return "17.9 sq in (115 sq cm)";
				
			case CM_126:
				return "19.6 inches2 (126 cm2)";
				
			case CM_230:
				return "35.8 sq in (230 sq cm)";
		}
		
		return "Unknown";		
	}
}
