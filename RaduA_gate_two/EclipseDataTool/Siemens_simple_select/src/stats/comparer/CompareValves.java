package stats.comparer;

import java.util.ArrayList;

import org.xBaseJ.DBF;
import org.xBaseJ.fields.CharField;
import org.xBaseJ.fields.Field;


public class CompareValves {
    private String base_v1;
    private String base_v2;
    private ArrayList<ValveInfo> parts_1 = new ArrayList<>();
    private ArrayList<ValveInfo> parts_2 = new ArrayList<>();
    private int recordCnt_1;
    private int recordCnt_2;

    
    public CompareValves(String v1, String v2) {
        base_v1 = v1;
        base_v2 = v2;
    }
    
    
    public void load() throws Exception {
        DBF db_1 = new DBF(base_v1, DBF.READ_ONLY);  
        recordCnt_1 = db_1.getRecordCount();
        
        // load parts from db_1
        while (db_1.getCurrentRecordNumber() < recordCnt_1) {
            db_1.read();
            
            // Skip Records marked for deletion
            if (db_1.deleted())
                continue;
            
            CharField partNumberField = (CharField) db_1.getField("PARTNO");
            String partNumber = partNumberField.get().trim();
            parts_1.add(new ValveInfo(partNumber));
        }
        
        DBF db_2 = new DBF(base_v2, DBF.READ_ONLY);  
        recordCnt_2 = db_2.getRecordCount();
        
        // load parts from db_2
        while (db_2.getCurrentRecordNumber() < recordCnt_2) {
            db_2.read();
            
            // Skip Records marked for deletion
            if (db_2.deleted())
                continue;
            
            CharField partNumberField = (CharField) db_2.getField("PARTNO");
            String partNumber = partNumberField.get().trim();
            ValveInfo newPart = new ValveInfo(partNumber);
            parts_2.add(newPart);
            
            Field field = db_2.getField(5);
            newPart.medium += field.get().trim();
            field = db_2.getField(38);
            newPart.medium += " - " + field.get().trim();
        }
        
        db_1.close();
        db_2.close();
    }
    
    
    
    
    public void printBallsFromNew() throws Exception {
        System.out.println("=== Right DB has " + recordCnt_2 + " and here are BALL Valves:");
        System.out.println("");
        for (ValveInfo part_2 : parts_2) {
            if (part_2.medium.startsWith("6 Way")) {
                System.out.println(part_2);
            }
        }
        System.out.println("--------------------------------\n");
    }
    
    
    
    
    public void comparePartNumbers() throws Exception {
        System.out.println("=== Left  DB has " + recordCnt_1);
        System.out.println("=== Right DB has " + recordCnt_2);
        System.out.println("");
        
        // parts in old/left DB
        System.out.println("<<<<<<< Parts only in LEFT/OLD db");
        for (ValveInfo part_1 : parts_1) {
            boolean found = false;
            for (ValveInfo part_2 : parts_2) {
                if (part_1.part.equals(part_2.part)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println(part_1);
            }
        }
        System.out.println("--------------------------------");
        
        // parts in new/right DB
        System.out.println(">>>>>>> Parts only in RIGHT/NEW db");
        for (ValveInfo part_2 : parts_2) {
            boolean found = false;
            for (ValveInfo part_1 : parts_1) {
                if (part_2.part.equals(part_1.part)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println(part_2);
            }
        }
        System.out.println("--------------------------------");
    }
    
    
    
    private static class ValveInfo {
        public final String part;
        public String medium = "";
        public ValveInfo(String p) { part = p; }
        public String toString() { return part + " \t\t " + medium; }
    }

    
    public static void compare() throws Exception {
        String v1 = "C:\\radua\\work_related_projects\\simple_select\\data_bases\\2017-01-17\\VALVE.DBF";
        String v2 = "C:\\radua\\work_related_projects\\simple_select\\data_bases\\2017-12-18\\VALVE_new.DBF";
        CompareValves cmp = new CompareValves(v1, v2);
        cmp.load();
        cmp.printBallsFromNew();
        cmp.comparePartNumbers();
    }
    
    
    
    
}
