package integration.controller;

import integration.actions.interfaces.IBasicAction;
import integration.events.interfaces.IBasicEvent;
import integration.models.interfaces.IBasicModel;

import java.util.ArrayList;


public class LibraryDescriptor 
{
	protected ArrayList<IBasicModel> _models;
	protected ArrayList<IBasicEvent> _events;
	protected ArrayList<IBasicAction> _actions;
	

	public LibraryDescriptor() {
		MainRegistrar.getInstance().addDescriptor(this);
		_models = new ArrayList<IBasicModel>();
		_events = new ArrayList<IBasicEvent>();
		_actions = new ArrayList<IBasicAction>();
	}
	
	
	public ArrayList<IBasicModel> getModels() {
		return _models;
	}
	
	
	public ArrayList<IBasicEvent> getEvents() {
		return _events;
	}
	
	
	public ArrayList<IBasicAction> getActions() {
		return _actions;
	}
}
