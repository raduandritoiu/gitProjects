package siemens.vsst.data.parsers.remove;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.extentech.ExtenXLS.CellHandle;
import com.extentech.ExtenXLS.RowHandle;
import com.extentech.ExtenXLS.WorkBookHandle;
import com.extentech.ExtenXLS.WorkSheetHandle;

import siemens.vsst.data.models.AbstractSiemensProduct;
import siemens.vsst.data.models.SiemensActuator;
import siemens.vsst.data.models.SiemensAssembly;
import siemens.vsst.data.models.SiemensPneumatic;
import siemens.vsst.data.models.SiemensValve;
import siemens.vsst.data.parsers.utils.VSSTLogger;


public class RemoveValves
{
	HashMap<String, Integer> removeProducts = new HashMap<>();
	
	
	public RemoveValves() {}
	
	
	public int getPartNumberCell() {
		return 0;
	}
	
	public int getSkipRows() {
		return 1;
	}
	
	private void loadValves(String filePath) {
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
		
		// Loop through all records
		removeProducts.clear();
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
			
			removeProducts.put(partNumber, 0);
		}
	}
	
	public void remove(String filePath, HashMap<String, AbstractSiemensProduct> currentProductMap) throws IOException {
		// parse the products that  need tot be removed
		loadValves(filePath);
		
		// run and remove products
		int removedTotal = 0;
		int removedValves = 0;
		int removedAssemblies = 0;
		int removedActuators = 0;
		int removedDampers = 0;
		int removedPneumatics = 0;
		
		int initialProducts = currentProductMap.size();
		// because the loop takes too long, this is to put breakpoints and monitor progress
		int removeSize = removeProducts.size();
		int currIdx = 0;

		Iterator<String> removeCursor = removeProducts.keySet().iterator();
		while (removeCursor.hasNext()) {
			String removePartNumber = removeCursor.next();
			AbstractSiemensProduct removedProduct = currentProductMap.remove(removePartNumber);
			// a product was found and removed
			if (removedProduct != null) {
				if (removedProduct instanceof SiemensAssembly) {
					VSSTLogger.Warning("ass: "+removedProduct.getPartNumber()+" \tval: "+((SiemensAssembly) removedProduct).getValve().getPartNumber()+" \t act:"+((SiemensAssembly) removedProduct).getActuator().getPartNumber());
				}
				
				removedTotal ++;
				removeProducts.put(removePartNumber, 1);
				if (removedProduct instanceof SiemensValve) removedValves++;
				if (removedProduct instanceof SiemensAssembly) removedAssemblies++;
				if (removedProduct instanceof SiemensActuator) {
					if (((SiemensActuator) removedProduct ).isDamperActuator()) removedDampers++;
					else removedActuators++;
				}
				if (removedProduct instanceof SiemensPneumatic) removedPneumatics++;
			}
			else {
				VSSTLogger.Warning("-----> did not remove anything for "+removePartNumber);
			}
		}
		VSSTLogger.Warning("Using "+removeSize+" entried, it removed "+removedTotal+" out of "+initialProducts+" products:");
		VSSTLogger.Warning("         - "+removedValves+" valves");
		VSSTLogger.Warning("         - "+removedAssemblies+" assemblies");
		VSSTLogger.Warning("         - "+removedActuators+" actuators");
		VSSTLogger.Warning("         - "+removedDampers+" dampers");
		VSSTLogger.Warning("         - "+removedPneumatics+" pneumatics");
	}
}
