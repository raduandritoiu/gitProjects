package siemens.vsst.data.models.metadata;

import siemens.vsst.data.enumeration.actuators.ActuatorControlSignal;
import siemens.vsst.data.enumeration.actuators.ActuatorEndSwitch;
import siemens.vsst.data.enumeration.actuators.ActuatorMotorType;
import siemens.vsst.data.enumeration.actuators.ActuatorPositioner;
import siemens.vsst.data.enumeration.actuators.ActuatorSpring;
import siemens.vsst.data.interfaces.ISiemensMetadata;
import siemens.vsst.data.parsers.utils.VSSTLogger;

/**
 * Siemens Actuator Metadata - this is actually metadata for Valve Actuators
 *
 * Contains all valve actuator specific properties
 * @author michael
 */
public class ActuatorMetadata implements ISiemensMetadata
{
    private String partNumber;                      // Part Number
    private ActuatorPositioner positioner;          // Positioner
    private ActuatorMotorType motorType;            // Motor Type
    private ActuatorControlSignal controlSignal;    // Control Signal
    //private float closeOffPressure; close off is an assembly parameter
    private ActuatorSpring springReturn;            // Safety Function
    private float supplyVoltage;                    // Supply Voltage
    private char butterflyConfiguration;            // Butterfly Configuration

    // ButterFly Only
    private boolean heater;                         // Butterfly has heater flag
    private boolean manualOverride;                 // Butterfly manual override flag
    private ActuatorEndSwitch endSwitch;            // Butterfly actuator end switch
	
    private static int debugCnt = 0;
    public int debugId;
	
    public ActuatorMetadata() {
    		debugCnt++;
    		debugId = debugCnt;
		
        endSwitch               = ActuatorEndSwitch.None;
        positioner              = ActuatorPositioner.None;
        supplyVoltage           = 24; // All Actuators default to 24 Volts
        butterflyConfiguration  = 0;
    }

    // Close off is an assembly parameter
//    public float getCloseOffPressure()
//    {
//        return closeOffPressure;
//    }
//
//    public void setCloseOffPressure(float closeOffPressure)
//    {
//        this.closeOffPressure = closeOffPressure;
//    }

    public ActuatorControlSignal getControlSignal() {
        return controlSignal;
    }
	
    public void setControlSignal(ActuatorControlSignal controlSignal) {
        this.controlSignal = controlSignal;
		
//		//TODO_TEST - SQV91P40U
//        if (partNumber.equalsIgnoreCase("SQV91P40U")) {
//            VSSTLogger.TestComponent("=================(" + partNumber + ")=  " + " set controlSignal to " + 
//					this.controlSignal.toString() + "  ----- metaId: " + debugId);
//        }
		//TODO_TEST - SAV81.00U
//        if (partNumber.equalsIgnoreCase("SAV81.00U")) {
//            VSSTLogger.TestComponent("=================(" + partNumber + ")=  " + " set controlSignal to " + 
//					this.controlSignal.toString() + "  ----- metaId: " + debugId);
//        }
    }
	
	
    public ActuatorMotorType getMotorType() {
        return motorType;
    }
	
    public void setMotorType(ActuatorMotorType motorType) {
        this.motorType = motorType;
        if (motorType.equals(ActuatorMotorType.Pneumatic))
            supplyVoltage = 0;
    }
	
	
    public String getPartNumber() {
        return partNumber;
    }
	
    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }
	
	
    public ActuatorPositioner getPositioner() {
        return positioner;
    }
	
    public void setPositioner(ActuatorPositioner positioner) {
        this.positioner = positioner;
    }
	
	
    public ActuatorSpring getSpringReturn() {
        return springReturn;
    }
	
    public void setSpringReturn(ActuatorSpring springReturn) {
        this.springReturn = springReturn;
    }
	
	
    public float getSupplyVoltage() {
        return supplyVoltage;
    }
	
    public void setSupplyVoltage(float supplyVoltage) {
        this.supplyVoltage = supplyVoltage;
    }
	
	
    public boolean hasHeater() {
        return heater;
    }
	
    public void setHeater(boolean heater) {
        this.heater = heater;
    }
	
	
    public boolean hasManualOverride() {
        return manualOverride;
    }
	
    public void setManualOverride(boolean manualOverride) {
        this.manualOverride = manualOverride;
    }
	
	
    public ActuatorEndSwitch getEndSwitch() {
        return endSwitch;
    }
	
    public void setEndSwitch(ActuatorEndSwitch endSwitch) {
        this.endSwitch = endSwitch;
    }
	
	
    public char getButterflyConfiguration() {
        return butterflyConfiguration;
    }
	
    public void setButterflyConfiguration(char butterflyConfiguration) {
        this.butterflyConfiguration = butterflyConfiguration;
    }
}
