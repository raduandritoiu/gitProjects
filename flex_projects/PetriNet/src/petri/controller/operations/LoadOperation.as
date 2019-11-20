package petri.controller.operations
{
	import com.joeberkovitz.moccasin.model.ModelRoot;
	import com.joeberkovitz.moccasin.service.AbstractOperation;
	import com.joeberkovitz.moccasin.service.MoccasinDocumentData;
	
	import flash.events.Event;
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	
	import petri.model.PetriWorld;
	
	public class LoadOperation extends AbstractOperation
	{
		private var _filePath:String;
		private var resultDocumentData:MoccasinDocumentData;
		
		
		public function LoadOperation(filePath:String) {
			super();
			_filePath = filePath;
		}
		
		
		override public function execute():void {
			var file:File = File.applicationStorageDirectory;
			file = file.resolvePath(_filePath);
			var stream:FileStream = new FileStream();
			stream.open(file, FileMode.READ);
			var resultXML:XML = new XML(stream.readUTFBytes(stream.bytesAvailable));
			stream.close();

			var world:PetriWorld = new PetriWorld();
			world.fromXML(resultXML, world);
			
			resultDocumentData = new MoccasinDocumentData(new ModelRoot(world), null)
			handleComplete(new Event(Event.COMPLETE));
		}
		
		
		override public function get result():* {
			return resultDocumentData;
		}
	}
}