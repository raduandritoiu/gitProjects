package siemens.vsst.data.parsers.utils;

import java.util.HashMap;
import siemens.vsst.data.enumeration.actuators.ActuatorEndSwitch;
import siemens.vsst.data.models.AbstractSiemensProduct;
import siemens.vsst.data.models.SiemensActuator;
import siemens.vsst.data.models.SiemensAssembly;
import siemens.vsst.data.models.SiemensAccessory;
import siemens.vsst.data.models.SiemensMiscProduct;
import siemens.vsst.data.models.SiemensValve;

/**
 * Siemens Parser Utilties
 *
 * Contains variaus utilities for parsing product information.
 * @author michael
 */
//this is the DECODER RING
public class SiemensParserUtil
{
    private static HashMap<String, String> valvePartMap;            // Valve Part Number Map
    private static HashMap<String, String> actuatorPartMap;         // Actuator Part Number Map
    private static HashMap<String, Float> actuatorSupplyVoltageMap; // Actuator Supply Voltage Map
    
	
	/**
     * Create Valve Map
     *
     * Creates map of valve part number prefixes
     */
    private static void createValveMap()
    {
        if (valvePartMap != null)
            return;

        valvePartMap    = new HashMap<String, String>();

        valvePartMap.put("599", null);
        valvePartMap.put("656", null);
        valvePartMap.put("658", null);
        valvePartMap.put("M", null);
        valvePartMap.put("V", null);
        //valvePartMap.put("B", null); // Assembly
    }
	
	
    /**
     * Creates Actuator Map
     *
     * Creates map of actuator part number and assembly prefixes.
     */
    private static void createActuatorMap()
    {
        if (actuatorPartMap != null)
            return;

        actuatorPartMap = new HashMap<String, String>();

        // Ball Valve Actuators
        actuatorPartMap.put("171A", "GDE131.1P");
        actuatorPartMap.put("171B", "GLB131.1P");
        actuatorPartMap.put("171C", "GDE161.1P");
        actuatorPartMap.put("171D", "GLB161.1P");
        actuatorPartMap.put("171E", "GMA121.1P");
        actuatorPartMap.put("171F", "GMA131.1P");
        actuatorPartMap.put("171G", "GMA161.1P");

        actuatorPartMap.put("171H", "GQD121.1P");
        actuatorPartMap.put("171J", "GQD131.1P");
        actuatorPartMap.put("171K", "GQD151.1P");
        actuatorPartMap.put("171N", "GQD126.1P"); // 5-1-2013
        actuatorPartMap.put("171P", "GMA126.1P"); // 5-1-2013
        
        actuatorPartMap.put("172H", "GQD121.1P");
        actuatorPartMap.put("172J", "GQD131.1P");
        actuatorPartMap.put("172K", "GQD151.1P");
        actuatorPartMap.put("172N", "GQD126.1P"); // 5-1-2013
        actuatorPartMap.put("172P", "GMA126.1P"); // 5-1-2013

        actuatorPartMap.put("172E", "GMA121.1P");
        actuatorPartMap.put("172F", "GMA131.1P");
        actuatorPartMap.put("172G", "GMA161.1P");

        actuatorPartMap.put("173A", "GDE131.1P+ASK76.1U");
        actuatorPartMap.put("173B", "GLB131.1P+ASK76.1U");
        actuatorPartMap.put("173C", "GDE161.1P+ASK76.1U");
        actuatorPartMap.put("173D", "GLB161.1P+ASK76.1U");
        
        // 120V Ball Valve Actuators
        actuatorPartMap.put("171L", "GQD221.1U");
        actuatorPartMap.put("171M", "GMA221.1U");        
        actuatorPartMap.put("172L", "GQD221.1U");
        actuatorPartMap.put("172M", "GMA221.1U");

        // PICV Actuators
        actuatorPartMap.put("230", "SSD81U");
        actuatorPartMap.put("231", "SSD61U");
        actuatorPartMap.put("232", "SSD81.5U");
        actuatorPartMap.put("233", "SSD61.5U");
        actuatorPartMap.put("234", "SQD85.03U");
        actuatorPartMap.put("235", "SQD65U");
        actuatorPartMap.put("238", "SQV91P30U");
        actuatorPartMap.put("239", "SQV91P40U");

        // Zone Valve Actuators
        actuatorPartMap.put("240", "SFA11U");
        actuatorPartMap.put("241", "SFP11U");
        actuatorPartMap.put("242", "SFA71U");
        actuatorPartMap.put("243", "SFP71U");
        actuatorPartMap.put("244", "SSA81U");
        actuatorPartMap.put("245", "SSA61U");
        actuatorPartMap.put("246", "STA71");
        actuatorPartMap.put("247", "STP71");
        actuatorPartMap.put("248", "SSP61U");
        actuatorPartMap.put("346", "STA73U");
        actuatorPartMap.put("347", "STP73U");

        // Powermite MZ Actuators
        actuatorPartMap.put("254", "SSB81U");
        actuatorPartMap.put("255", "SSB61U");

        // Powermite MT Actuators
        actuatorPartMap.put("256", "599-01088");
        actuatorPartMap.put("257", "599-01088");
        actuatorPartMap.put("258", "599-01088");
        actuatorPartMap.put("259", "SSC81U");
        actuatorPartMap.put("260", "SSC81.5U");
        actuatorPartMap.put("261", "SSC61U");
        actuatorPartMap.put("262", "SSC61.5U");
        actuatorPartMap.put("264", "SQS65U");
        actuatorPartMap.put("265", "SQS65.5U");
        actuatorPartMap.put("266", "SQS85.53U");
        
        actuatorPartMap.put("267", "SKD60U"); // Aded from change 2-27-2014

        actuatorPartMap.put("268", "599-01081");
        actuatorPartMap.put("269", "599-01082");
        actuatorPartMap.put("270", "599-01083");
        actuatorPartMap.put("271", "SQX62U");
        actuatorPartMap.put("272", "SQX82.00U");
        actuatorPartMap.put("273", "SQX82.03U");
        actuatorPartMap.put("274", "SKD62U");
        actuatorPartMap.put("275", "SKD82.50U");
        actuatorPartMap.put("276", "SKD82.51U");
        actuatorPartMap.put("277", "599-01050");
        actuatorPartMap.put("278", "599-01051");
        actuatorPartMap.put("279", "599-01010");
        actuatorPartMap.put("281", "599-01000");
        actuatorPartMap.put("283", "599-01050+599-00426");
        actuatorPartMap.put("284", "599-01051+599-00426");
        actuatorPartMap.put("285", "599-01010+599-00423");
        actuatorPartMap.put("287", "599-01000+599-00423");
        actuatorPartMap.put("289", "SKB82.51U");
        actuatorPartMap.put("290", "SKB82.50U");
        actuatorPartMap.put("291", "SKB62U");
        actuatorPartMap.put("292", "SKC82.61U");
        actuatorPartMap.put("293", "SKC82.60U");
        actuatorPartMap.put("294", "SKC62U");

        actuatorPartMap.put("298", "599-03609");
        actuatorPartMap.put("299", "599-03611");
        
        actuatorPartMap.put("334", "SAY81.03U");
        actuatorPartMap.put("335", "SAY61.03U");
        
        // SAS Actuators
        actuatorPartMap.put("363", "SAS81.03U");
        actuatorPartMap.put("364", "SAS61.03U");
        actuatorPartMap.put("365", "SAS61.33U");
        actuatorPartMap.put("366", "SAS81.33U");
        
        // SAX Actuators
        actuatorPartMap.put("371", "SAX61.03U"); // Spring Return
        actuatorPartMap.put("373", "SAX81.03U");
        actuatorPartMap.put("370", "SAX61.36U"); // Non-Spring Return
        actuatorPartMap.put("372", "SAX81.36U");
        
        actuatorPartMap.put("378", "SAV61.00U");
        actuatorPartMap.put("379", "SAV81.00U");
        
        createActuatorSupplyVoltageMap();
    }
    
	
    private static void createActuatorSupplyVoltageMap()
    {
        if (actuatorSupplyVoltageMap != null)
            return;
        
        actuatorSupplyVoltageMap    = new HashMap<String, Float>();
        
        // Ball Valve Actuators
        actuatorSupplyVoltageMap.put("GQD221.1U", 120F);
        actuatorSupplyVoltageMap.put("GMA221.1U", 120F);
        actuatorSupplyVoltageMap.put("GQD221.1U", 120F);
        actuatorSupplyVoltageMap.put("GQD221.1U", 120F);
        
        // Zone Valve Actuators
        actuatorSupplyVoltageMap.put("SFA11U", 120F);
        actuatorSupplyVoltageMap.put("SFP11U", 120F);
        
    }
	
	
    public static void setSupplyVoltageOnActuator(SiemensActuator actuator)
    {
        float voltage = 24; // Default to 24
        
        if ((actuatorSupplyVoltageMap != null) && (actuatorSupplyVoltageMap.containsKey(actuator.getActualPartNumber())))
            voltage = actuatorSupplyVoltageMap.get(actuator.getActualPartNumber());            
        
        actuator.getActuatorMetadata().setSupplyVoltage(voltage);
    }
	
	
    /**
     * Get Actuator Part Number By Prefix
     *
     * takes an assembly prefix and returns the actuator's part number
     *
     * @param prefix Assembly Prefix
     * @return Actuator Part Number
     */
    public static String getActuatorPartNumberByPrefix(String prefix)
    {
        if (actuatorPartMap == null)
            createActuatorMap();

        if (actuatorPartMap.containsKey(prefix))
            return actuatorPartMap.get(prefix);

        return null;
    }
	
	
    /**
     * Is Valve Part Number
     *
     * determines if the part number passed is a valve part number by looking up
     * its prefix in the valve map.
     *
     * @param partNumber Product Part Number
     * @return boolean
     */
    public static boolean isValvePartNumber(String partNumber)
    {
        if (valvePartMap == null)
            createValveMap();

        boolean isValve = false;

        if (Character.isDigit(partNumber.charAt(0)))
        {
            // Numeric Prefix
            // Extract Prefix
            String numericPrefix = partNumber.substring(0, partNumber.indexOf('-'));
            isValve  = valvePartMap.containsKey(numericPrefix);
        }
        else
        {
            // Character Prefix
            String firstCharacter   = new String(new char[] {partNumber.charAt(0)});
            firstCharacter          = firstCharacter.toUpperCase();
            isValve                 = valvePartMap.containsKey(firstCharacter);
        }
        return isValve;
    }
	
	
    /**
     * Is Assembly Part Number
     *
     * takes a product part number and determines if it is an assembly
     * this method always returns true for butterfly part numbers and uses
     * the actuator map to determine if the prefix is an assembly prefix
     *
     * @param partNumber Product Part Number
     * @return boolean
     */
    public static boolean isAssemblyPartNumber(String partNumber)
    {
        if (actuatorPartMap == null)
            createActuatorMap();

        if (partNumber.substring(0, 2).equalsIgnoreCase("BV"))
        {
            // Butterfly Assemblies
            return true;
        }

        // Extract Prefix
        String prefix = partNumber.substring(0, partNumber.indexOf('-'));

        return actuatorPartMap.containsKey(prefix);
    }
	
		
    public static void setSpecialProperties(AbstractSiemensProduct product)
    {
        if (product instanceof SiemensActuator || product instanceof SiemensAssembly)
        {
            SiemensActuator actuator    = (product instanceof SiemensAssembly) ? ((SiemensAssembly) product).getActuator() : (SiemensActuator) product;
            
            if (actuator != null && actuator.getActualPartNumber() != null)
            {
                if (actuator.getActualPartNumber().equalsIgnoreCase("GQD126.1P") ||
                    actuator.getActualPartNumber().equalsIgnoreCase("GMA126.1P"))
                {
                    actuator.getActuatorMetadata().setEndSwitch(ActuatorEndSwitch.Electric);
                }
            }
        }
    }
}
