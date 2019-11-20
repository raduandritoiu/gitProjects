package simpleEditor.view
{
	import com.joeberkovitz.moccasin.model.MoccasinModel;
	import com.joeberkovitz.moccasin.view.SelectableComponent;
	import com.joeberkovitz.moccasin.view.ViewContext;
	
	import simpleEditor.model.BaseModel;
	
	import spark.components.Group;
	import spark.primitives.Rect;
	
	
	public class BaseView extends SelectableComponent
	{
		protected var dragMediator:DiagramViewDragMediator;
		protected var mediators:Array;
		protected var container:Group;
		
		protected var backgroundRect:Rect;
		protected var backgroundColor:int = 0x888888;
		protected var useDefaultBackground:Boolean = true;
		
		
		public function BaseView(context:ViewContext, model:MoccasinModel) {
			super(context, model);
		}
		
		
		public function get diagramModel():BaseModel {
			return model.value as BaseModel;
		}
		
		
		public function addMediator(mediator:IViewMediator):void {
			if (mediators == null) {
				mediators = new Array();
			}
			mediator.handleViewEvents();
			mediators.addItem(mediator);
		}
		
		
		public function addDragMediator(mediator:DiagramViewDragMediator):void {
			if (dragMediator == mediator)
				return;
			dragMediator = mediator;
			addMediator(mediator);
		}
		
		
		public function getDragMediator():DiagramViewDragMediator {
			return dragMediator;
		}
		
		
		protected function get controller():DiagramController {
			return context.controller as DiagramController;
		}
		
		
		override protected function createFeedbackView():DisplayObject {
			return new DiagramFeedbackView(context, model, this);
		}
		
		
		override protected function createChildren():void {
			super.createChildren();
			
			if (container == null) {
				container = new Group();
				container.width = diagramModel.width;
				container.height = diagramModel.height;
				super.addChild(container);
				
				if (useDefaultBackground) {
					backgroundRect = new Rect();
					backgroundRect.fill = new SolidColor(backgroundColor);
					backgroundRect.stroke = new SolidColorStroke(0, 1);
					backgroundRect.percentHeight = 100;
					backgroundRect.percentWidth = 100;
					container.addElement(backgroundRect);
				}
			}
		}
		
		
		override protected function updateModelProperty(property:Object, oldValue:Object, newValue:Object):Boolean {
			var flag:Boolean = super.updateModelProperty(property, oldValue, newValue);
			if (flag) {
				return flag;
			}
			
			switch (property.toString()) {
				case "name":
				case "minWidth":
				case "minHeight":
					flag = true;
					break;
				
				case "x":
				case "y":
					move(diagramModel.x, diagramModel.y);
					flag = true;
					break;
				
				case "width":
					width = diagramModel.width;
					flag = true;
					break;
				
				case "height":
					height = diagramModel.height;
					flag = true;
					break;
			}
			
			return flag;
		}
		
		
		override protected function updateView():void {
			super.updateView();
			
			move(diagramModel.x, diagramModel.y);
			width = diagramModel.width;
			height = diagramModel.height;
			
			invalidateDisplayList();
			invalidateParentSizeAndDisplayList();
		}
		
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
			super.updateDisplayList(unscaledWidth,unscaledHeight);
			
			container.width = unscaledWidth;
			container.height = unscaledHeight;
		}
	}
}