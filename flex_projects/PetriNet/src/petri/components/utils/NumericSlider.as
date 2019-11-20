package petri.components.utils
{
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.FocusEvent;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.ui.Keyboard;
	
	import flashx.textLayout.formats.VerticalAlign;
	
	
	import mx.core.UIComponent;
	import mx.events.NumericStepperEvent;
	import mx.formatters.NumberFormatter;
	import mx.utils.StringUtil;
	
	import spark.components.Label;
	import spark.components.TextInput;
	
	[Event(name="change", type="mx.events.NumericStepperEvent")]
	
	public class NumericSlider extends UIComponent
	{
		
		private var _label:Label;
		private var _input:TextInput;
		private var _integerOnly:Boolean = true;
		private var lastValidTxt:String;
		
		public function NumericSlider()
		{
			super();
		}
		
		private var nf:NumberFormatter;
		override protected function createChildren():void
		{
			super.createChildren();
			
			if(!_label)
			{
				_label					= new Label();
				_label.text				= '0';
				_label.useHandCursor	= true;
				_label.buttonMode		= true;
				_label.mouseChildren	= false;
				_label.setStyle("verticalAlign", VerticalAlign.MIDDLE);
				/*_label.setStyle("color", 0x87BBFF);
				_label.setStyle("disabledColor", 0xACACAC);*/
				_label.addEventListener(MouseEvent.MOUSE_DOWN, onLabelMouseDown, false, 0, true);
				addChild(_label);
			}
			
			if(!_input)
			{
				_input			= new TextInput();
				_input.width	= 40;
				_input.addEventListener(FocusEvent.FOCUS_OUT, onInputFocusLost, false, 0, true);
				_input.addEventListener(KeyboardEvent.KEY_DOWN, onInputKeyDown, false, 0, true);
				_input.addEventListener(Event.CHANGE, onTextChange, false, 0, true);
			}
		}
		
		override protected function commitProperties():void
		{
			super.commitProperties();
			
			if (valueUpdated)
			{
				valueUpdated	= false;
				
				
				_label.text		= Math.round(_value).toString();
				lastValidTxt 	= Math.round(_value).toString();
			}
			
			if (enableUpdated)
			{
				enableUpdated		= false;
				_label.enabled		= enabled;
				var txtColor:uint	= enabled ? 0x87bbff : 0xacacac;
				_label.setStyle("color", txtColor);
			}
			
			if(minUpdated)
			{
				minUpdated	= false;
				if(_value < _minimum)
				{
					_value		= _minimum;
					_label.text	= Math.round(_value).toString();
				}
			}
			
			if(maxUpdated)
			{
				maxUpdated	= false;
				if(_value > _maximum)
				{
					_value		= _maximum;
					_label.text	= Math.round(_value).toString();
				}
			}
			
		}
		
		private var enableUpdated:Boolean;
		override public function set enabled(value:Boolean):void
		{
			super.enabled	= value;
			enableUpdated	= true;
			invalidateProperties();
		}
		
		override public function get measuredHeight():Number
		{
			return UIComponent.DEFAULT_MEASURED_MIN_HEIGHT;
		}
		
		override public function get measuredWidth():Number
		{
			return UIComponent.DEFAULT_MEASURED_MIN_WIDTH;
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void
		{
			super.updateDisplayList(w, h);
			
			if(contains(_label))
			{
				var labelHeight:Number	= _label.getExplicitOrMeasuredHeight() > 22 ? _label.getExplicitOrMeasuredHeight() : 22;
				
				_label.setActualSize(_label.getExplicitOrMeasuredWidth(), labelHeight);
				_label.move(0, 0);
				
				var dl:DashedLine	= new DashedLine(this, 2, 2);
				dl.lineStyle(1, 0xa0a0a0, 1);
				dl.moveTo(0, _label.height - 3);
				dl.lineTo(_label.width, _label.height - 3);
			}
			
			if(contains(_input))
			{
				graphics.clear();
				_input.setActualSize(_input.getExplicitOrMeasuredWidth(), _input.getExplicitOrMeasuredHeight());
				_input.move(0, 0);
			}
		}
		
		/*-------------------------------------------------//
		//	value
		//-------------------------------------------------*/
		
		/**
		 * @private
		 * Storage for the value property
		 */
		private var _value:Number	= 0;
		
		/**
		 * The numeric value of the control
		 */
		public function get value():Number
		{
			if (_value < minimum)
				return minimum;
			
			if (_value > maximum)
				return maximum;
			
			return _value;
		}
		
		private var valueUpdated:Boolean;
		[Bindable]
		public function set value(val:Number):void
		{
			if(_value == val)
				return;
			
			_value			= val;
			valueUpdated	= true;
			invalidateProperties();
		}
		
		/*-----------------------------------------------//
		//	maximum
		//-----------------------------------------------*/
		
		private var _maximum:Number;
		/**
		 * The maximum value that can be displayed by the control.
		 * @default 20000
		 */
		public function get maximum():Number
		{
			if(!isNaN(_maximum))
				return _maximum;
			else
				return 20000;
		}
		
		private var maxUpdated:Boolean;
		/**
		 * @private
		 */
		public function set maximum(value:Number):void
		{
			_maximum	= value;
			maxUpdated	= true;
			invalidateProperties();
		}
		
		/*--------------------------------------------//
		//	minimum
		//-------------------------------------------*/
		
		private var _minimum:Number;
		
		/**
		 * The minimum value that can be displayed by the control.
		 * @default -20000
		 */
		public function get minimum():Number
		{
			if(!isNaN(_minimum))
				return _minimum;
			else
				return -20000;
		}
		
		private var minUpdated:Boolean;
		/**
		 * @private
		 */
		public function set minimum(value:Number):void
		{
			_minimum	= value;
			minUpdated	= true;
			invalidateProperties();
		}
		
		private var initialPoint:Point;
		/**
		 * @private
		 */
		private function onLabelMouseDown(e:MouseEvent):void
		{
			if(!enabled)
				return;
			
			initialPoint	= new Point(e.stageX, e.stageY);
			
			(parentApplication as DisplayObject).stage.addEventListener(MouseEvent.MOUSE_UP, onMouseUp, false, 0, true);
			(parentApplication as DisplayObject).stage.addEventListener(MouseEvent.MOUSE_MOVE, onMouseMove, false, 0, true);
		}
		
		private function onMouseUp(e:MouseEvent):void
		{
			(parentApplication as DisplayObject).stage.removeEventListener(MouseEvent.MOUSE_UP, onMouseUp);
			(parentApplication as DisplayObject).stage.removeEventListener(MouseEvent.MOUSE_MOVE, onMouseMove);
			
			var p:Boolean	= _label.hitTestPoint(e.stageX, e.stageY);
			if(p)
			{
				removeChild(_label);
				addChild(_input);
				
				_input.text	= value.toString();
				
				_input.setFocus();
				_input.selectAll();
				
				(parentApplication as DisplayObject).addEventListener(MouseEvent.MOUSE_DOWN, afterInputMouseDown, false, 0, true);
				stage.addEventListener(MouseEvent.MOUSE_DOWN, afterInputMouseDown, false, 0, true);
				invalidateDisplayList();
			}
		}
		
		private function afterInputMouseDown(e:MouseEvent):void
		{
			var p:Boolean	= _input.hitTestPoint(e.stageX, e.stageY);
			if(!p)
			{
				(parentApplication as DisplayObject).removeEventListener(MouseEvent.MOUSE_DOWN, afterInputMouseDown);
				if(stage)
					stage.removeEventListener(MouseEvent.MOUSE_DOWN, afterInputMouseDown);
				(parentApplication as DisplayObject).stage.focus	= null;
			}
		}
		
		private function onInputKeyDown(e:KeyboardEvent):void
		{
			if(e.keyCode == Keyboard.ENTER)
			{
				(parentApplication as DisplayObject).removeEventListener(MouseEvent.MOUSE_DOWN, afterInputMouseDown);
				if(stage)
					stage.removeEventListener(MouseEvent.MOUSE_DOWN, afterInputMouseDown);
				(parentApplication as DisplayObject).stage.focus	= null;
			}
		}
		
		private function onMouseMove(e:MouseEvent):void
		{
			var cp:Point	= new Point(e.stageX, e.stageY);
			var delX:int	= Math.floor(cp.x - initialPoint.x);
			
			var tempVal:Number	= ((value + delX) >= minimum && (value + delX) <= maximum) ? value + delX : _value;
			
			setValue(tempVal);
			
			initialPoint	= cp;
		}
		
		private function onTextChange(e:Event):void 
		{
			var txt:String	= StringUtil.trim(_input.text);
			if ((txt == ".") || (txt == "-")) {
				lastValidTxt = _input.text;
				return;
				
			}
			
			var value:Number	= parseFloat(txt);
						
			setValue(value);
			
			if (!valueUpdated)
				lastValidTxt = _input.text;
		}
		
		/**
		 * @private
		 */
		private function onInputFocusLost(e:FocusEvent=null):void
		{
			
			if (contains(_input))
			{
				removeChild(_input);
				addChild(_label);
				invalidateDisplayList();
			}
			
			_input.text	=
			_label.text	= value.toString(); 
		}
		
		private function setValue(value:Number):void
		{
			
			if(value == _value)
				return;
			if(integerOnly){
				value = int(value);
			}
			this.value	= value;
			
			var nsce:NumericStepperEvent	= new NumericStepperEvent(NumericStepperEvent.CHANGE, false, false, _value);
			dispatchEvent(nsce);
		}

		public function get integerOnly():Boolean
		{
			return _integerOnly;
		}

		public function set integerOnly(value:Boolean):void
		{
			_integerOnly = value;
		}

	}
}