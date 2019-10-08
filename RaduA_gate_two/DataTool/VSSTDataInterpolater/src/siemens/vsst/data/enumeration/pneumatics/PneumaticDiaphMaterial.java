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
public enum PneumaticDiaphMaterial 
{
	Unknown,				// - 0			- "Unknown"
	RUBBER,					// - 1			- "Ozone resistant rubber"
	EPDM,					// - 2			- "EPDM ozone resistant"
	SILICON,				// - 3			- "Silicon rubber ozone res"
	WTF;					// - 4			- "------"

	
	public static PneumaticDiaphMaterial fromInt(int type) {
		switch (type) {
			case 1:
				return RUBBER;
				
			case 2:
				return EPDM;
				
			case 3:
				return SILICON;
				
			case 4:
				return WTF;
		}
		
		return Unknown;
	}
	
	
	public static PneumaticDiaphMaterial fromString(String type) {
		if (type.equalsIgnoreCase("Ozone resistant rubber")) {
			return RUBBER;
		}
		if (type.equalsIgnoreCase("EPDM ozone resistant")) {
			return EPDM;
		}
		if (type.equalsIgnoreCase("Silicon rubber ozone res")) {
			return SILICON;
		}
		if (type.equalsIgnoreCase("------")) {
			return WTF;
		}
		
		return Unknown;		
	}
	
	
	public int toFlag() {
		switch (this) {
			case RUBBER:
				return 1;
			
			case EPDM:
				return 2;
			
			case SILICON:
				return 3;

			case WTF:
				return 4;
		}
		
		return 0;
	}
	
	
	@Override
	public String toString() {
		switch (this) {
			case RUBBER:
				return "Ozone resistant rubber";
			
			case EPDM:
				return "EPDM ozone resistant";
			
			case SILICON:
				return "Silicon rubber ozone res";
				
			case WTF:
				return "Wtf ???";
		}
		
		return "Unknown";		
	}
}
