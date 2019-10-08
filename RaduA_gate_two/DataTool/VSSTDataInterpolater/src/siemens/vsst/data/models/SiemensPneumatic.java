/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.models;

import siemens.vsst.data.models.metadata.PneumaticMetadata;
import siemens.vsst.data.models.metadata.ValveMetadata;

/**
 *
 * @author User
 */
public class SiemensPneumatic extends AbstractSiemensProduct 
{
	private PneumaticMetadata pneumaticMetadata;
	
	
	public SiemensPneumatic() {
		pneumaticMetadata = new PneumaticMetadata();
	}
	
	
    @Override
    public void setPartNumber(String value) {
        super.setPartNumber(value);
        pneumaticMetadata.setPartNumber(value);
    }
	
	
    public PneumaticMetadata getPneumaticMetadata() {
        return pneumaticMetadata;
    }
	
    public void setPneumaticMetadata(PneumaticMetadata metadata) {
        pneumaticMetadata = metadata;
    }
}
