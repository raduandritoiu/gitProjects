package siemens.vsst.data.parsers.dbs;

import org.xBaseJ.DBF;
import org.xBaseJ.fields.Field;
import org.xBaseJ.fields.LogicalField;
import org.xBaseJ.xBaseJException;
import siemens.vsst.data.enumeration.actuators.ActuatorControlSignal;
import siemens.vsst.data.enumeration.actuators.ActuatorMotorType;
import siemens.vsst.data.enumeration.actuators.ActuatorPositioner;
import siemens.vsst.data.enumeration.valves.ValveNormalState;
import siemens.vsst.data.enumeration.actuators.ActuatorSpring;
import siemens.vsst.data.enumeration.valves.FlowCharacteristics;
import siemens.vsst.data.enumeration.valves.PackingMaterial;
import siemens.vsst.data.enumeration.valves.ValveConnection;
import siemens.vsst.data.enumeration.valves.ValvePattern;
import siemens.vsst.data.enumeration.valves.ValveTrim;
import siemens.vsst.data.enumeration.valves.ValveType;
import siemens.vsst.data.io.DBFReader;
import siemens.vsst.data.models.AbstractSiemensProduct;
import siemens.vsst.data.models.SiemensActuator;
import siemens.vsst.data.models.SiemensAssembly;
import siemens.vsst.data.models.SiemensValve;
import siemens.vsst.data.models.metadata.ActuatorMetadata;
import siemens.vsst.data.models.metadata.ValveMetadata;
import siemens.vsst.data.parsers.utils.SiemensParserUtil;
import siemens.vsst.data.parsers.utils.VSSTLogger;

/**
 * Valve DB Parser
 *
 * Parses Valve DB File
 * 
 * @author michael
 */
