/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siemens.vsst.data.enumeration.valves;

/**
 * Valve Normal State Enumeration
 * @author michael
 */
public enum ValveNormalState
{
    Unknown,
    NonFailSafe,
    NormallyOpen,
    NormallyClosed;


    public static ValveNormalState fromInt(int type)
    {
        switch (type) {
            case 1:
                return NormallyOpen;
                
            case 2:
                return NormallyClosed;
        }

        return Unknown;
    }

    public static ValveNormalState fromString(String name) 
    {
        if (name == null)
            return Unknown;

        if (name.equalsIgnoreCase("NO")) {
            return NormallyOpen;
        }
        else if (name.equalsIgnoreCase("NC")) {
            return NormallyClosed;
        }

        return Unknown;
    }

    public String toString()
    {
        switch (this) {
            case NormallyOpen:
                return "NO";

            case NormallyClosed:
                return "NC";
        }

        return "U";
    }

    public int toFlag()
    {
        switch (this) {
            case NonFailSafe:
                return -1;
                
            case NormallyOpen:
                return 1;

            case NormallyClosed:
                return 2;
        }

        return 0;
    }
}
