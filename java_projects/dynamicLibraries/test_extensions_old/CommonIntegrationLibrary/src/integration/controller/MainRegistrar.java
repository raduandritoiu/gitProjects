package integration.controller;

import java.util.ArrayList;

public class MainRegistrar 
{
	private static MainRegistrar _instance;
	
	private ArrayList<LibraryDescriptor> _descriptors;
	
	
	public ArrayList<LibraryDescriptor> getDescriptors() {
		return _descriptors;
	}

	
	public void addDescriptor(LibraryDescriptor desc) {
		_descriptors.add(desc);
	}
	
	
	private MainRegistrar() {
		_descriptors = new ArrayList<LibraryDescriptor>();
	}
	
	
	public static MainRegistrar getInstance() {
		if (_instance == null) {
			_instance = new MainRegistrar();
		}
		return _instance;
	}
	
	
	public static void create() {
		getInstance();
	}
}
