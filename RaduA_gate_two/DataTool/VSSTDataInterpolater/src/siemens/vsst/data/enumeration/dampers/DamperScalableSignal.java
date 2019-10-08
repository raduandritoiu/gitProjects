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
public enum DamperScalableSignal {
	Unknown,	// - 0;		- "Unknown"
	With_Sig,	// - 1;		- "Yes"			- "YES" <-(they are coded like this in the files received from siemens)
	No_Sig;		// - 2;		- "No"			- "NO" <-(they are coded like this in the files received from siemens)

	
	public static DamperScalableSignal fromInt(int type) {
		switch (type) {
			case 1:
				return With_Sig;
			
			case 2:
				return No_Sig;
		}
		return Unknown;
	}
	
	
	public static DamperScalableSignal fromString(String type) {
		if (type.equalsIgnoreCase("YES")) {
			return With_Sig;
		}
		else if (type.equalsIgnoreCase("NO")) {
			return No_Sig;
		}
		
		return Unknown;
	}
	
	
	public int toFlag() {
		switch (this) {
			case With_Sig:
				return 1;
			
			case No_Sig:
				return 2;
		}
		
		return 0;
	}
	
	
	@Override
	public String toString() {
		switch(this) {
			case With_Sig:
				return "Yes";
				
			case No_Sig:
				return "No";
		}
		
		return "Unknown";
	}
}
