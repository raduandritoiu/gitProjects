/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.io;

import java.util.HashMap;


import java.util.Iterator;
import siemens.vsst.data.enumeration.SiemensProductUtil;
import siemens.vsst.data.models.AbstractSiemensProduct;
import siemens.vsst.data.models.SiemensAccessory;
import siemens.vsst.data.models.SiemensActuator;
import siemens.vsst.data.models.SiemensAssembly;
import siemens.vsst.data.models.SiemensMiscProduct;
import siemens.vsst.data.models.SiemensPneumatic;
import siemens.vsst.data.models.SiemensValve;
import siemens.vsst.data.parsers.utils.VSSTLogger;

/**
 *
 * @author User
 */
public class DBFStatistics 
{
	/**
	 * Tests and prints some statistics/info about the products inside the product map.
	 * 
	 * @param currentProductmap 
	 */
	static public void testProducts(HashMap<String, AbstractSiemensProduct> currentProductmap) 
	{
		// total products counters
		int prodCnt = 0;
		int valvesCnt = 0;
		int actCnt = 0;
		int valveActCnt = 0;
		int damperActCnt = 0;
		int assemblyCnt = 0;
		int accessoryCnt = 0;
		int miscCnt = 0;
		int pneumaticCnt = 0;
		
		// products initialized from Product DBF counters
		int valvesProdInitCnt = 0;
		int actProdInitCnt = 0;
		int valveActProdInitCnt = 0;
		int damperActProdInitCnt = 0;
		int assemblyProdInitCnt = 0;
		int accessoryProdInitCnt = 0;
		int miscProdInitCnt = 0;
		int pneumaticProdInitCnt = 0;
		
		// products initialized from their speciffic DBF counters
		int valvesSpecifficInitCnt = 0;
		int actSpecifficInitCnt = 0;
		int valveActSpecifficInitCnt = 0;
		int damperActSpecifficInitCnt = 0;
		int assemblySpecifficInitCnt = 0;
		int accessorySpecifficInitCnt = 0;
		int miscSpecifficInitCnt = 0;
		int pneumaticSpecifficInitCnt = 0;
		
		Iterator<AbstractSiemensProduct> productCursor = currentProductmap.values().iterator();
		while (productCursor.hasNext()) {
			// if the product s not in the destination list, put it there and remember to delete it later form source
			AbstractSiemensProduct product = productCursor.next();
			
			prodCnt ++;
			if (product instanceof SiemensValve) { 
				valvesCnt ++;
				if (product.isProcessed()) {
					valvesSpecifficInitCnt ++;
				}
				if (product.isProductProcessed()) {
					valvesProdInitCnt ++;
				}
			}
			else if (product instanceof SiemensAccessory) {
				accessoryCnt ++;
				if (product.isProcessed()) {
					accessorySpecifficInitCnt ++;
				}
				if (product.isProductProcessed()) {
					accessoryProdInitCnt ++;
				}
			}
			else if (product instanceof SiemensAssembly) {
				assemblyCnt ++;
				if (product.isProcessed()) {
					assemblySpecifficInitCnt ++;
				}
				if (product.isProductProcessed()) {
					assemblyProdInitCnt ++;
				}
			}
			else if (product instanceof SiemensActuator) {
				SiemensActuator act = (SiemensActuator) product;
				if (act.isBothActuator()) {
					actCnt ++;
					if (product.isProcessed()) {
						actSpecifficInitCnt ++;
					}
					if (product.isProductProcessed()) {
						actProdInitCnt ++;
					}
				}
				else if (act.isValveActuator()) {
					valveActCnt ++;
					if (product.isProcessed()) {
						valveActSpecifficInitCnt ++;
					}
					if (product.isProductProcessed()) {
						valveActProdInitCnt ++;
					}
				}
				else if (act.isDamperActuator()) {
					damperActCnt ++;
					if (product.isProcessed()) {
						damperActSpecifficInitCnt ++;
					}
					if (product.isProductProcessed()) {
						damperActProdInitCnt ++;							
					}
				}
			}
			else if (product instanceof SiemensMiscProduct) {
				miscCnt ++;
				if (product.isProcessed()) {
					miscSpecifficInitCnt ++;
				}
				if (product.isProductProcessed()) {
					miscProdInitCnt ++;
				}
			}
			else if (product instanceof SiemensPneumatic) {
				pneumaticCnt ++;
				if (product.isProcessed()) {
					pneumaticSpecifficInitCnt ++;
				}
				if (product.isProductProcessed()) {
					pneumaticProdInitCnt ++;
				}
			}
		}
		
		// start logging
		VSSTLogger.Info("--------------- Products Summary ----------------");
		
		VSSTLogger.Info("-----> Total Products  " + prodCnt);
		VSSTLogger.Info("-----> Valves        " + valvesCnt);
		VSSTLogger.Info("-----> Valves init   " + valvesSpecifficInitCnt);
		VSSTLogger.Info("-----> Valves init P " + valvesProdInitCnt);		
		VSSTLogger.Info(" ");
		VSSTLogger.Info("-----> Both Actutors        " + actCnt);
		VSSTLogger.Info("-----> Both Actutors init   " + actSpecifficInitCnt);
		VSSTLogger.Info("-----> Both Actutors init P " + actProdInitCnt);
		VSSTLogger.Info(" ");		
		VSSTLogger.Info("-----> valve Actutors        " + valveActCnt);
		VSSTLogger.Info("-----> valve Actutors init   " + valveActSpecifficInitCnt);
		VSSTLogger.Info("-----> valve Actutors init P " + valveActProdInitCnt);
		VSSTLogger.Info(" ");		
		VSSTLogger.Info("-----> damper Actutors        " + damperActCnt);
		VSSTLogger.Info("-----> damper Actutors init   " + damperActSpecifficInitCnt);
		VSSTLogger.Info("-----> damper Actutors init P " + damperActProdInitCnt);
		VSSTLogger.Info(" ");		
		VSSTLogger.Info("-----> Assemblies        " + assemblyCnt);
		VSSTLogger.Info("-----> Assemblies init   " + assemblySpecifficInitCnt);
		VSSTLogger.Info("-----> Assemblies init P " + assemblyProdInitCnt);
		VSSTLogger.Info(" ");		
		VSSTLogger.Info("-----> Accessory        " + accessoryCnt);
		VSSTLogger.Info("-----> Accessory init   " + accessorySpecifficInitCnt);
		VSSTLogger.Info("-----> Accessory init P " + accessoryProdInitCnt);
		VSSTLogger.Info(" ");		
		VSSTLogger.Info("-----> Misc        " + miscCnt);
		VSSTLogger.Info("-----> Misc init   " + miscSpecifficInitCnt);
		VSSTLogger.Info("-----> Misc init P " + miscProdInitCnt);
		VSSTLogger.Info(" ");		
		VSSTLogger.Info("-----> Pneumatic        " + pneumaticCnt);
		VSSTLogger.Info("-----> Pneumatic init   " + pneumaticSpecifficInitCnt);
		VSSTLogger.Info("-----> Pneumatic init P " + pneumaticProdInitCnt);
		
		VSSTLogger.Info("--------------- End Summary ----------------");
		VSSTLogger.Info("/");
		VSSTLogger.Info(" ");
	}
	
	
	
