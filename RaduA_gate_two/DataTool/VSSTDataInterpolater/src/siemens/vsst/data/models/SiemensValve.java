package siemens.vsst.data.models;

import siemens.vsst.data.models.metadata.ValveMetadata;

/**
 * Siemens Valve Object Model
 * 
 * @author michael
 */
public class SiemensValve extends AbstractSiemensProduct
{
    private ValveMetadata valveMetadata;

	private static int debugCnt = 0;
	
	public SiemensValve() {
		debugCnt ++;
		debugId = debugCnt;
		valveMetadata = new ValveMetadata();
	}
	
	
    @Override
    public void setPartNumber(String value) {
        super.setPartNumber(value);
        valveMetadata.setPartNumber(value);
    }
	
	
    public ValveMetadata getValveMetadata() {
        return valveMetadata;
    }
	
    public void setValveMetadata(ValveMetadata metadata) {
        valveMetadata = metadata;
    }
}
