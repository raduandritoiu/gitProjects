package petri.model
{
	public class MarkingState
	{
		private var _uid:String;
		private var _tokens:Number;
		private var _state:PetriState;
		
		public function MarkingState(state:PetriState, uid:String = null, tokens:Number = -1)
		{
			_state = state;
			if (_state != null) {
				_uid = state.uid;
				_tokens = state.tokens;
			}
			if (uid != null) {
				_uid = uid;
			}
			if (tokens >= 0) {
				_tokens = tokens;
			}
		}
		
		
		public function get state():PetriState {
			return _state;
		}
		
		
		public function get tokens():Number {
			return _tokens;
		}
		
		
		public function get uid():String {
			return _uid;
		}
	}
}