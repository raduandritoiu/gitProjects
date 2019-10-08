/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siemens.vsst.data.enumeration.valves;

/**
 * Valve Application Enumeration
 * @author michael
 */
public enum ValveApplication
{
    Unknown,
    Mixing,
    Diverting;

    public static ValveApplication fromInt(int type)
    {
        switch (type)
        {
            case 2:
                return Mixing;
                
            case 4:
                return Diverting;
        }

        return Unknown;
    }

    public int toFlag()
    {
        switch (this)
        {
            case Unknown:
                return 0;

            case Mixing:
                return 2;

            case Diverting:
                return 4;
        }

        return 0;
    }
}
