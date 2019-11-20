package bindings.view
{
	import bindings.components.ModelPropertyItemRenderer;
	import bindings.model.ShapeNode;
	
	import diagram.model.DiagramSlot;
	import diagram.view.DiagramNodeView;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import j2inn.builder.model.J2BaseModel;
	
	import moccasin.model.MoccasinModel;
	import moccasin.view.ViewContext;
	
	import mx.collections.ArrayCollection;
	import mx.core.ClassFactory;
	
	import spark.components.Button;
	import spark.components.ComboBox;
	import spark.components.DataGroup;
	import spark.components.HGroup;
	import spark.components.Label;
	import spark.components.List;
	import spark.components.VGroup;
	import spark.layouts.HorizontalAlign;
	import spark.layouts.VerticalAlign;
	import spark.layouts.VerticalLayout;
	
	
	public class ShapeNodeView extends DiagramNodeView
	{
		private var viewContainer:VGroup;
		private var shapeName:Label;
		private var shapeType:Label;
		private var propsCombo:ComboBox;
		private var propsList:DataGroup;
		
		
		public function ShapeNodeView(context:ViewContext, model:MoccasinModel) {
			super(context, model);
		}
		
		
		public function get shapeNode():ShapeNode {
			return diagramModel as ShapeNode;
		}
		
		
		override protected function createChildren():void {
			super.createChildren();
			
			if (viewContainer == null) {
				viewContainer = new VGroup();
				viewContainer.y = 5;
				viewContainer.gap = 6;
				viewContainer.horizontalAlign = HorizontalAlign.CENTER;
				viewContainer.horizontalCenter = 0;
				container.addElement(viewContainer);
			}
			
			if (shapeName == null) {
				shapeName = new Label();
				shapeName.text = shapeNode.shape.name;
				viewContainer.addElement(shapeName);
			}
			
			if (shapeType == null) {
				shapeType = new Label();
				shapeType.text = "(" + shapeNode.shape.displayName + ")";
				viewContainer.addElement(shapeType);
			}
			
			if (propsCombo == null) {
				var addContainer:HGroup = new HGroup();
				addContainer.gap = 2;
				addContainer.paddingBottom = 5;
				addContainer.verticalAlign = VerticalAlign.MIDDLE;
				viewContainer.addElement(addContainer);
				
				propsCombo = new ComboBox();
				propsCombo.labelField = "displayName";
				propsCombo.width = 140;
				var props:Array	= J2BaseModel.getPropInfo(shapeNode.shape);
				props = props.sortOn("displayName");
				propsCombo.dataProvider = new ArrayCollection(props);
				addContainer.addElement(propsCombo);
				
				var butt:Button = new Button();
				butt.label = "+";
				butt.width = 28;
				butt.addEventListener(MouseEvent.CLICK, addProperty, false, 0, true);
				addContainer.addElement(butt);
			}
			
			if (propsList == null) {
				propsList = new DataGroup();
				propsList.percentWidth = 80;
				propsList.layout = new VerticalLayout();
				(propsList.layout as VerticalLayout).gap = 10;
				propsList.itemRenderer = new ClassFactory(bindings.components.ModelPropertyItemRenderer);
				propsList.dataProvider = shapeNode.bindedProps;
				viewContainer.addElement(propsList);
			}
		}
		
		
		protected function addProperty(evt:Event):void {
			var selectedProp:Object = propsCombo.selectedItem;
			if (shapeNode.bindedProps.getItemIndex(selectedProp) != -1)
				return;
			
			shapeNode.addBindedProp(selectedProp);
			propsList.dataProvider = shapeNode.bindedProps;
		}
	}
}