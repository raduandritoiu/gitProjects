/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siemens.vsst.data.enumeration.valves;

/**
 * Valve Medium Enumeration
 * @author michael
 */
public enum ValveMedium
{
    UNKNOWN,
    WATER,
    STEAM,
    GLYCOL;

    public static ValveMedium fromInt(int type)
    {
        switch (type)
        {
            case 2:
                return WATER;
            
            case 4:
                return GLYCOL;

            case 8:
                return STEAM;
        }

        return UNKNOWN;
    }

    public int toFlag()
    {
        switch (this)
        {
            case UNKNOWN:
                return 0;

            case WATER:
                return 2;

            case GLYCOL:
                return 4;

            case STEAM:
                return 8;
        }

        return 0;
    }
}
