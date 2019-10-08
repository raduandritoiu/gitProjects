/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siemens.vsst.data.enumeration.valves;

/**
 * Packing Material Enumeration
 * @author michael
 */
public enum PackingMaterial
{
    Unknown,
    BUNA,
    EP_O_RING,
    EP_QUAD_RING,
    EP_V_RING,
    EPDM,
    EPDM_O_RING,
    TEFLON_V_RING,
    VITON_O_RING;

    public static PackingMaterial fromInt(int type)
    {
        switch (type)
        {
            case 1:
                return BUNA;

            case 2:
                return EP_O_RING;

            case 3:
                return EP_QUAD_RING;

            case 4:
                return EP_V_RING;

            case 5:
                return EPDM;

            case 6:
                return EPDM_O_RING;

            case 7:
                return TEFLON_V_RING;

            case 8:
                return VITON_O_RING;
        }

        return Unknown;
    }

    public static PackingMaterial fromString(String name)
    {
        if (name.equalsIgnoreCase("Buna")) {
            return BUNA;
        }
        else if (name.equalsIgnoreCase("EP O-Ring")) {
            return EP_O_RING;
        }
        else if (name.equalsIgnoreCase("EP Quad Ring")) {
            return EP_QUAD_RING;
        }
        else if (name.equalsIgnoreCase("EP V-Ring")) {
            return EP_V_RING;
        }
        else if (name.equalsIgnoreCase("EPDM")) {
            return EPDM;
        }
        else if (name.equalsIgnoreCase("EPDM O-Ring")) {
            return EPDM_O_RING;
        }
        else if (name.equalsIgnoreCase("Teflon V-Ring")) {
            return TEFLON_V_RING;
        }
        else if (name.equalsIgnoreCase("Viton O-Ring")) {
            return VITON_O_RING;
        }

        return Unknown;
    }

    public int toFlag()
    {
        switch (this)
        {
            case Unknown:
                return 0;

            case BUNA:
                return 1;

            case EP_O_RING:
                return 2;

            case EP_QUAD_RING:
                return 3;

            case EP_V_RING:
                return 4;

            case EPDM:
                return 5;

            case EPDM_O_RING:
                return 6;

            case TEFLON_V_RING:
                return 7;

            case VITON_O_RING:
                return 8;
        }

        return 0;
    }
}
