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
public enum PneumaticHousing 
{
	Unknown,				// - 0			- "Unknown"
	ALUMINUN,				// - 1			- "Aluminum"
	STEEL;					// - 2			- "Steel with epoxy electrocoat"

	
	public static PneumaticHousing fromInt(int type) {
		switch (type) {
			case 1:
				return ALUMINUN;
				
			case 2:
				return STEEL;
		}
		
		return Unknown;
	}
	
	
	public static PneumaticHousing fromString(String type) {
		if (type.equalsIgnoreCase("Aluminum")) {
			return ALUMINUN;
		}
		else if (type.equalsIgnoreCase("Steel with epoxy electrocoat")) {
			return STEEL;
		}
		
		return Unknown;		
	}
	
	
	public int toFlag() {
		switch (this) {
			case ALUMINUN:
				return 1;
				
			case STEEL:
				return 2;
		}
		
		return 0;
	}
	
	
	@Override
	public String toString() {
		switch (this) {
			case ALUMINUN:
				return "Aluminum";
				
			case STEEL:
				return "Steel with epoxy electrocoat";
		}
		
		return "Unknown";		
	}
}
