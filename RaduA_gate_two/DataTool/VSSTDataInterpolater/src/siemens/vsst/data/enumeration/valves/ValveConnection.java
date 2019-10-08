/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siemens.vsst.data.enumeration.valves;

/**
 * Valve Connection Enumeration
 * @author michael
 */
public enum ValveConnection
{
    Unknown,
    AFxUM,
    FLxFL,
    FRxFR,
    FLANGED,
    FLANGED_LUG_THREAD,
    FLARED,
    FxF,
    FxUF,
    FxUM,
    MxM,
    NPT,
    NPT_FLANGE,
    UFxUF,
    UMxUM,
    WELD_FLANGE,
    LUG,
    WAFER;

    public static ValveConnection fromInt(int type)
    {
        switch (type)
        {
            case 1:
                return AFxUM;

            case 2:
                return FLxFL;

            case 3:
                return FRxFR;

            case 4:
                return FLANGED;

            case 5:
                return FLANGED_LUG_THREAD;

            case 6:
                return FLARED;

            case 7:
                return FxF;

            case 8:
                return FxUF;

            case 9:
                return FxUM;

            case 10:
                return MxM;

            case 11:
                return NPT;

            case 12:
                return NPT_FLANGE;

            case 13:
                return UFxUF;

            case 14:
                return UMxUM;

            case 15:
                return WELD_FLANGE;

            case 16:
                return LUG;

            case 17:
                return WAFER;
        }

        return Unknown;
    }

    public static ValveConnection fromString(String name)
    {
        if (name.equalsIgnoreCase("AFxUM")) {
            return AFxUM;
        }
        else if (name.equalsIgnoreCase("FLxFL")) {
            return FLxFL;
        }
        else if (name.equalsIgnoreCase("FRxFR")) {
            return FRxFR;
        }
        else if (name.equalsIgnoreCase("Flanged")) {
            return FLANGED;
        }
        else if (name.equalsIgnoreCase("Flanged Lug Thread")) {
            return FLANGED_LUG_THREAD;
        }
        else if (name.equalsIgnoreCase("Flared")) {
            return FLARED;
        }
        else if (name.equalsIgnoreCase("FxF")) {
            return FxF;
        }
        else if (name.equalsIgnoreCase("FxUF")) {
            return FxUF;
        }
        else if (name.equalsIgnoreCase("FxUM")) {
            return FxUM;
        }
        else if (name.equalsIgnoreCase("MxM")) {
            return MxM;
        }
        else if (name.equalsIgnoreCase("NPT")) {
            return NPT;
        }
        else if (name.equalsIgnoreCase("NPT-Flanged")) {
            return NPT_FLANGE;
        }
        else if (name.equalsIgnoreCase("UFxUF")) {
            return UFxUF;
        }
        else if (name.equalsIgnoreCase("UMxUM")) {
            return UMxUM;
        }
        else if (name.equalsIgnoreCase("Weld-Flanged")) {
            return WELD_FLANGE;
        }
        else if (name.equalsIgnoreCase("Lug")) {
            return LUG;
        }
        else if (name.equalsIgnoreCase("Wafer")) {
            return WAFER;
        }
       
        return Unknown;
    }

    public int toFlag()
    {
        switch (this)
        {
            case Unknown:
                return 0;

            case AFxUM:
                return 1;

            case FLxFL:
                return 2;

            case FRxFR:
                return 3;

            case FLANGED:
                return 4;

            case FLANGED_LUG_THREAD:
                return 5;

            case FLARED:
                return 6;

            case FxF:
                return 7;

            case FxUF:
                return 8;

            case FxUM:
                return 9;

            case MxM:
                return 10;

            case NPT:
                return 11;

            case NPT_FLANGE:
                return 12;

            case UFxUF:
                return 13;

            case UMxUM:
                return 14;

            case WELD_FLANGE:
                return 15;

            case LUG:
                return 16;

            case WAFER:
                return 17;
        }

        return 0;
    }
}
