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


public class Field
   extends java.rmi.server.UnicastRemoteObject
   implements FieldInterface
  {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String name = "";
    private char type = ' ';
    private int length = -1;
    private int decpos = -1;
    private String value = "";

	org.xBaseJ.DBF dbf = null;
	/** Constructor */
    public Field(org.xBaseJ.fields.Field inField)
              throws RemoteException {

              // Register the object with the activation system
              // then export it on an anonymous port
              //
              super();
              try {
                name = inField.getName();
                type = inField.getType();
                length = inField.getLength();
                decpos = inField.getDecimalPositionCount();
                value = "";
			  }
			  catch (org.xBaseJ.xBaseJException xbe)
			    {throw new RemotexBaseJException(xbe.getMessage());}

	}



  	/**
  	 * returns a Field name by its relative position
  	 * @throws xBaseJException
  	 */


  	public  String  getName()
  		throws RemoteException
  		{
				return name;
		}
  	/**
  	 * returns a Field type by its relative position
  	 * @return char
  	 * @throws xBaseJException
  	 */

  	public  char  getType()
  		throws RemoteException
  		{
				return type;
		}



  	/**
  	 * returns a Field Length as defined in database
  	 * @return int
  	 * @throws RemoteException
  	 */

  	public  int  getLength()
  	throws RemoteException
  		{
				return length;
		}


	/**
  	 * returns a Field's decimal position as defined in database by its relative position
	 * @return int - the number of decimal positions for numeric fields, zero returned otherwise
  	 * @throws RemoteException
	*/
	public int getDecimalPositionCount()
  		throws RemoteException
  		{
				return decpos;
		}


  	/**
  	 * returns a Field value by its relative position
  	 * @throws xBaseJException
  	 */


  	public  String  get()
  		throws RemoteException
  		{
				return value;
		}

  	/**
  	 * sets a Field value by its relative position
  	 * @param v Field value
  	 * @throws xBaseJException
  	 */


  	public  void  put(String v)
  		throws RemoteException
  		{
				 value = v;
		}


}
