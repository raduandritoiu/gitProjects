package radua.ui.utils;

import java.io.PrintStream;

import radua.ui.controllers.WorldController;
import radua.ui.models.IBasicModel;

public class Debug 
{
	public static void printWorldController(WorldController ctrl) {
		PrintStream o = System.out;
		
		o.println("Models:");
		for (IBasicModel model : ctrl.getModels()) {
			o.println(model.debugString(0));
		}
	}
}
