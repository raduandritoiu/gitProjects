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
public class ValveCrossParser extends GenericCrossParser
{
	public ValveCrossParser() {
		skipRows = 2;
		skipSheets = 0;
	}
	
	
	@Override
	public void parseCrossRow(CellHandle[] cells, CrossReferencePart part) {
		part.setSiemensPartNumber(cells[1].getStringVal());		
		part.setCompetitorPartNumber(cells[0].getStringVal());
	}
	
	
	public static ValveCrossParser newInstance() {
		return new ValveCrossParser();
	}
}
