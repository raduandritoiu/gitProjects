/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.parsers.xls;

import com.extentech.ExtenXLS.CellHandle;
import com.extentech.ExtenXLS.RowHandle;
import siemens.vsst.data.enumeration.dampers.DamperAuxilarySwitch;
import siemens.vsst.data.enumeration.dampers.DamperControlSignal;
import siemens.vsst.data.enumeration.dampers.DamperFailSafe;
import siemens.vsst.data.enumeration.dampers.DamperPlenum;
import siemens.vsst.data.enumeration.dampers.DamperPositionFeedback;
import siemens.vsst.data.enumeration.dampers.DamperScalableSignal;
import siemens.vsst.data.enumeration.dampers.DamperSystemSupply;
import siemens.vsst.data.enumeration.dampers.DamperType;
import siemens.vsst.data.models.AbstractSiemensProduct;
import siemens.vsst.data.models.SiemensActuator;
import siemens.vsst.data.models.metadata.DamperMetadata;
import siemens.vsst.data.parsers.utils.VSSTLogger;


/**
 *
 * @author radua
 */
public class DamperXlsParser extends GenericXlsParser
{
    /**
     * Returns how many rows must be skipped from the beginning.
	 * 
	 * @return number of rows.
     */
	@Override
	public int getSkipRows(){
		return 1;
	}
	
	
	/**
	 * Parse Data Row
	 *
	 * @param row RowHandle Reference (row from the XLS)
	 * @param product Current Product Reference
	 */
	@Override
	public void parseXlsRow(RowHandle row, AbstractSiemensProduct product) 
	{
		if (!(product instanceof SiemensActuator))
			return;
		
		// get the Damper and it's metadata
		SiemensActuator damper = (SiemensActuator) product;

		// add damper metadata - because it may be an actuator from valves 
		// that does not have damper metadata
		if (damper.getDamperMetadata() == null) {
			damper.setDamperMetadata(new DamperMetadata());
		}
		DamperMetadata metadata = damper.getDamperMetadata();

		// set all the properties
		CellHandle[] cells  = row.getCells();
		for (int i=0; i < cells.length; i++) {
			try {
				switch (i) {
					case 0: // Part Number
					{
						String partNumber = cells[i].getStringVal().trim();
						damper.setPartNumber(partNumber);
						break;
					}

					case 1: // Actuator Type
					{
						String actuatorType = cells[i].getStringVal().trim();
						metadata.setType(DamperType.fromString(actuatorType));
						if (metadata.getType() == DamperType.Electronic_Fast_Acting) {
							DamperFailSafe failSafe = DamperFailSafe.fromString(actuatorType);
							// must do something with failSafe option
						}
						break;
					}

					case 2: // Control Signal
					{
						String controlSignal = cells[i].getStringVal().trim();
						metadata.setControlSignal(DamperControlSignal.fromString(controlSignal));
						break;
					}

					case 3: // Control System Supply
					{
						String systemSupply = cells[i].getStringVal().trim();
						metadata.setSystemSupply(DamperSystemSupply.fromString(systemSupply));
						break;
					}

					case 4: // Plenum
					{
						String plenum = cells[i].getStringVal().trim();
						metadata.setPlenumRating(DamperPlenum.fromString(plenum));
						break;
					}

					case 5: // Position Feedback
					{
						String positionFeedback = cells[i].getStringVal().trim();
						metadata.setPositionFeedback(DamperPositionFeedback.fromString(positionFeedback));
						break;
					}

					case 6: // Auxilary Switch
					{
						String auxilarySwitch = cells[i].getStringVal().trim();
						metadata.setAuxilarySwitches(DamperAuxilarySwitch.fromString(auxilarySwitch));
						break;
					}

					case 7: // Scalable Control Input Signal
					{
						String scalableSignal = cells[i].getStringVal().trim();
						metadata.setScalability(DamperScalableSignal.fromString(scalableSignal));
						break;
					}

					case 8: // Torque
					{
						int rowId = row.getRowNumber();
						String torqueStr = cells[i].getStringVal().trim();
						torqueStr = torqueStr.substring(0, torqueStr.indexOf(" lb-in")).trim();
						float torque = Float.parseFloat(torqueStr);
						metadata.setTorque(torque);
						break;
					}

					case 9: // Accessories
					{
						String accessoriesStr = cells[i].getStringVal().trim();
						String[] accessories = accessoriesStr.split(";");
						for (int j=0; j < accessories.length; j++) {
							// add the accepted accessories, but just as part numbers (Strings); not the actual objects
							String accessoryPartNumber = accessories[j];
							accessoryPartNumber = accessoryPartNumber.substring(accessoryPartNumber.indexOf(":") + 1);
							metadata.getAcceptedAccessories().add(accessoryPartNumber);
						}
						break;
					}

					default:
					{
						VSSTLogger.Warning("Parsing Row: unknown cell " + i);
					}
				}
			}
			catch (Exception ex) {
				VSSTLogger.Warning("Error Parsing Row " + row.getRowNumber() + " : cell " + i + "  : " + ex.getMessage());
			}
		}

		// mark product as processed by the specific parser
		product.setProcessed(true);
	}
	
	
	/**
	 * Get Class for item part number
	 *
	 * @param partNumber
	 *
	 * @return Derivative of  class
	 * @throws Exception
	 */
	@Override
	public AbstractSiemensProduct getItemForPart(String partNumber) throws Exception {
		return new SiemensActuator(false, true);
	}
	
	
	public static DamperXlsParser newInstance() {
		return new DamperXlsParser();
	}
}
