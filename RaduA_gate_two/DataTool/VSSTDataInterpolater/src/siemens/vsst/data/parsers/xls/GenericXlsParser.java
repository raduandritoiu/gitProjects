/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.parsers.xls;

import com.extentech.ExtenXLS.CellHandle;
import com.extentech.ExtenXLS.RowHandle;
import com.extentech.ExtenXLS.WorkBookHandle;
import com.extentech.ExtenXLS.WorkSheetHandle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import siemens.vsst.data.interfaces.ISiemensXlsParser;
import siemens.vsst.data.models.AbstractSiemensProduct;
import siemens.vsst.data.parsers.utils.VSSTLogger;

/**
 *
 * @author User
 */
public class GenericXlsParser implements ISiemensXlsParser
{
	private boolean enableParsingOutput = false;
	
	
    /**
     * Print parsing errors and output as it runs.
	 * 
	 * @return enableParsing.
     */
	public boolean enableParsingOutput() {
		return enableParsingOutput;
	}
	

	/**
     * Returns the cell (column) number that contains the product Part Number
	 * 
	 * @return cell/column number.
     */
	@Override
	public int getPartNumberCell() {
		return 0;
	}
	
	
    /**
     * Returns how many rows must be skipped from the beginning.
	 * 
	 * @return number of rows.
     */
	@Override
	public int getSkipRows() {
		return 1;
	}
	
	
	/**
	 * Parse a XLS file.
	 * 
	 * @param filePath 
	 * @param currentProductMap Current hash map with Products
	 * @param isAbstract If the parsing is abstract => it can add new Products (creates)
	 * @throws IOException 
	 */
	@Override
	public void parseXLSFile(String filePath, HashMap<String, AbstractSiemensProduct> currentProductMap, boolean isAbstract) throws IOException
	{
		WorkBookHandle book				= new WorkBookHandle(filePath);
		WorkSheetHandle[] sheets		= book.getWorkSheets();
		WorkSheetHandle sheet			= sheets[0];
		
		// get Rows and Cursor on Rows
		List<RowHandle> sheetRows		= Arrays.asList(sheet.getRows());
		Iterator<RowHandle> rowCursor   = sheetRows.iterator();
		// Skip first rows
		for (int i = 0; i < getSkipRows(); i++) {
			rowCursor.next();
		}
		
		// could delete these and any code regatding them
		// they are for testing and progress ony
		int prodTotalCnt		= sheet.getNumRows() - getSkipRows();
		int prodFoundCnt		= 0;
		int prodNotFoundCnt		= 0;
		int prodCreatedCnt		= 0;
		int prodInitializedCnt	= 0;
		int subProdCnt			= 0;
		
		// Loop through all records
		int currIdx = 0; // because the loop takes too long, this is to put breakpoints and monitor progress
		while (rowCursor.hasNext()) {
			// get the current row
			RowHandle row = rowCursor.next();
			currIdx = currIdx + 1;
			
			// get current product part number
			CellHandle[] cells  = row.getCells();
			
			String partNumber = null;
			try {
				CellHandle cell = cells[getPartNumberCell()];
				partNumber = cell.getStringVal().trim();
			}
			catch (Exception ex) {
				int xxx = 9;
			}
			if (partNumber == null) {
				continue;
			}
				
			// find all existing products with this part number
			ArrayList<AbstractSiemensProduct> prodFoundList = new ArrayList<AbstractSiemensProduct>();
			Iterator<AbstractSiemensProduct> existingCursor = currentProductMap.values().iterator();
			while (existingCursor.hasNext()) {
				AbstractSiemensProduct existingProduct = existingCursor.next();
				if (existingProduct.getPartNumber().equalsIgnoreCase(partNumber)) {
					prodFoundList.add(existingProduct);
				}
			}
			
			// If we haven't found any products with the part number, 
			// then create and add an empty product to parts
			if (prodFoundList.isEmpty()) {
				// if the parsing is abstract we do not add products, only set properties
				// so if no previous items were found, we should skip this row
				prodNotFoundCnt ++;
				
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
					// an error in creating a new product
				}
				
				if (product == null) {
					continue;
				}
			}
			else {
				prodFoundCnt ++;
				if (enableParsingOutput()) {
					VSSTLogger.Info("-----> WOW found existing product " + partNumber + "  (" + prodFoundList.size() + ")");
				}
			}
			
			// initialize all the products we found with the values n the current row
			Iterator<AbstractSiemensProduct> prodFoundCursor = prodFoundList.iterator();
			while (prodFoundCursor.hasNext()) {
				AbstractSiemensProduct product = prodFoundCursor.next();
				parseXlsRow(row, product);
				prodInitializedCnt ++;
				
				// if the product has a sub-product (after nitialization) then add the sub-product
				if (!isAbstract && (product.getSubProduct() != null)) {
					currentProductMap.put(product.getSubProduct().getPartNumber(), product.getSubProduct());
					subProdCnt ++;
				}
			}
		}
		
		// test print the numbers
		VSSTLogger.Info("--- DB has num rows: " + prodTotalCnt);
		VSSTLogger.Info("-----> Total products  " + prodTotalCnt);
		VSSTLogger.Info("-----> found           " + prodFoundCnt);
		VSSTLogger.Info("-----> not found       " + prodNotFoundCnt);
		VSSTLogger.Info("-----> created         " + prodCreatedCnt);
		VSSTLogger.Info("-----> initialize      " + prodInitializedCnt);
		VSSTLogger.Info("-----> sub prods added " + subProdCnt);
	}
	
	
    /**
     * Parse Data Row.
     *
     * @param row RowHandle Reference (row from the XLS)
     * @param product Current Product Reference
     */
	@Override
    public void parseXlsRow(RowHandle row, AbstractSiemensProduct product)
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
}
