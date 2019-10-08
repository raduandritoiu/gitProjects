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


import java.awt.AWTException;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.util.Vector;


public class dbfShow extends Frame implements AdjustmentListener, ActionListener, WindowListener
  {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	Panel p;
//    GridLayout gb;
    GridBagLayout gb;
    GridBagConstraints gbc;
	org.xBaseJ.rmi.DBFInterface db;

    Label crl, trl;
    Scrollbar SBrecpos;
    Checkbox delCB;
    Button Prev, Next, Add,  Update, Clear;
    Vector fldObjects;
    memoDialog md;
    MenuItem Quit;

    Label l;
    TextField t;

    Button b;
    Checkbox c;

    public dbfShow(String title, String hostID, String fname) throws Exception
      {

         super(title + " " + hostID+ " " + fname);


         addWindowListener(this);
         MenuBar mb = new MenuBar();
         this.setMenuBar(mb);
         Menu file = new Menu("File");
         Quit = new MenuItem("Quit");
         Quit.addActionListener(this);
         file.add(Quit);
         mb.add(file);
         crl = new Label("Record" , Label.RIGHT);
         trl =  new Label(" of ", Label.LEFT);
		 SBrecpos = new Scrollbar(Scrollbar.HORIZONTAL, 1, 1, 0, 0);
         SBrecpos.addAdjustmentListener(this);
         delCB = new Checkbox("Deleted");

         Prev = new Button("<<Prev");
         Prev.addActionListener(this);
         Next = new Button("Next>>");
         Next.addActionListener(this);
         Add = new Button("Add");
         Add.addActionListener(this);
         Update = new Button("Update");
         Update.addActionListener(this);
         Clear = new Button("Clear");
         Clear.addActionListener(this);
         setupDBFields(hostID, fname);
         pack();
         setVisible(true);

      }

public void setupDBFields(String hostID, String dbname)  throws Exception
    {
         setLayout(null);
         removeAll();
		 String name = "//"+hostID+"/"+org.xBaseJ.rmi.DBFServerInterface.serviceName;
         org.xBaseJ.rmi.DBFServerInterface dbfServer = (org.xBaseJ.rmi.DBFServerInterface) java.rmi.Naming.lookup(name);
	     db =  dbfServer.connectToDBF(dbname);
         gb = new GridBagLayout();
         gbc = new  GridBagConstraints();
         setLayout(gb);
         int i, j;
         fldObjects = new Vector(db.getFieldCount());
         for (i = 1; i <= db.getFieldCount(); i++)
            {
               j = i - 1;
               if (db.getField(i).getType() == 'M') {
                  b = new Button(db.getField(i).getName());
                  addComponent(this, b, 1, j, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);
                  fldObjects.addElement(b);
                   }
               else if (db.getField(i).getType() == 'L') {
                  c = new Checkbox(db.getField(i).getName(), true);
                  addComponent(this, c, 1, j, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);
                  fldObjects.addElement(c);
                   }
               else {
                  l = new Label(db.getField(i).getName(), Label.RIGHT);
                  addComponent(this,  l, 0, j, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);
                  int ln = db.getField(i).getLength();
                  if (ln > 100) ln = 100;
                  t = new TextField(db.getField(i).getName(), ln);
                  addComponent(this, t, 1, j, ln, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);
                  fldObjects.addElement(t);
                  t.setEditable(true);
                  }
            }

         crl.setText("Record " + db.getCurrentRecordNumber());
         trl.setText(" of " + db.getRecordCount());
         SBrecpos.setMaximum(db.getRecordCount());
         addComponent(this, crl, 0, i,  1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);
         addComponent(this, trl, 1, i, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);
         i++;
         addComponent(this, SBrecpos, 0, i, 2, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);
         addComponent(this, delCB, 2, i, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST );
         i++;
         addComponent(this,  Prev, 0, i, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);
         addComponent(this, Next, 1, i, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);
         i++;
         addComponent(this, Add, 0, i,  1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);
         addComponent(this, Update, 1, i, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);
         addComponent(this, Clear, 2, i, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);
         Prev.setEnabled(false);
         if (db.getRecordCount() == 0) {
             Update.setEnabled(false);
             Next.setEnabled(false);
           }

        goTo(1);
 }

    public void setFields()
     {

        Checkbox c;
        TextField t;

        int i;
        try {
         for (i = 1; i <= db.getFieldCount(); i++)
            {

               if (db.getField(i).getType() == 'M') {
                  }
               else
               if (db.getField(i).getType() == 'L') {
                  c  = (Checkbox) fldObjects.elementAt(i-1);
                  db.getField(i).put(c.getState()?"T":"F");
                  }
               else {
                  t = (TextField)  fldObjects.elementAt(i-1);
                  db.getField(i).put(t.getText());
                  }
             }
           }

        catch (Exception e1) {System.out.println(e1);}
    }

    public void addRec()
    {
        try {
         setFields();
         db.write();
         goTo(db.getRecordCount());
         delCB.setState(false);
         trl.setText(" of " + db.getRecordCount());
         } // try
       catch (Exception e1) {System.out.println(e1); System.exit(1);}
       Update.setEnabled(true);
    }

    public void updateRec()
    {

        try {
         setFields();
         db.update();
         boolean b  = delCB.getState();
         if (db.deleted() && !b)
              db.undelete();
         else if (!db.deleted() && b)
               db.delete();
         } // try
       catch (Exception e1) {System.out.println(e1); System.exit(1);}

    }

    public void goTo(int where)
    {
        try {
        if (where < 1) return;
        if (where > db.getRecordCount()) return;
        db.gotoRecord(where);
        crl.setText("Record " + db.getCurrentRecordNumber());
        delCB.setState(db.deleted());


         Checkbox c;
         TextField t;

         int i;

         for (i = 1; i <= db.getFieldCount(); i++)
            {

               if (db.getField(i).getType() == 'M') {
                  }
               else
               if (db.getField(i).getType() == 'L') {
                  c  = (Checkbox) fldObjects.elementAt(i-1);
                  c.setState(db.getField(i).get().charAt(0)=='T');
                  }
               else {
                  t = (TextField)  fldObjects.elementAt(i-1);
                  t.setText(db.getField(i).get().trim());
                  }
            }

         if (db.getCurrentRecordNumber() == db.getRecordCount()) Next.setEnabled(false); else Next.setEnabled(true);
         if (db.getCurrentRecordNumber() == 1) Prev.setEnabled(false); else Prev.setEnabled(true);

         SBrecpos.setValues(db.getCurrentRecordNumber(), 1, 0, db.getRecordCount());
         } // try

         catch (Exception e1) {System.out.println(e1); System.exit(1);}
        }

    public void clearFields()
    {
         Checkbox c;
         TextField t;

         int i;

         try
          {
         for (i = 1; i <= db.getFieldCount(); i++)
            {

                 if (db.getField(i).getType() == 'M')
                   db.getField(i).put("");
                 else if (db.getField(i).getType() == 'L')
                  {
                    c  = (Checkbox) fldObjects.elementAt(i-1);
                    c.setState(false);
                    }
                 else {
                    t = (TextField)  fldObjects.elementAt(i-1);
                    t.setText("");
                    }
               }
		   }
           catch (Exception e1) {System.out.println(e1); System.exit(1);}

    }

    public void adjustmentValueChanged(AdjustmentEvent event)
    {
        if (event.getSource() == SBrecpos) {
             if(event.getID() == AdjustmentEvent.TRACK) return;
             SBrecpos.setEnabled(false);
             goTo(SBrecpos.getValue());
             SBrecpos.setEnabled(true);
             return;
             }

    }

    public void actionPerformed(ActionEvent event)
    {
        if (event.getSource() == Next)
          {
             try {
             if (db.getCurrentRecordNumber() < db.getRecordCount()) goTo(db.getCurrentRecordNumber()+1);
             }
             catch (Exception e1)
               {
                   System.out.println("caught Exception " + e1.getMessage());
                   return;
               }
             return;
          }
        if (event.getSource() == Prev)
          {
			 try {
             if (db.getCurrentRecordNumber() > 1) goTo(db.getCurrentRecordNumber()-1);
		 }
             catch (Exception e1)
               {
                   System.out.println("caught Exception " + e1.getMessage());
                   return;
               }
             return;
          }
        if (event.getSource() == Add)
          {
             addRec();
             return;
          }
        if (event.getSource() == Update)
          {
             updateRec();
             return;
          }
        if (event.getSource() == Clear)
          {
             clearFields();
             return;
          }
        if (event.getSource() == Quit)
          {
                System.exit(0);
                return;
          }

      if (event.getSource() instanceof Button)
      {
      int i;
      FieldInterface f;
      int fc = 0;
      try {fc = db.getFieldCount();}
      catch (Exception e1)
	      { System.out.println("caught Exception " + e1.getMessage());
	        return;   }

      for (i = 1; i <= fc; i++)
            {
               try {
               f = db.getField(i);
               if (f.getType() == 'M') {
                   md = new memoDialog(this,db, i);
                   md.setVisible(true);
                   return;
                   }
               }
              catch (Exception e1) {System.out.println(e1);}
             }
        }


    }


    public void windowClosing(WindowEvent event) {
           System.exit(0);
           }


    public void windowClosed(WindowEvent event) {
           System.exit(0);
           }


    public void windowDeiconified(WindowEvent event) { }

    public void windowIconified(WindowEvent event) { }

    public void windowActivated(WindowEvent event) { }

    public void windowDeactivated(WindowEvent event) { }

    public void windowOpened(WindowEvent event) { }

    public static void main(String[] args) throws Exception
    {

    if (args.length < 2 || args.length > 2)
         throw new Exception("format: org.xBaseJ.rmi.dbfShow hostID dbfname");
    else
        new dbfShow("dbfShow", args[0], args[1]);
    }


public static void addComponent (Container container, Component component,
    int gridx, int gridy, int gridwidth, int gridheight, int fill,
    int anchor) throws AWTException {
    LayoutManager lm = container.getLayout();
    if (!(lm instanceof GridBagLayout)) {
        throw new AWTException ("Invalid layout" + lm);
    } else {
        GridBagConstraints gbc = new GridBagConstraints ();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.fill = fill;
        gbc.anchor = anchor;
        ((GridBagLayout)lm).setConstraints(component, gbc);
        container.add (component);
    }
}

}


