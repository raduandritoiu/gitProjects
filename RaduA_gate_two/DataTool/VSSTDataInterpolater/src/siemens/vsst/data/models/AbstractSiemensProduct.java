package siemens.vsst.data.models;

import java.util.ArrayList;
import java.util.Date;
import siemens.vsst.data.io.DBFReader;

/**
 * Abstract Siemens Product
 *
 * base class for all siemens product classes
 * contains common product properties.
 * 
 * @author michael
 */
public abstract class AbstractSiemensProduct
{
    protected String partNumber;        // Part Number (Can be a constructed part number)
    protected String actualPartNumber;  // Actual Part Number (Only used as a reference for parsing, is not persisted)
    protected String sapPartNumber;     // SAP Part Number (Used for Ordering)
    protected float price;              // Primary Price
    protected String manufacturer;      // Manufacturer
    protected String description;       // Description
    protected String vendor;            // Vendor
    protected Date lastModified;        // Last Modified
    protected boolean obsolete;         // Is Obsolete Flag
    protected boolean productProcessed; // Was processed from Product DB
    protected boolean processed;        // Is Product Procesed, Internally used by parser to prevent valve row's from overwriting assembly rows
	
	// these 4 properties are used by Valve related products
    protected String model;             // Model
    protected boolean virtualPart;      // Is Virtual Part
    protected boolean assemblyOnly;     // Is Assembly Only

    protected AbstractSiemensProduct parent; // Aseembly
    protected AbstractSiemensProduct subProduct; // Sub Product
    protected ArrayList<SiemensDocumentMaterial> documentMaterial;
	
    public int debugId = 0;
	
    public AbstractSiemensProduct()
    {}
	
    
    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
        
        if (actualPartNumber == null) {
            actualPartNumber = partNumber;
            int plusIndex = partNumber.indexOf('+');
            if (plusIndex > 0) {
                String subPartNumber = partNumber.substring(plusIndex + 1);
                if (DBFReader.productMap.containsKey(subPartNumber)) {
                    subProduct = DBFReader.productMap.get(subPartNumber);
                }
                else {
                    subProduct = new SiemensMiscProduct();
                    subProduct.setPartNumber(subPartNumber);
                }
                actualPartNumber = partNumber.substring(0, plusIndex);
            }
        }
    }
	
	
    public String getActualPartNumber() {
        return this.actualPartNumber;
    }
	
	
    public String getSapPartNumber() {
        if ((sapPartNumber == null) && (parent != null))
            return parent.sapPartNumber;
        return sapPartNumber;
    }
	
    public void setSapPartNumber(String sapPartNumber) {
        this.sapPartNumber = sapPartNumber;
    }
	
	
    public String getAbsoluteDescription() {
        return description;
    }
    
    public String getDescription() {
        if ((description == null) && (parent != null))
            return parent.description;
        return description;
    }
	
    public void setDescription(String description) {
        this.description = description;
    }
	
	
    public Date getLastModified() {
        if ((lastModified == null) && (parent != null))
            return parent.lastModified;
        return lastModified;
    }
	
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
	
	
    public String getManufacturer() {
        if ((manufacturer == null) && (parent != null))
            return parent.manufacturer;
        return manufacturer;
    }
	
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
	
	
    public boolean isObsolete() {
        // Caused non-obsolete parts to be flagged obsolete if their assembly was flagged obsolete
		//if (parent != null)
		//	return parent.obsolete;
        return obsolete;
    }
	
    public void setObsolete(boolean obsolete) {                
        this.obsolete = obsolete;
    }
	
	
    public float getPrice() {
        if (isVirtualPart())
           return -1;
        return price;
    }
	
    public void setPrice(float price) {
        this.price = price;
    }
	
	
    public String getSubProductPartNumber() {
        if (this.subProduct == null)
            return "";
        return this.subProduct.getPartNumber();
    }
	
    public float getSubProductPrice() {
        if (this.subProduct == null)
            return 0;
        return this.subProduct.getPrice();
    }
		
    public AbstractSiemensProduct getSubProduct() {
        return this.subProduct;
    }
	
    public void setSubProduct(AbstractSiemensProduct subProduct) {
        this.subProduct = subProduct;
    }
	
	
    public String getVendor() {
        if ((vendor == null) && (parent != null))
            return parent.vendor;
        return vendor;
    }
	
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
	
	
    public boolean hasDocumentMaterial() {
        return (this.documentMaterial != null);
    }	
	
    public ArrayList<SiemensDocumentMaterial> getDocumentMaterial() {
        if ((documentMaterial == null) && (parent != null))
            return parent.documentMaterial;
        return documentMaterial;
    }
	
    public void setDocumentMaterial(ArrayList<SiemensDocumentMaterial> documentMaterial) {
        this.documentMaterial = documentMaterial;
    }
	
	
    public String getModel() {
        if ((model == null) && (parent != null))
            return parent.model;
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    
	
    public boolean isAssemblyOnly() {
        return assemblyOnly;
    }
	
    public void setAssemblyOnly(boolean assemblyOnly) {
        this.assemblyOnly = assemblyOnly;
    }
	
	
    public boolean isVirtualPart() {
        return virtualPart;
    }
	
    public void setVirtualPart(boolean virtualPart) {
        this.virtualPart = virtualPart;
    }
	
	
    public AbstractSiemensProduct getParent() {
        return parent;
    }
	
	
    public boolean isProcessed() {
        return processed;
    }
	
    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
	
		
    public boolean isProductProcessed() {
        return productProcessed;
    }
	
    public void setProductProcessed(boolean value) {
        productProcessed = value;
    }
}
