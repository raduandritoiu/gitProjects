/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siemens.vsst.data.enumeration.valves;

/**
 * Valve Trim Enumeration
 * @author michael
 */
public enum ValveTrim
{
    Unknown,
    Brass,
    BrassNickel,
    Bronze,
    StainlessSteel,
    ChromeBrass;

    public static ValveTrim fromInt(int type)
    {
        switch (type)
        {
            case 1:
                return Brass;

            case 2:
                return BrassNickel;

            case 3:
                return Bronze;

            case 4:
                return StainlessSteel;

            case 6:
                return ChromeBrass;
        }

        return Unknown;
    }

    public static ValveTrim fromString(String name)
    {
        if (name.equalsIgnoreCase("Brass")) {
            return Brass;
        }
        else if (name.equalsIgnoreCase("Brass/Nickel")) {
            return BrassNickel;
        }
        else if (name.equalsIgnoreCase("Bronze")) {
            return Bronze;
        }
        else if (name.equalsIgnoreCase("Stainless Steel")) {
            return StainlessSteel;
        }
        else if (name.equalsIgnoreCase("Chrome/Brass")) {
            return ChromeBrass;
        }

        return Unknown;
    }

    public int toFlag()
    {
        switch (this)
        {
            case Brass:
                return 1;

            case BrassNickel:
                return 2;

            case Bronze:
                return 3;

            case StainlessSteel:
                return 4;

            case ChromeBrass:
                return 6;
        }

        return 0;
    }
}
