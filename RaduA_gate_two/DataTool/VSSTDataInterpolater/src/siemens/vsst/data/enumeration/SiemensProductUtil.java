/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.enumeration;

import siemens.vsst.data.models.AbstractSiemensProduct;
import siemens.vsst.data.models.SiemensActuator;
import siemens.vsst.data.models.SiemensAssembly;
import siemens.vsst.data.models.SiemensAccessory;
import siemens.vsst.data.models.SiemensMiscProduct;
import siemens.vsst.data.models.SiemensPneumatic;
import siemens.vsst.data.models.SiemensValve;

/**
 * The thing with Type and ExtendedType is this:
 * - 
 * @author radua
 */
public class SiemensProductUtil 
{
    /**
     * Get Product Type.
     * Takes in an AbstractSiemensProduct and returns a product type flag.
     *
     * @return Type Flag
	 *		Valve			- 1
	 *		Valve Actuator	- 2
	 *		Assembly		- 3
	 *		Misc			- 4
	 * ---------------------------
	 *		Damper Actuator	- 16
	 *		Accessory		- 32
	 *		Pneumatic		- 64
     */
    public static int getProductType(AbstractSiemensProduct product)
    {
        if (product instanceof SiemensValve)
			// Valve
            return 1;

        if (product instanceof SiemensActuator) {
			SiemensActuator actuator = (SiemensActuator) product;
			if (actuator.isValveActuator())
				// Valve Actuator
				return 2;
			if (actuator.isDamperActuator())
				// Damper Actuator
				return 16;
		}

        if (product instanceof SiemensAssembly)
			// Assembly
            return 3;

        if (product instanceof SiemensMiscProduct)
			// Misc
            return 4;
		
		if (product instanceof SiemensAccessory)
			// Accesory
			return 32;
		
		if (product instanceof SiemensPneumatic)
			// Pneumatic
			return 64;
		
        return 0;
    }
	
	
    /**
     * Get extended Product Type. 
	 * Takes in an AbstractSiemensProduct and returns a product type flag.
     *
     * @return Type Flag - these ones are powers of 2 so they can be combined in any way possible
	 *		Valve				- 1
	 *		Valve Actuator		- 2
	 *		Misc				- 4
	 *		Assembly			- 8
	 *		Damper (Actuator)	- 16
	 *		Actuator (Both)		- 18
	 *		Accessory			- 32
	 *		Pneumatic			- 64
     */
    public static int getExtendedProductType(AbstractSiemensProduct product)
    {
        if (product instanceof SiemensValve)
			// Valve
            return 1;

        if (product instanceof SiemensActuator) {
			SiemensActuator actuator = (SiemensActuator) product;
			if (actuator.isBothActuator())
				// both actuator
				return 18;
			
			if (actuator.isValveActuator())
				// Valve Actuator
				return 2;
			
			if (actuator.isDamperActuator())
				// Damper Actuator
				return 16;
		}

        if (product instanceof SiemensMiscProduct)
			// Misc
            return 4;
		
        if (product instanceof SiemensAssembly)
			// Assembly
            return 8;
		
		if (product instanceof SiemensAccessory)
			// Accesory
			return 32;
		
		if (product instanceof SiemensPneumatic)
			// Pneumatic
			return 64;
		
        return 0;
    }
}
