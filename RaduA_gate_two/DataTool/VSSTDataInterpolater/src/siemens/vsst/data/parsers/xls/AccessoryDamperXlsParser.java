/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.parsers.xls;

import com.extentech.ExtenXLS.CellHandle;
import com.extentech.ExtenXLS.RowHandle;
import siemens.vsst.data.models.AbstractSiemensProduct;
import siemens.vsst.data.models.SiemensAccessory;
import siemens.vsst.data.models.metadata.AccessoryMetadata;
import siemens.vsst.data.parsers.utils.VSSTLogger;

/**
 *
 * @author radua
 */
public class AccessoryDamperXlsParser extends GenericXlsParser
{
    /**
     * Returns how many rows must be skipped from the beginning.
	 * 
	 * @return number of rows.
     */
	@Override
	public int getSkipRows(){
		return 1;
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
		if (product instanceof SiemensAccessory) {
			// get the Damper Accessory and it's metadata
			SiemensAccessory accessory = (SiemensAccessory) product;
			if (accessory.getAccessoryMetadata() == null) {
				accessory.setAccessoryMetadata(new AccessoryMetadata());
			}
			AccessoryMetadata metadata = accessory.getAccessoryMetadata();
			metadata.setFromDamper(1);
			
			// set all the properties
			CellHandle[] cells  = row.getCells();
			for (int i=0; i < cells.length; i++) {
				try {
					switch (i) {
						case 0: // Part Number
						{
							String partNumber = cells[i].getStringVal().trim();
							accessory.setPartNumber(partNumber);
							break;
						}
					}
				}
	            catch (Exception ex) {
					VSSTLogger.Warning("Error Parsing Row: " + i + "  : " + ex.getMessage());
	            }
			}
			
			// mark product as processed by the specific parser
			product.setProcessed(true);
		}
	}
	
	
	/**
	 * Get Class for item part number
	 *
	 * @param partNumber
	 *
	 * @return Derivative of  class
	 * @throws Exception
	 */
    public AbstractSiemensProduct getItemForPart(String partNumber) throws Exception {
		return new SiemensAccessory();
	}
	
	
	public static AccessoryDamperXlsParser newInstance() {
		return new AccessoryDamperXlsParser();
	}
}
