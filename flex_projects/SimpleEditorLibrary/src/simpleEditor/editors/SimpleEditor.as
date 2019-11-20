package simpleEditor.editors
{
	import com.joeberkovitz.moccasin.controller.IMoccasinController;
	import com.joeberkovitz.moccasin.editor.MoccasinEditor;
	import com.joeberkovitz.moccasin.service.BasicConfigurationService;
	import com.joeberkovitz.moccasin.service.IMoccasinDocumentService;
	import com.joeberkovitz.moccasin.view.IMoccasinView;
	import com.joeberkovitz.moccasin.view.ViewContext;
	
	import diagram.mediators.IViewMediator;
	import diagram.view.WorldView;
	
	import simpleEditor.controller.SimpleController;
	
	
	public class SimpleEditor extends MoccasinEditor
	{
		
		public function SimpleEditor() {
			super();
			configurationService = new BasicConfigurationService(null, null);
		}
		
		
		public function get simpleController():SimpleController {
			return controller as SimpleController;
		}
		
		
		override protected function createController():IMoccasinController {
			return new SimpleController(null, this);
		}
		
		
		override protected function createDocumentService():IMoccasinDocumentService {
			return null;
		}
		
		
		override protected function createDocumentView(context:ViewContext):IMoccasinView {
			var worldView:WorldView = new WorldView(context, moccasinDocument.root);
			var worldMediator:IViewMediator = new WorldDropMediator(context, worldView);
			worldView.addMediator(worldMediator);			
			
			worldMediator = new WorldToolMediator(context, worldView);
			worldView.addMediator(worldMediator);			
			return worldView;
		}
		
		
		override protected function createChildren():void {
			super.createChildren();
			viewLayer.horizontalCenter = 0;
			viewLayer.verticalCenter = 0;
		}
		
		
		protected function createWordView():
	}
}