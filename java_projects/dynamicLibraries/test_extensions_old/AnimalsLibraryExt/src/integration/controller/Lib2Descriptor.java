package integration.controller;

import integration.actions.DieAction;
import integration.actions.EatAction;
import integration.actions.SleepAction;
import integration.actions.SpeakAction;
import integration.events.WokeupEvent;
import integration.events.MadeSoundEvent;
import integration.models.Bird;
import integration.models.Cat;
import integration.models.Dog;


public class Lib2Descriptor extends LibraryDescriptor 
{
	public Lib2Descriptor() {
		super();
		
		_models.add(new Dog());
		_models.add(new Bird());
		_models.add(new Cat());
		
		_events.add(new WokeupEvent());
		_events.add(new MadeSoundEvent());

		_actions.add(new EatAction());
		_actions.add(new DieAction());
		_actions.add(new SleepAction());
		_actions.add(new SpeakAction());
	}
}
