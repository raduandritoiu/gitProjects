/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siemens.vsst.data.enumeration.actuators;

/**
 * Actuator Motor Type Enumeration
 * @author michael
 */
public enum ActuatorMotorType
{
    Unknown,
    Electric,
    Pneumatic;


    public static ActuatorMotorType fromInt(int type)
    {
        switch (type)
        {
            case 1:
            {
                return Electric;
            }
            case 2:
            {
                return Pneumatic;
            }
        }

        return Unknown;
    }

    public static ActuatorMotorType fromString(String name)
    {
        if (name.equalsIgnoreCase("Electric")) {
            return Electric;
        }
        else if (name.equalsIgnoreCase("Pneumatic")) {
            return Pneumatic;
        }
        
        return Unknown;
    }

    public int toFlag()
    {
        switch (this)
        {
            case Electric:
                return 1;

            case Pneumatic:
                return 2;
        }

        return 0;
    }
}
