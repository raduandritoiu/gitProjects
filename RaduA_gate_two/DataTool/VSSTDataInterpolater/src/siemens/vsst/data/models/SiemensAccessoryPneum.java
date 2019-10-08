/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.models;

import siemens.vsst.data.models.metadata.AccessoryPneumMetadata;

/**
 *
 * @author User
 */
public class SiemensAccessoryPneum extends AbstractSiemensProduct
{
	private AccessoryPneumMetadata accessoryMetadata;
	
	
	public SiemensAccessoryPneum()
	{}
	
	
	@Override
	public void setPartNumber(String value) {
		super.setPartNumber(value);
		if (accessoryMetadata != null) {
			accessoryMetadata.setPartNumber(value);
		}
	}

	
	public void setAccessoryMetadata(AccessoryPneumMetadata value) {
		accessoryMetadata = value;
	}
	
	public AccessoryPneumMetadata getAccessoryMetadata() {
		return accessoryMetadata;
	}

}
