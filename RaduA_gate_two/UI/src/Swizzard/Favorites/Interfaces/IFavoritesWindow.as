package Swizzard.Favorites.Interfaces
{
	import mx.controls.DataGrid;
	
	import spark.components.Button;
	
	
	public interface IFavoritesWindow
	{
		function get list():DataGrid;
		function get removeButton():Button;
		
		function set title(str:String):void;
		function set infoText(str:String):void;
		
		
		function open(openWindowActive:Boolean = true):void;
		function orderToFront():Boolean;
		function callLater(method:Function, args:Array = null):void;
	}
}