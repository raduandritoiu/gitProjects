package siemens.vsst.data.parsers.dbs;

import org.xBaseJ.DBF;
import org.xBaseJ.fields.CharField;
import org.xBaseJ.fields.LogicalField;
import org.xBaseJ.fields.NumField;
import org.xBaseJ.xBaseJException;
import siemens.vsst.data.io.DBFReader;
import siemens.vsst.data.models.AbstractSiemensProduct;

/**
 * Product DB Parser
 *
 * Parses Product DB File
 * 
 * @author michael
 */
public class ProductDBParser extends GenericDBParser
{
	@Override
    public void parseDataRow(DBF db, AbstractSiemensProduct product) throws xBaseJException {
		// read ALL THE FIELDS from the DBF
        CharField manufacturerField		= (CharField) db.getField("MANUFACT");
        NumField priceOneField			= (NumField) db.getField("MATER_COST");
        LogicalField obsoleteField		= (LogicalField) db.getField("OBSOLETE");
        //CharField partNumberField		= (CharField) db.getField("PARTNO"); This is retrieved from VAlves.DBF
        CharField descriptionField		= (CharField) db.getField("PROD_DES2");
        CharField vendorField			= (CharField) db.getField("VENDOR");
        CharField modifiedField			= (CharField) db.getField("LAST_MOD");
        CharField sapPartNumberField    = (CharField) db.getField("SBTPARTNO");
		
		// set the FIELDS on the product
        product.setManufacturer(manufacturerField.get().trim());
        product.setPrice(Float.parseFloat(priceOneField.get().trim()));
        product.setObsolete(obsoleteField.getBoolean());
        //product.setPartNumber(partNumberField.get().trim()); This is retrieved from VAlves.DBF (Do not overwrite, this will break the sub/constructed parts)
        product.setSapPartNumber(sapPartNumberField.get().trim());
        product.setDescription(descriptionField.get().trim());
        product.setVendor(vendorField.get().trim());
        
        // Last Modified Date
        StringBuffer dateStringBuffer   = new StringBuffer(modifiedField.get().trim());
        dateStringBuffer.insert(6, '/');
        dateStringBuffer.insert(4, '/');
		
        try {
            product.setLastModified(DBFReader.dateFormatter.parse(dateStringBuffer.toString()));
        }
        catch (Exception ex) {
            // Error Parsing Date
        }
		
		product.setProductProcessed(true);
    }
	
	
	@Override
    public AbstractSiemensProduct getItemForPart(String partNumber) throws Exception {
        throw new Exception("Error Abstact Parser cannot instantiate types.");
    }
	
	
    public static ProductDBParser newInstance() {
        return new ProductDBParser();
    }
}
