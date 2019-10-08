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
public enum DamperType 
{
	Unknown,						// - 0;			- "Unknown";
	Electronic_Spring_Return,		// - 101;		- "Electronic spring return";
	Electronic_Non_Spring_Return,	// - 102;		- "Electronic non-spring return";
	Electronic_Fire_And_Smoke,		// - 103;		- "Electronic Fire and Smoke";
	Electronic_Fast_Acting,			// - 104;		- "Electronic Fast-Acting";
	Pneumatic;						// - 105;		- "Pneumatic";
	
	
	public static DamperType fromInt(int type) {
		switch (type) {
			case 101:
				return Electronic_Spring_Return;
				
			case 102:
				return Electronic_Non_Spring_Return;
			
			case 103:
				return Electronic_Fire_And_Smoke;
			
			case 104:
				return Electronic_Fast_Acting;
			
			case 105:
				return Pneumatic;
		}
		
		return Unknown;
	}
	
	
	public static DamperType fromString(String type) {
		if (type.equalsIgnoreCase("Electronic spring return")) {
			return Electronic_Spring_Return;
		}
		else if (type.equalsIgnoreCase("Electronic non-spring return")) {
			return Electronic_Non_Spring_Return;
		}
		else if (type.equalsIgnoreCase("Electronic Fire and Smoke")) {
			return Electronic_Fire_And_Smoke;
		}
		else if (type.startsWith("Electronic Fast-Acting")) {
			return Electronic_Fast_Acting;
		}
		else if (type.equalsIgnoreCase("Pneumatic")) {
			return Pneumatic;
		}
		
		return Unknown;		
	}
	
	
	public int toFlag() {
		switch (this) {
			case Electronic_Spring_Return:
				return 101;
				
			case Electronic_Non_Spring_Return:
				return 102;
			
			case Electronic_Fire_And_Smoke:
				return 103;
			
			case Electronic_Fast_Acting:
				return 104;
			
			case Pneumatic:
				return 105;
		}
		
		return 0;
	}
	
	
	@Override
	public String toString() {
		switch (this) {
			case Electronic_Spring_Return:
				return "Electronic spring return";
				
			case Electronic_Non_Spring_Return:
				return "Electronic non-spring return";
			
			case Electronic_Fire_And_Smoke:
				return "Electronic Fire and Smoke";
			
			case Electronic_Fast_Acting:
				return "Electronic Fast-Acting";
			
			case Pneumatic:
				return "Pneumatic";
		}
		
		return "Unknown";		
	}
}
