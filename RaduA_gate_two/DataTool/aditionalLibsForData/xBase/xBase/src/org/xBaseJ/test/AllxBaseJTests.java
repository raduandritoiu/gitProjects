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


import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author joseph mcverry
 *
 */
public class AllxBaseJTests  {

	public static Test suite() {



		TestSuite suite = new TestSuite("Test for org.xBaseJ.test");
		suite.addTestSuite(FieldNameTest.class);
		suite.addTestSuite(MissingMDX.class);
		suite.addTestSuite(NumTest.class);
		suite.addTestSuite(testAdd.class);
		suite.addTestSuite(testCreate.class);
		suite.addTestSuite(testDBF.class);
		suite.addTestSuite(TestFields.class);
		//suite.addTestSuite(TestFoxPro.class);
		suite.addTestSuite(TestLock.class);
		suite.addTestSuite(TestLockRead.class);
		suite.addTestSuite(TestLockUpdateClose.class);
		suite.addTestSuite(testMaxLength.class);
		suite.addTestSuite(TestMultiAdd.class);
		suite.addTestSuite(TestNoBlanks.class);
		suite.addTestSuite(TestPack.class);
		suite.addTestSuite(TestConcurrInsert.class);
		return suite;
	}

	public static void main(StringBuffer args[]) {
		junit.textui.TestRunner.run(suite());
	}

}
