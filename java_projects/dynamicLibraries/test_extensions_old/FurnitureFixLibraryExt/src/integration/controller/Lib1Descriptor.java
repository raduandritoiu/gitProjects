package integration.controller;

import integration.actions.FixAction;
import integration.actions.RepairAction;
import integration.events.BrokeEvent;
import integration.events.FixedEvent;
import integration.models.Armchair;
import integration.models.Chair;
import integration.models.Plate;
import integration.models.Table;


public class Lib1Descriptor extends LibraryDescriptor 
{
	public Lib1Descriptor() {
		super();
		
		_models.add(new Armchair());
		_models.add(new Chair());
		_models.add(new Plate());
		_models.add(new Table());
		
		_events.add(new BrokeEvent());
		_events.add(new FixedEvent());

		_actions.add(new FixAction());
		_actions.add(new RepairAction());
	}
}
