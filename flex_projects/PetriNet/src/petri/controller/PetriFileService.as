package petri.controller
{
	import com.joeberkovitz.moccasin.service.AbstractOperation;
	import com.joeberkovitz.moccasin.service.IMoccasinDocumentService;
	import com.joeberkovitz.moccasin.service.IOperation;
	import com.joeberkovitz.moccasin.service.MoccasinDocumentData;
	
	import petri.controller.operations.LoadOperation;
	import petri.controller.operations.SaveOperation;
	
	public class PetriFileService implements IMoccasinDocumentService
	{
		private var filePath:String;
		
		
		public function PetriFileService() {
		}
		
		
		public function loadDocument(documentUri:String):IOperation {
			return new LoadOperation(documentUri);
		}
		
		
		public function set saveFilePath(value:String):void {
			filePath = value;
		}
		
		
		public function saveDocument(documentData:MoccasinDocumentData):IOperation {
			return new SaveOperation(documentData, filePath);
		}
		
		
		public function getAssetURL(assetUri:String):String {
			return null;
		}
	}
}