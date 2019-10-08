package Swizzard.Data.Models
{
	import Swizzard.Images.ReferenceIcons;
	
	import mx.collections.ArrayCollection;
	import mx.collections.IList;

	public class CrossReferenceProduct extends AbstractSiemensProduct
	{
		private var _competitorName:String;
		private var _competitorPartNumber:String;
		private var _siemensPartNumber:String;
		private var _siemensParts:IList;
		
		private var _isOpen:Boolean;
		
		public function CrossReferenceProduct()
		{
			super();
			
			_siemensParts	= new ArrayCollection();
		}
		
		override public function get icon():Class
		{
			var iconClass:Class;
			
			switch (competitorName.toLowerCase())
			{
				case "belimo":
				case "belimo picv":
				{
					iconClass	= ReferenceIcons.BELIMO_IMG;
					break;
				}
					
				case "schneider-invensys-tac":
				{
					iconClass	= ReferenceIcons.SCHNEIDER_IMG;
					break;
				}
					
				case "hw":
				case "honeywell picv":
				{
					iconClass	= ReferenceIcons.HONEYWELL_IMG;
					break;
				}
					
				case "griswold pic-v":
				{
					iconClass	= ReferenceIcons.GRISWOLD_IMG;
					break;
				}
					
				case "siemens":
				{
					iconClass	= ReferenceIcons.SIEMENS_IMG;
					break;
				}					
					
				case "jci":
				case "jci picv":
				{
					iconClass	= ReferenceIcons.JOHNSON_IMG;
					break;
				}
					
				case "other":
				default:
					iconClass	= ReferenceIcons.OTHER_IMG;
			}
			
			return iconClass;
		}

		public function get competitorName():String
		{
			return _competitorName;
		}

		public function set competitorName(value:String):void
		{
			_competitorName = value;
		}

		public function get competitorPartNumber():String
		{
			return _competitorPartNumber;
		}

		public function set competitorPartNumber(value:String):void
		{
			_competitorPartNumber = value;
		}
		
		public function get siemensPartNumber():String
		{
			return _siemensPartNumber;
		}
		
		public function set siemensPartNumber(value:String):void
		{
			_siemensPartNumber = value;
		}

		public function get siemensParts():IList
		{
			return _siemensParts;
		}

		public function set siemensParts(value:IList):void
		{
			_siemensParts = value;
		}

		public function get isOpen():Boolean
		{
			return _isOpen;
		}

		public function set isOpen(value:Boolean):void
		{
			_isOpen = value;
		}
		
		public function toAbstract():AbstractSiemensProduct
		{
			var p:AbstractSiemensProduct	= new AbstractSiemensProduct();
			p.id					= this.id;
			p.partNumber			= this.partNumber;
			p.productType			= this.productType;
			p.extendedProductType 	= this.extendedProductType;
			p.actualPartNumber		= this.actualPartNumber;
			p.sapPartNumber			= this.sapPartNumber;
			p.description			= this.description;
			p.manufacturer			= this.manufacturer;
			p.model					= this.model;
			p.vendor				= this.vendor;
			p.isObsolete			= this.isObsolete;
			p.lastModified			= this.lastModified;
			p.subProductPartNumber	= this.subProductPartNumber;
			p.subProductPrice		= this.subProductPrice;
			p.documents				= this.documents;
			p.assemblyOnly			= this.assemblyOnly;
			
			return p;
		}		

	}
}