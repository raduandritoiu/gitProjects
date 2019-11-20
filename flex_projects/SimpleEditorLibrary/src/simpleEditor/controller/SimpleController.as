package simpleEditor.controller
{
	import com.joeberkovitz.moccasin.controller.MoccasinController;
	import com.joeberkovitz.moccasin.document.MoccasinDocument;
	
	import flash.utils.Dictionary;
	
	import simpleEditor.enums.WorldTools;
	import simpleEditor.model.ContainerModel;
	
	
	public class SimpleController extends MoccasinController
	{
		private var _selectedTool:int = WorldTools.DEFAULT_TOOL;
		
		protected var editor:DiagramEditor;
		
		protected var viewDictionary:Dictionary = new Dictionary();
		protected var activeContainer:ContainerModel;
		protected var pasteModels:ArrayCollection;
		
		
		public function SimpleController(document:MoccasinDocument, diagramEditor:DiagramEditor) {
			super(document);
			editor = diagramEditor;
		}
		
		
		public function get world():DiagramWorld {
			return document.root.value as DiagramWorld;
		}
		
		
		public function get worldView():DiagramWorldView {
			return editor.documentView as DiagramWorldView;
		}
		
		
		public function set selectedTool(value:int):void {
			_selectedTool = value;
		}
		
		public function get selectedTool():int {
			return _selectedTool;
		}
		
		
		public function set currentSlot(value:DiagramSlot):void {
			_currentSlot = value;
		}
		
		public function get currentSlot():DiagramSlot {
			return _currentSlot;
		}
		
		
		/* Adding / Removing models */
		
		public function addModelToSelectedContainer(diagramModel:DiagramBaseModel):void {
			var parent:DiagramContainerModel;
			
			if (selection != null && !selection.empty) {
				parent = selection.selectedModels[0].value as DiagramContainerModel;
			}
			
			if (parent == null || !parent.acceptChild(diagramModel))
				parent = world;
			
			// !IMPORTANT: the model added must be in the world coordinates nad if the proposed parent
			// is not world then the new coordinates are recalculated
			if (parent != world) {
				var parentView:DiagramBaseView = getView(parent.uid);
				var offset:Point = parentView.localToGlobal(new Point()).subtract(worldView.localToGlobal(new Point()));
				diagramModel.x = diagramModel.x - offset.x;
				diagramModel.y = diagramModel.y - offset.y;
			}
			
			parent.addChild(diagramModel);
		}
		
		
		protected function addModelToActiveContainer(diagramModel:DiagramBaseModel):void {
			var parent:DiagramContainerModel = activeContainer;
			
			if ((parent == null || !parent.acceptChild(diagramModel)) &&
				selection != null && !selection.empty) {
				parent = selection.selectedModels[0].value as DiagramContainerModel;
			}
			
			if ((parent == null || !parent.acceptChild(diagramModel)) &&
				selection != null && !selection.empty) {
				parent = (selection.selectedModels[0].value as DiagramBaseModel).parent;
			}
			
			if (parent == null || !parent.acceptChild(diagramModel))
				parent = world;
			
			parent.addChild(diagramModel);
		}
		
		
		public function removeModel(diagramModel:DiagramBaseModel):void {
			if (diagramModel.parent) { 
				diagramModel.parent.removeChild(diagramModel);
			}
		}
		
		
		/* Adding / Removing views */
		
		public function getView(uid:String):DiagramBaseView {
			return viewDictionary[uid];
		}
		
		
		public function viewAdded(view:DiagramBaseView):void {
			viewDictionary[view.diagramModel.uid] = view;
		}
		
		
		public function viewRemoved(view:DiagramBaseView):void {
			delete viewDictionary[view.diagramModel.uid];
			// we do this because when you remove a container with children, the removal of
			// it's children is handled internally by FLEX
			if (view is DiagramContainerView) {
				removeViewFromContainer(view.diagramModel as DiagramContainerModel);
			}
		}
		
		protected function removeViewFromContainer(model:DiagramContainerModel):void {
			for each (var child:DiagramBaseModel in model.children) {
				delete viewDictionary[child.uid];
				if (child is DiagramContainerModel)
					removeViewFromContainer(child as DiagramContainerModel);
			}
		}
		
		
		/* Copy / Paste models */
		
		override public function copyClipboard():void {
			super.copyClipboard();
			addLinksToClipboard();
		}
		
		
		/* add to clipboard not selected links that are external to the current selection */
		protected function addLinksToClipboard():void {
			var slot:DiagramSlot;
			var node:DiagramNode;
			var link:DiagramLink;
			
			for each (var model:MoccasinModel in clipboard.models) {
				if (model.value is DiagramNode) {
					node = model.value as DiagramNode;
					for each (slot in node.slots) {
						for each (link in slot.links) {
							var linkFound:Boolean = false;
							for each (var model2:MoccasinModel in clipboard.models) {
								if (model2.value == link) {
									linkFound = true;
									break;
								}
							}
							if (!linkFound) {
								clipboard.models.push(MoccasinModel.forValue(link));
							}
						}
					}
				}
				
				if (model.value is DiagramSlot) {
					slot = model.value as DiagramSlot;
					for each (link in slot.links) {
						var linkFound:Boolean = false;
						for each (var model2:MoccasinModel in clipboard.models) {
							if (model2.value == link) {
								linkFound = true;
								break;
							}
						}
						if (!linkFound) {
							clipboard.models.push(MoccasinModel.forValue(link));
						}
					}
				}
			}
		}
		
		
		// after pasting models, deleted
		protected function clearLinks(models:ArrayCollection):void {
			for each (var model:DiagramBaseModel in models) {
				if (model is DiagramLink) {
					if ((model as DiagramLink).from == null || (model as DiagramLink).to == null) {
						removeModel(model);
					}
				}
			}
		}
		
		
		override public function pasteClipboard():void {
			// set parentContainer
			pasteModels = new ArrayCollection();
			super.pasteClipboard();
			RestoreReferences.restore(pasteModels);
			clearLinks(pasteModels);
		}
		
		
		override protected function transformPastedModel(model:MoccasinModel):MoccasinModel {
			var diagramModel:DiagramBaseModel = (model.value as DiagramBaseModel).clone();
			diagramModel.x += 15;
			diagramModel.y += 15;
			return MoccasinModel.forValue(diagramModel);
		}
		
		
		override protected function addPastedModel(model:MoccasinModel):void {
			pasteModels.addItem(model.value);
			addModelToActiveContainer(model.value as DiagramBaseModel);
		}
	}
}