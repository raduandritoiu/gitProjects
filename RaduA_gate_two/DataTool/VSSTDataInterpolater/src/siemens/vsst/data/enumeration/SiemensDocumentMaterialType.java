    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siemens.vsst.data.enumeration;

/**
 * Document Type Enumeration
 * @author michael
 */
public enum SiemensDocumentMaterialType
{
    Unknown,
    Image,
    Thumbnail,
    SubmittalSheet,
    TechnicalBulletin,
    InstallationInstructions,
    DataSheet,
    Misc;

    public static SiemensDocumentMaterialType getFromDescription(String desc)
    {
        if (desc.equalsIgnoreCase("detail"))
            return Image;
        else if (desc.equalsIgnoreCase("thumbnail"))
            return Thumbnail;
        else if (desc.equalsIgnoreCase("Installation Instructions"))
            return InstallationInstructions;
        else if (desc.equalsIgnoreCase("Data Sheet for Product"))
            return DataSheet;
        return Misc;
    }

    public static SiemensDocumentMaterialType getFromPartInfo(String partInfo)
    {
        try {
//            String[] parts      = partInfo.split("-");
//            String descString   = parts[0];
//            if (!descString.substring(0, 1).matches("[0-9]")) {
//                // First Digit is a Character, must convert to decodable type.
//                String subDescriminant = parts[1];
//                if (subDescriminant.substring(0, 3).equalsIgnoreCase("P25"))
//                    descString  = "023_125";
//            }
            
            String descString = partInfo.substring(0, 7); // Get first 7 characeters
            if (descString.equalsIgnoreCase("020_129"))
                return InstallationInstructions;
            else if (descString.equalsIgnoreCase("023_154"))
                return SubmittalSheet;
            else if (descString.equalsIgnoreCase("023_155") || descString.equalsIgnoreCase("023_125") || descString.equalsIgnoreCase("023_CA1"))
                return TechnicalBulletin;
            
//            Recent changes to the Step Extract caused us to no longer be able to user an integrer to compare. 7-24-2012
//            int descriminant    = Integer.parseInt(descString);
//            switch (descriminant) {
//                case 154:
//                {
//                    return SubmittalSheet;
//                }
//
//                case 125: // Magnetic
//                case 155:
//                {
//                    return TechnicalBulletin;
//                }
//
//                case 129:
//                {
//                    return InstallationInstructions;
//                }
//            }
        }
        catch (Exception ex) {
            // Error Parsing Document Type
        }

        return Unknown;
    }
}
