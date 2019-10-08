/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.models.metadata;

/**
 *
 * @author User
 */
public class AccessoryPneumMetadata {
	private String partNumber;
	private int extraInfo;
	
	public AccessoryPneumMetadata() {
		extraInfo = 12;
	}
	
	public void setPartNumber(String value) {
		partNumber = value;
	}
	
	public String getPartNumber() {
		return partNumber;
	}
	
	
	public void setExtraInfo(int value) {
		extraInfo = value;
	}
	
	public int getExtraInfo() {
		return extraInfo;
	}
}
