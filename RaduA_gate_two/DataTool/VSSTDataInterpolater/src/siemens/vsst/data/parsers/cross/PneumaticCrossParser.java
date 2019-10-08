/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.parsers.cross;

import com.extentech.ExtenXLS.CellHandle;
import siemens.vsst.data.models.CrossReferencePart;

/**
 *
 * @author User
 */
public class PneumaticCrossParser extends GenericCrossParser 
{
	public PneumaticCrossParser() {
		skipRows = 1;
		skipSheets = 1;
	}
	
	
	@Override
	public void parseCrossRow(CellHandle[] cells, CrossReferencePart part) {
		part.setSiemensPartNumber(cells[0].getStringVal());		
		part.setCompetitorPartNumber(cells[1].getStringVal());
	}

	
	public static PneumaticCrossParser newInstance() {
		return new PneumaticCrossParser();
	}
}