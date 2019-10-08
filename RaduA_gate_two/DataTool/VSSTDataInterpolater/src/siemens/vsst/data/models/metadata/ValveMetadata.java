package siemens.vsst.data.models.metadata;

import java.util.Date;
import siemens.vsst.data.enumeration.valves.FlowCharacteristics;
import siemens.vsst.data.enumeration.valves.PackingMaterial;
import siemens.vsst.data.enumeration.valves.ValveConnection;
import siemens.vsst.data.enumeration.valves.ValveDiscType;
import siemens.vsst.data.enumeration.valves.ValveNormalState;
import siemens.vsst.data.enumeration.valves.ValvePattern;
import siemens.vsst.data.enumeration.valves.ValveTrim;
import siemens.vsst.data.enumeration.valves.ValveType;
import siemens.vsst.data.interfaces.ISiemensMetadata;
import siemens.vsst.data.parsers.utils.VSSTLogger;

/**
 * Siemens Valve Metadata
 *
 * Contains all valve specific properties
 *
 * @author michael
 */
public class ValveMetadata implements ISiemensMetadata
{
    public static final int MIXING      = 2;    // Three-Way Function Mixing Flag
    public static final int DIVERTING   = 4;    // Three-Way Function Diverting Flag

    public static final int UNKNOWN     = 0;    // Unknown Flag
    
    public static final int WATER       = 2;    // Valve Medium Water Flag
    public static final int STEAM       = 4;    // Valve Medium Steam Flag
    public static final int GLYCOL      = 8;    // Valve Medium Glycol Flag

    private String partNumber;          // Part Number
    private float size;                 // Size
    private float cvu;                  // CVU
    private float cvl;                  // CVL
    private ValvePattern pattern;       // Valve Pattern
    private String failSafeU;           // Fail Safe U
    private ValveConnection connection; // Connection
    private ValveType type;             // Body Style
    private String seatStyle;           // Seat Style
    private FlowCharacteristics flowChar; // Flow Characteristics
    private float maxTemp;              // Maximum Temperature
    private float maxPress;             // Maximum Pressure
    private float maxDPWater;           // Maximum Direct Pressure for Water
    private float maxDPSteam;           // Maximum Direct Pressure for Steam
    private float maxIPWater;           // Maximum Input Pressure for Water
    private float maxIPSteam;           // Maximum Input Preassure for Steam
    private float maxPCTGly;            // Maximum PCT for Glycol
    private float minGPM;               // Minimum GPM
    private float maxGPM;               // Maximum GPM
    private float presetGPM;            // Preset GPM
    private String ambientTem;          // Ambient Temperature
    private String bodyMaterial;        // Body Material
    private PackingMaterial packingMaterial;     // Packing Material
    private String stemMaterial;        // Stem Material
    private ValveTrim plugMaterial;        // Plug Material
    private String discMaterial;        // Disc Material
    private String seatMaterial;        // Seat Material
    private String thread;              // Thread
    private float kvsu;                 // KVSU
    private float kvsl;                 // KVSL
    private Date lastModified;          // Last Modified Date
    private ValveNormalState normalState;   // Valve Normal State
    private ValveDiscType discType;         // Valve Disc Type
   
    private int medium;                     // Valve Medium
    private int mixingDiverting;            // Valve Three-Way Function
	
	private static int debugCnt = 0;
	public int debugId;
	
    public ValveMetadata()
    {
		debugCnt ++;
		debugId = debugCnt;
    }
	
    public ValvePattern getPattern()
    {
        return pattern;
    }
	
    public void setPattern(ValvePattern pattern)
    {
        this.pattern = pattern;
    }
	
    public String getAmbientTem()
    {
        return ambientTem;
    }
	
    public void setAmbientTem(String ambientTem)
    {
        this.ambientTem = ambientTem;
    }
	
    public String getBodyMaterial()
    {
        return bodyMaterial;
    }
	
    public void setBodyMaterial(String bodyMaterial)
    {
        this.bodyMaterial = bodyMaterial;
    }
	
    public ValveType getType()
    {
        return type;
    }
	
    public void setType(ValveType type)
    {
        this.type = type;
    }
	
    public ValveConnection getConnection()
    {
        return connection;
    }
	
    public void setConnection(ValveConnection connection)
    {
        this.connection = connection;
    }
	
    public float getCvl()
    {
        return cvl;
    }
	
    public void setCvl(float cvl)
    {
        this.cvl = cvl;
    }
	
    public float getCvu()
    {
        return cvu;
    }
	
    public void setCvu(float cvu)
    {
        this.cvu = cvu;
    }
	
    public String getDiscMaterial()
    {
        return discMaterial;
    }
	
	public void setDiscMaterial(String discMaterial)
    {
        this.discMaterial = discMaterial;
    }
	
    public String getFailSafeU()
    {
        return failSafeU;
    }
	
    public void setFailSafeU(String failSafeU)
    {
        this.failSafeU = failSafeU;
    }
	
