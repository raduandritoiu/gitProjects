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
public enum PneumaticStem 
{
	Unknown,				// - 0			- "Unknown"
	PLATED,					// - 1			- "Plated Steel"
	STAINLESS;				// - 2			- "Stainless Steel"

	
	public static PneumaticStem fromInt(int type) {
		switch (type) {
			case 1:
				return PLATED;
				
			case 2:
				return STAINLESS;
		}
		
		return Unknown;
	}
	
	
	public static PneumaticStem fromString(String type) {
		if (type.equalsIgnoreCase("Plated Steel")) {
			return PLATED;
		}
		else if (type.equalsIgnoreCase("Stainless Steel")) {
			return STAINLESS;
		}
		
		return Unknown;		
	}
	
	
	public int toFlag() {
		switch (this) {
			case PLATED:
				return 1;
				
			case STAINLESS:
				return 2;
		}
		
		return 0;
	}
	
	
	@Override
	public String toString() {
		switch (this) {
			case PLATED:
				return "Plated Steel";
				
			case STAINLESS:
				return "Stainless Steel";
		}
		
		return "Unknown";		
	}
}
