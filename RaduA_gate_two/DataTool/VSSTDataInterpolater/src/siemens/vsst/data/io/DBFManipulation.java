package siemens.vsst.data.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.xBaseJ.fields.CharField;
import org.xBaseJ.fields.LogicalField;
import org.xBaseJ.fields.NumField;

import siemens.vsst.data.enumeration.valves.ValveNormalState;
import siemens.vsst.data.models.AbstractSiemensProduct;
import siemens.vsst.data.models.SiemensValve;
import siemens.vsst.data.parsers.utils.VSSTLogger;

public class DBFManipulation {
	/**
	 * This is a Hack and should not be used.
	 * The products should be removed by the general processing algorithm 
	 * and by not being present in the VSST Step Extract file.
	 */
	public static void removeProducts(HashMap<String, AbstractSiemensProduct> productMap) {
		System.out.println("--------------- Manually Remove Dampers and Valves ----------------");
		
		// manually remove these dampers
		String[] productsNeedRemoving = new String[] {"", 
				"GDE131.1Q", "GDE132.1P", "GDE136.1P", 
				"GLB131.1P", "GLB131.1Q", "GLB132.1P", "GLB136.1P", "GLB163.1P", "GLB164.1P", 
				"331-2911", "331-2934", "331-2966", 
				"230-04320-0.5", "230-04320-1", "230-04320-1.5", 
				"231-04320-0.5", "231-04320-1", "231-04320-1.5", 
				"232-04320-0.5", "232-04320-1", "232-04320-1.5", 
				"233-04320-0.5", "233-04320-1", "233-04320-1.5", 
				"599-04320-0.5", "599-04320-1", "599-04320-1.5", 
		};
		for (String productPart : productsNeedRemoving) {
			productMap.remove(productPart);
		}
		
        // manually remove these type of valves
		ArrayList<String> removeParts = new ArrayList<>();
		Iterator<String> partsIter = productMap.keySet().iterator();
		while (partsIter.hasNext()) {
		    String partNumber = partsIter.next();
		    if (partNumber.startsWith("599-04306-") || partNumber.startsWith("599-04307-")) {
                removeParts.add(partNumber);
		    }
		}
        partsIter = removeParts.iterator();
        while (partsIter.hasNext()) {
            String partNumber = partsIter.next();
            System.out.println("--- Remove part: " + partNumber);
            productMap.remove(partNumber);
        }
		
        System.out.println("--------------- Done Manually Removing Dampers and Valves ----------------");
	}
	
	/**
	 * This is a Hack and should not be used.
	 * The correct information about the products should be found in the input DBF files.
	 */
	public static void changeProducts(HashMap<String, AbstractSiemensProduct> productMap) {
		// manually change these valves
		System.out.println("--------------- Manually Change PICV Valves to Normally Open----------------");
		String[] productsNeedChanging = new String[] {
				"599-07310", 
				"599-07311", 
				"599-07312", 
				"599-07313", 
				"599-07319", 
				"599-07320", 
				"599-07321"
		};
		for (String productPart : productsNeedChanging) {
		    AbstractSiemensProduct product = productMap.get(productPart);
	    	SiemensValve valve = (SiemensValve) product;
	    	System.out.println("Product: " + valve.getPartNumber() + " - " + valve.getValveMetadata().getType().name() + " - " + valve.getValveMetadata().getNormalState().name());
	    	valve.getValveMetadata().setNormalState(ValveNormalState.NormallyOpen);
		}
		System.out.println("--------------- Done Manually Changing PICV Valves ----------------\n");
		
		System.out.println("--------------- Manually Change Valves prices ----------------");
		// 599-04322-2
		AbstractSiemensProduct product = productMap.get("599-04322-2");
        product.setManufacturer("SIEMENS");
        product.setPrice(348.68f);
        product.setObsolete(false);
        product.setSapPartNumber("599-04322-2");
        product.setDescription("599-04322-2  3/4 PICV 2 GPM NO");
        product.setVendor("SIEMENS");
		product.setProductProcessed(true);
		// 599-04322-2.5
		product = productMap.get("599-04322-2.5");
        product.setManufacturer("SIEMENS");
        product.setPrice(348.68f);
        product.setObsolete(false);
        product.setSapPartNumber("599-04322-2.5");
        product.setDescription("599-04322-2.5  3/4 PICV 2.5 GPM NO");
        product.setVendor("SIEMENS");
		product.setProductProcessed(true);
		System.out.println("--------------- Done Manually Changing Valves prices ----------------\n\n");
	}
}