/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siemens.vsst.data.enumeration.actuators;

/**
 * Actuator Safety Function Enumeration
 * @author michael
 */
public enum ActuatorSpring
{
    Unknown,
    SpringReturn,
    NonSpringReturn;

    public static ActuatorSpring fromInt(int type)
    {
        switch (type)
        {
            case 1:
            {
                return SpringReturn;
            }
            case 2:
            {
                return NonSpringReturn;
            }
        }

        return Unknown;
    }

    public static ActuatorSpring fromString(String name)
    {
        if (name == null)
            return Unknown;

        String determinant  = name;

        if (name.contains("-"))
        {
            determinant = name.substring(name.indexOf("-") + 1);
        }

        if (determinant.equalsIgnoreCase("SR"))
        {
            return SpringReturn;
        }
        else
        if (determinant.equalsIgnoreCase("NSR"))
        {
            return NonSpringReturn;
        }

        return Unknown;
    }

    public int toFlag()
    {
        switch (this)
        {
            case SpringReturn:
                return 1;

            case NonSpringReturn:
                return 2;
        }

        return 0;
    }
}
