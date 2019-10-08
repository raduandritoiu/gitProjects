/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.parsers.xls;

import com.extentech.ExtenXLS.CellHandle;
import com.extentech.ExtenXLS.RowHandle;

import siemens.vsst.data.models.AbstractSiemensProduct;
import siemens.vsst.data.parsers.utils.VSSTLogger;


/**
 * Remove unnecessary columns so that productNumber is on column 0 and price on column 1
 * 
 * @author radua
 */
public class PricesXlsParser extends GenericXlsParser
{
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
	public int getSkipRows(){
		return 14;
	}
	
	
	/**
	 * Parse Data Row
	 *
	 * @param row RowHandle Reference (row from the XLS)
	 * @param product Current Product Reference
	 */
	@Override
	public void parseXlsRow(RowHandle row, AbstractSiemensProduct product) 
	{
		// set all the properties
		String partNumber = null;
		CellHandle[] cells  = row.getCells();
		for (int i=0; i < cells.length; i++) {
			try {
				switch (i) {
					case 0: // Part Number
					{
						partNumber = cells[i].getStringVal().trim();
						if (!product.getPartNumber().equals(partNumber)) {
							// this is read bad
							if (enableParsingOutput()) {
								VSSTLogger.Warning("Error Parsing Row (" + row.getRowNumber() + "): partNumbers do not match :" + 
										product.getPartNumber() + " - " + partNumber);
							}
						}
						break;
					}

					case 1: // Description
					{
						String description = cells[i].getStringVal().trim();
						String oldDescription = product.getAbsoluteDescription();
						String oldPart = product.getPartNumber();
						
//						// testing
//						String part = product.getPartNumber();
//						if (oldDescription == null || oldDescription.length() == 0) {
//							if (description != null && description.length() != 0) {
//								VSSTLogger.Warning(part + " \t old description is 0");
//							}
//							else {
//								VSSTLogger.Warning(part + " \t all descriptions are 0");
//							}
//						}
//						else if (description == null || description.length() == 0) {
//							VSSTLogger.Warning(part + " \t new description is 0");
//						}
//						else if (!description.equals(oldDescription)) {
//							VSSTLogger.Warning(part + " \t WTF  descriptions are different: \t <"+oldDescription+">     <"+description+">");
//						}
						
						//
						if (oldDescription == null || oldDescription.length() == 0) {
							if (description != null && description.length() != 0) {
								product.setDescription(description);
							}
						}
						
						// ==========
						int xxxx = 0;
						if (!partNumber.equals(oldPart)) {
							xxxx = 1;
						}
						if (oldPart.indexOf("-04321-4") == 3) {
							xxxx = 2;
						}
						if (oldPart.indexOf("599-04321-4") == 0) {
							xxxx = 3;
						}
						if (oldPart.indexOf("233-04321-4") == 0) {
							xxxx = 4;
						}
						break;
					}

					case 2: // Price
					{
						String priceStr = cells[i].getStringVal().trim();
						float price = Float.parseFloat(priceStr);
						
//						// testing
//						String part = product.getPartNumber();
//						float oldPrice = product.getPrice();
//						if (oldPrice == 0) {
//							if (price == 0) {
//								VSSTLogger.Warning(part + " \t all prices 0");
//							}
//							else {
//								VSSTLogger.Warning(part + " \t old price 0");
//							}
//						}
//						else if (price == 0) {
//							VSSTLogger.Warning(part + " \t new price 0");
//						}
						
						//
						product.setPrice(price);
						break;
					}

					default:
					{
					}
				}
			}
			catch (Exception ex) {
				if (enableParsingOutput()) {
					VSSTLogger.Warning("Error Parsing Price Xls Row (" + row.getRowNumber() + "): cell " + i + "  : " + ex.getMessage());
				}
			}
		}

		// mark product as processed by the specific parser
		product.setProcessed(true);
	}
	
	
	public static PricesXlsParser newInstance() {
		return new PricesXlsParser();
	}
}
