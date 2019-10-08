/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siemens.vsst.data.enumeration.actuators;

/**
 * Actuator Control Signal Enumeration
 * @author michael
 */
public enum ActuatorControlSignal
{
    Unknown,
    S0_10_VDC,	// Electric 0 - 10 Voltage DC
    S0_20_VDC,	// Electric 0 - 20 Voltage DC
    S4_20mA,	// Electric 4 - 20 MilliAmps
    S2_POS,	// Electric 2 Position
    S3_POS,	// Electric 3 Position
    ON_OFF,	// Electric ON / OFF
    S0_20_PSI,	// Pneumatic 0 - 20 PSI
    S0_60_PSI,	// Pneumatic 0 - 60 PSI
    S2_6_PSI,	// Pneumatic 2 - 6 PSI
    S2_12_PSI,	// Pneumatic 2 - 12 PSI
    S3_8_PSI,	// Pneumatic 3 - 8 PSI
    S3_13_PSI,	// Pneumatic 3 - 13 PSI
    S5_10_PSI,	// Pneumatic 5 - 10 PSI
    S8_13_PSI,	// Pneumatic 8 - 13 PSI
    S10_14_PSI,	// Pneumatic 10 - 14 PSI
    S10_15_PSI,	// Pneumatic 10 - 15 PSI
    S10_20_PSI,	// Pneumatic 10 - 20 PSI
    ON_OFF__FLOATING,
    S0_10V__4_20MA,
    DOUBLE_ACTING_60PSI,
    S2_10VDC,
    Floating_0_10V_4_20MA,
    s20PSISTD,
    s60PSIHighPress;

    public static ActuatorControlSignal fromInt(int type)
    {
        switch (type)
        {
            case 1:
                return S0_10_VDC;
            case 2:
                return S0_20_VDC;
            case 3:
                return S4_20mA;
            case 4:
                return S2_POS;
            case 5:
                return S3_POS;
            case 6:
                return ON_OFF;
            case 7:
                return S0_20_PSI;
            case 8:
                return S0_60_PSI;
            case 9:
                return S2_6_PSI;
            case 10:
                return S2_12_PSI;
            case 11:
                return S3_8_PSI;
            case 12:
                return S3_13_PSI;
            case 13:
                return S5_10_PSI;
            case 14:
                return S8_13_PSI;
            case 15:
                return S10_14_PSI;
            case 16:
                return S10_15_PSI;
            case 17:
                return S10_20_PSI;
            case 18:
                return ON_OFF__FLOATING;
            case 19:
                return S0_10V__4_20MA;
            case 20:
                return DOUBLE_ACTING_60PSI;
            case 21:
                return S2_10VDC;
            case 22:
                return Floating_0_10V_4_20MA;
            case 23:
                return s20PSISTD;
            case 24:
                return s60PSIHighPress;
        }

        return Unknown;
    }
    
    
    public static ActuatorControlSignal fromString(String name)
    {
        if (name.equalsIgnoreCase("0-10 VDC"))
        {
            return S0_10_VDC;
        }
		else if (name.equalsIgnoreCase("2-10 VDC"))
        {
            return S2_10VDC;
        }
		else if (name.equalsIgnoreCase("0-20 VDC"))
        {
            return S0_20_VDC;
        }
		else if (name.equalsIgnoreCase("0-20 psi"))
        {
            return S0_20_PSI;
        }
		else if (name.equalsIgnoreCase("0-60 psi"))
        {
            return S0_60_PSI;
        }
		else if (name.equalsIgnoreCase("10-14 psi"))
        {
            return S10_14_PSI;
        }
		else if (name.equalsIgnoreCase("10-15 psi"))
        {
            return S10_15_PSI;
        }
		else if (name.equalsIgnoreCase("10-20 psi"))
        {
            return S10_20_PSI;
        }
		else if (name.equalsIgnoreCase("2 Pos") || name.equalsIgnoreCase("2 Position"))
        {
            return S2_POS;
        }
		else if (name.equalsIgnoreCase("2-12 psi"))
        {
            return S2_12_PSI;
        }
		else if (name.equalsIgnoreCase("2-6 psi"))
        {
            return S2_6_PSI;
        }
		else if (name.equalsIgnoreCase("3 Pos"))
        {
            return S3_POS;
        }
		else if (name.equalsIgnoreCase("3-13 psi"))
        {
            return S3_13_PSI;
        }
		else if (name.equalsIgnoreCase("3-8 psi"))
        {
            return S3_8_PSI;
        }
		else if (name.equalsIgnoreCase("4-20 mA"))
        {
            return S4_20mA;
        }
		else if (name.equalsIgnoreCase("5-10 psi"))
        {
            return S5_10_PSI;
        }
		else if (name.equalsIgnoreCase("8-13 psi"))
        {
            return S8_13_PSI;
        }
		else if (name.equalsIgnoreCase("ON/OFF"))
        {
            return ON_OFF;
        }
		else if (name.equalsIgnoreCase("0-10 VDC,4-20 mA"))
        {
            return S0_10V__4_20MA;
        }
		else if (name.equalsIgnoreCase("3 Pos,0-10 VDC,4-20 mA"))
        {
            return Floating_0_10V_4_20MA;
        }
		else if (name.equalsIgnoreCase("3 Pos,0-10 VDC,4"))
        {
            return Floating_0_10V_4_20MA;
        }


        return Unknown;
    }

    public int toFlag()
    {
        switch (this)
        {
            case Unknown:
                return 0;

            case S0_10_VDC:
                return 1;

            case S0_20_VDC:
                return 2;

            case S4_20mA:
                return 3;

            case S2_POS:
            case ON_OFF:
                return 4;
            
            case S3_POS:
                return 5;
            
//            case ON_OFF:
//                return 6; // On/Off and 2 Position are the same thing


            case S0_20_PSI:
                return 7;
            case S0_60_PSI:
                return 8;
            case S2_6_PSI:
                return 9;
            case S2_12_PSI:
                return 10;
            case S3_8_PSI:
                return 11;
            case S3_13_PSI:
                return 12;
            case S5_10_PSI:
                return 13;
            case S8_13_PSI:
                return 14;
            case S10_14_PSI:
                return 15;
            case S10_15_PSI:
                return 16;
            case S10_20_PSI:
                return 17;
            case ON_OFF__FLOATING:
                return 18;
            case S0_10V__4_20MA:
                return 19;
            case DOUBLE_ACTING_60PSI:
                return 20;
            case S2_10VDC:
                return 21;
            case Floating_0_10V_4_20MA:
                return 22;
                
            case s20PSISTD:
                return 23;
            case s60PSIHighPress:
                return 24;
        }

        return 0;
    }
}
