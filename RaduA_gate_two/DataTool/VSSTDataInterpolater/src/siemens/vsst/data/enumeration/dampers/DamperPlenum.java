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
public enum DamperPlenum {
	Unknown,	// - 0;		- "Unknown"
	Plenum,		// - 1;		- "Plenum"
	Standard;	// - 2;		- "Standard"

	
	public static DamperPlenum fromInt(int type) {
		switch (type) {
			case 1:
				return Plenum;
			
			case 2:
				return Standard;
		}
		return Unknown;
	}
	
	
	public static DamperPlenum fromString(String type) {
		if (type.equalsIgnoreCase("Plenum")) {
			return Plenum;
		}
		else if (type.equalsIgnoreCase("Standard")) {
			return Standard;
		}
		
		return Unknown;
	}
	
	
	public int toFlag() {
		switch (this) {
			case Plenum:
				return 1;
			
			case Standard:
				return 2;
		}
		
		return 0;
	}
	
	
	@Override
	public String toString() {
		switch(this) {
			case Plenum:
				return "Plenum";
				
			case Standard:
				return "Standard";
		}
		
		return "Unknown";
	}
}
