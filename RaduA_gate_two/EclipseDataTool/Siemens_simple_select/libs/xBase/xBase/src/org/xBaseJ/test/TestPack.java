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


import org.xBaseJ.DBF;
import org.xBaseJ.fields.CharField;
import org.xBaseJ.fields.LogicalField;
import org.xBaseJ.fields.MemoField;
import org.xBaseJ.fields.NumField;

import junit.framework.TestCase;

/**
 * test packing logic
 * @author joseph mcverry
 *
 */
public class TestPack extends TestCase {

	public void setUp()
	{
		try{
			//Create a new dbf file
			DBF aDB=new DBF("testfiles/class.dbf",true);

			//Create the fields

			CharField classId = new CharField("classId",9);
			CharField className = new CharField("className",25);
			CharField teacherId = new CharField("teacherId",9);
			CharField daysMeet = new CharField("daysMeet",7);
			CharField timeMeet =new CharField("timeMeet",4);
			NumField credits = new NumField("credits",2, 0);
			LogicalField UnderGrad = new LogicalField("UnderGrad");
			MemoField discuss = new MemoField("discuss");


			//Add field definitions to database
			aDB.addField(classId);
			aDB.addField(className);
			aDB.addField(teacherId);
			aDB.addField(daysMeet);
			aDB.addField(timeMeet);
			aDB.addField(credits);
			aDB.addField(UnderGrad);
			aDB.addField(discuss);

			aDB.createIndex("testfiles/classId.ndx","classId",true,true);     //  true - delete ndx, true - unique index,
			aDB.createIndex("testfiles/TchrClass.ndx","teacherID+classId", true, false);     //true - delete NDX,  false - unique index,
			//System.out.println("index created");

			classId.put("JAVA10100");
			className.put("Introduction to JAVA");
			teacherId.put("120120120");
			daysMeet.put("NYNYNYN");
			timeMeet.put("0800");
			credits.put(3);
			UnderGrad.put(true);
			discuss.put("Intro class");

			aDB.write();

			classId.put("JAVA10200");
			className.put("Intermediate JAVA");
			teacherId.put("300020000");
			daysMeet.put("NYNYNYN");
			timeMeet.put("0930");
			credits.put(3);
			UnderGrad.put(true);
			discuss.put("itermediate class");

			aDB.write();

			classId.put("JAVA102D0");
			className.put("Interm");
			teacherId.put("300020000");
			daysMeet.put("ND");
			timeMeet.put("0930");
			credits.put(3);
			UnderGrad.put(true);
			discuss.put("itermediate class");

			aDB.write();

			aDB.delete();


			classId.put("JAVA501");
			className.put("JAVA And Abstract Algebra");
			teacherId.put("120120120");
			daysMeet.put("NNYNYNN");
			timeMeet.put("0930");
			credits.put(6);
			UnderGrad.put(false);
			discuss.put("weird class");

			aDB.write();

			aDB.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void testPack()
	{
		try {
		DBF dbf = new DBF("testfiles/class.DBF");

		assertEquals(dbf.getRecordCount(), 4);

		dbf.pack();

		assertEquals(dbf.getRecordCount(), 3);

		for (int i = 1; i < 4; i++)
		{
			dbf.gotoRecord(i);
			String bean = dbf.getField(1).get();
			if (i == 1)
				assertEquals("JAVA10100", bean);
			else if (i == 2)
				assertEquals("JAVA10200", bean);
			else
				assertEquals("JAVA501", bean);
		}



		}
		catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	public void testPackwithFPT() {
		try {
			DBF dbf = new DBF("testfiles/crw.DBF");

			int recCnt = dbf.getRecordCount();

			dbf.pack();

			assertEquals(dbf.getRecordCount(), recCnt);


			}
			catch (Exception e) {
				e.printStackTrace();
				fail(e.getMessage());
			}

	}
}
