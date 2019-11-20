package bindings.mediators.tools
{
	import bindings.model.PointNode;
	import bindings.model.ShapeNode;
	import bindings.model.TranslatorNode;
	
	import diagram.mediators.tools.DrawLinksTool;
	import diagram.model.DiagramBaseModel;
	import diagram.model.DiagramLink;
	import diagram.model.DiagramSlot;
	import diagram.utils.DiagramDataFactory;
	import diagram.utils.DiagramModelFactory;
	import diagram.utils.IDataModel;
	import diagram.view.DiagramWorldView;
	
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import j2inn.builder.mapping.interfaces.IPropertyTranslator;
	import j2inn.builder.mapping.interfaces.IVirtualPoint;
	import j2inn.builder.mapping.util.BindingUtil;
	import j2inn.builder.mapping.util.VirtualPointUtils;
	import j2inn.builder.model.J2BaseModel;
	
	import moccasin.view.ViewContext;
	
	
	public class BindingsDrawLinksTool extends DrawLinksTool
	{
		public function BindingsDrawLinksTool(context:ViewContext, view:DiagramWorldView) {
			super(context, view);
		}
		
		
//		override public function handleDragEndCapture(e:MouseEvent):void {
//			var acceptLink:Boolean = false;
//			var currentSlot:DiagramSlot = controller.currentSlot;
//			
//			if (newLink != null && currentSlot != null) {
//					
//				var shape:J2BaseModel = null;
//				var property:Object = null;
//				if (currentSlot.node is ShapeNode) {
//					shape = (currentSlot.node as ShapeNode).shape;
//					property = (currentSlot.node as ShapeNode).getSlotProperty(currentSlot);
//				}
//				if (shape == null && newLink.from.node is ShapeNode) {
//					shape = (newLink.from.node as ShapeNode).shape;
//					property = (newLink.from.node as ShapeNode).getSlotProperty(newLink.from);
//				}
//					
//				var point:IVirtualPoint = null;
//				if (currentSlot.node is PointNode) {
//					point = (currentSlot.node as PointNode).point;
//				}
//				if (point == null && newLink.from.node is PointNode) {
//					point = (newLink.from.node as PointNode).point;
//				}
//				
//				var translator:IPropertyTranslator = null;
//				if (currentSlot.node is TranslatorNode) {
//					translator = (currentSlot.node as TranslatorNode).translator;
//				}
//				if (translator == null && newLink.from.node is TranslatorNode) {
//					translator = (newLink.from.node as TranslatorNode).translator;
//				}
//				
//				
//				if (translator != null && shape != null) {
//					acceptLink = dragTranslatorToShape(translator, shape, property);
//				}
//				
//				if (point != null && shape != null) {
//					var pos:Point = new Point();
//					pos.x = (newLink.from.node.x + currentSlot.node.x) / 2;
//					pos.y = (newLink.from.node.y + currentSlot.node.y) / 2;
//					acceptLink = dragPointToShape(point, shape, property, pos);
//				}
//				
//				if (point != null && translator != null) {
//					acceptLink = dragPointToTranslator(point, translator);
//				}
//			}
//			
//			if (acceptLink) {					
//				newLink.interactiv = true;
//				newLink.to = currentSlot;
//			}
//			else if (newLink != null) {
//				controller.removeModel(newLink);
//				newLink.from = null;
//			}
//			newLink = null;
//			
//			e.stopImmediatePropagation();
//			e.stopPropagation();
//		}

		
		protected function dragPointToTranslator(point:IVirtualPoint, translator:IPropertyTranslator):Boolean {
			// verify point and translator - dar momentan mergem asa
			translator.updateParameters(point);
			return true;
		}
		
		
		protected function dragPointToShape(point:IVirtualPoint, shape:J2BaseModel, property:Object, newPos:Point):Boolean {
			var acceptPoint = BindingUtil.instance.acceptDragPointFor(shape, property.name, point);
			var defTranslator:IPropertyTranslator = BindingUtil.instance.getTranslatorFor(shape, property.name, point);
			if (defTranslator != null) {
				/// create translator node
				var dataModel:IDataModel = DiagramDataFactory.createData(defTranslator);
				var translatorNode:TranslatorNode = DiagramModelFactory.createModel(dataModel) as TranslatorNode;
				translatorNode.x = newPos.x;
				translatorNode.y = newPos.y;
				controller.addModelToActiveContainer(translatorNode);
				
				newLink.interactiv = true;
				newLink.to = translatorNode.inputSlot;
				
				newLink = new DiagramLink();
				newLink.from = translatorNode.outputSlot;
				controller.addModelToActiveContainer(newLink);
			}
			
			return true;
		}
		
		
		protected function dragTranslatorToShape(translator:IPropertyTranslator, shape:J2BaseModel, property:Object):Boolean {
			translator.model = shape;
			translator.propertyName = property.name;
			
			// maybe set some values
			return true;
		}
	}
}