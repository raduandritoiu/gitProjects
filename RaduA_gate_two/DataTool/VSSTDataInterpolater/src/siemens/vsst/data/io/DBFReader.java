package siemens.vsst.data.io;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xBaseJ.xBaseJException;
import org.xml.sax.SAXException;

import siemens.vsst.data.enumeration.SiemensDocumentMaterialType;
import siemens.vsst.data.interfaces.ISiemensCrossParser;
import siemens.vsst.data.interfaces.ISiemensDataParser;
import siemens.vsst.data.interfaces.ISiemensXlsParser;
import siemens.vsst.data.models.AbstractSiemensProduct;
import siemens.vsst.data.models.CrossReferencePart;
import siemens.vsst.data.models.SiemensAccessory;
import siemens.vsst.data.models.SiemensActuator;
import siemens.vsst.data.models.SiemensAssembly;
import siemens.vsst.data.models.SiemensDocumentMaterial;
import siemens.vsst.data.models.SiemensMiscProduct;
import siemens.vsst.data.models.SiemensPneumatic;
import siemens.vsst.data.models.SiemensValve;
import siemens.vsst.data.parsers.cross.DamperCrossParser;
import siemens.vsst.data.parsers.cross.PneumaticCrossParser;
import siemens.vsst.data.parsers.cross.ValveCrossParser;
import siemens.vsst.data.parsers.dbs.ProductDBParser;
import siemens.vsst.data.parsers.dbs.ValveDBParser;
import siemens.vsst.data.parsers.dbs.ValveMediumDBParser;
import siemens.vsst.data.parsers.dbs.ValveMixDivDBParser;
import siemens.vsst.data.parsers.remove.RemoveValves;
import siemens.vsst.data.parsers.utils.StaticParameters;
import siemens.vsst.data.parsers.utils.VSSTLogger;
import siemens.vsst.data.parsers.xls.AccessoryDamperXlsParser;
import siemens.vsst.data.parsers.xls.DamperXlsParser;
import siemens.vsst.data.parsers.xls.PneumaticXlsParser;
import siemens.vsst.data.parsers.xls.PricesXlsParser;


/**
 * DBF File reader
 * @author michael
 */
public class DBFReader
{
	public static final DateFormat dateFormatter    = DateFormat.getDateInstance(DateFormat.SHORT); // Date Formatter
	
