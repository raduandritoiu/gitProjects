
package siemens.vsst.data.models;

import siemens.vsst.data.enumeration.actuators.ActuatorControlSignal;
import siemens.vsst.data.enumeration.valves.ValveNormalState;

/**
 * Siemens Assembly Object Model
 * @author michael
 */
public class SiemensAssembly extends AbstractSiemensProduct
{
    private SiemensActuator actuator;               // Actuator
    private SiemensValve valve;                     // Valve

    private ValveNormalState normalPosition;        // Normal Position
    private ActuatorControlSignal controlSignal;    // Control Signal
    private int threeWayFunction;                   // Three-Way Function
    private float closeOff;                         // Close Off
	
	
    public SiemensAssembly() {
        controlSignal = ActuatorControlSignal.Unknown;
        valve = new SiemensValve();
        valve.parent = this;
        actuator = new SiemensActuator(true, false);
        actuator.parent = this;
    }
	
	
    @Override
    public void setProcessed(boolean processed) {
        super.setProcessed(processed);
        if (valve != null)
            valve.setProcessed(processed);
        if (actuator != null)
            actuator.setProcessed(processed);
    }
	
	
    public SiemensActuator getActuator() {
        return actuator;
    }

    public void setActuator(SiemensActuator actuator) {
        this.actuator = actuator;
        if (actuator != null)
            actuator.parent = this;
    }
	
	
    public SiemensValve getValve() {
        return valve;
    }
	
    public void setValve(SiemensValve valve) {
        this.valve = valve;
        if (valve != null)
            valve.parent = this;
    }
	
	
    public ValveNormalState getNormalPosition() {
        return normalPosition;
    }
	
    public void setNormalPosition(ValveNormalState normalPosition) {
        this.normalPosition = normalPosition;
    }
	
	
    public int getThreeWayFunction() {
        return threeWayFunction;
    }

    public void setThreeWayFunction(int threeWayFunction) {
        this.threeWayFunction = threeWayFunction;
    }
	
	
    public ActuatorControlSignal getControlSignal() {
        return controlSignal;
    }
	
    public void setControlSignal(ActuatorControlSignal controlSignal) {
        this.controlSignal = controlSignal;
    }
	
	
    public float getCloseOff() {
        return closeOff;
    }
	
    public void setCloseOff(float closeOff) {
        this.closeOff = closeOff;
    }
}
