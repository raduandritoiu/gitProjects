package siemens.vsst.data.parsers.dbs;

import org.xBaseJ.DBF;
import org.xBaseJ.fields.CharField;
import org.xBaseJ.xBaseJException;
import siemens.vsst.data.enumeration.valves.ValveMedium;
import siemens.vsst.data.models.AbstractSiemensProduct;
import siemens.vsst.data.models.SiemensAssembly;
import siemens.vsst.data.models.SiemensValve;
import siemens.vsst.data.models.metadata.ValveMetadata;


/**
 * Valve Medium DB Parser
 *
 * Parses Valve Medium DB File
 * @author michael
 */
public class ValveMediumDBParser extends GenericDBParser
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
        
		// read the MEDIUM from the DBF
        CharField mediumField   = (CharField) db.getField("MEDIUM");
        ValveMedium medium      = ValveMedium.valueOf(mediumField.get().trim().toUpperCase());
        
        if ((medium == null) || (medium == ValveMedium.UNKNOWN)) {
            return;
		}

        if (metadata.getMedium() < 1) {
            metadata.setMedium(medium.toFlag()); // Water, Glycol, Steam
		}
		else {
            metadata.setMedium(metadata.getMedium() | medium.toFlag()); // Water, Glycol, Steam
		}
    }
	
	
	@Override
    public AbstractSiemensProduct getItemForPart(String partNumber) throws Exception {
        return new SiemensValve();
    }
	
	
    public static ValveMediumDBParser newInstance() {
        return new ValveMediumDBParser();
    }
}