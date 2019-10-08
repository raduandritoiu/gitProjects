/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.interfaces;

import java.util.ArrayList;

import com.extentech.ExtenXLS.CellHandle;

import siemens.vsst.data.models.CrossReferencePart;

/**
 *
 * @author User
 */
public interface ISiemensCrossParser {
    /**
     * Parse The 
     *
     * @param filePath The name of the crossreference file (an XLS file)
     * @param currentReferenceParts Collection where the cross references will be added.
     */
	public void parseCrossItems(String filePath, ArrayList<CrossReferencePart> currentReferenceParts);
	
	
    /**
     * Parse the row cells
     *
     * @param cells
     * @param part
     */
	void parseCrossRow(CellHandle[] cells, CrossReferencePart part);
}
