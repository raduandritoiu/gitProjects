package Swizzard.System.Utils
{
	public class NumericUtils
	{
		public function NumericUtils()
		{
		}
		
		public static function isDigit(character:String):Boolean
		{
			return (character >= '0' && character <= '9');
		}
	}
}