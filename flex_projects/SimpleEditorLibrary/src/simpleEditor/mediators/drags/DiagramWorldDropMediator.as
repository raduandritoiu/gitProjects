package diagram.mediators.drags
{
	import diagram.controller.DiagramController;
	import diagram.model.DiagramBaseModel;
	import diagram.factory.DataModel;
	import diagram.factory.DiagramDataFactory;
	import diagram.factory.DiagramModelFactory;
	import diagram.factory.IDataModel;
	import diagram.view.DiagramWorldView;
	
	import flash.geom.Point;
	
	import moccasin.controller.DragMediator;
	import moccasin.view.ViewContext;
	
	import mx.collections.ArrayList;
	import mx.events.DragEvent;
	import mx.managers.DragManager;
	import diagram.mediators.IViewMediator;
	
	
	public class DiagramWorldDropMediator implements IViewMediator
	{
		protected var worldView:DiagramWorldView;
		protected var context:ViewContext;
		
		public function DiagramWorldDropMediator(viewContext:ViewContext, view:DiagramWorldView) {
			context = viewContext;
			worldView = view;
		}
		
		
		protected function get controller():DiagramController {
			return context.controller as DiagramController;
		}
		
		
		public function handleViewEvents():void  {
			worldView.addEventListener(DragEvent.DRAG_ENTER, handleDragEnter, true, 0, true);
		}
		
		
		public function removeViewEvents():void {
		}
		
		
		protected function handleDragEnter(evt:DragEvent):void {
			var format:String = evt.dragSource.formats[0] as String;
			var orgitems:* = evt.dragSource.dataForFormat(format);
			
			var items:Array = [];
			if (!orgitems || orgitems.length < 1)
				return;
			if (orgitems is Array)
				items = orgitems;
			if (orgitems is Vector.<Object>){				
				for (var i:int = 0; i < orgitems.length; i++){
					items.push(orgitems[i]);
				}
			}
			
			if (!acceptItems(items))
				return;
			
			worldView.addEventListener(DragEvent.DRAG_OVER, handleDragOver, false, 0, true);
			worldView.addEventListener(DragEvent.DRAG_DROP, handleDragDrop, false, 0, true);
			worldView.addEventListener(DragEvent.DRAG_EXIT, handleDragExit, false, 0, true);
			
			DragManager.acceptDragDrop(worldView);
			DragManager.showFeedback(DragManager.COPY);
		}
		
		
		protected function handleDragOver(event:DragEvent):void {
			DragManager.acceptDragDrop(worldView);
		}
		
		
		protected function handleDragExit(event:DragEvent):void {
			worldView.removeEventListener(DragEvent.DRAG_OVER, handleDragOver, false);
			worldView.removeEventListener(DragEvent.DRAG_DROP, handleDragDrop, false);
			worldView.removeEventListener(DragEvent.DRAG_EXIT,	handleDragExit, false);
		}
		
		
		protected function handleDragDrop(e:DragEvent):void	{
			worldView.removeEventListener(DragEvent.DRAG_OVER, handleDragOver, false);
			worldView.removeEventListener(DragEvent.DRAG_DROP, handleDragDrop, false);
			worldView.removeEventListener(DragEvent.DRAG_EXIT, handleDragExit, false);
			
			var format:String	= e.dragSource.formats[0] as String;
			var orgitems:*		= e.dragSource.dataForFormat(format);
			var items:Array = [];
			if (!orgitems || orgitems.length < 1)
				return;
			if (orgitems is Array)
				items = orgitems;
			if (orgitems is Vector.<Object>){				
				for (var i:int = 0; i < orgitems.length; i++){
					items.push(orgitems[i]);
				}
			}
			
			if (!acceptItems(items))
				return;
			
			var dropPoint:Point = new Point(e.localX, e.localY);
			
			for (var i:int = 0; i < items.length; i++) {
				var dataModel:IDataModel = DiagramDataFactory.createData(items[i]);
				var diagramModel:DiagramBaseModel = DiagramModelFactory.createModel(dataModel);
				diagramModel.x = dropPoint.x;
				diagramModel.y = dropPoint.y;
				controller.addModelToSelectedContainer(diagramModel);
			}
		}
		
		
		protected function acceptItems(items:Array):Boolean {
			for (var i:int = 0; i < items.length; i++) {
				if (!(items[i] is DataModel)) {
					return false;
				}
			}
			return true;
		}
	}
}