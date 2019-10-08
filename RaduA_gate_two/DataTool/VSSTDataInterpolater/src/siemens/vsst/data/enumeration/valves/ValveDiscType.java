/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siemens.vsst.data.enumeration.valves;

/**
 * Valve Disc Type Enumeration
 * @author michael
 */
public enum ValveDiscType
{
    None,
    FullCut,
    UnderCut;

    public static ValveDiscType fromInt(int type)
    {
        switch (type)
        {
            case 0:
                return None;

            case 1:
                return FullCut;

            case 2:
                return UnderCut;
        }

        return None;
    }

    public int toFlag()
    {
        switch (this)
        {
            case None:
                return 0;

            case FullCut:
                return 1;

            case UnderCut:
                return 2;
        }

        return 0;
    }
}
