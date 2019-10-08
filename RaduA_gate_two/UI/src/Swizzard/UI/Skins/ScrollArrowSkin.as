package Swizzard.UI.Skins
{

import flash.display.Graphics;

import mx.controls.scrollClasses.ScrollBar;
import mx.skins.Border;

/**
 *  The skin for all the states of the up or down button in a ScrollBar.
 */
public class ScrollArrowSkin extends Border
{
	
	

	//--------------------------------------------------------------------------
	//
	//  Constructor
	//
	//--------------------------------------------------------------------------

	/**
	 *  Constructor.
	 */
	public function ScrollArrowSkin()
	{
		super();
	}

    //--------------------------------------------------------------------------
    //
    //  Overridden properties
    //
    //--------------------------------------------------------------------------

    //----------------------------------
	//  measuredWidth
    //----------------------------------
    
    /**
     *  @private
     */    
    override public function get measuredWidth():Number
    {
        return 10;//ScrollBar.THICKNESS;
    }
    
    //----------------------------------
	//  measuredHeight
    //----------------------------------
    
    /**
     *  @private
     */        
    override public function get measuredHeight():Number
    {
        return 10;//ScrollBar.THICKNESS;
    }
	
	//--------------------------------------------------------------------------
	//
	//  Overridden methods
	//
	//--------------------------------------------------------------------------

	/**
	 *  @private
	 */
	override protected function updateDisplayList(w:Number, h:Number):void
	{
		super.updateDisplayList(w, h);
		
		var upArrow:Boolean = (name.charAt(0) == 'u');

		var horizontal:Boolean = parent &&
								 parent.parent &&
								 parent.parent.rotation != 0;
		
	

		//------------------------------
		//  background
		//------------------------------
		
		var g:Graphics = graphics;
		g.clear();
		
		g.beginFill(0,0);
		g.drawCircle(w*0.5,h*0.5,7);
		g.endFill();
		
		if(name.search("ArrowUpSkin") !== -1) {
			
			// Draw up or down arrow --- UP
			g.beginFill(0x757575);
			if (upArrow)
			{
				g.moveTo(2,7);
				g.lineTo(8,7);
				g.lineTo(5,2);
				g.lineTo(2,7);
			}
			else
			{		
				g.moveTo(2,3);
				g.lineTo(8,3);
				g.lineTo(5,8);
				g.lineTo(2,3);
			}
			g.endFill();
			
		}
		
		if(name.search("ArrowOverSkin") !== -1) {
			
			// Draw up or down arrow  --- OVER
			g.beginFill(0x999999);
			if (upArrow)
			{
				g.moveTo(2,7);
				g.lineTo(8,7);
				g.lineTo(5,2);
				g.lineTo(2,7);
			}
			else
			{		
				g.moveTo(2,3);
				g.lineTo(8,3);
				g.lineTo(5,8);
				g.lineTo(2,3);
			}
			g.endFill();
			
			
		}
		
		
		if(name.search("ArrowDownSkin") !== -1) {
			
			// Draw up or down arrow  --- DOWN
			g.beginFill(0x353535);
			if (upArrow)
			{
				g.moveTo(2,7);
				g.lineTo(8,7);
				g.lineTo(5,2);
				g.lineTo(2,7);
			}
			else
			{		
				g.moveTo(2,3);
				g.lineTo(8,3);
				g.lineTo(5,8);
				g.lineTo(2,3);
			}
			g.endFill();
			
			
		}
		
		
	
	}
}

}
