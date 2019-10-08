/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siemens.vsst.data.enumeration.actuators;

/**
 * Actuator Positioner Enumeration
 * @author michael
 */
public enum ActuatorPositioner
{
    None,
    Standard,
    Pneumatic,
    ElectroPneumatic,
    EPValve24V,
    EPValve120V;

    public static ActuatorPositioner fromInt(int type)
    {
        switch (type)
        {
            case 0:
                return None;

            case 1:
                return Standard;

            case 2:
                return Pneumatic;

            case 3:
                return ElectroPneumatic;

            case 4:
                return EPValve24V;

            case 5:
                return EPValve120V;

        }

        return None;
    }

    public int toFlag()
    {
        switch (this)
        {
            case None:
                return 0;

            case Standard:
                return 1;

            case Pneumatic:
                return 2;

            case ElectroPneumatic:
                return 3;

            case EPValve24V:
                return 4;

            case EPValve120V:
                return 5;
        }

        return 0;
    }

}
