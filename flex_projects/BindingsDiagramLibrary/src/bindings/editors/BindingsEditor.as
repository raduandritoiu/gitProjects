package bindings.editors
{
	import bindings.mediators.drags.BindingsWorldDropMediator;
	import bindings.mediators.tools.BindingsWorldToolMediator;
	import bindings.model.PointNode;
	import bindings.model.ShapeNode;
	import bindings.model.TranslatorNode;
	import bindings.utils.BindingsDataFactoryImpl;
	import bindings.utils.BindingsModelFactoryImpl;
	import bindings.utils.BindingsViewFactoryImpl;
	
	import diagram.editors.DiagramEditor;
	import diagram.mediators.IViewMediator;
	import diagram.mediators.tools.DiagramWorldToolMediator;
	import diagram.model.DiagramBaseModel;
	import diagram.model.DiagramLink;
	import diagram.utils.DiagramDataFactory;
	import diagram.utils.DiagramModelFactory;
	import diagram.utils.DiagramViewFactory;
	import diagram.utils.IDataModel;
	import diagram.view.DiagramWorldView;
	
	import j2inn.builder.mapping.bind.ComponentBindings;
	import j2inn.builder.model.J2BaseModel;
	import j2inn.builder.model.J2World;
	
	import moccasin.view.IMoccasinView;
	import moccasin.view.ViewContext;
	
	
	public class BindingsEditor extends DiagramEditor
	{
		public function BindingsEditor() {
			super();
			DiagramDataFactory.setImplementation(new BindingsDataFactoryImpl());
			DiagramModelFactory.setImplementation(new BindingsModelFactoryImpl());
			DiagramViewFactory.setImplementation(new BindingsViewFactoryImpl());
		}
		
		
		override protected function createDocumentView(context:ViewContext):IMoccasinView {
			var worldView:DiagramWorldView = new DiagramWorldView(context, moccasinDocument.root);
			var worldMediator:IViewMediator = new BindingsWorldDropMediator(context, worldView);
			worldView.addMediator(worldMediator);
			
//			worldMediator = new BindingsWorldToolMediator(context, worldView);
			worldMediator = new DiagramWorldToolMediator(context, worldView);
			worldView.addMediator(worldMediator);			

			return worldView;
		}
		
		
		public function loadBindings(j2World:J2World):void {
			var dataModel:IDataModel;
			var shapeNode:ShapeNode;
			var prop:Object;
			var translatorNode:TranslatorNode;
			var pointNode:PointNode;
			var newLink:DiagramLink;
			
			for each (var shape:J2BaseModel in j2World.modelsInWorld) {
				if (shape.bindings.length > 0) {
					// reset all nodes;
					shapeNode 		= null;
					pointNode 		= null;
					translatorNode 	= null;
					
					dataModel = DiagramDataFactory.createData(shape);
					shapeNode = DiagramModelFactory.createModel(dataModel) as ShapeNode;
					shapeNode.x = isNaN(shape.bindingsX) ? 500 : shape.bindingsX;
					shapeNode.y = isNaN(shape.bindingsY) ? 200 : shape.bindingsY;
					diagramController.addModelToActiveContainer(shapeNode);
					
					for each (var binding:ComponentBindings in shape.bindings) {
						var propArray:Array = J2BaseModel.getPropInfo(shape);
						for each (var propObj:Object in propArray) {
								if (propObj.name == binding.property.targetProperty) {
									prop = propObj;
								}
						}
						shapeNode.addBindedProp(prop);
						
						dataModel = DiagramDataFactory.createData(binding.bindingSource);
						pointNode = DiagramModelFactory.createModel(dataModel) as PointNode;
						pointNode.x = isNaN(binding.bindingSourceX) ? 50 : binding.bindingSourceX;
						pointNode.y = isNaN(binding.bindingSourceY) ? 50 : binding.bindingSourceY;
						diagramController.addModelToActiveContainer(pointNode);
						
						if (binding.property.valueTranslator != null) {
							dataModel = DiagramDataFactory.createData(binding.property.valueTranslator);
							translatorNode = DiagramModelFactory.createModel(dataModel) as TranslatorNode;
							translatorNode.x = isNaN(binding.property.translatorX) ? 200 : binding.property.translatorX;
							translatorNode.y = isNaN(binding.property.translatorY) ? 100 : binding.property.translatorY;
							diagramController.addModelToActiveContainer(translatorNode);
							
							// add links - with translator
							newLink = new DiagramLink();
							newLink.from = pointNode.slot;
							newLink.to = translatorNode.inputSlot;
							diagramController.addModelToActiveContainer(newLink);
							
							newLink = new DiagramLink();
							newLink.from = translatorNode.outputSlot;
							newLink.to = shapeNode.getPropertySlot(prop);
							diagramController.addModelToActiveContainer(newLink);
						}
						else {
							// add links - without translator
							newLink = new DiagramLink();
							newLink.from = pointNode.slot;
							newLink.to = shapeNode.getPropertySlot(prop);
							diagramController.addModelToActiveContainer(newLink);
						}
					}
				}
			}
		}
		
		
		public function saveBindings():void {
			for each (var model:DiagramBaseModel in diagramController.world.models) {
				if (model is ShapeNode) {
					(model as ShapeNode).saveBindings();
				}
			}
		}
	}
}