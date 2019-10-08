package siemens.vsst.data.io;

import java.awt.EventQueue;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;
import siemens.vsst.data.enumeration.SiemensProductUtil;
import siemens.vsst.data.events.ProgressEvent;
import siemens.vsst.data.events.ProgressEventListener;
import siemens.vsst.data.models.AbstractSiemensProduct;
import siemens.vsst.data.models.CrossReferencePart;
import siemens.vsst.data.models.SiemensActuator;
import siemens.vsst.data.models.SiemensAssembly;
import siemens.vsst.data.models.SiemensAccessory;
import siemens.vsst.data.models.SiemensDocumentMaterial;
import siemens.vsst.data.models.SiemensPneumatic;
import siemens.vsst.data.models.SiemensValve;
import siemens.vsst.data.models.metadata.AccessoryMetadata;
import siemens.vsst.data.models.metadata.PneumaticMetadata;
import siemens.vsst.data.parsers.utils.StaticParameters;
import siemens.vsst.data.parsers.utils.VSSTLogger;

/**
 * SQL|Lite File Writer
 * @author michael
 */
public class SQLiteWriter
{
	private File databaseFile;                          // SQL|lite Database File
//	private SqlJetDb db;                                // SQLJet Reference
	private ProgressEventListener progressListener;     // Progress event listener

