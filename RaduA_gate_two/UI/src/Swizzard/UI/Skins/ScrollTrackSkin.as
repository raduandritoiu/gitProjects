////////////////////////////////////////////////////////////////////////////////
//
//  ADOBE SYSTEMS INCORPORATED
//  Copyright 2004-2007 Adobe Systems Incorporated
//  All Rights Reserved.
//
//  NOTICE: Adobe permits you to use, modify, and distribute this file
//  in accordance with the terms of the license agreement accompanying it.
//
////////////////////////////////////////////////////////////////////////////////

package Swizzard.UI.Skins
{

import flash.display.GradientType;

import mx.core.FlexVersion;
import mx.skins.Border;
import mx.styles.StyleManager;
import mx.utils.ColorUtil;

/**
 *  The skin for the track in a ScrollBar.
 */
public class ScrollTrackSkin extends Border
{
	//--------------------------------------------------------------------------
	//
	//  Constructor
	//
	//--------------------------------------------------------------------------

	/**
	 *  Constructor.
	 */
	public function ScrollTrackSkin()
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
        return 10;
    }
    
    //----------------------------------
	//  measuredHeight
    //----------------------------------
    
    /**
     *  @private
     */        
    override public function get measuredHeight():Number
    {
        return 1;
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
		
		graphics.clear();
	
		var padding:int = 10;
		
		graphics.beginFill(0, 0.15);
		graphics.drawRect(0,0,1,h);
		
		
		drawRoundRect(0,0,w,h, 0, [0,0], [0.015,0.1], horizontalGradientMatrix(0,0,w,h), "linear", [0,255]);
		graphics.endFill();
		
		graphics.beginFill(0, .2);
		graphics.drawRoundRectComplex(0, padding-1, w, h-padding*2+2, 5,5,5,5);
				
		drawRoundRect(0,padding, w, h-padding*2, 5, [0xcacaca, 0xECECEC, 0xECECEC, 0xcacaca], [1,1,1,1], horizontalGradientMatrix(0,0,w,h), "linear", [0, 75, 200, 255]);
		graphics.endFill();
	}
}

}
