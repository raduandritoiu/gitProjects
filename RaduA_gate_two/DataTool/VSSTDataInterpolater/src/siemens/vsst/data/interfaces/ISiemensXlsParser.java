/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.interfaces;

import com.extentech.ExtenXLS.RowHandle;
import java.io.IOException;
import java.util.HashMap;
import siemens.vsst.data.models.AbstractSiemensProduct;


/**
 *
 * @author radua
 */
public interface ISiemensXlsParser {
    /**
     * Print parsing errors and output as it runs.
	 * 
	 * @return enableParsing.
     */
	boolean enableParsingOutput();
	
	
	/**
     * Returns the cell (column) number that contains the product Part Number
	 * 
	 * @return cell/column number.
     */
	int getPartNumberCell();
	
	
    /**
     * Returns how many rows must be skipped from the beginning.
	 * 
	 * @return number of rows.
     */
	int getSkipRows();
	
	
	/**
	 * Parse an XLS file.
	 * 
	 * @param filePath				Path to XLS File
	 * @param currentProductMap		Hash map with all current Products
	 * @param isAbstract			If the parsing is abstract => it can add new Products (creates)
	 * @throws IOException 
	 */
	void parseXLSFile(String filePath, HashMap<String, AbstractSiemensProduct> currentProductMap, boolean isAbstract) throws IOException;
	
	
    /**
     * Parse Data Row.
     *
     * @param row RowHandle Reference (row from the XLS)
     * @param product Current Product Reference
     */
    void parseXlsRow(RowHandle row, AbstractSiemensProduct product);
	
	
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
