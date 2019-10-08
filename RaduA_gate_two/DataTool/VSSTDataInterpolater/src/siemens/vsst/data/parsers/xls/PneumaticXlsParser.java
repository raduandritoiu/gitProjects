/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.parsers.xls;

import com.extentech.ExtenXLS.CellHandle;
import com.extentech.ExtenXLS.RowHandle;
import siemens.vsst.data.enumeration.pneumatics.PneumaticIsActuator;
import siemens.vsst.data.enumeration.pneumatics.PneumaticBallJoint;
import siemens.vsst.data.enumeration.pneumatics.PneumaticBracket;
import siemens.vsst.data.enumeration.pneumatics.PneumaticClevis;
import siemens.vsst.data.enumeration.pneumatics.PneumaticDiaphArea;
import siemens.vsst.data.enumeration.pneumatics.PneumaticDiaphMaterial;
import siemens.vsst.data.enumeration.pneumatics.PneumaticFwdTravelStops;
import siemens.vsst.data.enumeration.pneumatics.PneumaticHousing;
import siemens.vsst.data.enumeration.pneumatics.PneumaticMountingStyle;
import siemens.vsst.data.enumeration.pneumatics.PneumaticOperTemp;
import siemens.vsst.data.enumeration.pneumatics.PneumaticPivot;
import siemens.vsst.data.enumeration.pneumatics.PneumaticPosRelay;
import siemens.vsst.data.enumeration.pneumatics.PneumaticSpringRange;
import siemens.vsst.data.enumeration.pneumatics.PneumaticStem;
import siemens.vsst.data.enumeration.pneumatics.PneumaticStroke;
import siemens.vsst.data.enumeration.pneumatics.PneumaticType;
import siemens.vsst.data.enumeration.pneumatics.PneumaticULCerts;
import siemens.vsst.data.models.AbstractSiemensProduct;
import siemens.vsst.data.models.SiemensPneumatic;
import siemens.vsst.data.models.metadata.PneumaticMetadata;
import siemens.vsst.data.parsers.utils.VSSTLogger;

/**
 *
 * @author User
 */
public class PneumaticXlsParser extends GenericXlsParser
{
    /**
     * Returns how many rows must be skipped from the beginning.
	 * 
	 * @return number of rows.
     */
	@Override
	public int getSkipRows(){
		return 3;
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
		if (product instanceof SiemensPneumatic) {
			// get the Pneumatic Product and it's metadata
			SiemensPneumatic pneumatic = (SiemensPneumatic) product;
			if (pneumatic.getPneumaticMetadata()== null) {
				pneumatic.setPneumaticMetadata(new PneumaticMetadata());
			}
			PneumaticMetadata metadata = pneumatic.getPneumaticMetadata();
			
			
			// to test the mutual excluding cells
			int mntStyleCnt = 0;
			int springRangeCnt = 0;
			
			
			// set all the properties
			CellHandle[] cells  = row.getCells();
			for (int i=0; i < cells.length; i++) {
				try {
					switch (i) {
						case 0: // Part Number
						{
							String partNumber = cells[i].getStringVal().trim();
							pneumatic.setPartNumber(partNumber);
							break;
						}
						
						case 1: // Pneumatic Type
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setType(PneumaticType.fromString(cellString));
							break;
						}
						
						case 2: // Stroke in. (mm)
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setStroke(PneumaticStroke.fromString(cellString));
							break;
						}
						
						case 3: // Housing
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setHousing(PneumaticHousing.fromString(cellString));
							break;
						}
						
						case 4: // Stem
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setStem(PneumaticStem.fromString(cellString));
							break;
						}
						
						case 5: // Diaphragm Effective Area sq in. (sq cm)
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setDiaphArea(PneumaticDiaphArea.fromString(cellString));
							break;
						}
						
