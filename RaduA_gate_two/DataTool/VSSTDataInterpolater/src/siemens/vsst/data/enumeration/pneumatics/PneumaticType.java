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
public enum PneumaticType 
{
	Unknown,				// - 0				- "Unknown"
	NO_3,					// - 203			- "No. 3"
	NO_4,					// - 204			- "No. 4"
	NO_6,					// - 206			- "No. 6"
	HIGH_FORCE, 			// - 207			- "LC"
	TANDEM;					// - 208			- "No. 6 Tandem"
	
	
	public static PneumaticType fromInt(int type) {
		switch (type) {
			case 203:
				return NO_3;
			
			case 204:
				return NO_4;
			
			case 206:
				return NO_6;
			
			case 207:
				return HIGH_FORCE;
			
			case 208:
				return TANDEM;
		}
		
		return Unknown;
	}
	
	
	public static PneumaticType fromString(String type) {
		if (type.equalsIgnoreCase("No. 3")) {
			return NO_3;
		}
		else if (type.equalsIgnoreCase("No. 4")) {
			return NO_4;
		}
		else if (type.equalsIgnoreCase("No. 6")) {
			return NO_6;
		}
		else if (type.equalsIgnoreCase("LC")) {
			return HIGH_FORCE;
		}
		else if (type.equalsIgnoreCase("No. 6 Tandem")) {
			return TANDEM;
		}
		
		return Unknown;		
	}
	
	
	public int toFlag() {
		switch (this) {
			case NO_3:
				return 203;
				
			case NO_4:
				return 204;
			
			case NO_6:
				return 206;
			
			case HIGH_FORCE:
				return 207;
			
			case TANDEM:
				return 208;
		}
		
		return 0;
	}
	
	
	@Override
	public String toString() {
		switch (this) {
			case NO_3:
				return "No. 3";
				
			case NO_4:
				return "No. 4";
			
			case NO_6:
				return "No. 6";
			
			case HIGH_FORCE:
				return "High Force";
			
			case TANDEM:
				return "No. 6 Tandem";
		}
		
		return "Unknown";		
	}
}
