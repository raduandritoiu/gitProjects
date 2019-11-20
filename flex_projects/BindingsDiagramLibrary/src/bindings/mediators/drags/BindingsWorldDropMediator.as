package bindings.mediators.drags
{
	import diagram.mediators.drags.DiagramWorldDropMediator;
	import diagram.utils.DataModel;
	import diagram.view.DiagramWorldView;
	
	import j2inn.builder.mapping.interfaces.IPropertyTranslator;
	import j2inn.builder.mapping.interfaces.IVirtualPoint;
	import j2inn.builder.model.J2BaseModel;
	
	import moccasin.view.ViewContext;
	
	
	public class BindingsWorldDropMediator extends DiagramWorldDropMediator
	{
		public function BindingsWorldDropMediator(viewContext:ViewContext, view:DiagramWorldView) {
			super(viewContext, view);
		}
		
		
		override protected function acceptItems(items:Array):Boolean {
			for (var i:int = 0; i < items.length; i++) {
				var found:Boolean = false;
				if (items[i] is DataModel) {
					found = true;
				}
				if (items[i] is J2BaseModel) {
					found = true;
				}
				if (items[i] is IVirtualPoint) {
					found = true;
				}
				if (items[i] is IPropertyTranslator) {
					found = true;
				}
				if (!found) {
					return false;
				}
			}
			return true;
		}
	}
}