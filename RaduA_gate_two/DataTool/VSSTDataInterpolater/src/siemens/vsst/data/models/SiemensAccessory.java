/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.models;

import siemens.vsst.data.models.metadata.AccessoryMetadata;


/**
 *
 * @author radua
 */
public class SiemensAccessory extends AbstractSiemensProduct {
	private AccessoryMetadata accessoryMetadata;
	
	
	public SiemensAccessory() {
            accessoryMetadata = new AccessoryMetadata();
	}
	
	
	@Override
	public void setPartNumber(String value) {
		super.setPartNumber(value);
		if (accessoryMetadata != null) {
			accessoryMetadata.setPartNumber(value);
		}
	}

	
	public void setAccessoryMetadata(AccessoryMetadata value) {
		accessoryMetadata = value;
	}
	
	public AccessoryMetadata getAccessoryMetadata() {
		return accessoryMetadata;
	}
}
