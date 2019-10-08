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
import flash.geom.Matrix;

import mx.skins.Border;
import mx.styles.StyleManager;
import mx.utils.ColorUtil;

/**
 *  The skin for all the states of the thumb in a ScrollBar.
 */
public class ScrollThumbSkin extends Border
{
	
	//--------------------------------------------------------------------------
	//
	//  Constructor
	//
	//--------------------------------------------------------------------------

	/**
	 *  Constructor.
	 */
	public function ScrollThumbSkin()
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
		return 5;
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
		
		var horizontal:Boolean = parent &&
			parent.parent &&
			parent.parent.rotation != 0;
		
		graphics.clear();                  
		
		var m:Matrix = new Matrix();
		
		m.createGradientBox(w,h, (horizontal) ?  -Math.PI : -Math.PI);
		
		switch (name)
		{
			default:
			case "thumbUpSkin":
			{
				
				graphics.beginFill(0x83a9c1, 0.6);
				graphics.drawRoundRectComplex(0,0,w,h, h/2,h/2,h/2,h/2);
				graphics.endFill();
				
				graphics.beginGradientFill("linear", [0xb9def5, 0xb1d2e8, 0xb8daf0], [1,1,1], [0,125,255], m);
				graphics.drawRoundRectComplex(1,1,w - 2, h - 2, (h-2)/2,(h-2)/2,(h-2)/2,(h-2)/2);
				graphics.endFill();
				
				break;
			}
			
			case "thumbOverSkin":
			{
				
				graphics.beginFill(0x83a9c1, 0.6);
				graphics.drawRoundRectComplex(0,0,w,h, h/2,h/2,h/2,h/2);
				graphics.endFill();
				
				graphics.beginGradientFill("linear", [ColorUtil.adjustBrightness(0xb9def5, 5), ColorUtil.adjustBrightness(0xb1d2e8, 5), ColorUtil.adjustBrightness(0xb8daf0, -5)], [1,1,1], [0,125,255], m);
				graphics.drawRoundRectComplex(1,1,w - 2, h - 2, (h-2)/2,(h-2)/2,(h-2)/2,(h-2)/2);
				graphics.endFill();
				break;
			}
			
			case "thumbDownSkin":
			{				
				
				graphics.beginFill(0x83a9c1, 0.6);
				graphics.drawRoundRectComplex(0,0,w,h, h/2,h/2,h/2,h/2);
				graphics.endFill();
				
				graphics.beginGradientFill("linear", [ColorUtil.adjustBrightness(0xb9def5, -5), ColorUtil.adjustBrightness(0xb1d2e8, -5), ColorUtil.adjustBrightness(0xb8daf0, -5)], [1,1,1], [0,125,255], m);
				graphics.drawRoundRectComplex(1,1,w - 2, h - 2, (h-2)/2,(h-2)/2,(h-2)/2,(h-2)/2);
				graphics.endFill();
									
				break;
			}
			
			case "thumbDisabledSkin":
			{
				
				break;
			}
		}
		
	}
}

}
