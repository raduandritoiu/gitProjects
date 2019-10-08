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

import java.io.IOException;
import java.rmi.RemoteException;



public class DBFServer
   extends java.rmi.server.UnicastRemoteObject
   implements DBFServerInterface
  {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	org.xBaseJ.DBF dbf = null;
	/** Constructor */
    public DBFServer()
       throws RemoteException
       {
              super();
	    }

    public DBFInterface connectToDBF(String inDBFName)
        throws RemoteException {
		try {
			return new DBF(inDBFName);
	        }
			catch (IOException ioe) {
				throw new RemoteException("IOException " + ioe.getMessage());
			}
	}


}