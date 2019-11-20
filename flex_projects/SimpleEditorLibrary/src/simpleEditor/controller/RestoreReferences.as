package diagram.controller
{
	import diagram.model.DiagramBaseModel;
	import diagram.model.DiagramContainerModel;
	import diagram.model.DiagramLink;
	import diagram.model.DiagramSlot;
	
	import flash.utils.Dictionary;
	
	import mx.collections.IList;
	
	
	public class RestoreReferences
	{
		private static var copiedRefs:Dictionary;
		
		
		public function RestoreReferences()
		{}
		
		
		public static function restore(models:IList, useOriginalUid:Boolean = true):void {
			copiedRefs = new Dictionary();
			buildCopiedRefs(models, useOriginalUid);
			
			// restore references between models
			var link:DiagramLink;
			var slot:DiagramSlot;
			for each (var model:DiagramBaseModel in copiedRefs) {
				if (model is DiagramLink) {
					link = model as DiagramLink;
					if (copiedRefs.hasOwnProperty(link.fromRef)) {
						link.from = copiedRefs[link.fromRef] as DiagramSlot;
					} 
					if (copiedRefs.hasOwnProperty(link.toRef)) {
						link.to = copiedRefs[link.toRef] as DiagramSlot;
					}
				}
			}
		}
		
		
		private static function buildCopiedRefs(models:IList, useOriginalUid:Boolean):void {
			for each (var model:DiagramBaseModel in models) {
				if (useOriginalUid) {
					copiedRefs[model.originalUid] = model;
				}
				else {
					copiedRefs[model.uid] = model;
				}
				if (model is DiagramContainerModel) {
					buildCopiedRefs((model as DiagramContainerModel).children, useOriginalUid);
				}
			}
		}

	}
}