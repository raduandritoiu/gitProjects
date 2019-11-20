package petri.controller
{
	import com.joeberkovitz.moccasin.controller.MoccasinController;
	import com.joeberkovitz.moccasin.document.MoccasinDocument;
	import com.joeberkovitz.moccasin.event.ModelEvent;
	import com.joeberkovitz.moccasin.event.ModelUpdateEvent;
	import com.joeberkovitz.moccasin.event.SelectEvent;
	import com.joeberkovitz.moccasin.model.MoccasinModel;
	
	import flash.events.Event;
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	
	import petri.editor.feedback.HookHandler;
	import petri.model.Marking;
	import petri.model.MarkingTree;
	import petri.model.PetriBaseModel;
	import petri.model.PetriLayer;
	import petri.model.PetriState;
	import petri.model.PetriTransition;
	import petri.model.PetriWorld;
	import petri.utils.Tester;
	import petri.view.MarkingTreeView;
	import petri.view.PetriView;
	import petri.view.PetriViewWithHooks;
	
	
	public class PetriController extends MoccasinController
	{
		private var ADVANCED_MODE:Boolean = true;
		private var markingTree:MarkingTree;
		protected var _isRunning:Boolean = false;
		
		private var activeLayer:PetriLayer;
		private var hooks:ArrayCollection;
		private var _activeHook:HookHandler;
		
		private var mainController:MainEventController;
		
		
		public function PetriController(document:MoccasinDocument) {
			super(document);
			
			addEventListener(SelectEvent.CHANGE_SELECTION, handleSelectEvent, false, 0, true);
			
			mainController = MainEventController.instance;
			mainController.addEventListener(MainEvent.RUN, mainEventsHandler, false, 0, true);
			mainController.addEventListener(MainEvent.STEP, mainEventsHandler, false, 0, true);
			mainController.addEventListener(MainEvent.STOP, mainEventsHandler, false, 0, true);

			markingTree = new MarkingTree();
		}
		
		
		public function get world():PetriWorld {
			return document.root.value as PetriWorld;
		}
		
		
		public function get treeView():MarkingTreeView {
			return new MarkingTreeView(markingTree);
		}
		
		
		override public function set document(d:MoccasinDocument):void {
			if (document != null) {
				document.root.removeEventListener(ModelEvent.MODEL_CHANGE, handleWorldChange, false);
				document.root.removeEventListener(ModelUpdateEvent.MODEL_UPDATE, handleWorldUpdate, false);
			}
			super.document = d;
			if (document != null) {
				document.root.addEventListener(ModelEvent.MODEL_CHANGE, handleWorldChange, false, 0, true);
				document.root.addEventListener(ModelUpdateEvent.MODEL_UPDATE, handleWorldUpdate, false, 0, true);
			}
			if (document != null) {
				initMarkingTree();
			}
		}
		
		
		public function remove():void {
			removeEventListener(SelectEvent.CHANGE_SELECTION, handleSelectEvent, false);
			
			mainController.removeEventListener(MainEvent.RUN, mainEventsHandler, false);
			mainController.removeEventListener(MainEvent.STEP, mainEventsHandler, false);
			mainController.removeEventListener(MainEvent.STOP, mainEventsHandler, false);
			
			if (document != null) {
				document.root.removeEventListener(ModelEvent.MODEL_CHANGE, handleWorldChange, false);
				document.root.removeEventListener(ModelUpdateEvent.MODEL_UPDATE, handleWorldUpdate, false);
			}
		}
		
		
		// this is called when adding/removing models
		protected function handleWorldChange(evt:ModelEvent):void {
			if (evt.kind == ModelEvent.REMOVING_CHILD_MODEL) {
				return;
			}
			initMarkingTree();
		}
		
		
		protected function handleWorldUpdate(evt:ModelUpdateEvent):void {
			switch (evt.property.toString()) {
				case "tokens":
					initMarkingTree();
					break;
			}
		}
		
		
		// --------------------//
		// HOOKS related stuff //
		// --------------------//
		
		public function set activeHook(value:HookHandler):void {
			_activeHook = value;
		}
		
		public function get activeHook():HookHandler {
			return _activeHook;
		}
		
		
		public function getHookByPosition(x:Number, y:Number):HookHandler {
			for each (var hook:HookHandler in hooks) {
				if (hook.hitTestPoint(x, y)){
					return hook;
				}
			}
			return null;
		}
		
		
		public function addViewHooks(view:PetriViewWithHooks):void {
			if (hooks == null) {
				hooks = new ArrayCollection();
			}
			
			hooks.addItem(view.getLeftHook());
			hooks.addItem(view.getRightHook());
			hooks.addItem(view.getTopHook());
			hooks.addItem(view.getBottomHook());
		}
		
		
		// --------------------------------//
		// ADDING / REMOVING related stuff //
		// --------------------------------//
		
		public function viewAdded(view:PetriView):void {
		}
		
		
		public function removeModel(petriModel:PetriBaseModel):void {
			if (activeLayer == null) {
				return;
			}
			var index:int = activeLayer.children.getItemIndex(petriModel);
			if (index < 0) {
				return;
			}
			activeLayer.children.removeItemAt(index);
		}
		
		
		public function addModelToActiveContainer(petriModel:PetriBaseModel, index:int = -1):void {
			var layer:PetriLayer = getOrCreateLayer();
			petriModel.world = world;
			if (index < 0 || index > layer.children.length)
				layer.children.addItem(petriModel);
			else
				layer.children.addItemAt(petriModel, index);
		}
		
		
		private function getOrCreateLayer():PetriLayer {
			if (activeLayer == null) {
				makeNewLayer();
			}
			return activeLayer;
		}
		
		
		private function makeNewLayer():void {
			activeLayer = new PetriLayer();
			activeLayer.world = world;
			world.children.addItem(activeLayer);
		}
		
		
		private function handleSelectEvent(evt:SelectEvent):void
		{
			var selectedModel:PetriBaseModel;
			if (evt.selection != null && evt.selection.selectedModels.length > 0) {
				var m:MoccasinModel = evt.selection.selectedModels[0];
				selectedModel = m.value as PetriBaseModel;
			}
			
			mainController.send(MainEvent.SELECTION_CHANGE, selectedModel);
		}
		
		
		override public function removeSelection():void {
			for each (var m:MoccasinModel in selection.selectedModels) {
				(m.value as PetriBaseModel).remove();
			}
			
			super.removeSelection();
		}
		
		
		
		
		// -----------------------------//
		// PETRI NET WORK related stuff //
		// -----------------------------//
		
		public function get isRunning():Boolean {
			return _isRunning;
		}
		
		
		private var stepTimer:Timer;
		
		private function mainEventsHandler(evt:MainEvent):void {
			switch (evt.type) {
				case MainEvent.RUN:
					run();
					break;
				
				case MainEvent.STEP:
					step();
					break;
				
				case MainEvent.STOP:
					stop();
					break;
			}
		}
		
		
		private function run():void {
			if (stepTimer == null) {
				stepTimer = new Timer(2000, 0);
				stepTimer.addEventListener(TimerEvent.TIMER, takeStepTimer, false, 0, true);
			}
			stepTimer.start();
		}
		
		
		private function step():void {
			stop();
			takeStepTimer();
		}
		
		
		private function stop():void {
			if (stepTimer != null) {
				stepTimer.stop();
			}
		}
		
		
		private function takeStepTimer(evt:Event = null):void {
			_isRunning = true;
			
			if (!validatePetriNet()) {
				showAlert(0);
				return;
			}
			
			// init the tokens
			for each (var state:PetriState in world.states) {
				state.initStep();
			}
			
			// check if there are any transitions that can be executed
			var currentTransitions:ArrayCollection = new ArrayCollection();
			for each (var transition:PetriTransition in world.transitions) {
				if (transition.enabled) {
					transition.execute();
					currentTransitions.addItem(transition);
				}
			}
			
			if (currentTransitions.length == 0) {
				showAlert(1);
				return;
			}
			
			if (ADVANCED_MODE) {
				// check if marking exists and if YES try to generate a new one
				var marking:Marking = world.createFutureMarking();
				if (markingTree.getMarking(marking) != null) {
					// init the tokens
					for each (var state:PetriState in world.states) {
						state.initStep();
					}
					
					marking = world.createMarking();
					marking = markingTree.getMarking(marking);
					for each (var transitionsCode:int in markingTree.possibleTransitionsCode) {
						if (!marking.hasNextTransitions(transitionsCode)) {
							// execute transitions
							var transitions:ArrayCollection = markingTree.decodeTransitions(transitionsCode);
							var execution:Boolean = true;
							for each (var transition:PetriTransition in transitions) {
								if (transition.enabled) {
									transition.execute();
								}
								else { // could not be executed
									execution = false;
									break;
								}
							}
							
							// check is excution succesfull
							if (execution) {
								currentTransitions = transitions;
								break;
							} 
							else {
								// init the tokens
								for each (var state:PetriState in world.states) {
									state.initStep();
								}
							}
						}
					}
				}
			
				// init the tokens
				for each (var state:PetriState in world.states) {
					state.initStep();
				}
				
				// execut the calculated transitions
				for each (var transition:PetriTransition in currentTransitions) {
					if (transition.enabled) {
						transition.execute();
					}
				}
			}
				
			// commit tokens
			for each (var state:PetriState in world.states) {
				state.commitStep()
			}
			
			if (ADVANCED_MODE) {
				// add marking to tree
				marking = world.createMarking();
				markingTree.addMarking(marking, currentTransitions);
				
				// this must be deleted
				Tester.instance.printMarking(marking.code);
			}
			
			_isRunning = false;
		}
		
		
		private function initMarkingTree():void {
			if (isRunning) {
				return;
			}
			markingTree.root = world.createMarking();
			markingTree.transitions = world.transitions;
		}
		
		
		private function validatePetriNet():Boolean {
			for each (var transition:PetriTransition in world.transitions) {
				if (!transition.valid) {
					return false;
				}
			}
			return true;
		}
		
		
		private function showAlert(alertType:int):void {
			stop();
			var alertStr:String = "";
			
			switch (alertType) {
				case 0:
					alertStr = "Reteaua nu este valida.";
					break;
				case 1:
					alertStr = "Reteaua nu are nici o tranzitie executabila.";
					break;
			}
			Alert.show(alertStr);
		}
	}
}