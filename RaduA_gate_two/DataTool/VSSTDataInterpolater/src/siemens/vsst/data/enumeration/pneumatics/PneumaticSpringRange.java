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
public enum PneumaticSpringRange 
{
	Unknown,				// - 0			- "Unknown"
	PSI_3_7,				// - 1			- "3-7 psi (21-48 kPa)"	
	PSI_3_8,				// - 2			- "3-8 psi (21-55 kPa)"	
	PSI_3_13,				// - 3			- "3-13 psi (21-90 kPa)"	
	PSI_5_10,				// - 4			- "5-10 psi (35-70 kPa)"	
	PSI_8_13,				// - 5			- "8-13 psi (55-90 kPa)"	
	PSI_HM;					// - 6			- "2-3 psi, 8-13 psi (14-21, 55-90 kPa) Hesitation Model"

	
	public static PneumaticSpringRange fromInt(int type) {
		switch (type) {
			case 1:
				return PSI_3_7;
			
			case 2:
				return PSI_3_8;
			
			case 3:
				return PSI_3_13;
			
			case 4:
				return PSI_5_10;
			
			case 5:
				return PSI_8_13;
			
			case 6:
				return PSI_HM;
		}
		
		return Unknown;
	}
	
	
	public static PneumaticSpringRange fromString(String type) {
		if (type.equalsIgnoreCase("3-7 psi (21-48 kPa)")) {
			return PSI_3_7;
		}
		if (type.equalsIgnoreCase("3-8 psi (21-55 kPa)")) {
			return PSI_3_8;
		}
		if (type.equalsIgnoreCase("3-13 psi (21-90 kPa)")) {
			return PSI_3_13;
		}
		if (type.equalsIgnoreCase("5-10 psi (35-70 kPa)")) {
			return PSI_5_10;
		}
		if (type.equalsIgnoreCase("8-13 psi (55-90 kPa)")) {
			return PSI_8_13;
		}
		if (type.equalsIgnoreCase("2-3 psi, 8-13 psi (14-21, 55-90 kPa) Hesitation Model")) {
			return PSI_HM;
		}
		
		return Unknown;		
	}
	
	
	public int toFlag() {
		switch (this) {
			case PSI_3_7:
				return 1;
			
			case PSI_3_8:
				return 2;
			
			case PSI_3_13:
				return 3;
			
			case PSI_5_10:
				return 4;
			
			case PSI_8_13:
				return 5;
			
			case PSI_HM:
				return 6;
		}
		
		return 0;
	}
	
	
	@Override
	public String toString() {
		switch (this) {
			case PSI_3_7:
				return "3-7 psi (21-48 kPa)";
			
			case PSI_3_8:
				return "3-8 psi (21-55 kPa)";
			
			case PSI_3_13:
				return "3-13 psi (21-90 kPa)";
			
			case PSI_5_10:
				return "5-10 psi (35-70 kPa)";
			
			case PSI_8_13:
				return "8-13 psi (55-90 kPa)";
			
			case PSI_HM:
				return "2-3 psi, 8-13 psi (14-21, 55-90 kPa) Hesitation Model";
		}
		
		return "Unknown";		
	}
}
