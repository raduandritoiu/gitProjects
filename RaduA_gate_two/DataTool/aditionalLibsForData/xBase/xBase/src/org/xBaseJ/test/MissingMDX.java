package org.xBaseJ.test;
/**
 * xBaseJ - Java access to dBase files
 *<p>Copyright 1997-2007 - American Coders, LTD  - Raleigh NC USA
 *<p>All rights reserved
 *<p>Currently supports only dBase III format DBF, DBT and NDX files
 *<p>                        dBase IV format DBF, DBT, MDX and NDX files
*<p>American Coders, Ltd
*<br>P. O. Box 97462
*<br>Raleigh, NC  27615  USA
*<br>1-919-846-2014
*<br>http://www.americancoders.com
@author Joe McVerry, American Coders Ltd.
@version 2.2.0
*
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library Lesser General Public
 * License along with this library; if not, write to the Free
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
*/


import java.io.File;
import java.io.IOException;

import org.xBaseJ.DBF;
import org.xBaseJ.Util;
import org.xBaseJ.xBaseJException;
import org.xBaseJ.fields.CharField;


import junit.framework.TestCase;

public class MissingMDX extends TestCase {

	public void testMissingMDX() throws  xBaseJException, IOException
	{
		assertEquals(Util.getxBaseJProperty("ignoreMissingMDX"), "");

		File f = new File("testFiles/test.dbf");
		f.delete();
		f = new File("testFiles/test.mdx");
		f.delete();
		DBF d = new DBF("testFiles/test.dbf", DBF.DBASEIV, true );
		d.addField(new CharField("one", 10));
		d.close();
		f = new File("testFiles/test.mdx");
		f.delete();
		try {
			d = new DBF("testFiles/test.dbf");
			d.write();
			d.close();
		} catch (xBaseJException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testSetPropertyMissingMDXTrue() throws  xBaseJException, IOException
	{
		File f = new File("testFiles/test.dbf");
		f.delete();
		f = new File("testFiles/test.mdx");
		f.delete();
		DBF d = new DBF("testFiles/test.dbf", DBF.DBASEIV, true );
		d.addField(new CharField("one", 10));
		d.close();
		f = new File("testFiles/test.mdx");
		f.delete();
		try {
			Util.setxBaseJProperty("ignoreMissingMDX", "true");
			assertEquals(Util.getxBaseJProperty("ignoreMissingMDX"), "true");
			d = new DBF("testFiles/test.dbf");
			d.write();
			d.close();
		} catch (xBaseJException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void testSetPropertyMissingMDXFalse() throws  xBaseJException, IOException
	{
		File f = new File("testFiles/test.dbf");
		f.delete();
		f = new File("testFiles/test.mdx");
		f.delete();
		DBF d = new DBF("testFiles/test.dbf", DBF.DBASEIV, true );
		d.addField(new CharField("one", 10));
		d.close();
		f = new File("testFiles/test.mdx");
		f.delete();
		try {
			Util.setxBaseJProperty("ignoreMissingMDX", "false");
			assertEquals(Util.getxBaseJProperty("ignoreMissingMDX"), "false");
			d = new DBF("testFiles/test.dbf");
			d.write();
			d.close();
		} catch (xBaseJException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
