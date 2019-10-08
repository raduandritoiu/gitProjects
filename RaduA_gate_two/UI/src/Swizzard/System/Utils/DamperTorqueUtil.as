package Swizzard.System.Utils
{
	import mx.collections.ArrayCollection;
	
	import Swizzard.Data.Models.Enumeration.Dampers.DamperType;
	
	
	public class DamperTorqueUtil
	{
		public static const MAX_TORQUE_SPRING_RETURN:Number 	= 160;
		public static const MAX_TORQUE_NON_SPRING_RETURN:Number = 310;
		public static const MAX_TORQUE_FIRE_AND_SMOKE:Number 	= Number.MAX_VALUE;
		public static const MAX_TORQUE_FAST_ACTING:Number 		= Number.MAX_VALUE;
		
		
		public static const MAX_QTY_MULT_SPRING_RETURN:Number 		= 4;
		public static const MAX_QTY_MULT_NON_SPRING_RETURN:Number 	= 4;
		public static const MAX_QTY_MULT_FIRE_AND_SMOKE:Number 		= 1;
		public static const MAX_QTY_MULT_FAST_ACTING:Number 		= 1;
		

		public static const RECTANGULAR:String 	= "Rectangular";
		public static const ROUND:String 		= "Round";
		
		
		public static const FPM_1:Number  = 1200;
		public static const FPM_2:Number  = 2500;
		public static const FPM_3:Number  = 3000;
		
		
		public static const OPPOSED_NO_SEALS:String 	= "Opposed blades no seals";
		public static const PARALLEL_NO_SEALS:String 	= "Parallel blades no seals";
		public static const OPPOSED_SEALS:String 		= "Opposed blades with seals";
		public static const PARALLEL_SEALS:String 		= "Parallel blades with seals";
		
		
		public static const AreaTypes:ArrayCollection 	= new ArrayCollection([RECTANGULAR, ROUND]);
		public static const Fpms:ArrayCollection 		= new ArrayCollection([FPM_1, FPM_2, FPM_3]);
		public static const BladeTypes:ArrayCollection 	= new ArrayCollection([OPPOSED_NO_SEALS, PARALLEL_NO_SEALS, OPPOSED_SEALS, PARALLEL_SEALS]);
		
		
		private static const AirFlowTable:Array = [
			3, 			4.5, 		6, 			6,
			4, 			6, 			8, 			8,
			6, 			7.5, 		10, 		10,
			8.5, 		10.5, 		14, 		14];
		
		
		public static function CalculateN(bladeType:String, fpm:Number):Number {
			var i:int = -1;
			var j:int = -1;
			
			if 		(bladeType == OPPOSED_NO_SEALS) 	i = 0;
			else if (bladeType == PARALLEL_NO_SEALS) 	i = 1;
			else if (bladeType == OPPOSED_SEALS) 		i = 2;
			else if (bladeType == PARALLEL_SEALS) 		i = 3;
			
			if 		(fpm <= FPM_1) 	j = 0;
			else if (fpm <= FPM_2) 	j = 1;
			else if (fpm <= FPM_3) 	j = 2;
			else 					j = 3;
			
			if (i < 0) 
				return 1;
			return AirFlowTable[i * 4 + j];
		}
		
		
		public static function AdjustSearchTorque(damperType:uint, requiredTorque:Number):Number {
			var maxTorque:Number 	= Number.MAX_VALUE;
			var maxQtyMult:Number 	= 1;
			
			// determine parameters according to damper type
			if (damperType == DamperType.SPRING_RETURN) {
				maxTorque 	= MAX_TORQUE_SPRING_RETURN;
				maxQtyMult 	= MAX_QTY_MULT_SPRING_RETURN;
			}
			else if (damperType == DamperType.NON_SPRING_RETURN) {
				maxTorque 	= MAX_TORQUE_NON_SPRING_RETURN;
				maxQtyMult 	= MAX_QTY_MULT_NON_SPRING_RETURN;
			}
			else if (damperType == DamperType.FIRE_AND_SMOKE) {
				maxTorque 	= MAX_TORQUE_FIRE_AND_SMOKE;
				maxQtyMult 	= MAX_QTY_MULT_FIRE_AND_SMOKE;
			}
			else if (damperType == DamperType.FAST_ACTING) {
				maxTorque 	= MAX_TORQUE_FAST_ACTING;
				maxQtyMult 	= MAX_QTY_MULT_FAST_ACTING;
			}
			
			// calculate torque
			if (requiredTorque < maxTorque) {
				return requiredTorque;
			}
			else if (requiredTorque < maxTorque * maxQtyMult) {
				return maxTorque;
			}
			else {
				return requiredTorque;
			}
			
			return requiredTorque;
		}
		
		
		public static function AdjustQuantity(damperType:uint, torque:Number, requiredTorque:Number):int {
			var maxTorque:Number 	= Number.MAX_VALUE;
			var adjustedQty:Number 	= 1;
			
			// determine parameters according to damper type
			if (damperType == DamperType.SPRING_RETURN) {
				maxTorque 	= MAX_TORQUE_SPRING_RETURN;
			}
			else if (damperType == DamperType.NON_SPRING_RETURN) {
				maxTorque 	= MAX_TORQUE_NON_SPRING_RETURN;
			}
			else if (damperType == DamperType.FIRE_AND_SMOKE) {
				maxTorque 	= MAX_TORQUE_FIRE_AND_SMOKE;
			}
			else if (damperType == DamperType.FAST_ACTING) {
				maxTorque 	= MAX_TORQUE_FAST_ACTING;
			}

			// calculate quantity multiplier
			if (torque >= maxTorque) {
				adjustedQty = requiredTorque / torque;
				adjustedQty = Math.ceil(adjustedQty);
			}	
			return adjustedQty;
		}
	}
}