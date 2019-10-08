package siemens.vsst.data.models;

import siemens.vsst.data.models.metadata.ActuatorMetadata;
import siemens.vsst.data.models.metadata.DamperMetadata;

/**
 * Siemens Actuator Object Model
 * This class is used as Actuator for Valves and as Dampers (which are actually Damper Actuators)
 * 
 * @author michael
 */
public class SiemensActuator extends AbstractSiemensProduct
{
    private ActuatorMetadata actuatorMetadata;
	private DamperMetadata damperMetadata;
	
	private static int debugCnt = 0;
	
    public SiemensActuator(boolean createActuator, boolean createDamper) {
		debugCnt++;
		debugId = debugCnt;
		if (createActuator)
			actuatorMetadata = new ActuatorMetadata();
		if (createDamper)
			damperMetadata = new DamperMetadata();
	}
	
	
    @Override
    public void setPartNumber(String value) {
        super.setPartNumber(value);
        if (actuatorMetadata != null) {
			actuatorMetadata.setPartNumber(value);
		}
        if (damperMetadata != null) {
			damperMetadata.setPartNumber(value);
		}
    }
	
	
    public ActuatorMetadata getActuatorMetadata() {
        return actuatorMetadata;
    }

    public void setActuatorMetadata(ActuatorMetadata metadata) {
        actuatorMetadata = metadata;
    }
	
	
    public DamperMetadata getDamperMetadata() {
        return damperMetadata;
    }

    public void setDamperMetadata(DamperMetadata metadata) {
        damperMetadata = metadata;
    }
	
	
	public boolean isValveActuator() {
		return (actuatorMetadata != null);
	}
	
	
	public boolean isDamperActuator() {
		return (damperMetadata != null);
	}
	
	
	public boolean isBothActuator() {
		return (actuatorMetadata != null && damperMetadata != null);
	}
}