						case 6: // Diaphragm Material
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setDiaphMaterial(PneumaticDiaphMaterial.fromString(cellString));
							break;
						}
						
						case 7: // Operating Temp Range  °F  (°C)
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setOperatingTemp(PneumaticOperTemp.fromString(cellString));
							break;
						}
						
						case 8: // is an Actuator;
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setIsActuator(PneumaticIsActuator.fromString(cellString));
							break;
						}
						
						case 9: // has Clevis;
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setClevis(PneumaticClevis.fromString(cellString));
							break;
						}
						
						case 10: // has Bracket;	
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setBracket(PneumaticBracket.fromString(cellString));
							break;
						}
						
						case 11: // has Ball Joint Connector;
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setBallJoint(PneumaticBallJoint.fromString(cellString));
							break;
						}
						
						case 12: // has Pivot;
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setPivot(PneumaticPivot.fromString(cellString));
							break;
						}
						
						case 13: // has Positioning Relay;
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setPosRelay(PneumaticPosRelay.fromString(cellString));
							break;
						}
						
						case 14: // has Forward Travel Stops;
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setFwdTravelStops(PneumaticFwdTravelStops.fromString(cellString));
							break;
						}
						
						case 15: // has Mounting Style - FRONT
						{
							String cellString = cells[i].getStringVal().trim();
							if (!cellString.equals("")) {
								metadata.setMountingStyle(PneumaticMountingStyle.FRONT);
								mntStyleCnt ++;
							}
							break;
						}
						
						case 16: // has Mounting Style - FIXED
						{
							String cellString = cells[i].getStringVal().trim();
							if (!cellString.equals("")) {
								metadata.setMountingStyle(PneumaticMountingStyle.FIXED);
								mntStyleCnt ++;
							}
							break;
						}
						
						case 17: // has Mounting Style - PIVOT
						{
							String cellString = cells[i].getStringVal().trim();
							if (!cellString.equals("")) {
								metadata.setMountingStyle(PneumaticMountingStyle.PIVOT);
								mntStyleCnt ++;
							}
							break;
						}
						
						case 18: // has Mounting Style - TANDEM
						{
							String cellString = cells[i].getStringVal().trim();
							if (!cellString.equals("")) {
								metadata.setMountingStyle(PneumaticMountingStyle.TANDEM);
								mntStyleCnt ++;
							}
							break;
						}
						
						case 19: // has Mounting Style - UNIVERSAL Extended Shaft
						{
							String cellString = cells[i].getStringVal().trim();
							if (!cellString.equals("")) {
								metadata.setMountingStyle(PneumaticMountingStyle.UNIVERSAL);
								mntStyleCnt ++;
							}
							break;
						}
						
						case 20: // has the Certifficate UL Recognized EMKU2 (UL555/555S)
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setULCert(PneumaticULCerts.fromString(cellString));
							break;
						}
						
						// cases 21 to 28 are down below

						case 30: // Nominal Spring Range (NSR) - "3-7 psi (21-48 kPa)"	
						{
							String cellString = cells[i].getStringVal().trim();
							if (!cellString.equals("")) {
								metadata.setSpringRange(PneumaticSpringRange.PSI_3_7);
								springRangeCnt ++;
							}
							break;
						}
						
						case 31: // Nominal Spring Range (NSR) - "3-8 psi (21-55 kPa)"	
						{
							String cellString = cells[i].getStringVal().trim();
							if (!cellString.equals("")) {
								metadata.setSpringRange(PneumaticSpringRange.PSI_3_8);
								springRangeCnt ++;
							}
							break;
						}
						
						case 32: // Nominal Spring Range (NSR) - "3-13 psi (21-90 kPa)"	
						{
							String cellString = cells[i].getStringVal().trim();
							if (!cellString.equals("")) {
								metadata.setSpringRange(PneumaticSpringRange.PSI_3_13);
								springRangeCnt ++;
							}
							break;
						}
						
						case 33: // Nominal Spring Range (NSR) - "5-10 psi (35-70 kPa)"	
						{
							String cellString = cells[i].getStringVal().trim();
							if (!cellString.equals("")) {
								metadata.setSpringRange(PneumaticSpringRange.PSI_5_10);
								springRangeCnt ++;
							}
							break;
						}
						
						case 34: // Nominal Spring Range (NSR) - "8-13 psi (55-90 kPa)"	
						{
							String cellString = cells[i].getStringVal().trim();
							if (!cellString.equals("")) {
								metadata.setSpringRange(PneumaticSpringRange.PSI_8_13);
								springRangeCnt ++;
							}
							break;
						}
						
						case 35: // Nominal Spring Range (NSR) - "2-3 psi, 8-13 psi (14-21, 55-90 kPa) Hesitation Model"
						{
							String cellString = cells[i].getStringVal().trim();
							if (!cellString.equals("")) {
								metadata.setSpringRange(PneumaticSpringRange.PSI_HM);
								springRangeCnt ++;
							}
							break;
						}
						
						// geting back to 21 - 28
						
						case 21: // Maximum Thrust lb (N) - "Full Stroke Forward 15 psi (103 kPa)"	
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setMaxThrust_15(getFloatFromLb(cellString));
							break;
						}
						
						case 22: // Maximum Thrust lb (N) - "Full Stroke Forward 18 psi (124 kPa)"	
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setMaxThrust_18(getFloatFromLb(cellString));
							break;
						}
						
						case 23: // Maximum Thrust lb (N) - "Full Stroke Forward 25 psi (172kPa)"	
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setMaxThrust_25(getFloatFromLb(cellString));
							break;
						}
						
						case 24: // Maximum Thrust lb (N) - "Spring Return no stroke 0 psi (0 kPa)"
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setMaxThrust_no(getFloatFromLb(cellString));
							break;
						}

						case 25:// Torque Rating lb-in (Nm) with maximum hysteresis of 2.5 psi (17.2 kPa) @ 90° rotation - "Gradual Operation"
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setTorqueRating_go(getFloatFromLb(cellString));
							break;
						}

						case 26: // Torque Rating lb-in (Nm) with maximum hysteresis of 2.5 psi (17.2 kPa) @ 90° rotation - "2-Position Operation or with Positioner 15 psi (103 kPa)"	
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setTorqueRating_15(getFloatFromLb(cellString));
							break;
						}

						case 27: // Torque Rating lb-in (Nm) with maximum hysteresis of 2.5 psi (17.2 kPa) @ 90° rotation - "2-Position Operation or with Positioner 18 psi (124 kPa)"	
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setTorqueRating_18(getFloatFromLb(cellString));
							break;
						}

						case 28: // Torque Rating lb-in (Nm) with maximum hysteresis of 2.5 psi (17.2 kPa) @ 90° rotation - "2-Position Operation or with Positioner 25 psi (172kPa)"
						{
							String cellString = cells[i].getStringVal().trim();
							metadata.setTorqueRating_25(getFloatFromLb(cellString));
							break;
						}

						case 36: // Accessories
						case 37:
						case 38:
						case 39:
						{
							String cellString = cells[i].getStringVal().trim();
							String[] accessoriesPartNumbers = cellString.split("\n");
							for (int j=0; j < accessoriesPartNumbers.length; j++) {
								// add the accepted accessories, but just as part numbers (Strings); not the actual objects
								String accessoryPartNumber = accessoriesPartNumbers[j].trim();
								if (accessoryPartNumber.contains("no data"))
									continue;
								if (accessoryPartNumber.contains("qty"))
									continue;
								if (accessoryPartNumber.contains("N/A"))
									continue;
								if (accessoryPartNumber.length() < 1) 
									continue;
								
								// Base Replacement Accessory
								if (i == 36) {
									metadata.setBaseReplace(accessoryPartNumber);
								}
								// Normal Accessory
								else {
									metadata.getAcceptedAccessories().add(accessoryPartNumber);									
								}
							}
							break;
						}
						
						case 40: // Literture
						case 41:
						case 42:
						case 43:
						{
							String cellString = cells[i].getStringVal().trim();
							String[] docs  = cellString.split(",");
							for (int j=0; j < docs.length; j++) {
								// add the accepted accessories, but just as part numbers (Strings); not the actual objects
								String doc = docs[j].trim();
								if (doc.contains("qty")) 
									continue;
								if (doc.contains("N/A")) 
									continue;
								if (doc.length() < 1)
									continue;
								metadata.getDocs().add(doc);
							}
							break;
						}
												
						default:
						{
							String cellString = cells[i].getStringVal().trim();
						}
					}
				}
	            catch (Exception ex) {
	            	if (enableParsingOutput()) {
	            		VSSTLogger.Warning("Error Parsing Row: " + i + "  : " + ex.getMessage());
	            	}
	            }
			}
			
			
			// mark product as processed by the specific parser
			product.setProcessed(true);
			
			// to test the mutual excluding cells
			if (mntStyleCnt > 1) {
					VSSTLogger.Info("Multiple mounting Styles found");				
			}
			else if (mntStyleCnt < 1) {
					VSSTLogger.Info("NO mounting Styles found");				
			}
			if (springRangeCnt > 1) {
					VSSTLogger.Info("Multiple spring ranges found");				
			}
			else if (springRangeCnt < 1) {
					VSSTLogger.Info("NO spring ranges found");				
			}
		}
	}
	
	
	/**
	 * Used to parse numbers from the elaborious strings in the DB 
	 * 
	 * @param str
	 * @return 
	 */
	private float getFloatFromLb(String str) {
		int idx = str.indexOf(" ");
		if (idx > 0) str = str.substring(0, idx);
		idx = str.indexOf("lb");
		if (idx > 0) str = str.substring(0, idx);
		float val;
		try {
			val = Float.parseFloat(str);
		}
		catch (Exception ex) {
			val = -1;
		}
		return val;
	}
	
	/**
	 * Get Class for item part number
	 *
	 * @param partNumber
	 *
	 * @return Derivative of  class
	 * @throws Exception
	 */
	public AbstractSiemensProduct getItemForPart(String partNumber) throws Exception {
		return new SiemensPneumatic();
	}
	
	
	public static PneumaticXlsParser newInstance() {
		return new PneumaticXlsParser();
	}
}