    public FlowCharacteristics getFlowChar()
    {
        return flowChar;
    }
	
    public void setFlowChar(FlowCharacteristics flowChar)
    {
        this.flowChar = flowChar;
    }
	
    public float getKvsl()
    {
        return kvsl;
    }
	
    public void setKvsl(float kvsl)
    {
        this.kvsl = kvsl;
    }
	
    public float getKvsu()
    {
        return kvsu;
    }
	
    public void setKvsu(float kvsu)
    {
        this.kvsu = kvsu;
    }
	
    public Date getLastModified()
    {
        return lastModified;
    }
	
    public void setLastModified(Date lastModified)
    {
        this.lastModified = lastModified;
    }
	
    public float getMaxDPSteam()
    {
        return maxDPSteam;
    }
	
    public void setMaxDPSteam(float maxDPSteam)
    {
        this.maxDPSteam = maxDPSteam;
    }
	
    public float getMaxDPWater()
    {
        return maxDPWater;
    }
	
    public void setMaxDPWater(float maxDPWater)
    {
        this.maxDPWater = maxDPWater;
    }
	
    public float getMaxIPSteam()
    {
        return maxIPSteam;
    }
	
    public void setMaxIPSteam(float maxIPSteam)
    {
        this.maxIPSteam = maxIPSteam;
    }
	
    public float getMaxIPWater()
    {
        return maxIPWater;
    }
	
    public void setMaxIPWater(float maxIPWater)
    {
        this.maxIPWater = maxIPWater;
    }
	
    public float getMaxPCTGly()
    {
        return maxPCTGly;
    }
	
    public void setMaxPCTGly(float maxPCTGly)
    {
        this.maxPCTGly = maxPCTGly;
    }
	
    public float getMaxPress()
    {
        return maxPress;
    }
	
    public void setMaxPress(float maxPress)
    {
        this.maxPress = maxPress;
    }
	
    public float getMaxTemp()
    {
        return maxTemp;
    }
	
    public void setMaxTemp(float maxTemp)
    {
        this.maxTemp = maxTemp;
    }
	
    public PackingMaterial getPackingMaterial()
    {
        return packingMaterial;
    }
	
    public void setPackingMaterial(PackingMaterial packingMaterial)
    {
        this.packingMaterial = packingMaterial;
    }
	
    public String getPartNumber()
    {
        return partNumber;
    }
	
    public void setPartNumber(String partNumber)
    {
        this.partNumber = partNumber;
    }
	
    public ValveTrim getPlugMaterial()
    {
        return plugMaterial;
    }
	
    public void setPlugMaterial(ValveTrim plugMaterial)
    {
        this.plugMaterial = plugMaterial;
    }
	
    public String getSeatMaterial()
    {
        return seatMaterial;
    }
	
    public void setSeatMaterial(String seatMaterial)
    {
        this.seatMaterial = seatMaterial;
    }
	
    public String getSeatStyle()
    {
        return seatStyle;
    }
	
    public void setSeatStyle(String seatStyle)
    {
        this.seatStyle = seatStyle;
    }
	
    public float getSize()
    {
        return size;
    }
	
    public void setSize(float size)
    {
        this.size = size;
    }
	
    public String getStemMaterial()
    {
        return stemMaterial;
    }
	
    public void setStemMaterial(String stemMaterial)
    {
        this.stemMaterial = stemMaterial;
    }
	
    public String getThread()
    {
        return thread;
    }
	
    public void setThread(String thread)
    {
        this.thread = thread;
    }

    public int getMedium()
    {
        return medium;
    }

    public void setMedium(int medium)
    {
        this.medium = medium;
    }

    public int getMixingDiverting()
    {
        return mixingDiverting;
    }

    public void setMixingDiverting(int mixingDiverting)
    {
        this.mixingDiverting = mixingDiverting;
    }

    public ValveNormalState getNormalState()
    {
        return normalState;
    }

    public void setNormalState(ValveNormalState normalState)
    {
        this.normalState = normalState;
		
//		//TODO_TEST - 599-07310
//        if (partNumber.equalsIgnoreCase("599-07310")) {
//            VSSTLogger.TestComponent("=================(" + partNumber + ")=  " + " set normal state to " + 
//					this.normalState.toString() + "  ----- metaId: " + debugId);
//        }
    }

    public ValveDiscType getDiscType()
    {
        return discType;
    }

    public void setDiscType(ValveDiscType discType)
    {
        this.discType = discType;
    }

    public float getMaxGPM()
    {
        return maxGPM;
    }
	
    public void setMaxGPM(float maxGPM)
    {
        this.maxGPM = maxGPM;
    }
	
	public float getMinGPM()
    {
        return minGPM;
    }
	
    public void setMinGPM(float minGPM)
    {
        this.minGPM = minGPM;
    }
	
	
    public float getPresetGPM() {
        return presetGPM;
    }
	
    public void setPresetGPM(float presetGPM) {
        this.presetGPM = presetGPM;
    }   
}
