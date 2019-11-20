package petri.view
{
	import com.joeberkovitz.moccasin.model.MoccasinModel;
	import com.joeberkovitz.moccasin.view.ViewContext;
	
	import mx.graphics.SolidColor;
	import mx.graphics.SolidColorStroke;
	
	import petri.model.PetriState;
	
	import spark.components.Group;
	import spark.components.Label;
	import spark.primitives.Ellipse;
	
	public class PetriStateView extends PetriViewWithHooks
	{
		private var ellipse:Ellipse;
		private var tokenLabel:Label;
		private var tokenGr:Group;
		
		public function PetriStateView(context:ViewContext, model:MoccasinModel) {
			super(context, model);
		}
		
		
		public function get state():PetriState {
			return petriModel as PetriState;
		}
		
		
		override protected function createChildren():void {
			super.createChildren();
			
			_backGr.removeAllElements();
			
			if (ellipse == null) {
				ellipse = new Ellipse();
				ellipse.percentHeight = 100;
				ellipse.percentWidth = 100;
				ellipse.stroke = new SolidColorStroke(0, 3);
				ellipse.fill = new SolidColor(_color);
				
				_backGr.addElement(ellipse);
			}
			
			if (tokenLabel == null) {
				tokenLabel = new Label();
				tokenLabel.setStyle("color", 0xff0000);
				tokenLabel.setStyle("fontSize", 20);
				tokenLabel.text = state.tokens + "";
				tokenLabel.x = width / 2;
				tokenLabel.y = height / 2;
				_backGr.addElement(tokenLabel);
			}
			
			if (tokenGr == null) {
				tokenGr = new Group();
				var l:Number = Math.ceil(state.width / Math.SQRT2);
				tokenGr.width = tokenGr.height = l;
				tokenGr.x = tokenGr.y = (state.width - l) / 2
				drawTokens();
				addChild(tokenGr);
			}
		}
		
		
		override protected function updateModelProperty(property:Object, oldValue:Object, newValue:Object):Boolean {
			var flag:Boolean = false;
			switch (property.toString()) {
				case "tokens":
					tokenLabel.text = state.tokens + "";
					tokenLabel.x = width / 2 - tokenLabel.width / 2;
					drawTokens();
					flag = true;
					break;
			}
			
			flag = super.updateModelProperty(property, oldValue, newValue);
			return flag;
		}
		
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
			super.updateDisplayList(unscaledWidth,unscaledHeight);
			tokenLabel.x = width / 2 - tokenLabel.width / 2;
			tokenLabel.y = height - tokenLabel.height / 2;
		}
		
		
		private function drawTokens():void {
			tokenGr.removeAllElements();
			
			if (state.tokens <= 0)
				return;
			
			var cnt:int = Math.ceil(Math.sqrt(state.tokens));
			var pace:Number = Math.min(Math.ceil(tokenGr.width / cnt), 20);
			for (var i:int = 0; i < state.tokens; i++) {
				var circle:Ellipse = new Ellipse();
				circle.height = 
				circle.width = pace - 3;
				circle.fill = new SolidColor(0x960662);
				circle.x = (i % cnt) * pace;
				circle.y = Math.floor(i / cnt) * pace;
				tokenGr.addElement(circle);
			}
		}
	}
}