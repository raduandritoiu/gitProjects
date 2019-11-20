package simpleEditor.view
{
	import com.joeberkovitz.moccasin.model.MoccasinModel;
	import com.joeberkovitz.moccasin.view.ViewContext;
	
	import spark.components.Group;
	
	
	public class ContainerView extends BaseView
	{
		protected var childrenContainer:Group;
		
		
		public function ContainerView(context:ViewContext, model:MoccasinModel) {
			super(context, model);
		}
		
		
		public function get diagramContainer():DiagramContainerModel {
			return model.value as DiagramContainerModel;
		}
		
		
		override protected function createChildren():void {
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
			
			if (childrenContainer == null) {
				childrenContainer = new Group();
				childrenContainer.width = diagramModel.width;
				childrenContainer.height = diagramModel.height;
				super.addChild(childrenContainer);
			}
			
			super.createChildren();
		}
		
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			
			childrenContainer.width = unscaledWidth;
			childrenContainer.height = unscaledHeight;
		}
		
		
		override protected function handleModelChange(e:ModelEvent):void {
			if (e.parent != model || stage == null)
				return;
			
			var childView:DiagramBaseView;
			if (e.kind == ModelEvent.REMOVE_CHILD_MODEL)
					childView = getChildAt(e.index) as DiagramBaseView;

			super.handleModelChange(e);
			
			if (childView != null)
				controller.viewRemoved(childView);
		}
	
		
		override public function createChildView(child:MoccasinModel):IMoccasinView {
			var childView:DiagramBaseView = DiagramViewFactory.createView(context, child);
			controller.viewAdded(childView);
			return childView;
		}
		
		
		override public function addChild(child:DisplayObject):DisplayObject {
			return childrenContainer.addElement(child as IVisualElement) as DisplayObject;
		}
		
		
		override public function addChildAt(child:DisplayObject, index:int):DisplayObject {
			return childrenContainer.addElementAt(child as IVisualElement,index) as DisplayObject;
		}
		
		
		override public function removeChild(child:DisplayObject):DisplayObject {
			return childrenContainer.removeElement(child as IVisualElement) as DisplayObject;
		}
		
		
		override public function removeChildAt(index:int):DisplayObject {
			return childrenContainer.removeElementAt(index) as DisplayObject;
		}
		
		
		override public function getChildAt(index:int):DisplayObject {
			return childrenContainer.getElementAt(index) as DisplayObject;
		}
		
		
		override public function getChildIndex(child:DisplayObject):int {
			return childrenContainer.getElementIndex(child as IVisualElement);
		}
		
		
		override public function get numChildren():int {
			if (childrenContainer == null)
				return 0;
			return childrenContainer.numElements;
		}
	}
}