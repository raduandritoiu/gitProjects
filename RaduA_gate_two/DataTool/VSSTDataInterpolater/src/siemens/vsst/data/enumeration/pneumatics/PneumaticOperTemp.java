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
	TEMP_1,					// - 1			- "-20째F to +160째F (-29째C to 71째C)"
	TEMP_2,					// - 2			- "-20 to +200째F (-29째C to 93째C)"
	TEMP_3;					// - 3			- "50째F to 140째F (10째C to 60째C)"


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
    if (type.equalsIgnoreCase("-20째F to +160째F \n(-29째C to 71째C)")) {
      return TEMP_1;
    }
    if (type.equalsIgnoreCase("-20 to +200째F\n(-29째C to 93째C)")) {
      return TEMP_2;
    }
    if (type.equalsIgnoreCase("50째F to 140째F (10째C to 60째C)")) {
      return TEMP_3;
    }
    
    if (type.equalsIgnoreCase("-20캟 to +160캟 \n(-29캜 to 71캜)")) {
      return TEMP_1;
    }
    if (type.equalsIgnoreCase("-20 to +200캟\n(-29캜 to 93캜)")) {
      return TEMP_2;
    }
    if (type.equalsIgnoreCase("50캟 to 140캟 (10캜 to 60캜)")) {
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
				return "-20째F to +160째F (-29째C to 71째C)";
			
			case TEMP_2:
				return "-20 to +200째F (-29째C to 93째C)";
			
			case TEMP_3:
				return "50째F to 140째F (10째C to 60째C)";
		}
		
		return "Unknown";		
	}
}
