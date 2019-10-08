/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.models.metadata;

import java.util.ArrayList;
import siemens.vsst.data.enumeration.dampers.DamperAuxilarySwitch;
import siemens.vsst.data.enumeration.dampers.DamperControlSignal;
import siemens.vsst.data.enumeration.dampers.DamperPlenum;
import siemens.vsst.data.enumeration.dampers.DamperPositionFeedback;
import siemens.vsst.data.enumeration.dampers.DamperScalableSignal;
import siemens.vsst.data.enumeration.dampers.DamperSystemSupply;
import siemens.vsst.data.enumeration.dampers.DamperType;
import siemens.vsst.data.interfaces.ISiemensMetadata;

/**
 *
 * @author radua
 */
public class DamperMetadata implements ISiemensMetadata
{
	private String partNumber;
	private DamperType type;
	private float torque;
	private DamperControlSignal controlSignal;
	private ArrayList<String> acceptedAccessories;
	
	// these are not used yet
	private DamperSystemSupply systemSupply;
	private DamperPlenum plenumRating;
	private DamperPositionFeedback positionFeedback;
	private DamperAuxilarySwitch auxilarySwitches;
	private DamperScalableSignal scalability;
	
	
	public DamperMetadata() {
		acceptedAccessories = new ArrayList<String>();
	}
	
	
	@Override
	public void setPartNumber(String value) {
		partNumber = value;
	}
	
	@Override
	public String getPartNumber() {
		return partNumber;
	}
	
	
	public void setType(DamperType value) {
		type = value;
	}
	
	public DamperType getType() {
		return type;
	}
	
	
	public void setTorque(float value) {
		torque = value;
	}
	
	public float getTorque() {
		return torque;
	}
	
	
	public void setControlSignal(DamperControlSignal value) {
		controlSignal = value;
	}
	
	public DamperControlSignal getControlSignal() {
		return controlSignal;
	}
	
	
	public void setAcceptedAccessories(ArrayList<String> value) {
		acceptedAccessories = value;
	}
	
	public ArrayList<String> getAcceptedAccessories() {
		return acceptedAccessories;
	}
	
	
	// these are not used for the moment
	
	public void setSystemSupply(DamperSystemSupply value) {
		systemSupply = value;
	}
	
	public DamperSystemSupply getSystemSupply() {
		return systemSupply;
	}
	
	
	public void setPlenumRating(DamperPlenum value) {
		plenumRating = value;
	}
	
	public DamperPlenum getPlenumRating() {
		return plenumRating;
	}
	
	
	public void setPositionFeedback(DamperPositionFeedback value) {
		positionFeedback = value;
	}
	
	public DamperPositionFeedback getPositionFeedback() {
		return positionFeedback;
	}
	
	
	public void setAuxilarySwitches(DamperAuxilarySwitch value) {
		auxilarySwitches = value;
	}
	
	public DamperAuxilarySwitch getAuxilarySwitches() {
		return auxilarySwitches;
	}
	
	
	public void setScalability(DamperScalableSignal value) {
		scalability = value;
	}
	
	public DamperScalableSignal getScalability() {
		return scalability;
	}
}