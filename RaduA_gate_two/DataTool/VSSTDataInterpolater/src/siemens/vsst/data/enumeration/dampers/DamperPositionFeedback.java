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
public enum DamperPositionFeedback {
	Unknown,	// - 0;		- "Unknown"
	With_Feed,	// - 1;		- "Yes"
	No_Feed;	// - 2;		- "No"

	
	public static DamperPositionFeedback fromInt(int type) {
		switch (type) {
			case 1:
				return With_Feed;
			
			case 2:
				return No_Feed;
		}
		return Unknown;
	}
	
	
	public static DamperPositionFeedback fromString(String type) {
		if (type.equalsIgnoreCase("Yes")) {
			return With_Feed;
		}
		else if (type.equalsIgnoreCase("No")) {
			return No_Feed;
		}
		
		return Unknown;
	}
	
	
	public int toFlag() {
		switch (this) {
			case With_Feed:
				return 1;
			
			case No_Feed:
				return 2;
		}
		
		return 0;
	}
	
	
	@Override
	public String toString() {
		switch(this) {
			case With_Feed:
				return "Yes";
				
			case No_Feed:
				return "No";
		}
		
		return "Unknown";
	}
}
