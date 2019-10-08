package siemens.vsst.data.models;

import siemens.vsst.data.enumeration.SiemensDocumentMaterialType;

/**
 * Siemens Document Object Model
 * 
 * @author michael
 */
public class SiemensDocumentMaterial
{
    public static String IMAGE_BASE_PATH = "https://mall.industry.siemens.com/zu/assets/"; // Siemens' asset base path.
    public static String DOC_BASE_PATH   = "http://www.siemens.com/download?"; // Siemens' asset base path.

    private SiemensDocumentMaterialType materialType;   // Document Type
    private String name;                                // Document Name
    private String description;                         // Document Description
    private String path;                                // Document Location on server
    private String fullPath;                            // Document Full Location
    private boolean isImage;                            // Is Document an Image flag

    public SiemensDocumentMaterial()
    {}
	
    public void setName(String nname) {
        try {
            if (nname != null && nname.charAt(3) == '_')
                nname = nname.substring(4);
        }
        catch (Exception ex) {
            // Error removing Prefix from name.
        }
        name = nname;
    }
    public String getName() {
        return name;
    }
    
    public void setPath(String nPath) {
        path = nPath;
    }
    public String getPath() {
        return path;
    }
    
    public void setFullPath(String nFullPath) {
        fullPath = nFullPath;
    }
    public String getFullPath() {
        return fullPath;
    }
    
    public void setDescription(String ndescription) {
        description = ndescription;
    }
    public String getDescription() {
        return description;
    }
	
    public void setIsImage(boolean nIsImage) {
        isImage = nIsImage;
    }
    public boolean isIsImage() {
        return isImage;
    }
    
    public void setMaterialType(SiemensDocumentMaterialType nMaterialType) {
        materialType = nMaterialType;
        if ((nMaterialType == SiemensDocumentMaterialType.Image) || (nMaterialType == SiemensDocumentMaterialType.Thumbnail)) {
            setIsImage(true);
            setFullPath(IMAGE_BASE_PATH + path);
        }
        else if ((nMaterialType == SiemensDocumentMaterialType.SubmittalSheet) || (nMaterialType == SiemensDocumentMaterialType.TechnicalBulletin) ||
                 (nMaterialType == SiemensDocumentMaterialType.InstallationInstructions) || (nMaterialType == SiemensDocumentMaterialType.DataSheet)) {
            if (path.indexOf(".pdf") == (path.length() - 4)) {
                setFullPath(DOC_BASE_PATH + path.substring(0, path.length()-4));
            }
            else {
                setFullPath(DOC_BASE_PATH + path);
            }
        }
    }
    public SiemensDocumentMaterialType getMaterialType() {
        return materialType;
    }
}
