/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.parsers.dbs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.xBaseJ.DBF;
import org.xBaseJ.xBaseJException;
import org.xBaseJ.fields.CharField;

import siemens.vsst.data.interfaces.ISiemensDataParser;
import siemens.vsst.data.models.AbstractSiemensProduct;
import siemens.vsst.data.models.SiemensAssembly;
import siemens.vsst.data.parsers.utils.VSSTLogger;

/**
 *
 * @author User
 */
public abstract class GenericDBParser implements ISiemensDataParser 
{
	private boolean enableParsingOutput = false;
	
	
    /**
     * Print parsing errors and output as it runs.
	 * 
	 * @return cell/column number.
     */
	public boolean enableParsingOutput() {
		return enableParsingOutput;
	}

	
	/**
	 * Parse DBF File and Instantiates an Abstract Siemens Product from the DBF
	 * 
	 * @param filePath              Path to DBF File
	 * @param currentProductMap		Hash map with all current Products
   * @param isAbstract            Abstract Flag: if TRUE it will only update fields for existing products, if FALSE it will add new products
	 * @throws xBaseJException
	 * @throws IOException 
	 */
	@Override
	public void parseDbFile(String filePath, HashMap<String, AbstractSiemensProduct> currentProductMap, boolean isAbstract) throws xBaseJException, IOException
	{
		DBF db = new DBF(filePath, DBF.READ_ONLY);	
		int recordCnt = db.getRecordCount();
		
		// could remove these variables and any code attached to them 
		// - they are for testing purposes ONLY	
		int prodTotalCnt		= recordCnt;
		int prodFoundCnt		= 0;
		int prodNotFoundCnt		= 0;
		int prodCreatedCnt		= 0;
		int prodInitializedCnt	= 0;
		int valvesFromProdCnt	= 0;
		int actuatorsFromProdCnt = 0;
		int subProdCnt			= 0;
		HashMap<String, Integer> already = new HashMap<String, Integer>();
		
		// Loop through all records
		int currIdx = 0; // because the loop takes too leng, this is to put breakpoints and monitor progress
		while (db.getCurrentRecordNumber() < recordCnt) {
			db.read();
			currIdx = currIdx + 1;
			
			// Skip Records marked for deletion
			if (db.deleted())
				continue;
			
			// read PART NUMBER
			CharField partNumberField   = (CharField) db.getField("PARTNO");
			String partNumber           = partNumberField.get().trim();
			
            if (shouldSkip(partNumber, db))
                continue;
			
			// Find all existing products already added with this part number
			ArrayList<AbstractSiemensProduct> prodFoundList = new ArrayList<AbstractSiemensProduct>();
			Iterator<AbstractSiemensProduct> existingCursor = currentProductMap.values().iterator();
			while (existingCursor.hasNext()) {
				AbstractSiemensProduct existingProduct = existingCursor.next();
				if (existingProduct.getActualPartNumber().equalsIgnoreCase(partNumber)) {
					prodFoundList.add(existingProduct);
				}
			}
			
			// If we haven't found any products with the part number, 
			// then create and add an empty product to parts
			if (prodFoundList.isEmpty()) {
				prodNotFoundCnt ++;
				
				// if the parsing is abstract we do not add products, only set properties
				// so if no previous items were found, we should skip this row
				if (isAbstract) {
					continue;
				}
				
				AbstractSiemensProduct product = null;
				try {
					product = getItemForPart(partNumber);
					currentProductMap.put(partNumber, product);
					prodFoundList.add(product);
					prodCreatedCnt ++;
				}
				catch (Exception ex) {
					// Error Instantiating New Product
					if (enableParsingOutput()) {
						VSSTLogger.Warning("Error Instantiating New Product " + partNumber);
					}
				}

				if (product == null) {
					continue;
				}
			}
			else {
				prodFoundCnt ++;
				if (!isAbstract) {
					int a = 1;
					if (already.containsKey(partNumber))
						a += already.get(partNumber);
					already.put(partNumber, a);
					if (enableParsingOutput()) {
						VSSTLogger.Debug("-----> WOW found Valves/Assembly " + partNumber + "  (" + prodFoundList.size() + ")");
					}
				}
			}
			
			// set the properties from the current row to the product (products - but I doubt there are many)
			Iterator<AbstractSiemensProduct> prodFoundCursor = prodFoundList.iterator();
			while (prodFoundCursor.hasNext()) {
				AbstractSiemensProduct product = prodFoundCursor.next();
				try {                                        
					// Parse the current row => set properties values on the product
					parseDataRow(db, product);
					prodInitializedCnt ++;
	
					// if the product has a sub-product (after initialization) then add the sub-product
					if (!isAbstract && (product.getSubProduct() != null)) {
						currentProductMap.put(product.getSubProduct().getPartNumber(), product.getSubProduct());
						subProdCnt ++;
					}
				}
				catch (Exception pEx) {
					// Error Parsing Data
					VSSTLogger.Warning("Error Parsing Data For " + partNumber + "  :" + pEx.getMessage());
					continue;
				}

				// Add Product to Stack
				// If the product is an Assembly then add the Valve, the Actuator and Actuator subProduct (if any)
				if (product instanceof SiemensAssembly) {
					// again add products (Valves, Actuators) only if not abstract
					if (!isAbstract) {
						SiemensAssembly assembly = (SiemensAssembly) product;
						currentProductMap.put(assembly.getValve().getPartNumber(), assembly.getValve());
						currentProductMap.put(assembly.getActuator().getPartNumber(), assembly.getActuator());						
						valvesFromProdCnt ++;
						actuatorsFromProdCnt ++;
						if (assembly.getActuator().getSubProduct() != null) {
							currentProductMap.put(assembly.getActuator().getSubProduct().getPartNumber(), assembly.getActuator().getSubProduct());
							subProdCnt ++;
						}
					}
				}
			}
		}
		
		// test print the numbers
		VSSTLogger.Info("--- DB has num rows: " + recordCnt);
		VSSTLogger.Info("-----> Total products " + prodTotalCnt);
		VSSTLogger.Info("-----> found      " + prodFoundCnt);
		VSSTLogger.Info("-----> not found  " + prodNotFoundCnt);
		VSSTLogger.Info("-----> created    " + prodCreatedCnt);
		VSSTLogger.Info("-----> initialize " + prodInitializedCnt);
		VSSTLogger.Info("-----> valves from prod    " + valvesFromProdCnt);
		VSSTLogger.Info("-----> actuators from prod " + actuatorsFromProdCnt);
		VSSTLogger.Info("-----> sub prods added     " + subProdCnt);
		
		db.close();
	}
	
	
    /**
     * Parse Data Row
     *
     * @param db DBF Reference (Moved to current row)
     * @param product Current Product Reference
     *
     * @throws xBaseJException
     */
    @Override
	public void parseDataRow(DBF db, AbstractSiemensProduct product) throws xBaseJException
	{}
	
	
    /**
     * Get Class for item part number
     *
     * @param partNumber
     *
     * @return Derivative of  class
     * @throws Exception
     */
	@Override
	public AbstractSiemensProduct getItemForPart(String partNumber) throws Exception {
	  throw new Exception("Error Instantiating New Product " + partNumber);
	}
	
	
	/**
	 * This is a weird one.
	 * This is because the new VALVE DB file from Siemens is NO GOOD
	 */
	protected boolean shouldSkip(String partNumber, DBF db) throws xBaseJException, IOException {
	    return false;
	}
}