	private float currentIndex    = 1;      // Current Index
	private float size            = 0;      // Total Size
	
	
	public SQLiteWriter()
	{}
	
	
	public void addProgressListener(ProgressEventListener handler) {
		this.progressListener   = handler;
	}
	
	
	/**
	 * Write products to SQL|Lite database file
	 *
	 * @param dbFile File to output data to
	 * @param reader Collection of products to write to database
	 * @throws SqlJetException
	 */
	public void writeData(File dbFile, DBFReader reader) throws SqlJetException
	{
		VSSTLogger.Info("==== Writing to " + dbFile.getAbsolutePath() + " \t - EXTENDED_MODE : " +  StaticParameters.EXTENDED_VERSION + "  ====");
		
		// create the file and the DATA BASE structure and TABLES
		databaseFile   = dbFile;
		if (dbFile.exists())
		    dbFile.delete();
		
		// create the data base object
		SqlJetDb db				 = SqlJetDb.open(dbFile, true);
		SQLLiteCreator dbCreator = new SQLLiteCreator();
		dbCreator.prepareDatabase(db);
		
		// get the table objects
		ISqlJetTable documentTable  = db.getTable("Documents");
		ISqlJetTable productsTable  = db.getTable("Products");
		ISqlJetTable valveTable     = db.getTable("Valves");
		ISqlJetTable actuatorTable  = db.getTable("Actuators");
		ISqlJetTable assemblyTable  = db.getTable("Assemblies");
		ISqlJetTable dampersTable	= (!StaticParameters.EXTENDED_VERSION) ? null : db.getTable("Dampers");
		ISqlJetTable pneumaticTable	= (!StaticParameters.EXTENDED_VERSION) ? null : db.getTable("Pneumatics");
		ISqlJetTable accessoryTable	= (!StaticParameters.EXTENDED_VERSION) ? null : db.getTable("AccessoryKits");
		ISqlJetTable referenceTable = db.getTable("CrossReference");
		ISqlJetTable damperAccessoryCrossTable = (!StaticParameters.EXTENDED_VERSION) ? null : db.getTable("CrossAccessoriesDampers");
		ISqlJetTable pneumaAccessoryCrossTable = (!StaticParameters.EXTENDED_VERSION) ? null : db.getTable("CrossAccessoriesPneumatics");
		
		db.beginTransaction(SqlJetTransactionMode.WRITE);
		
		// iterators to loop through the lists of products and crossRefs
		HashMap<String, AbstractSiemensProduct> products	= reader.getProductsMap();
		Collection<CrossReferencePart> crossReferenceItems	= reader.getReferenceParts();
		Collection<AbstractSiemensProduct> productsList		= products.values();
		if (StaticParameters.EXTENDED_VERSION) {
			productsList									= sortProducts(productsList);
		}
		Iterator<AbstractSiemensProduct> productCursor		= productsList.iterator();
		Iterator<CrossReferencePart> crossReferenceCursor	= crossReferenceItems.iterator();
		
		// these 2 are for progress updates
		currentIndex = 1;
		size = products.size() + crossReferenceItems.size();
		
		// loop through the products
		while (productCursor.hasNext()) {
			// Dispatch Progress
			currentIndex++;
			if (progressListener != null) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						ProgressEvent event = new ProgressEvent(currentIndex, size);
						progressListener.handleProgress(event);
					}
				});
			}
			
			// Insert Data
			AbstractSiemensProduct product = productCursor.next();
			try {
				int productType			= SiemensProductUtil.getProductType(product);
				int extendedProductType = SiemensProductUtil.getExtendedProductType(product);

				// Write Product
				if (StaticParameters.EXTENDED_VERSION) {
					productsTable.insert(productType, extendedProductType, product.getPartNumber(), product.getActualPartNumber(), product.getSapPartNumber(), product.getDescription(), 
							product.getManufacturer(), product.getVendor(), product.isObsolete(), product.isVirtualPart(), product.isAssemblyOnly(), product.getPrice(), product.getSubProductPartNumber(), 
							product.getSubProductPrice(), ((product.getLastModified() == null) ? (new Date()).getTime() : product.getLastModified().getTime()));
				}
				else {
					productsTable.insert(productType, /*extendedProductType,*/ product.getPartNumber(), product.getActualPartNumber(), product.getSapPartNumber(), product.getDescription(), 
							product.getManufacturer(), product.getVendor(), product.isObsolete(), product.isVirtualPart(), product.isAssemblyOnly(), product.getPrice(), product.getSubProductPartNumber(), 
							product.getSubProductPrice(), ((product.getLastModified() == null) ? (new Date()).getTime() : product.getLastModified().getTime()));
				}
				
				// Insert Documents
				if (product.getDocumentMaterial() != null) {
					for (SiemensDocumentMaterial document : product.getDocumentMaterial()) {
						documentTable.insert(product.getPartNumber(), document.getName(), document.getDescription(), 
								document.getMaterialType().toString(), document.isIsImage(), document.getFullPath());
					}
				}
				
				// Write Valves
				if (product instanceof SiemensValve) {
					SiemensValve valve = (SiemensValve) product;
					valveTable.insertByFieldNames(getValveMap(valve));
				}
				
				// Write Valve Actuator
				if (product instanceof SiemensActuator) {
					SiemensActuator actuator = (SiemensActuator) product;
					if (actuator.isValveActuator()) {
						actuatorTable.insertByFieldNames(getActuatorMap(actuator));
					}
				}
				
				// Write Assembly
				if (product instanceof SiemensAssembly) {
					// Insert Assembly
					SiemensAssembly assembly = (SiemensAssembly) product;
					if (assembly.getNormalPosition() != null) {
						assemblyTable.insert(assembly.getPartNumber(), assembly.getValve().getPartNumber(), assembly.getActuator().getPartNumber(), 
								assembly.getNormalPosition().toFlag(), assembly.getThreeWayFunction(), assembly.getControlSignal().toFlag(), assembly.getCloseOff());
					}
					else {
						assemblyTable.insert(assembly.getPartNumber(), assembly.getValve().getPartNumber(), assembly.getActuator().getPartNumber(), 
								0, assembly.getThreeWayFunction(), assembly.getControlSignal().toFlag(), assembly.getCloseOff());
					}
				}
				
				// Write Damper Actuator
				if (product instanceof SiemensActuator) {
					SiemensActuator damper = (SiemensActuator) product;
					if (damper.isDamperActuator()) {
						// Insert Damper Actuator
						dampersTable.insert(damper.getPartNumber(), damper.getDamperMetadata().getType().toFlag(), 
								damper.getDamperMetadata().getTorque(), damper.getDamperMetadata().getControlSignal().toFlag(),
								damper.getDamperMetadata().getSystemSupply().toFlag(), damper.getDamperMetadata().getPlenumRating().toFlag(),
								damper.getDamperMetadata().getPositionFeedback().toFlag(), damper.getDamperMetadata().getAuxilarySwitches().toFlag(),
								damper.getDamperMetadata().getScalability().toFlag());
						
						// Write Dampers and Accessories cross reference Table
						Iterator<String> accessoryCursor = damper.getDamperMetadata().getAcceptedAccessories().iterator();
						while (accessoryCursor.hasNext()) {
							String accessoryPartNumber = accessoryCursor.next();
							damperAccessoryCrossTable.insert(null, damper.getPartNumber(), accessoryPartNumber);
						}
					}
				}
				
				// Write Pneumatic (Actuator)
				if (product instanceof SiemensPneumatic) {
					// Insert Pneumatic (Actuator)
					SiemensPneumatic pneumatic = (SiemensPneumatic) product;
					pneumaticTable.insertByFieldNames(getPneumaticMap(pneumatic.getPneumaticMetadata()));
					
					// Write Pneumatic and Accessories cross reference Table
					Iterator<String> accessoryCursor = pneumatic.getPneumaticMetadata().getAcceptedAccessories().iterator();
					while (accessoryCursor.hasNext()) {
						String accessoryPartNumber = accessoryCursor.next();
						pneumaAccessoryCrossTable.insert(null, pneumatic.getPartNumber(), accessoryPartNumber);
					}
				}
				
				// Write Accessory
				if (product instanceof SiemensAccessory) {
					SiemensAccessory accessory = (SiemensAccessory) product;
					accessoryTable.insertByFieldNames(getAccessoryMap(accessory.getAccessoryMetadata()));
				}
			}
			catch (Exception ex) {
				VSSTLogger.Error("Error Storing Product: " + product.getPartNumber() + " : " + ex.getMessage());
				//db.rollback();
			}
		}
		
		// loop through the cross references
		while (crossReferenceCursor.hasNext()) {
			// Dispatch Progress
			currentIndex++;
			if (progressListener != null) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						ProgressEvent event = new ProgressEvent(currentIndex, size);
						progressListener.handleProgress(event);
					}
				});
			}
			
			// Insert Data
			CrossReferencePart crossReferenceItem = crossReferenceCursor.next();
			try {
				// Insert Cross Reference
				referenceTable.insert(crossReferenceItem.getCompetitorPartNumber(), crossReferenceItem.getCompetitorName(), 
						crossReferenceItem.getSiemensPartNumber(), crossReferenceItem.getSubProductPartNumber(), crossReferenceItem.getActualPartNumber());
			}
			catch(Exception cEx) {
				VSSTLogger.Error("Error Storing CrossReference: " + crossReferenceItem.getCompetitorPartNumber() + " : " + cEx.getMessage());
			}
		}
		
		// commit and close the database
		db.commit();
		db.close();
	}
    
    
    /**
     * Convert damper model to a hashmap - property names are keys and property values are values
     */
    private HashMap<String, Object> getProductMap(SiemensActuator damper)
    {
        HashMap<String, Object> damperItem = new HashMap<>();
        return damperItem;
    }
    
    
	/**
	 * Convert actuator model to a hashmap - property names are keys and property values are values
	 */
	private HashMap<String, Object> getActuatorMap(SiemensActuator actuator)
	{
		HashMap<String, Object> actuatorItem = new HashMap<>();
		actuatorItem.put("partNumber",			actuator.getPartNumber());
		actuatorItem.put("motorType",			actuator.getActuatorMetadata().getMotorType().toFlag());
		actuatorItem.put("positioner",			actuator.getActuatorMetadata().getPositioner().toFlag());
		actuatorItem.put("controlSignal",		actuator.getActuatorMetadata().getControlSignal().toFlag());
		//actuatorItem.put("closeOff",			actuator.getMetadata().getCloseOffPressure());      close off is an assembly parameter
		actuatorItem.put("supplyVoltage",		actuator.getActuatorMetadata().getSupplyVoltage());
		actuatorItem.put("hasHeater",			actuator.getActuatorMetadata().hasHeater());
		actuatorItem.put("hasManualOverride",	actuator.getActuatorMetadata().hasManualOverride());
		
		if (actuator.getActuatorMetadata().getButterflyConfiguration() != 0)
			actuatorItem.put("butterflyConfig", String.valueOf(actuator.getActuatorMetadata().getButterflyConfiguration()));
		
		if (actuator.getActuatorMetadata().getEndSwitch() != null)
			actuatorItem.put("endSwitch",		actuator.getActuatorMetadata().getEndSwitch().toFlag());
		
		if (actuator.getActuatorMetadata().getSpringReturn() != null)
			actuatorItem.put("safetyFunction",	actuator.getActuatorMetadata().getSpringReturn().toFlag());
		
		return actuatorItem;
	}
	
	
    /**
     * Convert damper model to a hashmap - property names are keys and property values are values
     */
    private HashMap<String, Object> getAssemblyMap(SiemensActuator damper)
    {
        HashMap<String, Object> damperItem = new HashMap<>();
        return damperItem;
    }

	
	/**
	 * Convert valve model to hashmap - property names are keys, property values are values
	 */
	private HashMap<String, Object> getValveMap(SiemensValve valve) 
	{
        if (valve.getPartNumber().equals("599-07310") || valve.getValveMetadata().getPartNumber().equals("599-07310") ) {
            VSSTLogger.Debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  " + "valve " + valve.getPartNumber() + " (" + valve.getValveMetadata().getPartNumber() + ") " + 
                    "   has normalState: " + valve.getValveMetadata().getNormalState().toString() + " " + valve.getValveMetadata().getNormalState().toFlag());
        }
		HashMap<String, Object> valveItem = new HashMap<>();
		valveItem.put("partNumber",		valve.getPartNumber());
		valveItem.put("size",			valve.getValveMetadata().getSize());
		valveItem.put("medium",			valve.getValveMetadata().getMedium());
		valveItem.put("mixingDiverting", valve.getValveMetadata().getMixingDiverting());
		valveItem.put("cvu",			valve.getValveMetadata().getCvu());
		valveItem.put("cvl",			valve.getValveMetadata().getCvl());
		
		if (valve.getValveMetadata().getPattern() != null)
			valveItem.put("pattern",	valve.getValveMetadata().getPattern().toFlag());
		
		valveItem.put("failSafeU",		valve.getValveMetadata().getFailSafeU());
		//valveItem.put("failSafeL",	valve.getMetadata().getFailSafeL());
		
		if (valve.getValveMetadata().getConnection() != null)
			valveItem.put("connection", valve.getValveMetadata().getConnection().toFlag());
		
		if (valve.getValveMetadata().getType() != null)
			valveItem.put("type",		valve.getValveMetadata().getType().toFlag());

		valveItem.put("seatStyle",		valve.getValveMetadata().getSeatStyle());
		
		if (valve.getValveMetadata().getFlowChar() != null)
			valveItem.put("flowChar",	valve.getValveMetadata().getFlowChar().toFlag());
		
		valveItem.put("maxTemp",		valve.getValveMetadata().getMaxTemp());
		valveItem.put("maxPress",		valve.getValveMetadata().getMaxPress());
		valveItem.put("maxDPWater",		valve.getValveMetadata().getMaxDPWater());
		valveItem.put("maxDPSteam",		valve.getValveMetadata().getMaxDPSteam());
		valveItem.put("maxIPWater",		valve.getValveMetadata().getMaxIPWater());
		valveItem.put("maxIPSteam",		valve.getValveMetadata().getMaxIPSteam());
		valveItem.put("maxPCTGly",		valve.getValveMetadata().getMaxPCTGly());
		valveItem.put("minGPM",			valve.getValveMetadata().getMinGPM());
		valveItem.put("maxGPM",			valve.getValveMetadata().getMaxGPM());
		valveItem.put("presetGPM",		valve.getValveMetadata().getPresetGPM());
		//valveItem.put("closeSlope",	valve.getMetadata().getCloseSlope());
		// valveItem.put("closeInt",		valve.getMetadata().getCloseInterval());
		//valveItem.put("motorType",	valve.getMetadata().getMotorType().toFlag());
		//valveItem.put("positioner",	valve.getMetadata().isPositioner());
		//valveItem.put("controlSignal", valve.getMetadata().getControlSig().toFlag());
		valveItem.put("ambientTemp",	valve.getValveMetadata().getAmbientTem());
		valveItem.put("model",			valve.getModel());
		
		if (valve.getValveMetadata().getPlugMaterial() != null)
			valveItem.put("plugMaterial",valve.getValveMetadata().getPlugMaterial().toFlag());
		
		if (valve.getValveMetadata().getPackingMaterial() != null)
			valveItem.put("packingMaterial", valve.getValveMetadata().getPackingMaterial().toFlag());
		
		valveItem.put("stemMaterial",	valve.getValveMetadata().getStemMaterial());
		valveItem.put("bodyMaterial",	valve.getValveMetadata().getBodyMaterial());
		valveItem.put("discMaterial",	valve.getValveMetadata().getDiscMaterial());
		valveItem.put("seatMaterial",	valve.getValveMetadata().getSeatMaterial());
		//valveItem.put("closeOff",		valve.getMetadata().getCloseOff());
		valveItem.put("thread",			valve.getValveMetadata().getThread());
		valveItem.put("kvsu",			valve.getValveMetadata().getKvsu());
		valveItem.put("kvsl",			valve.getValveMetadata().getKvsl());
		
		if (valve.getValveMetadata().getDiscType() != null)
			valveItem.put("discType",	valve.getValveMetadata().getDiscType().toFlag());

		if (valve.getValveMetadata().getLastModified() != null)
			valveItem.put("lastModified", valve.getValveMetadata().getLastModified().getTime());

		if (valve.getValveMetadata().getNormalState() != null)
			valveItem.put("normalState", valve.getValveMetadata().getNormalState().toFlag());

		return valveItem;
	}
	
    
    /**
     * Convert damper model to a hashmap - property names are keys and property values are values
     */
    private HashMap<String, Object> getDamperMap(SiemensActuator damper)
    {
        HashMap<String, Object> damperItem = new HashMap<>();
        return damperItem;
    }

	
	/**
	 * Convert pneumatic metadata to hashmap - property names are keys, property values are values
	 */
	private HashMap<String, Object> getPneumaticMap(PneumaticMetadata pneumatic) 
	{
		HashMap<String, Object> pneumaticItem = new HashMap<>();
		pneumaticItem.put("partNumber",			pneumatic.getPartNumber());
		pneumaticItem.put("type",				pneumatic.getType().toFlag());
		pneumaticItem.put("stroke",				pneumatic.getStroke().toFlag());
		pneumaticItem.put("housing",			pneumatic.getHousing().toFlag());
		pneumaticItem.put("stem",				pneumatic.getStem().toFlag());
		pneumaticItem.put("diaphArea",			pneumatic.getDiaphArea().toFlag());
		pneumaticItem.put("diaphMaterial",		pneumatic.getDiaphMaterial().toFlag());
		pneumaticItem.put("operatingTemp",		pneumatic.getOperatingTemp().toFlag());
		pneumaticItem.put("isActuator",			pneumatic.getIsActuator().toFlag());
		pneumaticItem.put("clevis",				pneumatic.getClevis().toFlag());
		pneumaticItem.put("bracket",			pneumatic.getBracket().toFlag());
		pneumaticItem.put("ballJoint",			pneumatic.getBallJoint().toFlag());
		pneumaticItem.put("pivot",				pneumatic.getPivot().toFlag());
		pneumaticItem.put("posRelay",			pneumatic.getPosRelay().toFlag());
		pneumaticItem.put("fwdTravelStops",		pneumatic.getFwdTravelStops().toFlag());
		pneumaticItem.put("mountingStyle",		pneumatic.getMountingStyle().toFlag());
		pneumaticItem.put("ULCert",				pneumatic.getULCert().toFlag());
		pneumaticItem.put("springRange",		pneumatic.getSpringRange().toFlag());
		pneumaticItem.put("baseReplacement",	pneumatic.getBaseReplace());
		pneumaticItem.put("maxThrust_18",		pneumatic.getMaxThrust_18());
		pneumaticItem.put("maxThrust_no",		pneumatic.getMaxThrust_no());
		return pneumaticItem;
	}

	/**
	 * Convert accessory metadata to hashmap - property names are keys, property values are values
	 */
	private HashMap<String, Object> getAccessoryMap(AccessoryMetadata accessory) 
	{
		HashMap<String, Object> accessoryItem = new HashMap<>();
		accessoryItem.put("partNumber",			accessory.getPartNumber());
		accessoryItem.put("fromDamper",			accessory.getFromDamper());
		accessoryItem.put("fromPneumatic",		accessory.getFromPneumatic());
		return accessoryItem;		
	}	
	
	
	private Collection<AbstractSiemensProduct> sortProducts(Collection<AbstractSiemensProduct> currentProducts) 
	{
		// the list where all the products end up
		ArrayList<AbstractSiemensProduct> newProducts = new ArrayList<>();
		
		// the temporary list to store the products that will be put at the end (dampers and accessories)
		ArrayList<AbstractSiemensProduct> tmpProducts = new ArrayList<>();
		
		// the temporary list to store the products that will be put at the end (pneumatics and accessories)
		ArrayList<AbstractSiemensProduct> lastProducts = new ArrayList<>();
		
		// loop through all the current products and test where to put them
		Iterator<AbstractSiemensProduct> iterator1 = currentProducts.iterator();
        Iterator<AbstractSiemensProduct> iterator2;
		while (iterator1.hasNext()) {
			AbstractSiemensProduct prod = iterator1.next();
			if (prod instanceof SiemensAccessory) {
				SiemensAccessory accessory = (SiemensAccessory) prod;
				if (accessory.getAccessoryMetadata().getFromDamper() == 1) {
					tmpProducts.add(prod);
				}
				if (accessory.getAccessoryMetadata().getFromPneumatic() == 1) {
					lastProducts.add(prod);
				}				
			}
			else if (prod instanceof SiemensActuator) {
				SiemensActuator act = (SiemensActuator) prod;
				if (act.isDamperActuator() && !act.isValveActuator()) {
					tmpProducts.add(prod);					
				}
				else {
					newProducts.add(prod);				
				}
			}
			else if (prod instanceof SiemensPneumatic) {
				lastProducts.add(prod);
			}
			else {
				newProducts.add(prod);
			}
		}
		
        // order the products based on partNumber
		ProdComp comp = new ProdComp();
		newProducts.sort(comp);
		tmpProducts.sort(comp);
		lastProducts.sort(comp);
		
		// add the products from tmp at the end
        newProducts.addAll(tmpProducts);
		// add the products from last at the end
        newProducts.addAll(lastProducts);
        
        // search current in new
		iterator1 = currentProducts.iterator();
		boolean equal1 = true;
		while (iterator1.hasNext()) {
			AbstractSiemensProduct prod1 = iterator1.next();
			boolean found = false;
			iterator2 = newProducts.iterator();
			while (iterator2.hasNext()) {
				AbstractSiemensProduct prod2 = iterator2.next();
				if (prod1.getPartNumber().equals(prod2.getPartNumber())) {
					found = true;
					break;
				}
			}
			if (!found) {
				equal1 = false;
				break;
			}
		}
		// search new in current
		iterator1 = newProducts.iterator();
		boolean equal2 = true;
		while (iterator1.hasNext()) {
			AbstractSiemensProduct prod1 = iterator1.next();
			boolean found = false;
			iterator2 = currentProducts.iterator();
			while (iterator2.hasNext()) {
				AbstractSiemensProduct prod2 = iterator2.next();
				if (prod1.getPartNumber().equals(prod2.getPartNumber())) {
					found = true;
					break;
				}
			}
			if (!found) {
				equal2 = false;
				break;
			}
		}
				
		VSSTLogger.Info("---------------- Sort the products ----------------------");
		VSSTLogger.Info("-----> original products size " + currentProducts.size());
		VSSTLogger.Info("-----> moved products size    " + tmpProducts.size());
		VSSTLogger.Info("-----> final products size    " + newProducts.size());
		VSSTLogger.Info("-----> the two lists are equal: " + equal1 + " - " + equal2);
		VSSTLogger.Info("---------------- End Sort -------------------------------");
		return newProducts;
	}
	
	
	
	private static class ProdComp implements Comparator<AbstractSiemensProduct> {
	    public int compare(AbstractSiemensProduct o1, AbstractSiemensProduct o2) {
	        return o1.getActualPartNumber().compareTo(o2.getActualPartNumber());
	    }
	}
}