package utils
{
	public class LogUtils
	{
		private static const error:Boolean = true;
		private static const debug:Boolean = false;
		private static const info:Boolean = false;
		private static const log:Boolean = false;
		private static const testing:Boolean = true;
		
		public static function Error(str:Object):void {
			trace(str);
		}
		
		public static function Debug(str:Object):void {
//			trace(str);
		}
		
		public static function Info(str:String):void {
			trace(str);
		}
		
		public static function Log(str:String):void {
			trace(str);
		}
		
		
		//=============================
		public static function ValveSQL(str:Object):void {
//			trace(str);
		}
		public static function SQLResults(str:Object):void {
//			trace(str);
		}
		public static function CvTesting(str:Object):void {
//			trace(str);
		}
		public static function UiWorkflow(str:Object):void {
//			trace(str);
		}
		public static function Schedule(str:Object):void {
//			trace(str);
		}
	}
}