package petri.utils
{
	public class ModelData
	{
		private var _label:String;
		private var _type:String;
		
		
		public function ModelData(label:String, type:String) {
			_label = label;
			_type = type;
		}
		
		
		public function set label(value:String):void {
			_label	= value;
		}
		
		public function get label():String {
			return _label;
		}
		
		
		public function set type(value:String):void {
			_type	= value;
		}
		
		public function get type():String {
			return _type;
		}
	}
}