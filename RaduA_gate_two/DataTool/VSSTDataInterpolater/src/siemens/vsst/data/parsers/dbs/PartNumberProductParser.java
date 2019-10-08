package siemens.vsst.data.parsers.dbs;

import siemens.vsst.data.enumeration.actuators.ActuatorControlSignal;
import siemens.vsst.data.enumeration.actuators.ActuatorEndSwitch;
import siemens.vsst.data.enumeration.actuators.ActuatorMotorType;
import siemens.vsst.data.enumeration.actuators.ActuatorPositioner;
import siemens.vsst.data.enumeration.actuators.ActuatorSpring;
import siemens.vsst.data.enumeration.valves.ValveConnection;
import siemens.vsst.data.enumeration.valves.ValveDiscType;
import siemens.vsst.data.enumeration.valves.ValveNormalState;
import siemens.vsst.data.enumeration.valves.ValvePattern;
import siemens.vsst.data.models.SiemensAssembly;

/**
 * Product Part Number Parser
 *
 * Parses part number to determine valve information
 * @author michael
 */
public class PartNumberProductParser
{
    /**
     * Butterfly Part Number Parser
     *
     * parses butterfly assemblies and sets values based on the product's part number.
     *
     * @param asm Butterfly Assembly
     */
    public static void parseButterflyAssembly(SiemensAssembly asm)
    {
        int charIndex       = 2;
        String partNumber   = asm.getPartNumber();
        
        asm.getValve().setPartNumber(partNumber.substring(0, 7));
        asm.getActuator().setPartNumber(partNumber.substring(7));

        String valveDesc = "BFV";
        String actuatorDesc = "";
        while (charIndex < partNumber.length())
        {
            String descriminant = new String(new char[] {partNumber.charAt(charIndex)});

            switch (charIndex)
            {
                case 2:
                {
                    // Valve Assembly Type

                    descriminant += new String(new char[] {partNumber.charAt(++charIndex)});

                    if (descriminant.equalsIgnoreCase("2W"))
                    {
                        valveDesc += " 2-Way";
                        asm.getValve().getValveMetadata().setPattern(ValvePattern.TwoWay);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("3W"))
                    {
                        valveDesc += " 3-Way";
                        asm.getValve().getValveMetadata().setPattern(ValvePattern.ThreeWay);
                    }
                    else
                    {
                        // Unknown
                        asm.getValve().getValveMetadata().setPattern(ValvePattern.Unknown);
                    }
                    break;
                }

                case 4:
                {
                    // Valve Size
                    descriminant += new String(new char[] {partNumber.charAt(++charIndex)});

                    if (Integer.parseInt(descriminant) > 20)
                    {
                        descriminant = descriminant.charAt(0) + "." + descriminant.charAt(1);
                    }

                    float valveSize = Float.parseFloat(descriminant);

                    asm.getValve().getValveMetadata().setSize(valveSize);
                    valveDesc += " " + valveSize + "\"";
                    break;
                }

                case 6:
                {
                    // Disc Type
                    if (descriminant.equalsIgnoreCase("F"))
                    {
                        valveDesc += " FullCut";
                        asm.getValve().getValveMetadata().setDiscType(ValveDiscType.FullCut);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("U"))
                    {
                        valveDesc += " UnderCut";
                        asm.getValve().getValveMetadata().setDiscType(ValveDiscType.UnderCut);
                    }
                    break;
                }

                case 7:
                {
                    // Actuator Type
                    descriminant += new String(new char[] {partNumber.charAt(++charIndex)});

                    if (descriminant.equalsIgnoreCase("S2"))
                    {
                        actuatorDesc += " Pneumatic SR 20-PSI STD";
                        asm.getActuator().getActuatorMetadata().setMotorType(ActuatorMotorType.Pneumatic);
                        asm.getActuator().getActuatorMetadata().setSpringReturn(ActuatorSpring.SpringReturn);
                        asm.getActuator().getActuatorMetadata().setControlSignal(ActuatorControlSignal.s20PSISTD);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("S6"))
                    {
                        actuatorDesc += " Pneumatic SR 60-PSI High Press";
                        asm.getActuator().getActuatorMetadata().setMotorType(ActuatorMotorType.Pneumatic);
                        asm.getActuator().getActuatorMetadata().setSpringReturn(ActuatorSpring.SpringReturn);
                        asm.getActuator().getActuatorMetadata().setControlSignal(ActuatorControlSignal.s60PSIHighPress);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("D6"))
                    {
                        actuatorDesc += " Pneumatic NSR 60-PSI Double Acting";
                        asm.getActuator().getActuatorMetadata().setMotorType(ActuatorMotorType.Pneumatic);
                        asm.getActuator().getActuatorMetadata().setSpringReturn(ActuatorSpring.NonSpringReturn);
                        asm.getActuator().getActuatorMetadata().setControlSignal(ActuatorControlSignal.DOUBLE_ACTING_60PSI);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("E2"))
                    {
                        actuatorDesc += " Electric NSR 2 Position/Floating Control, 100-240 VAC/DC Power";
                        asm.getActuator().getActuatorMetadata().setMotorType(ActuatorMotorType.Electric);
                        asm.getActuator().getActuatorMetadata().setControlSignal(ActuatorControlSignal.ON_OFF__FLOATING);
                        asm.getActuator().getActuatorMetadata().setSpringReturn(ActuatorSpring.NonSpringReturn);
                        asm.getActuator().getActuatorMetadata().setSupplyVoltage(24240);
                        asm.getActuator().getActuatorMetadata().setEndSwitch(ActuatorEndSwitch.Electric);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("EM"))
                    {
                        actuatorDesc += " Electric NSR Modulating 0-10VDC Control, 100-240 VAC/DC Power";
                        asm.getActuator().getActuatorMetadata().setMotorType(ActuatorMotorType.Electric);
                        asm.getActuator().getActuatorMetadata().setControlSignal(ActuatorControlSignal.S0_10_VDC);
                        asm.getActuator().getActuatorMetadata().setSpringReturn(ActuatorSpring.NonSpringReturn);
                        asm.getActuator().getActuatorMetadata().setSupplyVoltage(24240);
                        asm.getActuator().getActuatorMetadata().setEndSwitch(ActuatorEndSwitch.Electric);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("EP"))
                    {
                        actuatorDesc += " Electric NSR Modulating 4-20mA Control, 100-240 VAC/DC Power";
                        asm.getActuator().getActuatorMetadata().setMotorType(ActuatorMotorType.Electric);
                        asm.getActuator().getActuatorMetadata().setControlSignal(ActuatorControlSignal.S4_20mA);
                        asm.getActuator().getActuatorMetadata().setSpringReturn(ActuatorSpring.NonSpringReturn);
                        asm.getActuator().getActuatorMetadata().setSupplyVoltage(24240);
                        asm.getActuator().getActuatorMetadata().setEndSwitch(ActuatorEndSwitch.Electric);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("G1"))
                    {
                        actuatorDesc += " Electric SR ON/OFF Control, 120 VAC Power";
                        asm.getActuator().getActuatorMetadata().setMotorType(ActuatorMotorType.Electric);
                        asm.getActuator().getActuatorMetadata().setControlSignal(ActuatorControlSignal.ON_OFF);
                        asm.getActuator().getActuatorMetadata().setSpringReturn(ActuatorSpring.SpringReturn);
                        asm.getActuator().getActuatorMetadata().setSupplyVoltage(120);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("G2"))
                    {
                        actuatorDesc += " Electric SR ON/OFF Control, 24 VAC Power";
                        asm.getActuator().getActuatorMetadata().setMotorType(ActuatorMotorType.Electric);
                        asm.getActuator().getActuatorMetadata().setControlSignal(ActuatorControlSignal.ON_OFF);
                        asm.getActuator().getActuatorMetadata().setSpringReturn(ActuatorSpring.SpringReturn);
                        asm.getActuator().getActuatorMetadata().setSupplyVoltage(24);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("G3"))
                    {
                        actuatorDesc += " Electric SR Floating Control, 24 VAC Power";
                        asm.getActuator().getActuatorMetadata().setMotorType(ActuatorMotorType.Electric);
                        asm.getActuator().getActuatorMetadata().setControlSignal(ActuatorControlSignal.S3_POS);
                        asm.getActuator().getActuatorMetadata().setSpringReturn(ActuatorSpring.SpringReturn);
                        asm.getActuator().getActuatorMetadata().setSupplyVoltage(24);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("G4"))
                    {
                        actuatorDesc += " Electric SR Modulating 1-10VDC Control, 24 VAC Power";
                        asm.getActuator().getActuatorMetadata().setMotorType(ActuatorMotorType.Electric);
                        asm.getActuator().getActuatorMetadata().setControlSignal(ActuatorControlSignal.S0_10_VDC);
                        asm.getActuator().getActuatorMetadata().setSpringReturn(ActuatorSpring.SpringReturn);
                        asm.getActuator().getActuatorMetadata().setSupplyVoltage(24);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("G5"))
                    {
                        actuatorDesc += " Electric SR Modulating 4-20mA Control, 24 VAC Power";
                        asm.getActuator().getActuatorMetadata().setMotorType(ActuatorMotorType.Electric);
                        asm.getActuator().getActuatorMetadata().setControlSignal(ActuatorControlSignal.S4_20mA);
                        asm.getActuator().getActuatorMetadata().setSpringReturn(ActuatorSpring.SpringReturn);
                        asm.getActuator().getActuatorMetadata().setSupplyVoltage(24);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("G6"))
                    {
                        actuatorDesc += " Electric NSR Floating Control, 24 VAC Power";

                        asm.getActuator().getActuatorMetadata().setMotorType(ActuatorMotorType.Electric);
                        asm.getActuator().getActuatorMetadata().setControlSignal(ActuatorControlSignal.S3_POS);
                        asm.getActuator().getActuatorMetadata().setSpringReturn(ActuatorSpring.NonSpringReturn);
                        asm.getActuator().getActuatorMetadata().setSupplyVoltage(24);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("G7"))
                    {
                        actuatorDesc += " Electric NSR Modulating 0-10VDC Control, 24 VAC Power";

                        asm.getActuator().getActuatorMetadata().setMotorType(ActuatorMotorType.Electric);
                        asm.getActuator().getActuatorMetadata().setControlSignal(ActuatorControlSignal.S0_10_VDC);
                        asm.getActuator().getActuatorMetadata().setSpringReturn(ActuatorSpring.NonSpringReturn);
                        asm.getActuator().getActuatorMetadata().setSupplyVoltage(24);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("G8"))
                    {
                        actuatorDesc += " Electric NSR Modulating 4-20mA Control, 24 VAC Power";

                        asm.getActuator().getActuatorMetadata().setMotorType(ActuatorMotorType.Electric);
                        asm.getActuator().getActuatorMetadata().setControlSignal(ActuatorControlSignal.S4_20mA);
                        asm.getActuator().getActuatorMetadata().setSpringReturn(ActuatorSpring.NonSpringReturn);
                        asm.getActuator().getActuatorMetadata().setSupplyVoltage(24);
                    }

                }

                case 9:
                {
                    // Lug / Wafer (Cast Iron)
                    if (descriminant.equalsIgnoreCase("L"))
                    {
                        valveDesc += " Lug";
                        asm.getValve().getValveMetadata().setConnection(ValveConnection.LUG);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("W"))
                    {
                        valveDesc += " Wafer";
                        asm.getValve().getValveMetadata().setConnection(ValveConnection.WAFER);
                    }
                    break;
                }

                case 10:
                {
                    // This is an assembly parameter
                    if (descriminant.equalsIgnoreCase("O"))
                    {
                        //actuatorDesc += " Normally Open";
                        //asm.getValve().getMetadata().setNormalState(ValveNormalState.NormallyOpen);
                        asm.setNormalPosition(ValveNormalState.NormallyOpen);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("C"))
                    {
                        //actuatorDesc += " Normally Closed";
                       // asm.getValve().getMetadata().setNormalState(ValveNormalState.NormallyClosed);
                        asm.setNormalPosition(ValveNormalState.NormallyClosed);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("N"))
                    {
                        asm.setNormalPosition(ValveNormalState.NonFailSafe);
                        //asm.getValve().getMetadata().setNormalState(ValveNormalState.Unknown);
                    }

                    asm.getValve().getValveMetadata().setNormalState(ValveNormalState.Unknown);
                    actuatorDesc += " | Type " + descriminant;
                    asm.getActuator().getActuatorMetadata().setButterflyConfiguration(descriminant.charAt(0)); // Covers A-F
//
//                    if (descriminant.equalsIgnoreCase("A"))
//                    {
//                        // Mixing/Diverting
//                    }
//                    else
//                    if (descriminant.equalsIgnoreCase("B"))
//                    {
//                        // Mixing/Diverting
//                    }
//                    else
//                    if (descriminant.equalsIgnoreCase("C"))
//                    {
//                        // Mixing/Diverting
//                    }
//                    else
//                    if (descriminant.equalsIgnoreCase("D"))
//                    {
//                        // Mixing/Diverting
//                    }
//                    else
//                    if (descriminant.equalsIgnoreCase("E"))
//                    {
//                        // Mixing/Diverting
//                    }
//                    else
//                    if (descriminant.equalsIgnoreCase("F"))
//                    {
//                        // Mixing/Diverting
//                    }
                    break;
                }

                case 11:
                {
                    if (descriminant.equalsIgnoreCase("P"))
                    {
                        actuatorDesc += " | Pneumatic Positioner";
                        asm.getActuator().getActuatorMetadata().setPositioner(ActuatorPositioner.Pneumatic);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("Q"))
                    {
                        actuatorDesc += " | Pneumatic Positioner";
                        asm.getActuator().getActuatorMetadata().setPositioner(ActuatorPositioner.Pneumatic);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("R"))
                    {
                        actuatorDesc += " | Pneumatic Positioner";
                        asm.getActuator().getActuatorMetadata().setPositioner(ActuatorPositioner.Pneumatic);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("E"))
                    {
                        actuatorDesc += " | ElectroPneumatic Positioner";
                        asm.getActuator().getActuatorMetadata().setPositioner(ActuatorPositioner.ElectroPneumatic);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("F"))
                    {
                        actuatorDesc += " | ElectroPneumatic Positioner";
                        asm.getActuator().getActuatorMetadata().setPositioner(ActuatorPositioner.ElectroPneumatic);
                    }
                    if (descriminant.equalsIgnoreCase("T"))
                    {
                        actuatorDesc += " | E/P Valve 120V Positioner";
                        asm.getActuator().getActuatorMetadata().setPositioner(ActuatorPositioner.EPValve120V);
                    }
                    if (descriminant.equalsIgnoreCase("U"))
                    {
                        actuatorDesc += " | E/P Valve 24V Positioner";
                        asm.getActuator().getActuatorMetadata().setPositioner(ActuatorPositioner.EPValve24V);
                    }
                    if (descriminant.equalsIgnoreCase("V"))
                    {
                        actuatorDesc += " | E/P Valve 120V Positioner";
                        asm.getActuator().getActuatorMetadata().setPositioner(ActuatorPositioner.EPValve120V);
                    }
                    if (descriminant.equalsIgnoreCase("W"))
                    {
                        actuatorDesc += " | E/P Valve 24V Positioner";
                        asm.getActuator().getActuatorMetadata().setPositioner(ActuatorPositioner.EPValve24V);
                    }
                    if (descriminant.equalsIgnoreCase("Y"))
                    {
                        actuatorDesc += " | E/P Valve 120V Positioner";
                        asm.getActuator().getActuatorMetadata().setPositioner(ActuatorPositioner.EPValve120V);
                    }
                    if (descriminant.equalsIgnoreCase("Z"))
                    {
                        actuatorDesc += " | E/P Valve 24V Positioner";
                        asm.getActuator().getActuatorMetadata().setPositioner(ActuatorPositioner.EPValve24V);
                    }

                    break;
                }

                case 12: // END
                {
                    if (descriminant.equalsIgnoreCase("S"))
                    {
                        actuatorDesc += " | Pnuematic End Switch";
                        asm.getActuator().getActuatorMetadata().setEndSwitch(ActuatorEndSwitch.Pneumatic);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("A"))
                    {
                        actuatorDesc += " | Electric End Switch";
                        asm.getActuator().getActuatorMetadata().setEndSwitch(ActuatorEndSwitch.Electric);
                    }
                    else
                    if (descriminant.equalsIgnoreCase("P"))
                    {
                        actuatorDesc += " | Potentiometer End Switch";
                        asm.getActuator().getActuatorMetadata().setEndSwitch(ActuatorEndSwitch.Potentiometer);
                    }
                    
                    break;
                }

                case 13: // Heater
                {
                    if (descriminant.equalsIgnoreCase("H"))
                    {
                        actuatorDesc += " | Heater";
                        asm.getActuator().getActuatorMetadata().setHeater(true);
                    }
                    break;
                }

                case 14: // Manual Override
                {
                    if (descriminant.equalsIgnoreCase("M"))
                    {
                        actuatorDesc += " | Manual Override";
                        asm.getActuator().getActuatorMetadata().setManualOverride(true);
                    }
                    break;
                }
            }



            charIndex++;
        }

        asm.getValve().setDescription(valveDesc);
        asm.getActuator().setDescription(actuatorDesc);

        // Add description to Assembly (let this come from SAP)
        //asm.setDescription(valveDesc + " | " + actuatorDesc);
    }
}
