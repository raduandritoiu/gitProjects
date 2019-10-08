package org.xBaseJ.rmi;
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
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the Free
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
*/

import java.rmi.RemoteException;

public interface DBFInterface extends java.rmi.Remote
  {

	  public static final String serviceName = "DBF";


	/**
	 * returns the number of fields in a database
	 */
	public int getFieldCount()
		throws RemoteException;

	/**
	 * returns the number of records in a database
	 */

	public int getRecordCount()
		throws RemoteException;

	/**
	 * returns the current record number
	 */
	public int getCurrentRecordNumber()
		throws RemoteException;

	/**
	 * opens an Index file associated with the database.  This index becomes the primary
	 * index used in subsequent find methods.
	 * @param filename      an existing ndx file(can be full or partial pathname) or mdx tag
	 * @throws RemoteException
	 *                                    org.xBaseJ Fields defined in index do not match fields in database
	 */
	public void useIndex(String filename)
		throws RemoteException;



	/**
	 * associates all Index operations with an existing tag
	 * @param tagname      an existing tag name in the production MDX file
	 * @throws RemoteException
	 *                                    no MDX file
	 *                                    tagname not found
	 */
	public void useTag(String tagname)
		throws RemoteException;

	/**
	 * used to find a record with an equal or greater string value
	 * when done the record pointer and field contents will be changed
	 * @param keyString  a search string
	 * @return boolean indicating if the record found contains the exact key
	 * @throws RemoteException
	 *                                    org.xBaseJ no Indexs opened with database
	 */
	public boolean find(String keyString)
	throws RemoteException;

	/**
	 * used to get the next  record in the index list
	 * when done the record pointer and field contents will be changed
	 * @throws RemoteException
	 *                                    org.xBaseJ Index not opened or not part of the database
	 *                                    eof - end of file
	 */

	public void findNext()
	throws RemoteException;
	/**
	 * used to get the previous record in the index list
	 * when done the record pointer and field contents will be changed
	 * @throws RemoteException
	 *                                    org.xBaseJ Index not opened or not part of the database
	 *                                    tof - top of file
	 */
	public void findPrev()
	throws RemoteException;


	/**
	 * used to read the next record, after the current record pointer, in the database
	 * when done the record pointer and field contents will be changed
	 * @throws RemoteException
	 *                                    usually the end of file condition
	 */
	public  void  read()
	throws RemoteException;


	/**
	 * used to read the previous record, before the current record pointer, in the database
	 * when done the record pointer and field contents will be changed
	 * @throws RemoteException
	 *                                    usually the top of file condition
	 */


	public  void  readPrev()
	throws RemoteException;

	/**
	 * used to read a record at a particular place in the database
	 * when done the record pointer and field contents will be changed
	 * @param recno the relative position of the record to read
	 * @throws RemoteException
	 *                                    passed an negative number, 0 or value greater than the number of records in database
	 */


	public  void  gotoRecord(int recno)
	throws RemoteException;

	/**
	 * used to position record pointer at the first record or index in the database
	 * when done the record pointer will be changed.  NO RECORD IS READ.
	 * Your program should follow this with either a read (for non-index reads) or findNext (for index processing)
	 * @return String starting index
	 * @throws RemoteException
	 *                                    most likely no records in database
	 */


	public  void  startTop()
	throws RemoteException;

	/**
	 * used to position record pointer at the last record or index in the database
	 * when done the record pointer will be changed. NO RECORD IS READ.
	 * Your program should follow this with either a read (for non-index reads) or findPrev (for index processing)
	 * @return String terminal index
	 * @throws RemoteException
	 *                                    most likely no records in database
	 */


	public  void startBottom()
	throws RemoteException;

	/**
	 * used to write a new record in the database
	 * when done the record pointer is at the end of the database
	 * @throws RemoteException
	 *                                    any one of several errors
	 */
	public  void  write()
	throws RemoteException;


	/**
	 * updates the record at the current position
	 * @throws RemoteException
	 *                                    any one of several errors
	 */


	public  void update()
	throws RemoteException;

	/**
	 * marks the current records as deleted
	 * @throws RemoteException
	 *                                    usually occurs when no record has been read
	 */


	public  void delete()
		throws RemoteException;

	/**
	 * marks the current records as not deleted
	 * @throws RemoteException
	 *                                    usually occurs when no record has been read.
	 */



	public  void  undelete()
	throws RemoteException;

	/**
	 * @return boolean true if record is marked for deletion
	 */


	public  boolean  deleted()
		throws RemoteException;

	/**
	 * closes the database
	 */



	public  void  close()
		throws RemoteException;

	/**
	 * returns a Field by its relative position
	 * @param i Field number
	 * @return String
	 * @throws RemoteException
	 *                                    usually occurs when Field number is less than 1 or greater than the number of fields
	 */


  	public  FieldInterface  getField(int i)
  		throws RemoteException;

  	/**
  	 * returns a Field by its name in the database
  	 * @param name Field name
  	 * @throws RemoteException
  	 *                                    Field name is not correct
  	 */

  	public  FieldInterface  getField(String name)
  		throws RemoteException;




}
