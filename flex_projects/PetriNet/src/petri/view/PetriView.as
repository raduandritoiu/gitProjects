package petri.view
{
	import com.joeberkovitz.moccasin.model.MoccasinModel;
	import com.joeberkovitz.moccasin.view.SelectableComponent;
	import com.joeberkovitz.moccasin.view.ViewContext;
	
	import flash.display.DisplayObject;
	
	import mx.collections.ArrayList;
	import mx.graphics.SolidColor;
	
	import petri.controller.PetriController;
	import petri.editor.feedback.BoxFeedbackView;
	import petri.mediators.IViewMediator;
	import petri.model.PetriBaseModel;
	
	import spark.components.Group;
	import spark.primitives.Rect;
	
	public class PetriView extends SelectableComponent
	{
		protected var _backGr:Group;
		protected var _color:uint = 0x888888;
		protected var mediators:ArrayList;
		

		public function PetriView(context:ViewContext, model:MoccasinModel) {
			super(context, model);
		}
		
		
		override protected function createChildren():void {
			if (_backGr == null) {
				_backGr = new Group();
				_backGr.percentHeight = _backGr.percentWidth = 100;
				addChild(_backGr);
				
				var rect:Rect = new Rect();
				rect.percentHeight = rect.percentWidth = 100;
				rect.fill = new SolidColor(_color);
				_backGr.addElement(rect);
			}
			
			super.createChildren();
			
			updateView();
		}
		
		
		public function get petriModel():PetriBaseModel {
			return model.value as PetriBaseModel;
		}
		
		
		public function addMediator(mediator:IViewMediator):void {
			if (mediators == null) {
				mediators = new ArrayList();
			}
			mediator.handleViewEvents();
			mediators.addItem(mediator);
		}
		
		
		public function rolledOver():void {
		}
		
		
		public function rolledOut():void {
		}
		
		
		protected function get controller():PetriController {
			return context.controller as PetriController;
		}
		
		
		override protected function createFeedbackView():DisplayObject {
			return new BoxFeedbackView(context, model);
		}
		
		
		override protected function updateModelProperty(property:Object, oldValue:Object, newValue:Object):Boolean {
			var flag:Boolean = super.updateModelProperty(property, oldValue, newValue);
			if (flag) {
				return flag;
			}
			
			switch (property.toString()) {
				case "name":
					flag = true;
					break;
				
				case "tokens":
					flag = true;
					break;
			}
			
			return flag;
		}
		
		
		override protected function updateView():void {
			super.updateView();
			
			move(petriModel.x, petriModel.y);
			width = petriModel.width;
			height = petriModel.height;
			
			invalidateDisplayList();
			invalidateParentSizeAndDisplayList();
		}
		
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
			super.updateDisplayList(unscaledWidth,unscaledHeight);
			_backGr.width = unscaledWidth;
			_backGr.height = unscaledHeight;
		}
	}
}