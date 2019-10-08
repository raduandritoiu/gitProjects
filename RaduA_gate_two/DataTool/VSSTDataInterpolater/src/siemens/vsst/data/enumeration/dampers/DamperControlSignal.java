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
public enum DamperControlSignal {
	Unknown,		// - 0;			- "Unknown"
	PT_2,			// - 1;			- "2-position"
	Floating,		// - 2;			- "Floating control"
	Floating_PT_2,	// - 3;			- "Floating control/2-position"
	VDC_0_10,		// - 4;			- "0...10 Vdc"
	VDC_2_10,		// - 8;			- "2...10 Vdc"
	VDC_0_2_10, 	// - 12; 		- "0...10Vdc / 2...10Vdc"
	Universal;		// - 16;		- "Universal"//
	
	
	public static DamperControlSignal fromInt(int type) {
		switch (type) {
			case 1:
				return PT_2;
				
			case 2:
				return Floating;
				
			case 3:
				return Floating_PT_2;
				
			case 4:
				return VDC_0_10;
				
			case 8:
				return VDC_2_10;
				
			case 12:
				return VDC_0_2_10;
				
			case 16:
				return Universal;
		}
		
		return Unknown;
	}
	
	
	public static DamperControlSignal fromString(String type) {
        if (type.equalsIgnoreCase("2-position")) {
			return PT_2;
		}
		else if (type.equalsIgnoreCase("Floating control")) {
			return Floating;
		}
        else if (type.equalsIgnoreCase("Floating control/2-position")) {
            return Floating_PT_2;
        }
        else if (type.equalsIgnoreCase("2-position;Floating control")) {
            return Floating_PT_2;
        }
		else if (type.equalsIgnoreCase("0...10 Vdc")) {
			return VDC_0_10;
		}
		else if (type.equalsIgnoreCase("2...10 V")) {
			return VDC_2_10;
		}
		else if (type.equalsIgnoreCase("0...10Vdc / 2...10Vdc")) {
			return VDC_0_2_10;
		}
		else if (type.equalsIgnoreCase("Universal")) {
			return Universal;
		}
		
		return Unknown;
	}
	
	
	public int toFlag() {
		switch (this) {
			case PT_2:
				return 1;
				
			case Floating:
				return 2;
			
			case Floating_PT_2:
				return 3;
			
			case VDC_0_10:
				return 4;
			
			case VDC_2_10:
				return 8;
			
			case VDC_0_2_10:
				return 12;
			
			case Universal:
				return 16;
		}
		
		return 0;
	}
	
	
	@Override
	public String toString() {
		switch (this) {
			case PT_2:
				return "2-position";
				
			case Floating:
				return "Floating control";
			
			case Floating_PT_2:
				return "Floating control/2-position";
			
			case VDC_0_10:
				return "0...10 Vdc";
			
			case VDC_2_10:
				return "2...10 V";
			
			case VDC_0_2_10:
				return "0...10Vdc / 2...10Vdc";
			
			case Universal:
				return "Universal";
		}

		return "Unknown";
	}
}
