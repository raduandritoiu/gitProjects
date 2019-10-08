package siemens.vsst.data.parsers.dbs;

import org.xBaseJ.DBF;
import org.xBaseJ.fields.CharField;
import org.xBaseJ.xBaseJException;
import siemens.vsst.data.enumeration.valves.ValveApplication;
import siemens.vsst.data.models.AbstractSiemensProduct;
import siemens.vsst.data.models.SiemensAssembly;
import siemens.vsst.data.models.SiemensValve;
import siemens.vsst.data.models.metadata.ValveMetadata;


/**
 * Valve Mixing/Diverting DB Parser
 *
 * Parses Valve MixDiv DB File
 * @author michael
 */
public class ValveMixDivDBParser extends GenericDBParser
{
	@Override
    public void parseDataRow(DBF db, AbstractSiemensProduct product) throws xBaseJException {
        ValveMetadata metadata  =  null;

        if (product instanceof SiemensValve) {
            metadata    = ((SiemensValve) product).getValveMetadata();
        }
		else if (product instanceof SiemensAssembly) {
            metadata    = ((SiemensAssembly) product).getValve().getValveMetadata();
        }
		
		// read the MIXING from the DBF
        CharField applicationField      = (CharField) db.getField("APPL");
        ValveApplication application    = ValveApplication.valueOf(applicationField.get().trim());
     
        if ((application == null) || (application == ValveApplication.Unknown))
            return;

        if (metadata.getMixingDiverting() < 1) {
            metadata.setMixingDiverting(application.toFlag());
		}
		else {
            metadata.setMixingDiverting(metadata.getMixingDiverting() | application.toFlag());
		}

        if (product instanceof SiemensAssembly) {
            SiemensAssembly asm = (SiemensAssembly) product;
            if (asm.getThreeWayFunction() < 1)  {
                asm.setThreeWayFunction(application.toFlag());
			}
			else {
                asm.setThreeWayFunction(asm.getThreeWayFunction() | application.toFlag());
			}
        }
    }
	
	
    public AbstractSiemensProduct getItemForPart(String partNumber) throws Exception {
        return new SiemensValve();
    }
	
	
    public static ValveMixDivDBParser newInstance() {
        return new ValveMixDivDBParser();
    }
}
