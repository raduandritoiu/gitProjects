/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.models;

/**
 *
 * @author michael
 */
public class CrossReferencePart
{
    private String competitorPartNumber;
    private String competitorName;
    private String siemensPartNumber;
    private String actualPartNumber;
    private String subProductPartNumber;

    public CrossReferencePart()
    {}
	
	
    public String getCompetitorName() {
        return competitorName;
    }

    public void setCompetitorName(String competitorName) {
        this.competitorName = competitorName;
    }

    
    public String getCompetitorPartNumber() {
        return competitorPartNumber;
    }

    public void setCompetitorPartNumber(String competitorPartNumber) {
        this.competitorPartNumber = competitorPartNumber;
    }
	
	
    public String getSiemensPartNumber() {
        return siemensPartNumber;
    }

    public void setSiemensPartNumber(String partNumber) {
        this.siemensPartNumber = partNumber;
        
        if (this.actualPartNumber == null) {
            this.actualPartNumber = partNumber;

            int plusIndex = partNumber.indexOf('+');
            
            if (plusIndex > 0) {
                subProductPartNumber  = partNumber.substring(plusIndex + 1);
                this.actualPartNumber = partNumber.substring(0, plusIndex);
            }
        }
    }
	
	
    public String getActualPartNumber() {
        return actualPartNumber;
    }

    public void setActualPartNumber(String actualPartNumber) {
        this.actualPartNumber = actualPartNumber;
    }
	
	
    public String getSubProductPartNumber() {
        return subProductPartNumber;
    }

    public void setSubProductPartNumber(String subProductPartNumber) {
        this.subProductPartNumber = subProductPartNumber;
    }
}