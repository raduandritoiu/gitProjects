import integration.actions.interfaces.IBasicAction;
import integration.controller.Lib1Start;
import integration.controller.Lib2Start;
import integration.controller.LibraryDescriptor;
import integration.controller.LibraryLoader;
import integration.controller.MainRegistrar;
import integration.events.interfaces.IBasicEvent;
import integration.models.interfaces.IBasicModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;

public class MainClass 
{
	public static void main (String[] args) throws IOException
	{
		ArrayList<IBasicModel> models = new ArrayList<IBasicModel>();
		ArrayList<IBasicEvent> events = new ArrayList<IBasicEvent>();
		ArrayList<IBasicAction> actions = new ArrayList<IBasicAction>();
		
		System.out.println("start!  ");
		MainRegistrar reg = MainRegistrar.getInstance();
		
		System.out.println("load?  ");
//		Lib1Start.startLib();
//		Lib2Start.startLib();
		LibraryLoader lbl = new LibraryLoader("");
		if (lbl.load("C:/radua/workspaces/java_workspace/my_projects/TestLibrary1/jarFile/lib1.jar") == -1) {
			System.out.println("Double fuck!");
		}
		if (lbl.load("C:/radua/workspaces/java_workspace/my_projects/TestLibrary1/jarFile/lib1.jar") == -1) {
			System.out.println("Double fuck!");
		}
			
		
		System.out.println("merge?  ");
		for (int i=0; i < reg.getDescriptors().size(); i++) {
			LibraryDescriptor desc = reg.getDescriptors().get(i);
			models.addAll(desc.getModels());
			events.addAll(desc.getEvents());
			actions.addAll(desc.getActions());
		}
		
		System.out.println("print models:  ");
		for (int i=0; i < models.size(); i++) {
			IBasicModel model = models.get(i);
			System.out.println("\t" + model);
		}
		
		System.out.println("print events:  ");
		for (int i=0; i < events.size(); i++) {
			IBasicEvent event = events.get(i);
			System.out.println("\t" + event);
		}
		
		System.out.println("print actions:  ");
		for (int i=0; i < actions.size(); i++) {
			IBasicAction action = actions.get(i);
			System.out.println("\t" + action);
		}
		
		System.out.println("gata!  ");
		
		
	}
}


