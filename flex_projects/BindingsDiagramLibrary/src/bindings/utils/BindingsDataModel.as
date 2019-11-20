package bindings.utils
{
	import diagram.utils.DataModel;
	
	public class BindingsDataModel extends DataModel
	{
		private var _instance:Object;
		
		
		public function BindingsDataModel(label:String, type:String, objInstance:Object) {
			super(label, type);
			_instance = objInstance;
		}
		
		
		public function set instance(value:Object):void {
			_instance = value;
		}
		
		public function get instance():Object {
			return _instance;
		}
	}
}