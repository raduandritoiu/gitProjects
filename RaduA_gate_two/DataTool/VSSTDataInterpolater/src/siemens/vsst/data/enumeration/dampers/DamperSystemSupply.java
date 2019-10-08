/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.enumeration.dampers;

/**
 *
 * @author radua
 */
public enum DamperSystemSupply {
	Unknown,	// - 0;		- "Unknown"
	VAC_24,		// - 1;		- "24 VAC"
	DC_24,		// - 2;		- "24 DC"		//- this is just reserved for now, there is no DC_24
	VAC_DC_24,	// - 3;		- "24 VAC/DC"
	VAC_120,	// - 4;		- "120 VAC"
	VAC_230;	// - 8;		- "230 VAC"
	
	
	public static DamperSystemSupply fromInt(int type) {
		switch (type) {
			case 1:
				return VAC_24;
				
			case 3:
				return VAC_DC_24;
				
			case 4:
				return VAC_120;
				
			case 8:
				return VAC_230;
		}
		
		return Unknown;
	}
	
	
	public static DamperSystemSupply fromString(String type) {
		if (type.equalsIgnoreCase("24 VAC")) {
			return VAC_24;
		}
		else if (type.equalsIgnoreCase("24 VAC/DC")) {
			return VAC_DC_24;
		}
		else if (type.equalsIgnoreCase("120 VAC")) {
			return 	VAC_120;
		}
		else if (type.equalsIgnoreCase("230 VAC")) {
			return 	VAC_230;
		}
		
		return Unknown;
	}
	
	
	public int toFlag() {
		switch (this) {
			case VAC_24:
				return 1;
				
			case VAC_DC_24:
				return 3;
			
			case VAC_120:
				return 4;
			
			case VAC_230:
				return 8;
		}
		
		return 0;
	}
	
	
	@Override
	public String toString() {
		switch (this) {
			case VAC_24:
				return "24 VAC";
			
			case VAC_DC_24:
				return "24 VAC/DC";
			
			case VAC_120:
				return "120 VAC";
			
			case VAC_230:
				return "230 VAC";
		}
		
		return "Unknown";
	}
}
