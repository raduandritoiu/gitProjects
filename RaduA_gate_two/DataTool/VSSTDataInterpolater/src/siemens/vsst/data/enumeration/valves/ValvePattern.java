/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siemens.vsst.data.enumeration.valves;

/**
 * Valve Pattern Enumeration
 * @author michael
 */
public enum ValvePattern
{
    Unknown,
    TwoWay,
    ThreeWay,
    Both,
    SixWay;

    public static ValvePattern fromInt(int type)
    {
        switch (type)
        {
            case 1:
                return TwoWay;

            case 2:
                return ThreeWay;
                
            case 3:
                return Both;
                    
            case 4:
                return SixWay;
        }

        return Unknown;
    }

    public static ValvePattern fromString(String name)
    {
        if (name.equalsIgnoreCase("2 Way")) {
            return TwoWay;
        }
        else if (name.equalsIgnoreCase("3 Way")) {
            return ThreeWay;
        }
        else if (name.equalsIgnoreCase("2 Way,3 Way")) {
            return Both;
        }
        else if (name.equalsIgnoreCase("6 Way")) {
            return SixWay;
        }
        else if (name.equalsIgnoreCase("")) { //6 Way
            return Unknown;
        }
        
        return Unknown;
    }

    public int toFlag()
    {
        switch (this)
        {
            case TwoWay:
                return 1;

            case ThreeWay:
                return 2;

            case Both:
                return 3;

            case SixWay:
                return 4;
        }

        return 0;
    }

}
