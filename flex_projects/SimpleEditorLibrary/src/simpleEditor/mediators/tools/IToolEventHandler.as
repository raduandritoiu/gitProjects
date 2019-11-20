package diagram.mediators.tools
{
	import flash.events.MouseEvent;

	public interface IToolEventHandler
	{
		function handleMouseDownCapture(e:MouseEvent):void;
		function handleMouseDownBubble(e:MouseEvent):void;
		
		function handleClickCapture(e:MouseEvent):void;
		function handleClickBubble(e:MouseEvent):void;
		
		function handleDragStartCapture(e:MouseEvent):void;
		function handleDragStartBubble(e:MouseEvent):void;
		
		function handleDragMoveCapture(e:MouseEvent):void;
		function handleDragMoveBubble(e:MouseEvent):void;
		
		function handleDragEndCapture(e:MouseEvent):void;
		function handleDragEndBubble(e:MouseEvent):void;
		
		function handleMouseMove(e:MouseEvent):void;
		function handleDoubleClick(e:MouseEvent):void;
		
		function handleRollOver(e:MouseEvent):void;
		function handleRollOut(e:MouseEvent):void;
		
		function handleMouseWheel(e:MouseEvent):void;
		
		function closeTool():void;
	}
}