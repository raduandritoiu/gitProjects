/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siemens.vsst.data.enumeration.valves;

/**
 * Valve Flow Characteristics Enumeration
 * @author michael
 */
public enum FlowCharacteristics
{
    Unknown,
    EqualPercent,
    ModifiedEqualPercent,
    Linear,
    QuickOpening,
    Linear__EqualPercent;

    public static FlowCharacteristics fromInt(int type)
    {
        switch (type)
        {
            case 1:
                return EqualPercent;
                
            case 2:
                return ModifiedEqualPercent;
                
            case 3:
                return Linear;
                
            case 4:
                return QuickOpening;
                
            case 5:
                return Linear__EqualPercent;
        }

        return Unknown;
    }

    public static FlowCharacteristics fromString(String name)
    {
        if (name.equalsIgnoreCase("Equal %")) {
            return EqualPercent;
        }
        else if (name.equalsIgnoreCase("Linear")) {
            return Linear;
        }
        else if (name.equalsIgnoreCase("Modified Equal %")) {
            return ModifiedEqualPercent;
        }
        else if (name.equalsIgnoreCase("Quick Opening")) {
            return QuickOpening;
        }
        else if (name.equalsIgnoreCase("Linear,Equal %")) {
            return Linear__EqualPercent;
        }
        else if (name.equalsIgnoreCase("")) {
            return Unknown;
        }

        return Unknown;
    }

    public int toFlag()
    {
        switch (this)
        {
            case EqualPercent:
                return 1;

            case ModifiedEqualPercent:
                return 2;

            case Linear:
                return 3;

            case QuickOpening:
                return 4;

            case Linear__EqualPercent:
                return 5;
        }

        return 0;
    }
}
