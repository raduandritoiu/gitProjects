package Swizzard.System.Mediators
{
	import flash.display.Bitmap;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.utils.setTimeout;
	
	import mx.core.DragSource;
	import mx.core.UIComponent;
	import mx.events.DragEvent;
	import mx.managers.DragManager;
	
	import spark.components.Image;
	import spark.effects.Fade;
	import spark.effects.easing.EaseInOutBase;
	
	import Swizzard.Data.Models.Enumeration.ProductType;
	import Swizzard.Data.Models.Enumeration.Dampers.DamperType;
	import Swizzard.Data.Models.Enumeration.Pneumatic.PneumaticSelection;
	import Swizzard.Data.Models.Enumeration.Valves.ValveType;
	import Swizzard.Data.Models.Query.DamperQueryModel;
	import Swizzard.Data.Models.Query.PneumaticQueryModel;
	import Swizzard.Data.Models.Query.ValveSystemQueryModel;
	import Swizzard.System.ApplicationFacade;
	import Swizzard.System.Persistence.Commands.PersistenceCommand;
	import Swizzard.System.Utils.SwizzardGlobals;
	import Swizzard.UI.Components.ProductDragDropper.IProductContainer;
	import Swizzard.UI.Components.ProductDragDropper.Product;
	import Swizzard.UI.Components.ProductDragDropper.ProductDragDrop;
	import Swizzard.UI.Components.ProductDragDropper.ProductFamily;
	import Swizzard.UI.Events.ProductEvent;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.mediator.Mediator;

	
	public class ProductDragDropMediator extends Mediator
	{
		public static const NAME:String = "ValveDragDropperMediator";
		
		
		// this is the same instance as in application mediator 
		// is used to change (in real time) the query
		public var valveQueryModel:ValveSystemQueryModel;
		public var damperQueryModel:DamperQueryModel;
		public var pneumaticQueryModel:PneumaticQueryModel;
		
		private var productArray:Array;
		private var fadeIn:Fade;
		
		
		public function ProductDragDropMediator(viewComponent:Object=null) {
			super(NAME, viewComponent);
		}
		
		
		public function get view():ProductDragDrop {
			return viewComponent as ProductDragDrop;
		}
		
		
		override public function onRegister():void {
			fadeIn = new Fade();
			fadeIn.alphaTo = 1;
			fadeIn.duration = 500;
			fadeIn.easer = new EaseInOutBase();
			
			// add event listeners on all the rest of the components
			view.addAllButton.addEventListener(MouseEvent.CLICK, onAddButtonClick, false, 0, true);
			view.resetButton.addEventListener(MouseEvent.CLICK, onResetButtonClick, false, 0, true);
			view.productSource.addEventListener(ProductEvent.ADD_ALL, onAddAllClick, false, 0, true);
			view.productSource.addEventListener(ProductEvent.REMOVE_ALL, onRemoveAllClick, false, 0, true);
			
			// add stage event
			view.stage.addEventListener(MouseEvent.MOUSE_UP, stageClickHandler, false, 0, true);
			
			// add event listeners for drag and drop of valves
			view.productTarget.addEventListener(DragEvent.DRAG_ENTER, dragEnterHandler);
			view.productTarget.addEventListener(DragEvent.DRAG_DROP, dragDropHandler);
			
			view.productSource.addEventListener(DragEvent.DRAG_ENTER, dragEnterHandler);
			view.productSource.addEventListener(DragEvent.DRAG_DROP, dragDropHandler);
			
			// initialize the products
			view.productSource.currentProductType = SwizzardGlobals.CURRENT_PRODUCT_TYPE;
			view.productTarget.currentProductType = SwizzardGlobals.CURRENT_PRODUCT_TYPE;
			createProducts();
			callLaterUpdateProductType();
		}
		
		
		private function createProducts():void {
			productArray = [];
			
			// add the Valve type and all the valves
			addNewProduct(ProductType.VALVE, 	"/assets/ValveImages/globe-70x70.png", "Valves");
			addNewFamily(ValveType.PICV, 		1, ProductType.VALVE, "/assets/ValveImages/picv-70x70.png");
			addNewFamily(ValveType.ZONE, 		2, ProductType.VALVE, "/assets/ValveImages/zone-70x70.png");
			addNewFamily(ValveType.GLOBE, 		3, ProductType.VALVE, "/assets/ValveImages/globe-70x70.png");
			addNewFamily(ValveType.BALL, 		4, ProductType.VALVE, "/assets/ValveImages/ball-70x70.png");
			addNewFamily(ValveType.MAGNETIC, 	5, ProductType.VALVE, "/assets/ValveImages/magnetic-70x70.png");
			
			// add the Damper type and all the dampers
			addNewProduct(ProductType.DAMPER, 	"/assets/DamperImages/non_spring_return.png", "Electric Actuators");
			addNewFamily(DamperType.SPRING_RETURN, 		1, ProductType.DAMPER, "/assets/DamperImages/spring_return.png");
			addNewFamily(DamperType.NON_SPRING_RETURN, 	2, ProductType.DAMPER, "/assets/DamperImages/non_spring_return.png");
			addNewFamily(DamperType.FAST_ACTING, 		3, ProductType.DAMPER, "/assets/DamperImages/fast_acting.png");
			addNewFamily(DamperType.FIRE_AND_SMOKE, 	4, ProductType.DAMPER, "/assets/DamperImages/fire_and_smoke.png");
			
			// add the Pneumatic type and all the dampers
			addNewProduct(ProductType.PNEUMATIC, 	"/assets/PneumaticImages/no_6.png", "Pneumatic Actuators");
			addNewFamily(PneumaticSelection.DAMPER, 		1, ProductType.PNEUMATIC, "/assets/PneumaticImages/no_3.png", "Damper Actuators");
			addNewFamily(PneumaticSelection.WITH_POSITIONER, 2, ProductType.PNEUMATIC, "/assets/PneumaticImages/with_positioner.png", "with Positioner");
			addNewFamily(PneumaticSelection.HIGH_FORCE, 	3, ProductType.PNEUMATIC, "/assets/PneumaticImages/high_force.png", "High Force");
			addNewFamily(PneumaticSelection.UL_LISTING, 	4, ProductType.PNEUMATIC, "/assets/PneumaticImages/extra_UL_Cert.png", "Fire and Smoke Listed");
		}
		
		private function addNewProduct(productType:uint, source:String, label:String = null):void {
			var product:Product = new Product();
			product.label 		= label;
			product.productType = productType;
			product.source 		= source;
			view.productSource.addFamily(product);
		}
		
		private function addNewFamily(familyType:uint, pos:int, productType:uint, source:String, label:String = null):void {
			var family:ProductFamily 	= new ProductFamily();
			family.pos 				= pos;
			family.label 			= label;
			family.familyType 		= familyType;
			family.productType 		= productType;
			family.source			= source;
			family.addEventListener(MouseEvent.MOUSE_DOWN, productMouseDown, false, 0, true);
			productArray.push(family);
			view.productSource.addProduct(family);
		}
		
		
		private function stageClickHandler(e:MouseEvent):void {
			if (!view.productSource.opened) return;
			
			var mouseY:Number = e.stageY;
			var lowY:Number = view.localToGlobal(new Point(0, 0)).y;
			var highY:Number = lowY + view.height;
			if (mouseY > lowY && mouseY < highY) return;
			
			var mouseX:Number = e.stageX;
			var lowX:Number = view.productSource.localToGlobal(new Point(0, 0)).x;
			var highX:Number = lowX + view.productSource.width;
			lowY = view.productSource.localToGlobal(new Point(0, 0)).y;
			highY = lowY + view.productSource.height;
			if (mouseX > lowX && mouseX < highX && mouseY > lowY && mouseY < highY) return;
			
			hideSource();
		}
		
		
		private function showSource():void {
			view.productSource.openMain();
		}
		
		private function hideSource():void {
			view.productSource.closeAll();
		}
		
		
		private function productTypeChanged(val:uint):void {
			view.productTarget.currentProductType = val;
			
			view.addAllButton.toolTip = "Add all " + ProductType.toString(val).toLowerCase() + " types to form";
			view.resetButton.toolTip = "Remove all " + ProductType.toString(val).toLowerCase() + " and reset form parameters to default"
		}
		
		
		private function addProducts():void {
			for each (var product:ProductFamily in productArray) {
				if (!product.isAdded && product.productType == view.productTarget.currentProductType) {
					view.productSource.removeProduct(product);
					view.productTarget.addProduct(product);
				}
			}
		}
		
		private function resetProducts(allProducts:Boolean = false):void {
			for each (var product:ProductFamily in productArray) {
				if (product.isAdded && (product.productType == view.productTarget.currentProductType || allProducts)) {
					view.productTarget.removeProduct(product);
					view.productSource.addProduct(product);
				}
			}
		}
		
		
		private function onAddButtonClick(e:Event):void {
			addProducts();
			hideSource();
			timeoutUpdateProductTypes();
		}

		
		// no need to update valve types because RESET will erase the query model
		// also send notification before showing so the show animation works smoothly
		private function onResetButtonClick(e:MouseEvent):void {
			resetProducts();
			sendNotification(ApplicationFacade.RESET_QUERY);
			showSource();
		}
		
		
		private function onAddAllClick(e:Event):void {
			sendNotification(ApplicationFacade.PRODUCT_CHANGED, view.productSource.currentProductType);
			addProducts();
			updateSelectionTypes();
		}
		
		
		private function onRemoveAllClick(e:Event):void {	
			sendNotification(ApplicationFacade.PRODUCT_CHANGED, view.productSource.currentProductType);
			resetProducts();
			updateSelectionTypes();
		}
		
		
		/** Handlers for drag events */		
		
		private var startMoveX:Number = 0;
		private var startMoveY:Number = 0;
		private var dragDistance:Number = 3;
		
		private function productMouseDown(e:MouseEvent):void {
			startMoveX = e.stageX;
			startMoveY = e.stageY;
			e.currentTarget.addEventListener(MouseEvent.MOUSE_UP, productMouseUp, false, 0, true);
			e.currentTarget.addEventListener(MouseEvent.MOUSE_MOVE, productMouseMove, false, 0, true);
		}
		
		
		private function productMouseUp(event:MouseEvent):void {
			event.currentTarget.removeEventListener(MouseEvent.MOUSE_UP, productMouseUp);
			event.currentTarget.removeEventListener(MouseEvent.MOUSE_MOVE, productMouseMove);
			
			var product:ProductFamily = event.currentTarget as ProductFamily;
			if (product == null) return;
				
			// the way the logic is designed this IF will never be TRUE here
			if (!product.isDragging) {
				sendNotification(ApplicationFacade.PRODUCT_CHANGED, product.productType);
				changeProductParent(product);
				callLaterUpdateProductType();
			}
				
			product.isDragging = false;
		}
		
		
		private function changeProductParent(product:ProductFamily):void {
			if (product.isAdded) {
				//valve is in Target
				view.productTarget.removeProduct(product);
				view.productSource.addProduct(product);
			} 
			else {
				//valve is in Source
				view.productSource.removeProduct(product);
				view.productTarget.addProduct(product);
			}
		}
		
		
		private function productMouseMove(evt:MouseEvent):void {
			if (Math.abs(evt.stageX - startMoveX) < dragDistance || 
				Math.abs(evt.stageY - startMoveY) < dragDistance) return;
			
			// The component you click on will become the candidate for D-N-D
			var product:ProductFamily = evt.currentTarget as ProductFamily;
			if (product == null) return;
			
			// show the drop down
			showSource();
			
			// Add a proxy image here for drag manager to use if you would like!
			var bitmap:Bitmap = new Bitmap(product.productImage.bitmapData.clone());
			var image:Image = new Image();
			image.source = bitmap;
			
			// because the product is a UIComponent we will sent it as the dragInitiator, 
			// so no need for any DragSource formats
			var dragSource:DragSource = new DragSource();
			
			// Let the drag manager take care of the rest - with a visual indicator of what its dragging...
			DragManager.doDrag(product, dragSource, evt, image);
			
			product.isDragging = true;
			product.removeEventListener(MouseEvent.MOUSE_MOVE, productMouseMove);
			product.removeEventListener(MouseEvent.MOUSE_UP, productMouseUp);
		}
		
		
		private function dragEnterHandler(e:DragEvent):void {
			var dragProduct:ProductFamily = e.dragInitiator as ProductFamily;
			if (dragProduct == null)
				return;
			
			var dropTarget:IProductContainer = e.currentTarget as IProductContainer;
	    
			// we are moving in the correct container
			if ((dropTarget == view.productSource && dragProduct.isAdded) ||
				(dropTarget == view.productTarget && !dragProduct.isAdded)) {
				DragManager.acceptDragDrop(dropTarget);
				DragManager.showFeedback(DragManager.COPY);
			}						
		}
		
		
		private function dragDropHandler(e:DragEvent):void {
			var dragProduct:ProductFamily = e.dragInitiator as ProductFamily;
			var dropTarget:IProductContainer = e.currentTarget as IProductContainer;
			
			// we are moving in the correct container
			if ((dropTarget == view.productSource && dragProduct.isAdded) ||
				(dropTarget == view.productTarget && !dragProduct.isAdded)) {
				
				dragProduct.isDragging = false;
				dragProduct.alpha = 0;
				
				// change the current selected product type
				sendNotification(ApplicationFacade.PRODUCT_CHANGED, dragProduct.productType);
				
				// in stead of these I could use end drag drop
				var dragSource:IProductContainer = (dragProduct.isAdded ? view.productTarget : view.productSource);
				dragSource.removeProduct(dragProduct);
				dropTarget.addProduct(dragProduct);
				
				fadeIn.end();
				fadeIn.play([dragProduct]);
				
				callLaterUpdateProductType();
			}
		}
		
		
		private function timeoutUpdateProductTypes():void {
			setTimeout(updateSelectionTypes, 650);
		}
		// I hate callLater plus I hate using the view for this
		private function callLaterUpdateProductType():void {
			UIComponent(view).callLater(updateSelectionTypes);
		}
		
		private function updateSelectionTypes():void  {
			if (valveQueryModel == null) return;
			
			var selection:Array = new Array();
			for (var i:int = 0; i < productArray.length; i++) {
				var product:ProductFamily = productArray[i];
				if (product.isAdded && product.productType == view.productTarget.currentProductType) {
					selection.push(product.familyType);
				}
			}
			
			if (view.productTarget.currentProductType == ProductType.VALVE){
				valveQueryModel.valve.valveTypes = selection;
			}
			else if (view.productTarget.currentProductType == ProductType.DAMPER){
				damperQueryModel.damperTypes = selection;
			}
			else if (view.productTarget.currentProductType == ProductType.PNEUMATIC){
				PneumaticSelection.AdjustSelection(pneumaticQueryModel, selection);
			}
		}
		
		
		
		// ------------ Mediator implementation Functions --------------------
		
		override public function listNotificationInterests():Array {
			return [PersistenceCommand.NEW_PROJECT, ApplicationFacade.PRODUCT_CHANGED];
		}
		
		
		override public function handleNotification(notification:INotification):void {
			switch (notification.getName()) {
				case PersistenceCommand.NEW_PROJECT:
					resetProducts(true);
					showSource();
					break;
				
				case ApplicationFacade.PRODUCT_CHANGED:
					var newProductType:uint = notification.getBody() as uint;
					productTypeChanged(newProductType);
					break;
			}
		}
	}
}