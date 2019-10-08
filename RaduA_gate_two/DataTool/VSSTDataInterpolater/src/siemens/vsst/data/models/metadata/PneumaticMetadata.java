/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.models.metadata;
	
import java.util.ArrayList;
import siemens.vsst.data.enumeration.pneumatics.PneumaticIsActuator;
import siemens.vsst.data.enumeration.pneumatics.PneumaticBallJoint;
import siemens.vsst.data.enumeration.pneumatics.PneumaticBracket;
import siemens.vsst.data.enumeration.pneumatics.PneumaticClevis;
import siemens.vsst.data.enumeration.pneumatics.PneumaticDiaphArea;
import siemens.vsst.data.enumeration.pneumatics.PneumaticDiaphMaterial;
import siemens.vsst.data.enumeration.pneumatics.PneumaticFwdTravelStops;
import siemens.vsst.data.enumeration.pneumatics.PneumaticHousing;
import siemens.vsst.data.enumeration.pneumatics.PneumaticMountingStyle;
import siemens.vsst.data.enumeration.pneumatics.PneumaticOperTemp;
import siemens.vsst.data.enumeration.pneumatics.PneumaticPivot;
import siemens.vsst.data.enumeration.pneumatics.PneumaticPosRelay;
import siemens.vsst.data.enumeration.pneumatics.PneumaticSpringRange;
import siemens.vsst.data.enumeration.pneumatics.PneumaticStem;
import siemens.vsst.data.enumeration.pneumatics.PneumaticStroke;
import siemens.vsst.data.enumeration.pneumatics.PneumaticType;
import siemens.vsst.data.enumeration.pneumatics.PneumaticULCerts;
import siemens.vsst.data.interfaces.ISiemensMetadata;

/**
 *
 * @author User
 */
public class PneumaticMetadata implements ISiemensMetadata
{
	private String partNumber;
	
	private PneumaticType type;
	private String baseReplace;
	
	private PneumaticStroke stroke;
	private PneumaticHousing housing;
	private PneumaticStem stem;
	private PneumaticDiaphArea diaphArea;
	private PneumaticDiaphMaterial diaphMaterial;
	private PneumaticOperTemp operatingTemp;
	
	// these could be booleans, but made them ints (with backed enums) - just for fun and for extension in the future
	private PneumaticIsActuator isActuator;
	private PneumaticClevis clevis;
	private PneumaticBracket bracket;	
	private PneumaticBallJoint ballJoint;
	private PneumaticPivot pivot;
	private PneumaticPosRelay posRelay;
	private PneumaticFwdTravelStops fwdTravelStops;
	
	private PneumaticMountingStyle mountingStyle;
	private PneumaticULCerts ULCert;
	private PneumaticSpringRange springRange;

	// these could  be ints/floats (with backed enums), but made them just floats - for extension purposes
	private float maxThrust_15; // this is for: Maximum Thrust lb (N) - "Full Stroke Forward 15 psi (103 kPa)"	
	private float maxThrust_18; // this is for: Maximum Thrust lb (N) - "Full Stroke Forward 18 psi (124 kPa)"	
	private float maxThrust_25; // this is for: Maximum Thrust lb (N) - "Full Stroke Forward 25 psi (172kPa)"	
	private float maxThrust_no; // this is for: Maximum Thrust lb (N) - "Spring Return no stroke 0 psi (0 kPa)"

	// these could  be ints/floats (with backed enums), but made them just floats - forsome extension purposes
	private float torqueRating_go; // this is for: Torque Rating lb-in (Nm) with maximum hysteresis of 2.5 psi (17.2 kPa) @ 90° rotation - "Gradual Operation"	
	private float torqueRating_15; // this is for: Torque Rating lb-in (Nm) with maximum hysteresis of 2.5 psi (17.2 kPa) @ 90° rotation - "2-Position Operation or with Positioner 15 psi (103 kPa)"	
	private float torqueRating_18; // this is for: Torque Rating lb-in (Nm) with maximum hysteresis of 2.5 psi (17.2 kPa) @ 90° rotation - "2-Position Operation or with Positioner 18 psi (124 kPa)"	
	private float torqueRating_25; // this is for: Torque Rating lb-in (Nm) with maximum hysteresis of 2.5 psi (17.2 kPa) @ 90° rotation - "2-Position Operation or with Positioner 25 psi (172kPa)"

	private ArrayList<String> acceptedAccessories;
	private ArrayList<String> docs;
	
	
	public PneumaticMetadata() {
		acceptedAccessories = new ArrayList<>();
		docs = new ArrayList<>();
	}
	
	
	@Override
	public void setPartNumber(String value) {
		partNumber = value;
	}
	
	@Override
	public String getPartNumber() {
		return partNumber;
	}
	
	
	public void setType(PneumaticType value) {
		type = value;
	}
	
