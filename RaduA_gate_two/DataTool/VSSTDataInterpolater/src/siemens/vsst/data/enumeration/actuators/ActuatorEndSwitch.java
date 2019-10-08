/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siemens.vsst.data.enumeration.actuators;

/**
 * Actuator End Switch Enumeration
 * @author michael
 */
public enum ActuatorEndSwitch
{
    None,
    Electric,
    Pneumatic,
    Potentiometer;

    public static ActuatorEndSwitch fromInt(int type)
    {
        switch (type)
        {
            case 0:
                return None;

            case 1:
                return Electric;

            case 2:
                return Pneumatic;

            case 3:
                return Potentiometer;
        }

        return None;
    }

    public int toFlag()
    {
        switch (this)
        {
            case None:
                return 0;

            case Electric:
                return 1;

            case Pneumatic:
                return 2;

            case Potentiometer:
                return 3;
        }

        return 0;
    }


}