class memoDialog extends Dialog implements ActionListener, WindowListener
  {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public Button Okay = new Button("Okay");
    public Button Cancel = new Button("Cancel");
    public TextArea text = new TextArea();
    public int savei;
    org.xBaseJ.rmi.DBFInterface savedb;

    public memoDialog(Frame fr, org.xBaseJ.rmi.DBFInterface db, int i)
      throws RemoteException
      {
         super(fr, db.getField(i).getName(), true);
         addWindowListener(this);

         savedb = db;
         savei = i;

         text.setText(db.getField(i).get());
         this.add("Center", text);

         Panel p =  new Panel();
         Okay.addActionListener(this);
         Cancel.addActionListener(this);
         p.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
         p.add(Okay);
         this.add("East", p);
         p.add(Cancel);
         this.add("West", p);
         this.pack();
      }

     public void actionPerformed(ActionEvent e)
      {
        if (e.getSource() == Cancel) {
        setVisible(false);
        dispose();
        return;
        }
        if (e.getSource() == Okay)
          {
           try{
           setVisible(false);
           savedb.getField(savei).put(text.getText());
           dispose();
           return;
           }
          catch (Exception e1) {System.out.println(e1);}

           return;
          }
       return;
       }
    public void windowClosing(WindowEvent event) {
           System.exit(0);
           }


    public void windowClosed(WindowEvent event) {
           System.exit(0);
           }


    public void windowDeiconified(WindowEvent event) { }

    public void windowIconified(WindowEvent event) { }

    public void windowActivated(WindowEvent event) { }

    public void windowDeactivated(WindowEvent event) { }

    public void windowOpened(WindowEvent event) { }


}