	// Made public static for sub product lookup
	// Product table that holds Valves, Actuators, Assemblies
	public static HashMap<String, AbstractSiemensProduct> productMap;
	// Cross Reference Tables
	private final ArrayList<CrossReferencePart> referenceParts;               
	
	
	public DBFReader() {
		productMap		= new HashMap<>();
		referenceParts	= new ArrayList<>();
	}
	
	
	/**
	 * Returns parsed siemens products
	 */
	public Collection<AbstractSiemensProduct> getProducts() {
		return productMap.values();
	}
	
	
	public HashMap<String, AbstractSiemensProduct> getProductsMap() {
		return productMap;
	}
	
	
	public ArrayList<CrossReferencePart> getReferenceParts() {
		return referenceParts;
	}
	
	
	/**
	 * Begin Importing data from files in the folder.
	 *
	 * VALVE.DBF, VMEDIUM.DBF, VMIXDIV.DBF, PRODUCT.DBF, STEP_Asset_Xref.xml
	 */
	public void startImport(String basePath) throws xBaseJException, IOException, XMLStreamException
	{
		VSSTLogger.Info("==== Reading from " + basePath + " \t - EXTENDED_MODE : " +  StaticParameters.EXTENDED_VERSION + "  ====");
		
		// ========================================
		// === Import Cross Reference Items =======
		// read Valves cross reference items
		readCrossReferenceItems("Valve", basePath + "/ValveReference.xls", referenceParts, ValveCrossParser.newInstance());
		// read Dampers cross reference items
		if (StaticParameters.EXTENDED_VERSION) {
			readCrossReferenceItems("Damper", basePath + "/DAC_Reference.xls", referenceParts, DamperCrossParser.newInstance());
		}
		// read Pneumatics cross reference items
		if (StaticParameters.EXTENDED_VERSION) {
			readCrossReferenceItems("Pneumatics", basePath + "/Pneumatic_Reference.xls", referenceParts, PneumaticCrossParser.newInstance());
		}
		
		
		// ========================================
		// === Import the Products by categories ==
		// read Valves, Actuators and Assemblies database 
		readDbFile("Valves", basePath + "/VALVE.DBF", productMap, false, ValveDBParser.newInstance());
		// read Damper Accessories database
		if (StaticParameters.EXTENDED_VERSION) {
			readXLSFile("Kits", basePath + "/DAC_Kit.xls", productMap, false, AccessoryDamperXlsParser.newInstance());
		}		
		// read Damper database
		if (StaticParameters.EXTENDED_VERSION) {
			readXLSFile("Dampers", basePath + "/DAC_Damper.xls", productMap, false, DamperXlsParser.newInstance());
		}
		// read Pneumatic database
		if (StaticParameters.EXTENDED_VERSION) {
			readXLSFile("Pneumatics", basePath + "/Pneumatic.xls", productMap, false, PneumaticXlsParser.newInstance());
		}
		// test Pneumatic Accessories Map
		if (StaticParameters.EXTENDED_VERSION) {
			DBFStatistics.testPneumaticAccessoriesMap(productMap, productMap);
		}
		// add Accessories for Pneumatics from the Pneumatics themselves
		if (StaticParameters.EXTENDED_VERSION) {
			addAccessoriesFromPneumatics(productMap);
		}
		
		
		// ==========================================================
		// test products after they were added
		testProductsAfterImport();
		
		
		// ==========================================================
		// HACK: Siemes wanted to manually change and remove products
		DBFManipulation.removeProducts(productMap);
		DBFManipulation.changeProducts(productMap);
		
		
		// ==========================================================
		// === Import information related to already added Products =
		// read Products Database (This gives us the rest of the information on all products)
		readDbFile("Products", basePath + "/PRODUCT.DBF", productMap, true, ProductDBParser.newInstance());
		// read Medium Database. This gives us the valve's medium flags
//		readXLSFile("Prices_old", basePath + "/Prices_old.xls", productMap, true, PricesXlsParser.newInstance());
		readXLSFile("Prices_new", basePath + "/Prices_new.xls", productMap, true, PricesXlsParser.newInstance());

		
		// ==================================================
		// test products after removing
		testProductsAfterUpdate();
		
		
		// read Medium Database. This gives us the valve's medium flags
		readDbFile("Medium", basePath + "/VMEDIUM.DBF", productMap, true, ValveMediumDBParser.newInstance());
		// read Mixing Diverting Database. This gives us the valves' mixing/diverting flags
		readDbFile("Mixing", basePath + "/VMIXDIV.DBF", productMap, true, ValveMixDivDBParser.newInstance());


		// ========================================
		// === Remove products ====================
		// remove Emerson PNs
		if (StaticParameters.EXTENDED_VERSION) {
			removeXLSFile("Emerson PNs", basePath + "/Emerson_PNs.xls", productMap, new RemoveValves());
		}		
		
		
		// ==================================================
		// = Read XML Data - to delete unorderable products =
		VSSTLogger.Info("--------------- Read Step Extract XML (for products) ----------------");
		try {
			readVSSTStepFile(new File(basePath + "/VSST_STEP_Extract.xml"), productMap);
		}
		catch (ParserConfigurationException pException) {
			VSSTLogger.Info("Parser Config Error: " + pException.getMessage());
		}
		catch (SAXException sException) {
			VSSTLogger.Info("Sax Error: " + sException.getMessage());
		}

		
		// ==================================================
		// test products after removing
		testProductsAfterRemove();
		
		
		// Log the products summary
		DBFStatistics.testProducts(productMap);
	}
	
	
	/**
	 * Reads Cross References from an excel file.
	 */
	private void readCrossReferenceItems(String title, String filePath, ArrayList<CrossReferencePart> currentReferenceParts, ISiemensCrossParser parser)
	{
		VSSTLogger.Info("--------------- Read " + title + " Cross References excel ----------------");
		try {
			parser.parseCrossItems(filePath, currentReferenceParts);
		}
		catch (Exception crossReferenceError) {
			VSSTLogger.Error("" + title + " CrossReference Error: " + crossReferenceError.getMessage());
		}
		VSSTLogger.Info("--- " + currentReferenceParts.size() + " " + title + " cross refs");
		VSSTLogger.Info("--------------- Done " + title + " Cross References excel ----------------");
		VSSTLogger.Info("/");
		VSSTLogger.Info(" ");	
	}
	
	
	/**
	 * Reads DBF File and Instantiates an Abstract Siemens Product from the DBF
	 */
	private void readDbFile(String title, String filePath, HashMap<String, AbstractSiemensProduct> currentProductMap, boolean isAbstract, ISiemensDataParser parser) 
			throws xBaseJException, IOException
	{
		VSSTLogger.Info("--------------- Read " + title + " DB ----------------");		
		parser.parseDbFile(filePath, currentProductMap, isAbstract);				
		VSSTLogger.Info("--- " + currentProductMap.size() + " products");
		VSSTLogger.Info("--------------- Done " + title + " DB ----------------");
		VSSTLogger.Info("/");
		VSSTLogger.Info(" ");
	}
	
	
	/**
	 * Reads XLS File and Instantiates an Abstract Siemens Product from the DBF
	 */
	private void readXLSFile(String title, String filePath, HashMap<String, AbstractSiemensProduct> currentProductMap, boolean isAbstract, ISiemensXlsParser parser) 
			throws IOException
	{
		VSSTLogger.Info("--------------- Read " + title + " DB ----------------");
		parser.parseXLSFile(filePath, currentProductMap, isAbstract);
		VSSTLogger.Info("--- " + currentProductMap.size() + " products");
		VSSTLogger.Info("--------------- Done " + title + " DB ----------------");
		VSSTLogger.Info("/");
		VSSTLogger.Info(" ");
	}
	
	
	/**
	 * Remove products by reading XLS File
	 */
	private void removeXLSFile(String title, String filePath, HashMap<String, AbstractSiemensProduct> currentProductMap, RemoveValves remover) 
			throws IOException
	{
		VSSTLogger.Info("--------------- Remove " + title + " products ----------------");
		remover.remove(filePath, currentProductMap);
		VSSTLogger.Info("--- " + currentProductMap.size() + " products");
		VSSTLogger.Info("--------------- Done " + title + " products ----------------");
		VSSTLogger.Info("/");
		VSSTLogger.Info(" ");
	}
	
	
	/**
	 * Read XML File Into Products.
	 * Reads fields from Siemens WebShop XML Dump into products in productMap.
	 */
	private void readVSSTStepFile(File file, HashMap<String, AbstractSiemensProduct> currentProductMap) 
			throws XMLStreamException, IOException, ParserConfigurationException, SAXException 
	{
		int removedProdCnt		= 0;
		int removedValvesCnt	= 0;
		int removedActCnt		= 0;
		int removedValveActCnt	= 0;
		int removedDamperActCnt = 0;
		int removedPneumCnt		= 0;
		int removedAssemblyCnt	= 0;
		int removedAccessoryCnt = 0;
		int removedMiscCnt		= 0;

		//FileInputStream stream			= new FileInputStream(file);
		DocumentBuilderFactory domFactory	= DocumentBuilderFactory.newInstance();
		domFactory.setIgnoringElementContentWhitespace(true);
		domFactory.setIgnoringComments(true);
		domFactory.setValidating(false);
		domFactory.setExpandEntityReferences(false);
		
		DocumentBuilder builder		= domFactory.newDocumentBuilder();
		Document doc				= builder.parse(file);
		
		// Filter Non-Existent Product Ids and Get documents
		XPathFactory xFactory		= XPathFactory.newInstance();
		XPath xpath					= xFactory.newXPath();
		
		// This iteration using xpath is slow.
		int currIdx		= 0; // because the loop takes too long, this is to put breakpoints and monitor progress
		int currSize	= currentProductMap.size();
		boolean enableVSSTOutput = false;
		Iterator<AbstractSiemensProduct> productCursor = currentProductMap.values().iterator();
		while (productCursor.hasNext()) {
			currIdx = currIdx + 1;
			try {
				AbstractSiemensProduct product = productCursor.next();
				
				if (product.isVirtualPart()) {
					if (enableVSSTOutput) {
						VSSTLogger.Debug("Step Check: skipping virtual part " + product.getPartNumber());
					}
					continue;
				}
				
				XPathExpression expr	= xpath.compile("//PRODUCT[@partNumber = '" + product.getActualPartNumber() + "']");
				Node result				= (Node) expr.evaluate(doc, XPathConstants.NODE);

				if (result != null) {
					// initialize product documents array
					if (!product.hasDocumentMaterial()) {
						product.setDocumentMaterial(new ArrayList<SiemensDocumentMaterial>());
					}
					
					// Grab Documents
					NodeList children = result.getChildNodes();
					SiemensDocumentMaterial document = null;
					
					for (int i = 0; i < children.getLength(); i++) {
						Node resultItem = children.item(i);

						if (resultItem.getNodeName().equalsIgnoreCase("#text"))
							continue; // White space

						if (resultItem.getNodeName().equalsIgnoreCase("MIME_SOURCE")) {
							// Remove old document if type was unable to be determined
							if (document != null) {
								if (document.getMaterialType() == SiemensDocumentMaterialType.Unknown)
									product.getDocumentMaterial().remove(document);
							}

							// New Document
							String source = resultItem.getTextContent();
							document = new SiemensDocumentMaterial();
                            document.setName(source);
                            document.setPath(source);
                            document.setFullPath(SiemensDocumentMaterial.IMAGE_BASE_PATH + source);
							product.getDocumentMaterial().add(document);
						}
						else if (resultItem.getNodeName().equalsIgnoreCase("MIME_DESCR")) {
							String name = resultItem.getTextContent();
							document.setName(name);
							document.setMaterialType(SiemensDocumentMaterialType.getFromPartInfo(name));
						}
						else if (resultItem.getNodeName().equalsIgnoreCase("MIME_PURPOSE") || resultItem.getNodeName().equalsIgnoreCase("MIME_ALT")) {
							if ((document.getMaterialType() == null) || (document.getMaterialType() == SiemensDocumentMaterialType.Unknown))
								document.setMaterialType(SiemensDocumentMaterialType.getFromDescription(resultItem.getTextContent()));
						}
					}
					
					// Remove Unknown Documents
					for (SiemensDocumentMaterial docMaterial : product.getDocumentMaterial()) {
						if (docMaterial.getMaterialType().equals(SiemensDocumentMaterialType.Unknown))
							product.getDocumentMaterial().remove(docMaterial);
					}
				}
				else {
					// Remove Part Number
					testProductRemove(product);
					
					productCursor.remove();
					if (enableVSSTOutput) {
						VSSTLogger.Debug("Removing: '" + product.getPartNumber() + "' doesn't exist in step.");
					}
					
					removedProdCnt ++;
					if (product instanceof SiemensValve) { 
						removedValvesCnt ++;					
					}
					else if (product instanceof SiemensAccessory) {
						removedAccessoryCnt ++;
					}
					else if (product instanceof SiemensAssembly) {
						removedAssemblyCnt ++;
					}
					else if (product instanceof SiemensActuator) {
						SiemensActuator act = (SiemensActuator) product;
						if (act.isBothActuator()) {
							removedActCnt ++;
						}
						else if (act.isValveActuator()) {
							removedValveActCnt ++;
						}
						else if (act.isDamperActuator()) {
							removedDamperActCnt ++;							
						}
					}
					else if (product instanceof SiemensPneumatic) {
						removedPneumCnt ++;
					}
					else if (product instanceof SiemensMiscProduct) {
						removedMiscCnt ++;
					}
				}
			}
			catch (XPathExpressionException xEx) {
				// Invalid XPath Expression
				if (enableVSSTOutput) {
					VSSTLogger.Error("Invalid XPATH ");
				}
			}
		}
		
		VSSTLogger.Info("-----> Removed Total Products  " + removedProdCnt);
		VSSTLogger.Info("-----> Removed Valves          " + removedValvesCnt);
		VSSTLogger.Info("-----> Removed Both Actutors   " + removedActCnt);
		VSSTLogger.Info("-----> Removed valve Actutors  " + removedValveActCnt);
		VSSTLogger.Info("-----> Removed damper Actutors " + removedDamperActCnt);
		VSSTLogger.Info("-----> Removed pneumatic       " + removedPneumCnt);
		VSSTLogger.Info("-----> Removed Assemblies      " + removedAssemblyCnt);
		VSSTLogger.Info("-----> Removed Accessories     " + removedAccessoryCnt);
		VSSTLogger.Info("-----> Removed Misc            " + removedMiscCnt);
		
		VSSTLogger.Info("--- " + currentProductMap.size() + " producs");
		VSSTLogger.Info("--------------- Done Step Extract XML ----------------");
		VSSTLogger.Info("/");
		VSSTLogger.Info(" ");
	}
		
	
	/**
	 * 
	 */
	private void addAccessoriesFromPneumatics(HashMap<String, AbstractSiemensProduct> currentProductMap)
	{
		HashMap<String, String> accessoriesMap = new HashMap<>();
		
		// go through the Pneumatic products list and find all the Pneumatic Accessories
		Iterator<AbstractSiemensProduct> productCursor = currentProductMap.values().iterator();
		while (productCursor.hasNext()) 
		{
			AbstractSiemensProduct product = productCursor.next();
			if (product instanceof SiemensPneumatic) 
			{
				// get the pneumatic product
				SiemensPneumatic pneumatic = (SiemensPneumatic) product;
				Iterator<String> accessoryCursor = pneumatic.getPneumaticMetadata().getAcceptedAccessories().iterator();
				while (accessoryCursor.hasNext()) 
				{
					String accessoryPartNumber = accessoryCursor.next();
					if (!accessoriesMap.containsKey(accessoryPartNumber)) {
						accessoriesMap.put(accessoryPartNumber, accessoryPartNumber);
					}
				}
			}
		}
		
		// add the non existing Pneumatic Accessories
		int existingAccessories = 0;
		int otherAccessories = 0;
		int addedAccessories = 0;
		Iterator <String> accessoriesCursor = accessoriesMap.values().iterator();
		while (accessoriesCursor.hasNext()) 
		{
			SiemensAccessory accessoryPneumatic = null;
			String accessoryPartNumber = accessoriesCursor.next();
			
			// check if the current accessory part number is already added
			AbstractSiemensProduct currentProduct = currentProductMap.get(accessoryPartNumber);			
			if (currentProduct == null) {
				// create the new accessory
				accessoryPneumatic = new SiemensAccessory();
				accessoryPneumatic.setPartNumber(accessoryPartNumber);				
				currentProductMap.put(accessoryPartNumber, accessoryPneumatic);
				addedAccessories ++;
				VSSTLogger.Info(" added - " + accessoryPartNumber);
			}
			else if (currentProduct instanceof SiemensAccessory) {
				existingAccessories ++;
				accessoryPneumatic = (SiemensAccessory) currentProduct;
			}
			else {
				otherAccessories ++;
			}
			
			// set the flag for penumatics on the accessory
			if (accessoryPneumatic != null) {
				accessoryPneumatic.getAccessoryMetadata().setFromPneumatic(1);				
			}
		}
		
		VSSTLogger.Info("--------------- Add Pneumatic Accessory ----------------");
		VSSTLogger.Info("--- " + currentProductMap.size() + " producs");
		VSSTLogger.Info("--- pneumatic accessories         " + accessoriesMap.size());
		VSSTLogger.Info("--- added accessories             " + addedAccessories);
		VSSTLogger.Info("--- already existing accessories  " + existingAccessories);
		VSSTLogger.Info("--- other produt types            " + otherAccessories);
		VSSTLogger.Info("--------------- Done Pneumatic Accessory Extract ----------------");
		VSSTLogger.Info("/");
		VSSTLogger.Info(" ");
	}
	
	
	
	
	