	public PneumaticType getType() {
		return type;
	}
	
	
	public void setBaseReplace(String value) {
		baseReplace = value;
	}
	
	public String getBaseReplace() {
		return baseReplace;
	}

	
	public void setStroke(PneumaticStroke value) {
		stroke = value;
	}
	
	public PneumaticStroke getStroke() {
		return stroke;
	}
	
	
	public void setHousing(PneumaticHousing value) {
		housing = value;
	}
	
	public PneumaticHousing getHousing() {
		return housing;
	}
	
	
	public void setStem(PneumaticStem value) {
		stem = value;
	}
	
	public PneumaticStem getStem() {
		return stem;
	}
	
	
	public void setDiaphArea(PneumaticDiaphArea value) {
		diaphArea = value;
	}
	
	public PneumaticDiaphArea getDiaphArea() {
		return diaphArea;
	}
	
	
	public void setDiaphMaterial(PneumaticDiaphMaterial value) {
		diaphMaterial = value;
	}
	
	public PneumaticDiaphMaterial getDiaphMaterial() {
		return diaphMaterial;
	}
	
	
	public void setOperatingTemp(PneumaticOperTemp value) {
		operatingTemp = value;
	}
	
	public PneumaticOperTemp getOperatingTemp() {
		return operatingTemp;
	}
	
	
	public void setIsActuator(PneumaticIsActuator value) {
		isActuator = value;
	}
	
	public PneumaticIsActuator getIsActuator() {
		return isActuator;
	}
	
	
	public void setClevis(PneumaticClevis value) {
		clevis = value;
	}
	
	public PneumaticClevis getClevis() {
		return clevis;
	}
	
	
	public void setBracket(PneumaticBracket value) {
		bracket = value;
	}
	
	public PneumaticBracket getBracket() {
		return bracket;
	}
	
	
	public void setBallJoint(PneumaticBallJoint value) {
		ballJoint = value;
	}
	
	public PneumaticBallJoint getBallJoint() {
		return ballJoint;
	}
	
	
	public void setPivot(PneumaticPivot value) {
		pivot = value;
	}
	
	public PneumaticPivot getPivot() {
		return pivot;
	}
	
	
	public void setPosRelay(PneumaticPosRelay value) {
		posRelay = value;
	}
	
	public PneumaticPosRelay getPosRelay() {
		return posRelay;
	}
	
	
	public void setFwdTravelStops(PneumaticFwdTravelStops value) {
		fwdTravelStops = value;
	}
	
	public PneumaticFwdTravelStops getFwdTravelStops() {
		return fwdTravelStops;
	}
	
	
	public void setSpringRange(PneumaticSpringRange value) {
		springRange = value;
	}
	
	public PneumaticSpringRange getSpringRange() {
		return springRange;
	}
	
	
	public void setMountingStyle(PneumaticMountingStyle value) {
		mountingStyle = value;
	}
	
	public PneumaticMountingStyle getMountingStyle() {
		return mountingStyle;
	}
	
	
	public void setULCert(PneumaticULCerts value) {
		ULCert = value;
	}
	
	public PneumaticULCerts getULCert() {
		return ULCert;
	}
	
	
	public void setMaxThrust_15(float value) {
		maxThrust_15 = value;
	}
	
	public float getMaxThrust_15() {
		return maxThrust_15;
	}
	
	
	public void setMaxThrust_18(float value) {
		maxThrust_18 = value;
	}
	
	public float getMaxThrust_18() {
		return maxThrust_18;
	}
	
	
	public void setMaxThrust_25(float value) {
		maxThrust_25 = value;
	}
	
	public float getMaxThrust_25() {
		return maxThrust_25;
	}
	
	
	public void setMaxThrust_no(float value) {
		maxThrust_no = value;
	}
	
	public float getMaxThrust_no() {
		return maxThrust_no;
	}
	
	
	public void setTorqueRating_go(float value) {
		torqueRating_go = value;
	}
	
	public float getTorqueRating_go() {
		return torqueRating_go;
	}
	
	
	public void setTorqueRating_15(float value) {
		torqueRating_15 = value;
	}
	
	public float getTorqueRating_15() {
		return torqueRating_15;
	}
	
	
	public void setTorqueRating_18(float value) {
		torqueRating_18 = value;
	}
	
	public float getTorqueRating_18() {
		return torqueRating_18;
	}
	
	
	public void setTorqueRating_25(float value) {
		torqueRating_25 = value;
	}
	
	public float getTorqueRating_25() {
		return torqueRating_25;
	}
	
	
	public void setAcceptedAccessory(ArrayList value) {
		acceptedAccessories = value;
	}
	
	public ArrayList getAcceptedAccessories() {
		return acceptedAccessories;
	}
	
	
	public void setDocs(ArrayList value) {
		docs = value;
	}
	
	public ArrayList getDocs() {
		return docs;
	}
}
