package siemens.vsst.data.parsers.dbs;

import java.io.IOException;

import org.xBaseJ.DBF;
import org.xBaseJ.xBaseJException;
import org.xBaseJ.fields.Field;

/**
 * Valve DB Parser
 *
 * Parses Valve DB File
 * 
 * @author michael
 */
public class SixWayBallValveDBParser extends ValveDBParser
{
    public static SixWayBallValveDBParser newInstance() {
        return new SixWayBallValveDBParser();
    }
    
    
    protected boolean shouldSkip(String partNumber, DBF db) throws xBaseJException, IOException {
        Field field = db.getField(5);
        String medium_1 = field.get().trim();
        field = db.getField(38);
        String medium_2 = field.get().trim();

        if (medium_1.equals("6 Way") || medium_2.equals("6 Way"))
            return false;
        return true;
    }
}