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
public enum PneumaticULCerts 
{
	Unknown,				// - 0			- "Unknown"
	YES,					// - 1			- "Y"
	NO;						// - 2			- "N"// - 2			- "N"// - 2			- "N"// - 2			- "N"

	
	public static PneumaticULCerts fromInt(int type) {
		switch (type) {
			case 1:
				return YES;
			
			case 2:
				return NO;
		}
		
		return Unknown;
	}
	
	
	public static PneumaticULCerts fromString(String type) {
		if (type.equalsIgnoreCase("Yes")) {
			return YES;
		}
		if (type.equalsIgnoreCase("Y")) {
			return YES;
		}
		if (type.equalsIgnoreCase("Y1")) {
			return YES;
		}
		if (type.equalsIgnoreCase("Y2")) {
			return YES;
		}
		if (type.equalsIgnoreCase("Y3")) {
			return YES;
		}
		if (type.equalsIgnoreCase("No")) {
			return NO;
		}
		if (type.equalsIgnoreCase("N")) {
			return NO;
		}
		if (type.equalsIgnoreCase("")) {
			return NO;
		}
		
		return Unknown;		
	}
	
	
	public int toFlag() {
		switch (this) {
			case YES:
				return 1;
			
			case NO:
				return 2;
		}
		
		return 0;
	}
	
	
	@Override
	public String toString() {
		switch (this) {
			case YES:
				return "Yes";
			
			case NO:
				return "No";
		}
		
		return "Unknown";		
	}
}
