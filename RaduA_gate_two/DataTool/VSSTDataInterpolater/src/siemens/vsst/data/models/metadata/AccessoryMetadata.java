/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.models.metadata;

import siemens.vsst.data.interfaces.ISiemensMetadata;

/**
 *
 * @author radua
 */
public class AccessoryMetadata implements ISiemensMetadata 
{
	private String partNumber;
	private int extraInfo;
	private int fromDamper;
	private int fromPneumatic;
	
	
	public AccessoryMetadata() {
		extraInfo = 12;
		fromDamper = 0;
		fromPneumatic = 0;
	}
	
	
	@Override
	public void setPartNumber(String value) {
		partNumber = value;
	}
	
	@Override
	public String getPartNumber() {
		return partNumber;
	}
	
	
	public void setExtraInfo(int value) {
		extraInfo = value;
	}
	
	public int getExtraInfo() {
		return extraInfo;
	}
	
	
	public void setFromDamper(int value) {
		fromDamper = value;
	}
	
	public int getFromDamper() {
		return fromDamper;
	}
	
	
	public void setFromPneumatic(int value) {
		fromPneumatic = value;
	}
	
	public int getFromPneumatic() {
		return fromPneumatic;
	}
}