	/**
	 * Moves all the objects form the sourceMap (means deletes them) to the destinationMap (adds them), 
	 * if they are not already in the destinationMap.
	 */
	private void moveProducts(HashMap<String, AbstractSiemensProduct> sourceMap, HashMap<String, AbstractSiemensProduct> destinationMap) 
	{
		ArrayList<AbstractSiemensProduct> movedProducts = new ArrayList<>();
		
		// go through the source list
		Iterator<AbstractSiemensProduct> productCursor = sourceMap.values().iterator();
		while (productCursor.hasNext()) {
			// if the product s not in the destination list, put it there and remember to delete it later form source
			AbstractSiemensProduct product = productCursor.next();
			if (!destinationMap.containsKey(product.getPartNumber())) {
				destinationMap.put(product.getPartNumber(), product);
				movedProducts.add(product);
			}
		}
		
		// remove moved products from source
		productCursor = movedProducts.iterator();
		while (productCursor.hasNext()) {
			AbstractSiemensProduct product = productCursor.next();
			sourceMap.remove(product.getPartNumber());
		}
	}
	
	
	
	/**/
	private void testProductsAfterImport()
	{
		AbstractSiemensProduct prod = productMap.get("599-04322-2");
		if (prod != null) {
			VSSTLogger.Debug("=============(" + prod.getPartNumber() + ")");
		}
		prod = productMap.get("599-04322-2.5");
		if (prod != null) {
			VSSTLogger.Debug("=============(" + prod.getPartNumber() + ")");
		}
	}
	
	/**/
	private void testProductsAfterUpdate()
	{
		AbstractSiemensProduct prod = productMap.get("599-04322-2");
		if (prod != null) {
			VSSTLogger.Debug("=============(" + prod.getPartNumber() + ")");
		}
		prod = productMap.get("599-04322-2.5");
		if (prod != null) {
			VSSTLogger.Debug("=============(" + prod.getPartNumber() + ")");
		}
	}
	
	/**/
	private void testProductsAfterRemove()
	{
		// test Damper Accessories Map
		if (StaticParameters.EXTENDED_VERSION) {
			DBFStatistics.testDamperAccessoriesMap(productMap, productMap);
		}
	}
	
	/**/
	private void testProductRemove(AbstractSiemensProduct product)
	{
		
	}
}