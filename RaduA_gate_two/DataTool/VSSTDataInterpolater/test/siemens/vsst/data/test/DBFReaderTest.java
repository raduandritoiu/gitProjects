/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.test;

import java.io.File;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import siemens.vsst.data.io.DBFReader;
import siemens.vsst.data.io.SQLiteWriter;
import siemens.vsst.data.models.AbstractSiemensProduct;
import static org.junit.Assert.*;

/**
 *
 * @author michael
 */
public class DBFReaderTest
{

    public DBFReaderTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testDBFReader()
            throws Exception
    {
        DBFReader reader = new DBFReader();

        reader.startImport("C:/Development/Contracts/Siemens/VSST/New Data");

        Collection<AbstractSiemensProduct> products = reader.getProducts();

        SQLiteWriter writer = new SQLiteWriter();
        writer.writeData(new File("C:/Development/Contracts/Siemens/VSST/parts.db"), reader);

        assertTrue(products.size() > 0);
    }
}
