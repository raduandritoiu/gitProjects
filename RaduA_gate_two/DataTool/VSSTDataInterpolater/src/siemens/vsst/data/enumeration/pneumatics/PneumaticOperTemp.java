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
public enum PneumaticOperTemp 
{
	Unknown,				// - 0			- "Unknown"
	TEMP_1,					// - 1			- "-20°F to +160°F (-29°C to 71°C)"
	TEMP_2,					// - 2			- "-20 to +200°F (-29°C to 93°C)"
	TEMP_3;					// - 3			- "50°F to 140°F (10°C to 60°C)"


	public static PneumaticOperTemp fromInt(int type) {
		switch (type) {
			case 1:
				return TEMP_1;
			
			case 2:
				return TEMP_2;
			
			case 3:
				return TEMP_3;
		}
		
		return Unknown;
	}
	
	
	public static PneumaticOperTemp fromString(String type) {
    if (type.equalsIgnoreCase("-20°F to +160°F \n(-29°C to 71°C)")) {
      return TEMP_1;
    }
    if (type.equalsIgnoreCase("-20 to +200°F\n(-29°C to 93°C)")) {
      return TEMP_2;
    }
    if (type.equalsIgnoreCase("50°F to 140°F (10°C to 60°C)")) {
      return TEMP_3;
    }
    
    if (type.equalsIgnoreCase("-20�F to +160�F \n(-29�C to 71�C)")) {
      return TEMP_1;
    }
    if (type.equalsIgnoreCase("-20 to +200�F\n(-29�C to 93�C)")) {
      return TEMP_2;
    }
    if (type.equalsIgnoreCase("50�F to 140�F (10�C to 60�C)")) {
      return TEMP_3;
    }
    
		return Unknown;
	}
	
	
	public int toFlag() {
		switch (this) {
			case TEMP_1:
				return 1;
			
			case TEMP_2:
				return 2;
			
			case TEMP_3:
				return 3;
		}
		
		return 0;
	}
	
	
	@Override
	public String toString() {
		switch (this) {
			case TEMP_1:
				return "-20°F to +160°F (-29°C to 71°C)";
			
			case TEMP_2:
				return "-20 to +200°F (-29°C to 93°C)";
			
			case TEMP_3:
				return "50°F to 140°F (10°C to 60°C)";
		}
		
		return "Unknown";		
	}
}
