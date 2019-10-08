package Swizzard.UI
{
	import Swizzard.UI.Skins.AddButtonSkin;
	
	import flash.display.DisplayObject;
	import flash.events.MouseEvent;
	import flash.filters.ColorMatrixFilter;
	import flash.filters.GlowFilter;
	
	import mx.controls.Button;
	import mx.core.mx_internal;
	
	
	public class AddButton extends Button
	{
		private var iconCopy:DisplayObject;
		private var disabledIconCopy:DisplayObject;
		private var isOver:Boolean;
		
		
		public function AddButton()
		{
			super();
			width = 40;
			height = 38;
			setStyle("skin", AddButtonSkin);
		}
		
		
		override protected function rollOverHandler(event:MouseEvent):void
		{
			super.rollOverHandler(event);
			isOver = true;
		}
		
		
		override protected function rollOutHandler(event:MouseEvent):void
		{
			super.rollOutHandler(event);
			isOver = false;
		}
		
		
		override protected function updateDisplayList(w:Number, h:Number):void
		{
			super.updateDisplayList(w,h);
			
			if (!enabled) 
			{
				var m:Array = [0.5, 0, 0, 0, 50,   0, 0.5, 0, 0, 50,   0, 0, 0.5, 0, 50,  0, 0, 0, 1, -150];
				mx_internal::currentIcon.filters = [new ColorMatrixFilter(m)];
			} 
			else if (enabled && isOver) 
			{
				this.filters = [new GlowFilter(0xffffff, 1, 10, 10, 2, 3)];
			} 
			else 
			{
				this.filters = [];
			}
		}
	}
}