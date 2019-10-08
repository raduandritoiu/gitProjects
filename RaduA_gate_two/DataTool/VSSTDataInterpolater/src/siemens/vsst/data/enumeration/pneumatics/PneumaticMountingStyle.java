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
public enum PneumaticMountingStyle 
{
	Unknown,				// - 0			- "Unknown"
	FRONT,					// - 1			- "Front"
	FIXED,					// - 2			- "Fixed"
	PIVOT,					// - 3			- "Pivot"
	TANDEM,					// - 4			- "Tandem"
	UNIVERSAL;				// - 5			- "Universal - Extended Shaft"// - 1			- "Universal - Extended Shaft"

	
	public static PneumaticMountingStyle fromInt(int type) {
		switch (type) {
			case 1:
				return FRONT;
			
			case 2:
				return FIXED;
			
			case 3:
				return PIVOT;
			
			case 4:
				return TANDEM;
			
			case 5:
				return UNIVERSAL;
		}
		
		return Unknown;
	}
	
	
	public static PneumaticMountingStyle fromString(String type) {
		if (type.equalsIgnoreCase("Front")) {
			return FRONT;
		}
		if (type.equalsIgnoreCase("Fixed")) {
			return FIXED;
		}
		if (type.equalsIgnoreCase("Pivot")) {
			return PIVOT;
		}
		if (type.equalsIgnoreCase("Tandem")) {
			return TANDEM;
		}
		if (type.equalsIgnoreCase("Universal - Extended Shaft")) {
			return UNIVERSAL;
		}
		
		return Unknown;		
	}
	
	
	public int toFlag() {
		switch (this) {
			case FRONT:
				return 1;
				
			case FIXED:
				return 2;
				
			case PIVOT:
				return 3;
				
			case TANDEM:
				return 4;
				
			case UNIVERSAL:
				return 5;
		}
		
		return 0;
	}
	
	
	@Override
	public String toString() {
		switch (this) {
			case FRONT:
				return "Front";
				
			case FIXED:
				return "Fixed";
				
			case PIVOT:
				return "Pivot";
				
			case TANDEM:
				return "Tandem";
				
			case UNIVERSAL:
				return "Universal - Extended Shaft";
		}
		
		return "Unknown";		
	}
}