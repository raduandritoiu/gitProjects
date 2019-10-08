/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siemens.vsst.data.enumeration.valves;

/**
 * Valve Type Enumeration
 * @author michael
 */
public enum ValveType
{
    Unknown,
    Ball,
    Globe,
    Zone,
    Magnetic,
    Butterfly,
    PICV;

    public static ValveType fromInt(int type)
    {
        switch (type)
        {
            case 1:
                return Ball;

            case 2:
                return Globe;

            case 3:
                return Zone;

            case 4:
                return Magnetic;

            case 5:
                return Butterfly;
            
            case 6:
                return PICV;

        }

        return Unknown;
    }

    public static ValveType fromString(String name)
    {
        if (name.equalsIgnoreCase("Ball")) {
            return Ball;
        }
        else if (name.equalsIgnoreCase("Butterfly")) {
            return Butterfly;
        }
        else if (name.equalsIgnoreCase("Globe")) {
            return Globe;
        }
        else if (name.equalsIgnoreCase("Magnetic")) {
            return Magnetic;
        }
        else if (name.equalsIgnoreCase("Zone")) {
            return Zone;
        }
        else if (name.equalsIgnoreCase("PICV")) {
            return PICV;
        }

        return Unknown;
    }

    public int toFlag()
    {
        switch (this)
        {
            case Ball:
                return 1;

            case Globe:
                return 2;

            case Zone:
                return 3;

            case Magnetic:
                return 4;

            case Butterfly:
                return 5;
                
            case PICV:
                return 6;
        }

        return 0;
    }
}
