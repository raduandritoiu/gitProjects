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
public enum DamperAuxilarySwitch {
	Unknown,	// - 0;		- "Unknown"
	With_Aux,	// - 1;		- "Yes"			- "Ano" <-(they are coded like this in the files received from siemens)
	No_Aux;		// - 2;		- "No"			- "Ne" <-(they are coded like this in the files received from siemens)

	
	public static DamperAuxilarySwitch fromInt(int type) {
		switch (type) {
			case 1:
				return With_Aux;
			
			case 2:
				return No_Aux;
		}
		return Unknown;
	}
	
	
	public static DamperAuxilarySwitch fromString(String type) {
		if (type.equalsIgnoreCase("Yes")) {
			return With_Aux;
		}
		else if (type.equalsIgnoreCase("No")) {
			return No_Aux;
		}
		
		return Unknown;
	}
	
	
	public int toFlag() {
		switch (this) {
			case With_Aux:
				return 1;
			
			case No_Aux:
				return 2;
		}
		
		return 0;
	}
	
	
	@Override
	public String toString() {
		switch(this) {
			case With_Aux:
				return "Yes";
				
			case No_Aux:
				return "No";
		}
		
		return "Unknown";
	}
}
