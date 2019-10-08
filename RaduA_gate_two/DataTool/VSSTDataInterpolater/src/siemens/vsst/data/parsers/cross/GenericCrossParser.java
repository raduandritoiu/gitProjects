/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.parsers.cross;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.extentech.ExtenXLS.CellHandle;
import com.extentech.ExtenXLS.RowHandle;
import com.extentech.ExtenXLS.WorkBookHandle;
import com.extentech.ExtenXLS.WorkSheetHandle;

import siemens.vsst.data.interfaces.ISiemensCrossParser;
import siemens.vsst.data.models.CrossReferencePart;

/**
 *
 * @author User
 */
public abstract class GenericCrossParser implements ISiemensCrossParser
{
	protected int skipRows;
	protected int skipSheets;
	
	
	public GenericCrossParser() {
		skipRows = 1;
		skipSheets = 0;
	}
	
	public void parseCrossItems(String filePath, ArrayList<CrossReferencePart> currentReferenceParts) 
	{
		WorkBookHandle book			= new WorkBookHandle(filePath);
		WorkSheetHandle[] sheets	= book.getWorkSheets();

		for (int i = skipSheets; i < sheets.length; i++) {
			WorkSheetHandle sheet   = sheets[i];
			try {
				// New Sheet, Get Competitor Name                
				String competitorName			= sheet.getSheetName();
				List<RowHandle> sheetRows		= Arrays.asList(sheet.getRows());
				Iterator<RowHandle> rowCursor   = sheetRows.iterator();
				
				// Skip a number of rows
				for (int r=0; r < skipRows; r++) {
					rowCursor.next(); // Skip first Two rows
				}
				
				while (rowCursor.hasNext()) {
					RowHandle row       = rowCursor.next();
					CellHandle[] cells  = row.getCells();
					
					if (cells.length < 2)
						continue;
					
					CrossReferencePart part = new CrossReferencePart();
					part.setCompetitorName(competitorName);
					parseCrossRow(cells, part);
					
					currentReferenceParts.add(part);
				}
			}
			catch (Exception ex) {
//				VSSTLogger.Warning("Error Parsing Cross Reference Item: " + ex.getMessage());
			}
		}
	}
}
