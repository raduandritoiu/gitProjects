package petri.model
{
	import mx.collections.IList;

	public class PetriLayer extends PetriContainerModel
	{
		public function PetriLayer() {
			super();
			
			name = "Layer " + cnt;
		}
	}
}