package petri.editor
{
	import com.joeberkovitz.moccasin.controller.IMoccasinController;
	import com.joeberkovitz.moccasin.document.MoccasinDocument;
	import com.joeberkovitz.moccasin.editor.MoccasinEditor;
	import com.joeberkovitz.moccasin.model.ModelRoot;
	import com.joeberkovitz.moccasin.service.FileDocumentService;
	import com.joeberkovitz.moccasin.service.IMoccasinDocumentService;
	import com.joeberkovitz.moccasin.service.IOperation;
	import com.joeberkovitz.moccasin.service.MoccasinDocumentData;
	import com.joeberkovitz.moccasin.view.IMoccasinView;
	import com.joeberkovitz.moccasin.view.ViewContext;
	
	import flash.events.Event;
	
	import mx.core.UIComponent;
	
	import petri.controller.PetriController;
	import petri.controller.PetriFileService;
	import petri.mediators.PetriWorldMediator;
	import petri.model.PetriWorld;
	import petri.view.PetriWorldView;
	
	public class PetriEditor extends MoccasinEditor
	{
		public function PetriEditor()
		{
			super();
		}
		
		
		override public function initializeEditor():void {
			super.initializeEditor();
			loadFromDocumentData(new MoccasinDocumentData(new ModelRoot(new PetriWorld()), null));	
		}
		
		
		override protected function createController():IMoccasinController {
			return new PetriController(null);
			moccasinDocument = new MoccasinDocument(new ModelRoot(new PetriWorld()));
		}
		
		
		override protected function createDocumentService():IMoccasinDocumentService {
			return new PetriFileService();
		}
		
		
		override protected function createDocumentView(context:ViewContext):IMoccasinView {
			var worldView:PetriWorldView = new PetriWorldView(context, moccasinDocument.root);
			var worldMediator:PetriWorldMediator = new PetriWorldMediator(context, worldView);
			worldView.addMediator(worldMediator);			
			return worldView;
		}
		
		
		override protected function createChildren():void {
			super.createChildren();
			viewLayer.horizontalCenter = 0;
			viewLayer.verticalCenter = 0;
		}
		
		
		public function saveDocumentToFile(filePath:String):void {
			(documentService as PetriFileService).saveFilePath = filePath;
			super.saveDocument();
		}
		
		
		public function remove():void {
			petriController.remove();
		}
		
		
		public function get petriController():PetriController {
			return controller as PetriController;
		}
	}
}