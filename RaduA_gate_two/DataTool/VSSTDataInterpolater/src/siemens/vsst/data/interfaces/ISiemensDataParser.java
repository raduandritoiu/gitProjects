/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.interfaces;

import java.io.IOException;
import java.util.HashMap;
import org.xBaseJ.DBF;
import org.xBaseJ.xBaseJException;
import siemens.vsst.data.models.AbstractSiemensProduct;

/**
 * Siemens Data parser Interface
 *
 * @author michael
 */
public interface ISiemensDataParser
{
    /**
     * Print parsing errors and output as it runs.
	 * 
	 * @return cell/column number.
     */
	boolean enableParsingOutput();
	
	/**
	 * Parse DBF File and Instantiates an Abstract Siemens Product from the DBF
	 * 
	 * @param filePath              Path to DBF File
	 * @param currentProductMap		Hash map with all current Products
	 * @param isAbstract            Abstract Flag: if TRUE it will only update fields for existing products, if FALSE it will add new products
	 * @throws xBaseJException
	 * @throws IOException 
	 */
	void parseDbFile(String filePath, HashMap<String, AbstractSiemensProduct> currentProductMap, boolean isAbstract) throws xBaseJException, IOException;
	
	
    /**
     * Parse Data Row
     *
     * @param db DBF Reference (Moved to current row)
     * @param product Current Product Reference
     *
     * @throws xBaseJException
     */
    void parseDataRow(DBF db, AbstractSiemensProduct product) throws xBaseJException;
	
	
    /**
     * Get Class for item part number
     *
     * @param partNumber
     *
     * @return Derivative of  class
     * @throws Exception
     */
    AbstractSiemensProduct getItemForPart(String partNumber) throws Exception;
}