	/**
	 * Loop through every dampers and accessories founds and finds out, what kits that may be used/accepted by dampers were not found;
	 * and what kits that were found are not used/accepted by any damper.
	 * 
	 * OBS: Does not change the input product maps!!! 
	 *		This is just for Test.
	 * 
	 * @param damperMap
	 * @param accessoryMap
	 */
	static public void testDamperAccessoriesMap(HashMap<String, AbstractSiemensProduct> damperMap, HashMap<String, AbstractSiemensProduct> accessoryMap) 
	{
		VSSTLogger.Info("--------------- Test for Damper Kit ----------------");
		// create a list to store all used Kits just to see at the end if there were any defined but not used
		HashMap<String, String> usedKitMap = new HashMap<>();
		
		// loop through all the dampers
		Iterator<AbstractSiemensProduct> damperCursor = damperMap.values().iterator();
		while (damperCursor.hasNext()) 
		{
			AbstractSiemensProduct product = damperCursor.next();
			if (product instanceof SiemensActuator) 
			{
				SiemensActuator damper = (SiemensActuator) product;
				if (damper.isDamperActuator()) 
				{
					// search for each accepted Kits by part number and if not found create a new Damper Kit
					Iterator <String> existingKitCursor = damper.getDamperMetadata().getAcceptedAccessories().iterator();
					while (existingKitCursor.hasNext()) {
						String kitPartNumber = existingKitCursor.next();

						// did not find the used Kit in the map created from the database
						if (!accessoryMap.containsKey(kitPartNumber)) {
							VSSTLogger.Debug("------> did not find kit " + kitPartNumber);
						}

						// gather all the used kits in one place just to test 
						if (!usedKitMap.containsKey(kitPartNumber)) {
							usedKitMap.put(kitPartNumber, kitPartNumber);
						}
					}
				}
			}
		}
		
		VSSTLogger.Info("-----> KITS from dampers  " + usedKitMap.size());
		VSSTLogger.Info("-----> KITS from database " + accessoryMap.size());
		Iterator <AbstractSiemensProduct> kitCursor = accessoryMap.values().iterator();
		while (kitCursor.hasNext()) {
			AbstractSiemensProduct kit = kitCursor.next();
			if ((kit instanceof SiemensAccessory) && !usedKitMap.containsKey(kit.getPartNumber())) {
				VSSTLogger.Debug(" * kit not used " + kit.getPartNumber());
			}
		}
		VSSTLogger.Info("--------------- Done Kit Test ----------------");
		VSSTLogger.Info("/");
		VSSTLogger.Info(" ");
	}

	
	static public void testPneumaticAccessoriesMap(HashMap<String, AbstractSiemensProduct> currentProductMap, HashMap<String, AbstractSiemensProduct> allProducts) 
	{
		VSSTLogger.Info("--------------- Read test for Pneumatic Kits ----------------");			
		// create a list to store all used Kits just to see at the end if there were any defined but not used
		HashMap<String, String> damperKits = new HashMap<>();
		HashMap<String, String> pneumaticKits = new HashMap<>();
		HashMap<String, String> baseKits  = new HashMap<>();
		
		int kits_allEntries = 0;
		int kits_inProducts = 0;
		int kits_notInProducts = 0;
		int kits_inKits = 0;
		int kits_notInKits = 0;
		
		int base_allEntries = 0;
		int base_inProducts = 0;
		int base_notInProducts = 0;

		
		// loop through all the pneumatics
		Iterator<AbstractSiemensProduct> productCursor = currentProductMap.values().iterator();
		while (productCursor.hasNext()) {
			AbstractSiemensProduct product = productCursor.next();
			
			if (product instanceof SiemensAccessory) {
				damperKits.put(product.getPartNumber(), product.getPartNumber());
			}
			
			if (product instanceof SiemensPneumatic) {
				// search for each accepted Kits by part number and if not found create a new Damper Kit
				SiemensPneumatic pneumatic = (SiemensPneumatic) product;
				
				//add the base replacement
				String basePartNumber = pneumatic.getPneumaticMetadata().getBaseReplace();
				if (basePartNumber != null) {
					base_allEntries ++;
					if (!baseKits.containsKey(basePartNumber)) {
						baseKits.put(basePartNumber, basePartNumber);
					}
				}
				
				Iterator <String> tmpKitCursor = pneumatic.getPneumaticMetadata().getAcceptedAccessories().iterator();
				while (tmpKitCursor.hasNext()) {
					kits_allEntries ++;
					String pneumaticKit = tmpKitCursor.next();
					// gather all the used kits in one place just to test 
					if (!pneumaticKits.containsKey(pneumaticKit)) {
						pneumaticKits.put(pneumaticKit, pneumaticKit);
					}
				}
			}
		}
		
		Iterator <String> pneumaticKitCursor = pneumaticKits.values().iterator();
		while (pneumaticKitCursor.hasNext()) 
		{
			String pneumaticKitPartNumber = pneumaticKitCursor.next();
			if (!damperKits.containsKey(pneumaticKitPartNumber)) {
				kits_notInKits++;
			}
			else {
				kits_inKits++;
			}
			
			if (!allProducts.containsKey(pneumaticKitPartNumber)) {
				kits_notInProducts++;
			}
			else {
				kits_inProducts++;
				AbstractSiemensProduct prod = allProducts.get(pneumaticKitPartNumber);
				int tp = SiemensProductUtil.getProductType(prod);
				VSSTLogger.Debug(".......... found pneum kit " + pneumaticKitPartNumber + " as " + tp);				
			}
		}
					
		Iterator <String> baseKitCursor = baseKits.values().iterator();
		while (baseKitCursor.hasNext()) 
		{
			String basePartNumber = baseKitCursor.next();
			
			if (!allProducts.containsKey(basePartNumber)) {
				base_notInProducts++;
				VSSTLogger.Debug(".......... NOT found BASE " + basePartNumber);								
			}
			else {
				base_inProducts++;
				AbstractSiemensProduct prod = allProducts.get(basePartNumber);
				int tp = SiemensProductUtil.getProductType(prod);
				VSSTLogger.Debug(".......... found base part " + basePartNumber + " as " + tp);				
			}
		}
		
		
		VSSTLogger.Info("-----> KITS from pneumatics  " + pneumaticKits.size());
		VSSTLogger.Info("----->      total usage      " + kits_allEntries);
		VSSTLogger.Info("----->      not found in products  " + kits_notInProducts);
		VSSTLogger.Info("----->      found in produts       " + kits_inProducts);
		VSSTLogger.Info("----->      not found in kits  " + kits_notInKits);
		VSSTLogger.Info("----->      found in kits      " + kits_inKits);		
		VSSTLogger.Info("-----> BASE from pneumatics  " + baseKits.size());
		VSSTLogger.Info("----->      total usage      " + base_allEntries);
		VSSTLogger.Info("----->      not found in products  " + base_notInProducts);
		VSSTLogger.Info("----->      found in produts       " + base_inProducts);
		VSSTLogger.Info("--------------- Done test for Pneumatic Kits ----------------");
		VSSTLogger.Info("/");
		VSSTLogger.Info(" ");
	}
}
