package Swizzard.UI.Controls
{
	import flash.display.GradientType;
	import flash.display.Graphics;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.FocusEvent;
	import flash.events.MouseEvent;
	import flash.geom.Matrix;
	
	import mx.controls.AdvancedDataGrid;
	import mx.controls.advancedDataGridClasses.AdvancedDataGridColumn;
	import mx.controls.advancedDataGridClasses.AdvancedDataGridHeaderInfo;
	import mx.controls.listClasses.IListItemRenderer;
	import mx.core.ClassFactory;
	import mx.core.FlexSprite;
	import mx.core.mx_internal;
	
	import Swizzard.Data.UI.Renderers.SiemensAdvancedGridHeaderRenderer;
	
	import utils.LogUtils;
	
	use namespace mx_internal;
	

	public class SiemensAdvancedDataGrid extends AdvancedDataGrid
	{
		
		public function SiemensAdvancedDataGrid() {
			super();
			setStyle("paddingLeft",		0);
			setStyle("paddingRight",	0);
			setStyle("paddingTop",		0);
			setStyle("paddingBottom",	0);
			setStyle("horizontalGap",	0);
			setStyle("leading",	0);
			this.headerRenderer	= new ClassFactory(SiemensAdvancedGridHeaderRenderer);
		}
		
		
		override protected function focusInHandler(event:FocusEvent):void {			
			// Fixes scrolling bug when the menu is displayed from an item renderer
			// And the user clicks back into the grid.
			
			// Prevent Editing on focus in			
			var isPressedState:Boolean	= mx_internal::isPressed;
			mx_internal::isPressed		= true;
			super.focusInHandler(event);
			mx_internal::isPressed		= isPressedState;
		}
		
		
		override protected function drawSelectionIndicator(indicator:Sprite, x:Number, y:Number, width:Number, height:Number, color:uint, itemRenderer:IListItemRenderer):void {
			super.drawSelectionIndicator(indicator, x, y, width, height, color, itemRenderer);
			indicator.alpha = 0.3;
		}
		
		
		override protected function drawHighlightIndicator(indicator:Sprite, x:Number, y:Number, width:Number, height:Number, color:uint, itemRenderer:IListItemRenderer):void {
			super.drawHighlightIndicator(indicator, x, y, width, height, color, itemRenderer);
			indicator.alpha = 0.3;
		}
		
		
		override protected function configureScrollBars():void {
			try {
				super.configureScrollBars();
			}
			catch (err:Error) {
				// Error Occurred while Configuring scrollbars.
				// There seems to be a bug in the Flex SDK where sometimes the
				// component attempts to configure the scroll bars before
				// the header item renderer is created
				// this causes a null pointer exception to be thrown.
				// Fixes: Issue #1800
				LogUtils.Error(" ********* Data Grid - Error Occurred");
			}
		}
		
		
		override mx_internal function get headerVisible():Boolean {
			// Fixes: Issue #1800
			return super.showHeaders;
		}
		
		/**
		 * Maybe use this to stop things moving to a position 
		 * to the left of a locked column? 
		 * @param event
		 * 
		 */		
		override protected function columnDraggingMouseMoveHandler(event:MouseEvent):void {
			if (event.buttonDown && (lockedColumnCount > 0)) {
				var columnIndex:int	= this.columns.indexOf(movingColumn);
				var preventMove:Boolean;
				
				// Do not allow to move locked columns
				if (columnIndex <= (lockedColumnCount - 1)) {
					preventMove	= true;
				}
				
				if (preventMove) {
					columnDraggingMouseUpHandler(event);
					return;
				}
			}
			super.columnDraggingMouseMoveHandler(event);
		}
		
		
		override mx_internal function shiftColumns(oldIndex:int, newIndex:int, trigger:Event=null):void {
			if (newIndex > -1)
			if (newIndex <= (lockedColumnCount - 1))
				newIndex	= lockedColumnCount;
			super.shiftColumns(oldIndex, newIndex, trigger);
		}		
		
		override protected function childrenCreated():void
		{
			super.childrenCreated();
			
			var s:Sprite = new FlexSprite();
			s.name = "headerSelection";
			selectionLayer.addChild(s);
		}

		override protected function mouseDownHandler(event:MouseEvent):void
		{
			super.mouseDownHandler(event);
			var r:IListItemRenderer;
			var s:Sprite;
			r = mouseEventToItemRenderer(event);
			
			var optimumColumns:Array = getOptimumColumns();
			// if headers are visible and clickable for sorting
			if (enabled && (sortableColumns || draggableColumns)
				&& headerVisible && hasHeaderItemsCreated())
			{
				// find out if we clicked on a header
				var n:int = orderedHeadersList.length;
				var headerItem:IListItemRenderer;
				for( var i:int = 0; i < n && r; i++)
				{
					headerItem = orderedHeadersList[i].headerItem;
					// if we did click on a header
					if(headerItem == r)
					{
						var headerInfo:AdvancedDataGridHeaderInfo = orderedHeadersList[i];
						
						var c:AdvancedDataGridColumn = orderedHeadersList[i].column;
						
						if (sortableColumns && c.sortable)
						{
							s = Sprite(selectionLayer.getChildByName("headerSelection"));
							if (!s)
							{
								s = new FlexSprite();
								s.name = "headerSelection";
								selectionLayer.addChild(s);
							}
							
							var h:Number = r.height + cachedPaddingBottom + cachedPaddingTop;
							var w:Number = r.getExplicitOrMeasuredWidth();
							var x:Number = r.x;
							
							//In case we have scrolled the "selection" shown need to be shifted
							if(headerInfo.actualColNum >= lockedColumnCount)
							{
								x = getAdjustedXPos(r.x);
								// In case of column grouping, it may be partially visible, so need to get the visible width as well as the
								//x pos from which it is visible
								if(horizontalScrollPosition > 0 && headerInfo.actualColNum - horizontalScrollPosition < lockedColumnCount)
								{
									var lockedWidth:Number = 0;
									if(lockedColumnCount > 0)
									{
										var lastLockedInfo:AdvancedDataGridHeaderInfo = getHeaderInfo(_columns[lockedColumnCount-1]);
										lockedWidth = lastLockedInfo.headerItem.x + _columns[lockedColumnCount - 1].width;
									}
									else
									{
										lockedWidth = 0;
									}
									
									w -= (lockedWidth - x);
									x = lockedWidth;
								}
							}
							
							var g:Graphics = s.graphics;

							var colors:Array = [0xE5F5FF,0xD7E3EB];
							var ratios:Array = [ 0, 255 ];
							var alphas:Array = [ 1.0, 1.0 ];
							var matrix:Matrix = new Matrix();
							matrix.createGradientBox(w, h -0.5, Math.PI/2, 0, 0);
							g.clear();
							g.beginGradientFill(GradientType.LINEAR, colors, alphas, ratios, matrix);
							g.lineStyle(0, 0x000000, 0);
							g.moveTo(0, 0);
							g.lineTo(w, 0);
							g.lineTo(w, h - 0.5);
							g.lineStyle(0, getStyle("borderColor"), 100);
							g.lineTo(0, h - 0.5);
							g.lineStyle(0, 0x000000, 0);
							g.endFill();
						}
						return;
					}
				}
			}
		}
		
		override protected function mouseOverHandler(event:MouseEvent):void
		{
			super.mouseOverHandler(event);
			
			if (movingColumn)
				return;
			
			if (!enabled || !selectable)
				return;
			
			var r:IListItemRenderer;
			var n:int;
			if (enabled && headerVisible && getNumColumns() //headerItems.length
				&& !isPressed)
			{
				r = mouseEventToItemRenderer(event);
				n = orderedHeadersList.length;
				
				var headerItem:IListItemRenderer;
				var headerInfo:AdvancedDataGridHeaderInfo;
				var i:int;
				for( i = 0; i < n && r; i++)
				{
					headerItem = orderedHeadersList[i].headerItem;
					if(headerItem == r)
					{
						headerInfo = orderedHeadersList[i];
						if(orderedHeadersList[i].column.sortable)
						{
							var s:Sprite = Sprite(
								selectionLayer.getChildByName("headerSelection"));
							if (!s)
							{
								s = new FlexSprite();
								s.name = "headerSelection";
								selectionLayer.addChild(s);
							}
							var h:Number = r.height + cachedPaddingBottom + cachedPaddingTop;
							var w:Number = r.getExplicitOrMeasuredWidth();
							var x:Number = r.x;
							
							//In case we have scrolled the "selection" shown need to be shifted
							if(headerInfo.actualColNum >= lockedColumnCount)
							{
								x = getAdjustedXPos(r.x);
								// In case of column grouping, it may be partially visible, so need to get the visible width as well as the
								//x pos from which it is visible
								if(horizontalScrollPosition > 0 && headerInfo.actualColNum - horizontalScrollPosition < lockedColumnCount)
								{
									var lockedWidth:Number = 0;
									if(lockedColumnCount > 0)
									{
										var lastLockedInfo:AdvancedDataGridHeaderInfo = getHeaderInfo(_columns[lockedColumnCount-1]);
										lockedWidth = lastLockedInfo.headerItem.x + _columns[lockedColumnCount - 1].width;
									}
									else
										lockedWidth = 0;
									
									w -= (lockedWidth - x);
									x = lockedWidth;
								}
							}
							
							var g:Graphics = s.graphics;
							
							var colors:Array = [0xf0f9ff,0xDFE6EB];
							var ratios:Array = [ 0, 255 ];
							var alphas:Array = [ 1.0, 1.0 ];
							var matrix:Matrix = new Matrix();
							matrix.createGradientBox(w, h -0.5, Math.PI/2, 0, 0);
							g.clear();
							g.beginGradientFill(GradientType.LINEAR, colors, alphas, ratios, matrix);
							g.lineStyle(0, 0x000000, 0);
							g.moveTo(0, 0);
							g.lineTo(w, 0);
							g.lineTo(w, h - 0.5);
							g.lineStyle(0, getStyle("borderColor"), 100);
							g.lineTo(0, h - 0.5);
							g.lineStyle(0, 0x000000, 0);
							g.endFill();
							
						}
						return;
					}
				}
			}
		}
	}
}