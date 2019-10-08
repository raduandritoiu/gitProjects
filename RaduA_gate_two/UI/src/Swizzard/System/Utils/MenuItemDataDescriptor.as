package Swizzard.System.Utils
{
	import mx.controls.treeClasses.DefaultDataDescriptor;
	
	
	public class MenuItemDataDescriptor extends DefaultDataDescriptor
	{
		public function MenuItemDataDescriptor() {
			super();
		}
		
		override public function isBranch(node:Object, model:Object=null):Boolean
		{
			if (node is MenuItem)
				return MenuItem(node).isBranch;
			
			return false;
		}
	}
}