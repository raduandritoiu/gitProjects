package petri.view
{
	import com.joeberkovitz.moccasin.model.MoccasinModel;
	import com.joeberkovitz.moccasin.view.ViewContext;
	
	import flash.display.DisplayObject;
	import flash.geom.Point;
	
	import mx.graphics.SolidColorStroke;
	
	import petri.components.utils.CurvedArrow;
	import petri.editor.feedback.ArcFeedbackView;
	import petri.model.PetriArc;
	
	import spark.components.Label;
	
	public class PetriArcView extends PetriView
	{
		private var arrow:CurvedArrow;
		private var tokenLabel:Label;
		
		public function PetriArcView(context:ViewContext, model:MoccasinModel) {
			super(context, model);
			
			_color = 0x000000;
		}
		
		
		public function get arc():PetriArc {
			return petriModel as PetriArc;
		}
		
		
		override protected function createChildren():void {
			super.createChildren();
			
			_backGr.removeAllElements();
			
			if (arrow == null) {
				arrow = new CurvedArrow();
				arrow.stroke = new SolidColorStroke(_color, 2);
				arrow.startOrient = arc.fromOrient;
				arrow.endOrient = arc.toOrient;
				computePathData();
				_backGr.addElement(arrow);
			}
			
			if (tokenLabel == null) {
				tokenLabel = new Label();
				tokenLabel.setStyle("color", 0xff0000);
				tokenLabel.setStyle("fontSize", 20);
				tokenLabel.text = arc.tokens + "";
				tokenLabel.x = width / 2 - tokenLabel.width / 2;
				tokenLabel.y = height / 2 - tokenLabel.height / 2;
				_backGr.addElement(tokenLabel);
			}
		}
		
		
		override protected function updateModelProperty(property:Object, oldValue:Object, newValue:Object):Boolean {
			var flag:Boolean = false;
			switch (property.toString()) {
				case "tokens":
					tokenLabel.text = arc.tokens + "";
					flag = true;
					break;
				
				case "fromOrient":
					arrow.startOrient = arc.fromOrient;
					break;
					
				case "toOrient":
					arrow.endOrient = arc.toOrient;
					break;
			}
			
			flag = super.updateModelProperty(property, oldValue, newValue);
			return flag;
		}
		
		protected function computePathData():void {
			var xDiff:Number = arc.toPos.x - arc.fromPos.x;
			var yDiff:Number = arc.toPos.y - arc.fromPos.y;
			var xInit:Number = 0;
			var yInit:Number = 0;
			
			if (xDiff < 0) {
				xInit = -xDiff;
				xDiff = 0;
			}
			if (yDiff < 0) {
				yInit = -yDiff;
				yDiff = 0;
			}
			
			arrow.startPoint = new Point(xInit, yInit);
			arrow.endPoint = new Point(xDiff, yDiff);
		}
		
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
			super.updateDisplayList(unscaledWidth,unscaledHeight);
			computePathData();
			tokenLabel.x = width / 2 - tokenLabel.width / 2;
			tokenLabel.y = height / 2- tokenLabel.height / 2;
		}
		
		
		override protected function createFeedbackView():DisplayObject {
			return new ArcFeedbackView(context, model);
		}
	}
}