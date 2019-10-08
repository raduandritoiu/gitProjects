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
public enum PneumaticStroke 
{
	Unknown,				// - 0			- "Unknown"
	MM_60,					// - 60			- "2-3/8 in (60 mm)"
	MM_70,					// - 70			- "2-3/4 in (70 mm)"
	MM_76,					// - 76			- "3 in (76 mm)"
	MM_102,					// - 102		- "4 in (102 mm)"
	MM_178;					// - 178		- "7 in (178 mm)"

	
	public static PneumaticStroke fromInt(int type) {
		switch (type) {
			case 60:
				return MM_60;
				
			case 70:
				return MM_70;
				
			case 76:
				return MM_76;
				
			case 102:
				return MM_102;
				
			case 178:
				return MM_178;
		}
		
		return Unknown;
	}
	
	
	public static PneumaticStroke fromString(String type) {
		if (type.equalsIgnoreCase("2-3/8 in\n(60 mm)")) {
			return MM_60;
		}
		else if (type.equalsIgnoreCase("2-3/4 in\n(70 mm)")) {
			return MM_70;
		}
		else if (type.equalsIgnoreCase("3 in\n(76 mm)")) {
			return MM_76;
		}
		else if (type.equalsIgnoreCase("4 in\n(102 mm)")) {
			return MM_102;
		}
		else if (type.equalsIgnoreCase("7 in\n(178 mm)")) {
			return MM_178;
		}
		
		return Unknown;		
	}
	
	
	public int toFlag() {
		switch (this) {
			case MM_60:
				return 60;
				
			case MM_70:
				return 70;
				
			case MM_76:
				return 76;
				
			case MM_102:
				return 102;
				
			case MM_178:
				return 178;
		}
		
		return 0;
	}
	
	
	@Override
	public String toString() {
		switch (this) {
			case MM_60:
				return "2-3/8 in (60 mm)";
				
			case MM_70:
				return "2-3/4 in (70 mm)";
				
			case MM_76:
				return "3 in (76 mm)";
				
			case MM_102:
				return "4 in (102 mm)";
				
			case MM_178:
				return "7 in (178 mm)";
		}
		
		return "Unknown";		
	}
}