public class ValveDBParser extends GenericDBParser
{
    @Override
    public void parseDataRow(DBF db, AbstractSiemensProduct product) throws xBaseJException {
        SiemensAssembly assembly	= null;
        SiemensActuator actuator    = null;
        ValveMetadata valveMetadata         = null;
        ActuatorMetadata actuatorMetadata   = null;
        
        if (product instanceof SiemensValve) {
            if (product.isProcessed())
                return;
            valveMetadata = ((SiemensValve) product).getValveMetadata();
        }
        else if (product instanceof SiemensAssembly) {
            assembly	= (SiemensAssembly) product;
            actuator    = assembly.getActuator();
            valveMetadata       = assembly.getValve().getValveMetadata();
            actuatorMetadata    = assembly.getActuator().getActuatorMetadata();
        }
        else if (product instanceof SiemensActuator) {
            actuator = (SiemensActuator) product;
            if (product.isProcessed())
                return;
            actuatorMetadata   = actuator.getActuatorMetadata();
        }
        
        String partNumber           = ""; 
        Field noncField             = db.getField("NONC");
        Field valveTypeField        = db.getField(9);
        Field valvePatternFiled     = db.getField(38);
        Field actuatorFailSafe      = db.getField(6);
        
        for (int i = 0; i < db.getFieldCount(); i++) {
            Field field = db.getField(i + 1);
            try {
                switch (i) {
                    case 0: // Part Number
                    {
                        product.setPartNumber(field.get().trim());
                        partNumber  = product.getPartNumber();
                        
                        if (product instanceof SiemensValve) {
                            valveMetadata.setPartNumber(field.get().trim());
                        }
                        else if (product instanceof SiemensActuator) {
                            actuatorMetadata.setPartNumber(field.get().trim());
                        }
                        else if ((product instanceof SiemensAssembly) && (partNumber.indexOf('-') > -1)) {
                            int firstDashIndex          = partNumber.indexOf('-');
                            String[] partNumberParts    = new String[] {partNumber.substring(0, firstDashIndex), partNumber.substring(firstDashIndex + 1)};
                            assembly.getValve().setPartNumber("599-" + partNumberParts[1]);
                            assembly.getActuator().setPartNumber(SiemensParserUtil.getActuatorPartNumberByPrefix(partNumberParts[0]));
                            
                            // retrieve actuator from productMap
                            ValveType valveType = ValveType.fromString(valveTypeField.get().trim());
                            if ((valveType.equals(ValveType.Ball))
                                && (ValvePattern.fromString(valvePatternFiled.get().trim()).equals(ValvePattern.TwoWay))
                                && (ActuatorSpring.fromString(actuatorFailSafe.get().trim()).equals(ActuatorSpring.SpringReturn)) )
                            {
                                // This actuator determines if its normally open or normally closed
                                ValveNormalState normalState    = ValveNormalState.fromString(noncField.get().trim());
                                String constructedPartNumber    = assembly.getActualPartNumber() + "-" + normalState.toString();
                                if (DBFReader.productMap.containsKey(constructedPartNumber)) {
                                    assembly.setActuator((SiemensActuator) DBFReader.productMap.get(constructedPartNumber));
                                    // if there is already a actuator in the productMap, do not override any of its properties values
                                    // with the ones on this row; for that I set the metadata to null
                                    actuatorMetadata = null;
                                    actuator = null;
                                }
                                // HAVE NO (GODLY) IDEEA WHY IT DOESN"T SEARCH FOR MATHCING VALVES IN THIS IF-BRANCH (BUT HEY)
                            }

                            else {
                                // retrieve valve from productMap
                                if (DBFReader.productMap.containsKey(assembly.getValve().getPartNumber())) {
                                    assembly.setValve((SiemensValve) DBFReader.productMap.get(assembly.getValve().getPartNumber()));
                                    // if there is already a valve in the productMap, do not override any of its properties values
                                    // with the ones on this row; for that I set the metadata to null
                                    valveMetadata = null;
                                }
                                if (DBFReader.productMap.containsKey(assembly.getActuator().getPartNumber())) {
                                    assembly.setActuator((SiemensActuator) DBFReader.productMap.get(assembly.getActuator().getPartNumber()));
                                    // if there is already a actuator in the productMap, do not override any of its properties values
                                    // with the ones on this row; for that I set the metadata to null
                                    actuatorMetadata = null;
                                    actuator = null;
                                }
                            }
                        }
                        
                        // Set Supply voltage. value is not in DBF.
                        if (actuator != null)
                            SiemensParserUtil.setSupplyVoltageOnActuator(actuator);
                        
                        break;
                    } 
                    case 1: // Size
                    {
                        if (valveMetadata != null)
                            valveMetadata.setSize(Float.parseFloat(field.get().trim()));
                        break;
                    }
                    case 2: // CVU
                    {
                        if (valveMetadata != null)
                            valveMetadata.setCvu(Float.parseFloat(field.get().trim()));
                        break;
                    }
                    case 3: // CVL
                    {
                        if (valveMetadata != null)
                            valveMetadata.setCvl(Float.parseFloat(field.get().trim()));
                        break;
                    }
                    case 4: // Action
                    case 37: // Action 2
                    {
                        if (valveMetadata != null)
                            valveMetadata.setPattern(ValvePattern.fromString(field.get().trim()));
                        break;
                    }
                    case 5: // Fail Safe U
                    {
                        if (valveMetadata != null)
                            valveMetadata.setFailSafeU(field.get().trim());
                        if (actuatorMetadata != null) {
                            actuatorMetadata.setSpringReturn(ActuatorSpring.fromString(field.get().trim()));
                            //actuatorMetadata.setNormalPosition(ValveNormalState.fromString(field.get().trim()));
                        }
                        break;
                    }
                    /*
                    case 6: // Fail Safe L
                    {
                        metadata.setFailSafeL(field.get().trim());
                        break;
                    }
                     */
                    case 7: // Connection
                    {
                        if (valveMetadata != null)
                            valveMetadata.setConnection(ValveConnection.fromString(field.get().trim()));
                        break;
                    }
                    case 8: // Body Style
                    {
                        if (valveMetadata != null)
                            valveMetadata.setType(ValveType.fromString(field.get().trim()));
                        break;
                    }
                    case 9: // Seat Style
                    {
                        if (valveMetadata != null)
                            valveMetadata.setSeatStyle(field.get().trim());
                        break;
                    }
                    case 10: // Flow Characteristics
                    case 39: // Flow Characteristics 2
                    {
                        if (valveMetadata != null)
                            valveMetadata.setFlowChar(FlowCharacteristics.fromString(field.get().trim()));
                        break;
                    }
                    case 11: // Max Temperature
                    {
                        if (valveMetadata != null)
                            valveMetadata.setMaxTemp(Float.parseFloat(field.get().trim()));
                        break;
                    }
                    case 12: // Maximum Pressure
                    {
                        if (valveMetadata != null)
                            valveMetadata.setMaxPress(Float.parseFloat(field.get().trim()));
                        break;
                    }
                    case 13: // Maximum Direct Water Pressure
                    {
                        if (valveMetadata != null)
                            valveMetadata.setMaxDPWater(Float.parseFloat(field.get().trim()));
                        break;
                    }
                    case 14: // Maximum Direct Steam Pressure
                    {
                        if (valveMetadata != null)
                            valveMetadata.setMaxDPSteam(Float.parseFloat(field.get().trim()));
                        break;
                    }
                    case 15: // Maximum Input Water Pressure
                    {
                        if (valveMetadata != null)
                            valveMetadata.setMaxIPWater(Float.parseFloat(field.get().trim()));
                        break;
                    }
                    case 16: // Maximum Input Steam Pressure
                    {
                        if (valveMetadata != null)
                            valveMetadata.setMaxIPSteam(Float.parseFloat(field.get().trim()));
                        break;
                    }
                    case 17: // Maximum PCT Glycol
                    {
                        if (valveMetadata != null)
                            valveMetadata.setMaxPCTGly(Float.parseFloat(field.get().trim()));
                        break;
                    }
                    /*
                    case 18: // Close Slope
                    {
                        metadata.setCloseSlope(Float.parseFloat(field.get().trim()));
                        break;
                    }
                    case 19: // Close Interval
                    {
                        metadata.setCloseInterval(Float.parseFloat(field.get().trim()));
                        break;
                    }
                    */
                    case 20: // Motor Type
                    {
                        if (actuatorMetadata != null)
                            actuatorMetadata.setMotorType(ActuatorMotorType.fromString(field.get().trim()));
                        break;
                    }
                    case 21: // Positioner
                    {
                        if (actuatorMetadata != null) {
                            boolean hasPositioner   = ((LogicalField)field).getBoolean();
                            actuatorMetadata.setPositioner((hasPositioner) ? ActuatorPositioner.Standard : ActuatorPositioner.None);
                        }
                        break;
                    }
                    case 22: // Control Signal
                    case 38: // Control Signal 2
                    {
                        if (actuatorMetadata != null) {
                            actuatorMetadata.setControlSignal(ActuatorControlSignal.fromString(field.get().trim()));
                        }
                        if (product instanceof SiemensAssembly) {
                            ((SiemensAssembly) product).setControlSignal(ActuatorControlSignal.fromString(field.get().trim()));
                        }
                        break;
                    }
                    case 23: // Ambeient Temperature
                    {
                        if (valveMetadata != null)
                            valveMetadata.setAmbientTem(field.get().trim());
                        break;
                    }
                    case 24: // Model
                    {
                        product.setModel(field.get().trim());
                        break;
                    }
                    case 25: // Body Material
                    {
                        if (valveMetadata != null)
                            valveMetadata.setBodyMaterial(field.get().trim());
                        break;
                    }
                    case 26: // Packing Material
                    {
                        if (valveMetadata != null)
                            valveMetadata.setPackingMaterial(PackingMaterial.fromString(field.get().trim()));
                        break;
                    }
                    case 27: // Stem Material
                    {
                        if (valveMetadata != null)
                            valveMetadata.setStemMaterial(field.get().trim());
                        break;
                    }
                    case 28: // Plug Material
                    {
                        if (valveMetadata != null)
                            valveMetadata.setPlugMaterial(ValveTrim.fromString(field.get().trim()));
                        break;
                    }
                    case 29: // Disc Material
                    {
                        if (valveMetadata != null)
                            valveMetadata.setDiscMaterial(field.get().trim());
                        break;
                    }
                    case 30: // Seat Material
                    {
                        if (valveMetadata != null)
                            valveMetadata.setSeatMaterial(field.get().trim());
                        break;
                    }
                    case 31: // Close Off
                    {
                        String closeOffValue = field.get().trim();
                        if (product instanceof SiemensAssembly)
                            ((SiemensAssembly) product).setCloseOff(Float.parseFloat(closeOffValue));
                        break;
                    }
                    
                    case 32: // Thread
                    {
                        if (valveMetadata != null)
                            valveMetadata.setThread(field.get().trim());
                        break;
                    }
                    case 33: // KVSU
                    {
                        if (valveMetadata != null)
                            valveMetadata.setKvsu(Float.parseFloat(field.get().trim()));
                        break;
                    }
                    case 34: // KVSL
                    {
                        if (valveMetadata != null)
                            valveMetadata.setKvsl(Float.parseFloat(field.get().trim()));
                        break;
                    }
                    case 35: // Last Modified
                    {
                        if (valveMetadata != null)
                            break;

                        StringBuffer lastModifiedString = new StringBuffer(field.get().trim());
                        lastModifiedString.insert(6, '/');
                        lastModifiedString.insert(4, '/');

                        try {
                            valveMetadata.setLastModified(DBFReader.dateFormatter.parse(lastModifiedString.toString()));
                        }
                        catch (Exception lmEx) {/* Do nothing */}
                        break;
                    }
                    case 36: // NONC
                    {
                        String noncValue = field.get().trim();                     
                        if (valveMetadata != null) {
                            valveMetadata.setNormalState(ValveNormalState.fromString(noncValue));
                            if (valveMetadata.getPattern().equals(ValvePattern.TwoWay)) {
                                // Actuator Determines NO or NC
                                if (valveMetadata.getType().equals(ValveType.Ball))
                                    valveMetadata.setNormalState(ValveNormalState.Unknown);
                                if (valveMetadata.getType().equals(ValveType.Zone))
                                    valveMetadata.setNormalState(ValveNormalState.Unknown);
                            }
                        }

                        if (product instanceof SiemensAssembly) {
                            ((SiemensAssembly)product).setNormalPosition(ValveNormalState.fromString(noncValue));
                        }
                        
                        if (actuator != null && actuatorMetadata != null) {
                            // For Two Way Ball Valves Append NO or NC to the Actuator Part Number
                            if ((valveMetadata != null) && (valveMetadata.getType().equals(ValveType.Ball))&& valveMetadata.getPattern().equals(ValvePattern.TwoWay) && actuatorMetadata.getSpringReturn().equals(ActuatorSpring.SpringReturn)) {
                                actuator.setPartNumber(actuator.getPartNumber() + "-" + ((SiemensAssembly)product).getNormalPosition().toString());
                            }
                        }
                        break;
                    }
                    case 40: // Min GPM
                    {
                        if (valveMetadata != null) {
                            String minGPMStr    = field.get().trim();
                            if (minGPMStr != null && minGPMStr.length() > 0)
                                valveMetadata.setMinGPM(Float.parseFloat(minGPMStr));
                        }
                        break;
                    }
                    case 41: // Max GPM
                    {
                        if (valveMetadata != null) {
                            String maxGPMStr    = field.get().trim();
                            if (maxGPMStr != null && maxGPMStr.length() > 0)
                                valveMetadata.setMaxGPM(Float.parseFloat(maxGPMStr));
                        }
                        break;
                    }
                    case 42: // Preset GPM
                    {
                        if (valveMetadata != null) {
                            String presetGPMStr    = field.get().trim();
                            if (presetGPMStr != null && presetGPMStr.length() > 0)
                                valveMetadata.setPresetGPM(Float.parseFloat(presetGPMStr));
                        }
                        break;
                    }

                    default:
                        // Skip

                }
            }
            catch (Exception ex) {
				if (enableParsingOutput()) {
					VSSTLogger.Warning("Error Parsing Field: " + field.getName() + "  : " + ex.getMessage());
				}
            }
        }
        
        // set special properties based on Product Number
        SiemensParserUtil.setSpecialProperties(product);
        
        // again set special properties based on Product Number
        if (partNumber.substring(0 , 2).equalsIgnoreCase("BV")) {
            // Parse Butterfly Assembly by Part Number
            // product.setPartNumber(partNumber);

            ((SiemensAssembly) product).getValve().setVirtualPart(true);
            ((SiemensAssembly) product).getActuator().setVirtualPart(true);

            ((SiemensAssembly) product).getValve().setAssemblyOnly(true);
            ((SiemensAssembly) product).getActuator().setAssemblyOnly(true);
            
            product.setAssemblyOnly(true);

            PartNumberProductParser.parseButterflyAssembly((SiemensAssembly) product);
        }

        product.setProcessed(true);
    }



    @Override
    public AbstractSiemensProduct getItemForPart(String partNumber) throws Exception {
        if (SiemensParserUtil.isValvePartNumber(partNumber)) {
            return new SiemensValve();
        }
        if (SiemensParserUtil.isAssemblyPartNumber(partNumber)) {
            return new SiemensAssembly();
        }
		if (enableParsingOutput()) {
			VSSTLogger.Warning("Unable to classify part number: " + partNumber);
		}
        throw new Exception("Error Instantiating New Product " + partNumber);
    }


    public static ValveDBParser newInstance() {
        return new ValveDBParser();
    }
}