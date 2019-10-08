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
public class DamperCrossParser extends GenericCrossParser
{
	public DamperCrossParser() {
		skipRows = 1;
		skipSheets = 0; // they introduced 2 new extra sheets that are not used
	}
	
	
	public void parseCrossRow(CellHandle[] cells, CrossReferencePart part) {
		part.setSiemensPartNumber(cells[1].getStringVal());		
		part.setCompetitorPartNumber(cells[0].getStringVal());
	}
	
	
	public static DamperCrossParser newInstance() {
		return new DamperCrossParser();
	}
}