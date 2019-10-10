package siemens.vsst.data.parsers.remove;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import siemens.vsst.data.enumeration.valves.ValveType;
import siemens.vsst.data.models.AbstractSiemensProduct;
import siemens.vsst.data.models.SiemensAssembly;
import siemens.vsst.data.models.SiemensValve;
import siemens.vsst.data.parsers.utils.VSSTLogger;

public class CustomRemove
{
	public static void RemoveButterfly(HashMap<String, AbstractSiemensProduct> currentProductMap)
	{
		ArrayList<String> removedValves = new ArrayList<>();
		ArrayList<String> removedAssemblies = new ArrayList<>();
		
		// search for butterflies
		Iterator<AbstractSiemensProduct> productsCursor = currentProductMap.values().iterator();
		while (productsCursor.hasNext()) {
			AbstractSiemensProduct product = productsCursor.next();
			// test valves
			if (product instanceof SiemensValve) {
				SiemensValve valve = (SiemensValve) product;
				if (valve.getValveMetadata().getType() == ValveType.Butterfly) {
					removedValves.add(valve.getPartNumber());
				}
			}
			// test assemblies
			if (product instanceof SiemensAssembly) {
				SiemensAssembly assembly = (SiemensAssembly) product;
				if (assembly.getValve().getValveMetadata().getType() == ValveType.Butterfly) {
					removedAssemblies.add(assembly.getValve().getPartNumber());
				}
			}
		}
		
		VSSTLogger.Info("--------------------------------");
		VSSTLogger.Info("Removing BUTTERFLYs: "+removedValves.size()+" valves and "+removedAssemblies.size()+" assemblies");

		// remove butterfly valves
		Iterator<String> removeCursor = removedValves.iterator();
		while (removeCursor.hasNext()) {
			String partNumber = removeCursor.next();
			currentProductMap.remove(partNumber);
		}
		// remove butterfly assemblies
		removeCursor = removedAssemblies.iterator();
		while (removeCursor.hasNext()) {
			String partNumber = removeCursor.next();
			currentProductMap.remove(partNumber);
		}
		
		VSSTLogger.Info("--------------------------------\n");
	}
}