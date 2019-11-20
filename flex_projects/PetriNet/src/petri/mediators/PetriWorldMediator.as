package petri.mediators
{
	import com.joeberkovitz.moccasin.controller.DragMediator;
	import com.joeberkovitz.moccasin.view.ViewContext;
	
	import flash.geom.Point;
	
	import mx.collections.ArrayList;
	import mx.events.DragEvent;
	import mx.managers.DragManager;
	
	import petri.controller.PetriController;
	import petri.model.PetriBaseModel;
	import petri.utils.ModelData;
	import petri.utils.PetriModels;
	import petri.view.PetriWorldView;

	public class PetriWorldMediator extends DragMediator implements IViewMediator
	{
		private var _worldView:PetriWorldView
		private var _context:ViewContext;
		
		
		public function PetriWorldMediator(context:ViewContext, view:PetriWorldView) {
			super(context);
			_worldView = view;
			_context = context;
		}
		
		
		public function handleViewEvents():void  {
			_worldView.addEventListener(DragEvent.DRAG_ENTER, handleDragEnter, true, 0, true);
		}
		
		
		public function removeViewEvents():void {
		}
		
		
		protected function get worldView():PetriWorldView {
			return _worldView;
		}
		
		
		protected function get controller():PetriController {
			return _context.controller as PetriController;
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
			
			if (notAcceptItems(items))
				return;
			
			_worldView.addEventListener(DragEvent.DRAG_OVER, handleDragOver, false, 0, true);
			_worldView.addEventListener(DragEvent.DRAG_DROP, handleDragDrop, false, 0, true);
			_worldView.addEventListener(DragEvent.DRAG_EXIT, handleDragExit, false, 0, true);
			
			DragManager.acceptDragDrop(worldView);
			DragManager.showFeedback(DragManager.COPY);
		}
		
		
		protected function handleDragOver(event:DragEvent):void {
			DragManager.acceptDragDrop(worldView);
		}
		
		
		protected function handleDragExit(event:DragEvent):void {
			_worldView.removeEventListener(DragEvent.DRAG_OVER, handleDragOver, false);
			_worldView.removeEventListener(DragEvent.DRAG_DROP, handleDragDrop, false);
			_worldView.removeEventListener(DragEvent.DRAG_EXIT,	handleDragExit, false);
		}
		
		
		protected function handleDragDrop(e:DragEvent):void	{
			_worldView.removeEventListener(DragEvent.DRAG_OVER, handleDragOver, false);
			_worldView.removeEventListener(DragEvent.DRAG_DROP, handleDragDrop, false);
			_worldView.removeEventListener(DragEvent.DRAG_EXIT, handleDragExit, false);
			
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
			
			if (notAcceptItems(items))
				return;
			
			var dropPoint:Point = new Point(e.localX, e.localY);
			
			for (var i:int = 0; i < items.length; i++) {
				var petriModel:PetriBaseModel = PetriModels.createModel(items[i] as ModelData);
				petriModel.x = dropPoint.x;
				petriModel.y = dropPoint.y;
				controller.addModelToActiveContainer(petriModel);
			}
		}
		
		
		protected function notAcceptItems(items:Array):Boolean {
			for (var i:int = 0; i < items.length; i++) {
				if (!(items[i] is ModelData)) {
					return true;
				}
			}
			return false;
		}
	}
}