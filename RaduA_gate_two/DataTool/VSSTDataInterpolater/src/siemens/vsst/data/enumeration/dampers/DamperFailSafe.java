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
public enum DamperFailSafe {
	Unknown,			// - 0;			- "Unknown"
	Fail_Safe,			// - 1;			- "Fail Safe"
	Fail_In_Place;		// - 2;			- "Fail-In-Place"
	
	
	public static DamperFailSafe fromInt(int type) {
		switch (type) {
			case 1:
				return Fail_Safe;
				
			case 2:
				return Fail_In_Place;
		}
		
		return Unknown;
	}
	
	
	public static DamperFailSafe fromString(String type) {
        if (type.indexOf("Fail Safe") != -1) {
			return Fail_Safe;
		}
		else if (type.indexOf("Fail-In-Place") != -1) {
			return Fail_In_Place;
		}
		
		return Unknown;
	}
	
	
	public int toFlag() {
		switch (this) {
			case Fail_Safe:
				return 1;
				
			case Fail_In_Place:
				return 2;
		}
		
		return 0;
	}
	
	
	@Override
	public String toString() {
		switch (this) {
			case Fail_Safe:
				return "Fail Safe";
				
			case Fail_In_Place:
				return "Fail-In-Place";			
		}

		return "Unknown";
	}
}
