package petri.controller.operations
{
	import com.joeberkovitz.moccasin.service.AbstractOperation;
	import com.joeberkovitz.moccasin.service.MoccasinDocumentData;
	
	import flash.events.Event;
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	
	import petri.model.PetriWorld;
	
	public class SaveOperation extends AbstractOperation
	{
		private var _filePath:String;
		private var _documentData:MoccasinDocumentData;
		private var resultXML:XML;
		
		public function SaveOperation(documentData:MoccasinDocumentData, filePath:String) {
			super();
			_documentData = documentData;
			_filePath = filePath;
		}
		
		
		override public function execute():void {
			var world:PetriWorld = _documentData.root.value as PetriWorld;
			resultXML = world.toXML();
			
			var file:File = File.applicationStorageDirectory;
			file = file.resolvePath(_filePath);
			var stream:FileStream = new FileStream();
			stream.open(file, FileMode.WRITE);
			stream.writeUTFBytes(resultXML.toString());
			stream.close();
			
			handleComplete(new Event(Event.COMPLETE));
		}
		
		
		override public function get result():* {
			return resultXML;
		}
	}
}