/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.io;

import org.tmatesoft.sqljet.core.SqlJetEncoding;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.SqlJetDb;
import siemens.vsst.data.parsers.utils.StaticParameters;
import siemens.vsst.data.parsers.utils.VSSTLogger;

/**
 *
 * @author User
 */
public class SQLLiteCreator 
{
    /**
     * Create SQL|lite database file and structure
     */
    public void prepareDatabase(SqlJetDb db) throws SqlJetException
    {
		// Set Options
		db.getOptions().setEncoding(SqlJetEncoding.UTF8);
		db.beginTransaction(SqlJetTransactionMode.WRITE);

		// Create Tables			
		try {
			// --- Products Table ---
			createProductsTable(db);
			
			// --- Document Material Table ---
			createDocumentMaterialTable(db);
			
			// --- Valve Info Table ---
			createValveTable(db);
			
			// ---- Create Actuator Table -----
			createActuatorTable(db);
			
			// --- Create Assemblies Table ---
			createAssembliesTable(db);
			
			// --- Create Damper Table ---
			if (StaticParameters.EXTENDED_VERSION) {
				createDamperTable(db);
			}
			
			// --- Create Damper Table ---
			if (StaticParameters.EXTENDED_VERSION) {
				createPneumaticTable(db);
			}
			
			// --- Create Accessories Table ---
			if (StaticParameters.EXTENDED_VERSION) {
				createAccessoriesTable(db);
			}
			
			// --- Create Damper Accessory Cross Table ---
			if (StaticParameters.EXTENDED_VERSION) {
				createDamperAccessoryCrossTable(db);
			}
			
			// --- Create Pneumatic Accessory Cross Table ---
			if (StaticParameters.EXTENDED_VERSION) {
				createPneumaticAccessoryCrossTable(db);
			}
			
			// --- Create Cross Reference Table ---
			createCrossReferenceTable(db);
		}
		catch (Exception e){
			VSSTLogger.Info("Data Base " + e);
		}
		finally {
			db.commit();
		}
	}
	
	
	// --- Products Table ---
	public void createProductsTable(SqlJetDb db) throws SqlJetException
	{
		String tableSQL;
		tableSQL =		"CREATE TABLE Products (" +
//							"id						integer PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE," +
							"productType			integer,";
		if (StaticParameters.EXTENDED_VERSION) {
			tableSQL +=		"extendedProductType	integer,";
		}
		tableSQL +=			"partNumber				varchar(25) PRIMARY KEY NOT NULL UNIQUE," +
							"actualPartNumber		varchar(25)," +
							"sapPartNumber			varchar(25)," +
							"description			varchar(2048)," +
							"manufacturer			varchar(20)," +
							"vendor					varchar(10)," +
							"isObsolete				bit," +
							"isVirtualPart			bit," +
							"assemblyOnly			bit," +
							"price					real," +
							"subProductPartNumber   varchar(25)," +
							"subProductPrice        real," +
							"lastModified			datetime" +
							");";
		db.createTable(tableSQL);		
		// ---- index -----
		db.createIndex("CREATE UNIQUE INDEX Products_partNumber " +
							"ON Products " +
							"(partNumber);");
		db.createIndex("CREATE INDEX Products_sapPartNumber " +
							"ON Products " +
							"(sapPartNumber);");
		db.createIndex("CREATE INDEX Products_type " +
							"ON Products " +
							"(productType);");
		if (StaticParameters.EXTENDED_VERSION) {
			db.createIndex("CREATE INDEX Products_extendedProductType " +
							"ON Products " +
							"(extendedProductType);");
		}
		db.createIndex("CREATE INDEX Products_price " +
							"ON Products " +
							"(price);");
		db.createIndex("CREATE INDEX Products_obsolete " +
							"ON Products " +
							"(isObsolete);");
		db.createIndex("CREATE INDEX Products_lastModified " +
							"ON Products " +
							"(lastModified);");
	}		
	
	
	// --- Document Material Table ---
	public void createDocumentMaterialTable(SqlJetDb db) throws SqlJetException
	{
		db.createTable("CREATE TABLE Documents (" +
							"partNumber     varchar(25)," +
							"name           varchar(25)," +
							"description    varchar(50)," +
							"documentType   varchar(25)," +
							"isImage        bit," +
							"location       varchar(255));");		
		// ---- index -----
		db.createIndex("CREATE INDEX Documents_Index01 " +
							"ON Documents " +
							"(partNumber);");
		db.createIndex("CREATE INDEX Documents_docType " +
							"ON Documents " +
							"(documentType);");
	}		


	// --- Valve Info Table ---
	public void createValveTable(SqlJetDb db) throws SqlJetException
	{
		db.createTable("CREATE TABLE Valves (" +
//							"id               integer PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE," +
							"partNumber       varchar(25) PRIMARY KEY NOT NULL UNIQUE," +
							"medium           integer," +
							"mixingDiverting  integer," +
							"size             real," +
							"cvu              real," +
							"cvl              real," +
							"pattern          integer," +
							"failSafeU        varchar(13)," +
							"connection       integer," +
							"type             integer," +
							"seatStyle        varchar(15)," +
							"flowChar         integer," +
							"maxTemp          real," +
							"maxPress         real," +
							"maxDPWater       real," +
							"maxDPSteam       real," +
							"maxIPWater       real," +
							"maxIPSteam       real," +
							"maxPCTGly        real," +
							"minGPM           real," +
							"maxGPM           real," +
							"presetGPM        real," +
							//"motorType        integer," +
							//"positioner       bit," +
							//"controlSignal    integer," +
							"ambientTemp      varchar(13)," +
							"model            varchar(19)," +
							"bodyMaterial     varchar(15)," +
							"packingMaterial  integer," +
							"stemMaterial     varchar(15)," +
							"plugMaterial     integer," +
							"discMaterial     varchar(15)," +
							"seatMaterial     varchar(15)," +
							"closeOff         real," +
							"thread           varchar(3)," +
							"kvsu             real," +
							"kvsl             real," +
							"lastModified     datetime," +
							"normalState      integer," +
							"discType         integer" +
							/* Foreign keys */
//							"FOREIGN KEY (partNumber) " +
//								"REFERENCES Products(partNumber) " +
//								"ON DELETE CASCADE " +
//								"ON UPDATE CASCADE" +
							");");
		// ---- index -----		
		db.createIndex("CREATE UNIQUE INDEX ValveInfo_partNumber " +
							"ON Valves " +
							"(partNumber);");
		db.createIndex("CREATE INDEX ValveInfo_medium " +
							"ON Valves " +
							"(medium);");
		db.createIndex("CREATE INDEX ValveInfo_mixdiv " +
							"ON Valves " +
							"(mixingDiverting);");
		db.createIndex("CREATE INDEX ValveInfo_size " +
							"ON Valves " +
							"(size);");
		db.createIndex("CREATE INDEX ValveInfo_cvu " +
							"ON Valves " +
							"(cvu);");
		db.createIndex("CREATE INDEX ValveInfo_action " +
							"ON Valves " +
							"(pattern);");
		db.createIndex("CREATE INDEX ValveInfo_connection " +
							"ON Valves " +
							"(connection);");
		db.createIndex("CREATE INDEX ValveInfo_type " +
							"ON Valves " +
							"(type);");
		db.createIndex("CREATE INDEX ValveInfo_flowChar " +
							"ON Valves " +
							"(flowChar);");
		db.createIndex("CREATE INDEX ValveInfo_maxTemp " +
							"ON Valves " +
							"(maxTemp);");
		db.createIndex("CREATE INDEX ValveInfo_maxPress " +
							"ON Valves " +
							"(maxPress);");
		db.createIndex("CREATE INDEX ValveInfo_maxDPWater " +
							"ON Valves " +
							"(maxDPWater);");
		db.createIndex("CREATE INDEX ValveInfo_maxDPSteam " +
							"ON Valves " +
							"(maxDPSteam);");
		db.createIndex("CREATE INDEX ValveInfo_maxPCTGly " +
							"ON Valves " +
							"(maxPCTGly);");
		db.createIndex("CREATE INDEX ValveInfo_plugMaterial " +
							"ON Valves " +
							"(plugMaterial);");
		db.createIndex("CREATE INDEX ValveInfo_normalState " +
							"ON Valves " +
							"(normalState);");
		db.createIndex("CREATE INDEX ValveInfo_discType " +
							"ON Valves " +
							"(discType);");
	}		


	// ---- Create Valve Actuator Table -----
	public void createActuatorTable(SqlJetDb db) throws SqlJetException
	{
		db.createTable("CREATE TABLE Actuators (" +
							"partNumber			varchar(25) PRIMARY KEY NOT NULL UNIQUE," +
							"motorType			integer," +
							"positioner			integer," +
							"controlSignal		integer," +
							/*"closeOff         real," +  Close off is an assembly parameter*/
							"safetyFunction		integer," +
							"supplyVoltage		integer," +
							"hasHeater          bit," +
							"hasManualOverride  bit," +
							"endSwitch          integer," +
							"butterflyConfig	varchar(1)" +
							");");
		// ---- index -----
		db.createIndex("CREATE UNIQUE INDEX Actuators_partNumber " +
							"ON Actuators " +
							"(partNumber);");
		db.createIndex("CREATE INDEX Actuators_motorType " +
							"ON Actuators " +
							"(motorType);");
		db.createIndex("CREATE INDEX Actuators_controlSignal " +
							"ON Actuators " +
							"(controlSignal);");
		db.createIndex("CREATE INDEX Actuators_safetyFunction " +
							"ON Actuators " +
							"(safetyFunction);");
		db.createIndex("CREATE INDEX Actuators_positioner " +
							"ON Actuators " +
							"(positioner);");
		db.createIndex("CREATE INDEX Actuators_endSwitch " +
							"ON Actuators " +
							"(endSwitch);");
	}		


	// --- Create Assemblies Table ---
	public void createAssembliesTable(SqlJetDb db) throws SqlJetException
	{
		db.createTable("CREATE TABLE Assemblies (" +
							"partNumber         varchar(25) PRIMARY KEY NOT NULL UNIQUE," +
							"valvePartNumber    varchar(25)," +
							"actuatorPartNumber varchar(25)," +
							"normalState        integer," +
							"threeWayFunction   integer," +
							"controlSignal      integer," +
							"closeOff           real," +
							/* Foreign keys */
							"FOREIGN KEY (valvePartNumber) " +
								"REFERENCES Valves (partNumber) " +
								"ON DELETE CASCADE " +
								"ON UPDATE CASCADE," +
							"FOREIGN KEY (actuatorPartNumber) " +
								"REFERENCES Actuators (partNumber) " +
								"ON DELETE CASCADE " +
								"ON UPDATE CASCADE" +
							");");		
		// ---- index -----		
		db.createIndex("CREATE UNIQUE INDEX Assemblies_partNumber " +
							"ON Assemblies " +
							"(partNumber);");
		db.createIndex("CREATE INDEX Assemblies_normalState " +
							"ON Assemblies " +
							"(normalState);");
		db.createIndex("CREATE INDEX Assemblies_valvePartNumber " +
							"ON Assemblies " +
							"(valvePartNumber);");
		db.createIndex("CREATE INDEX Assemblies_actuatorPartNumber " +
							"ON Assemblies " +
							"(actuatorPartNumber);");
		db.createIndex("CREATE INDEX Assemblies_threeWayFunction " +
							"ON Assemblies " +
							"(threeWayFunction);");
		db.createIndex("CREATE INDEX Assemblies_controlSignal " +
							"ON Assemblies " +
							"(controlSignal);");
		db.createIndex("CREATE INDEX Assemblies_closeOff " +
							"ON Assemblies " +
							"(closeOff);");
	}		


	// --- Create Damper Table ---
	public void createDamperTable(SqlJetDb db) throws SqlJetException
	{
		db.createTable("CREATE TABLE Dampers (" +
//							"id					integer PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE," +
							"partNumber			varchar(25) PRIMARY KEY NOT NULL UNIQUE," +
							"type				integer," +
							"torque				real," +
							"controlSignal		integer," +
							"systemSupply		integer," +
							"plenumRating		integer," +
							"positionFeedback	integer," +
							"auxilarySwitch		integer," +
							"scalableSignal		integer" +
							");");
		// ---- index -----
		db.createIndex("CREATE UNIQUE INDEX Dampers_partNumber " +
							"ON Dampers " +
							"(partNumber);");
		db.createIndex("CREATE INDEX Dampers_type " +
							"ON Dampers " +
							"(type);");
		db.createIndex("CREATE INDEX Dampers_torque " +
							"ON Dampers " +
							"(torque);");
		db.createIndex("CREATE INDEX Dampers_controlSignal " +
							"ON Dampers " +
							"(controlSignal);");
		db.createIndex("CREATE INDEX Dampers_systemSupply " +
							"ON Dampers " +
							"(systemSupply);");
		db.createIndex("CREATE INDEX Dampers_plenumRating " +
							"ON Dampers " +
							"(plenumRating);");
		db.createIndex("CREATE INDEX Dampers_positionFeedback " +
							"ON Dampers " +
							"(positionFeedback);");
		db.createIndex("CREATE INDEX Dampers_auxilarySwitch " +
							"ON Dampers " +
							"(auxilarySwitch);");
		db.createIndex("CREATE INDEX Dampers_scalableSignal " +
							"ON Dampers " +
							"(scalableSignal);");
	}		


	// --- Create Damper Table ---
	public void createPneumaticTable(SqlJetDb db) throws SqlJetException
	{
		db.createTable("CREATE TABLE Pneumatics (" +
//							"id					integer PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, " +
							"partNumber			varchar(25) PRIMARY KEY NOT NULL UNIQUE, " +
							"type				integer, " +
							"stroke				integer, " +	//--
							"housing			integer, " +
							"stem				integer, " +
							"diaphArea			integer, " +
							"diaphMaterial		integer, " +
							"operatingTemp		integer, " +
							"isActuator			integer, " +	//--
							"clevis				integer, " +
							"bracket			integer, " +
							"ballJoint			integer, " +
							"pivot				integer, " +
							"posRelay			integer, " +
							"fwdTravelStops		integer, " +
							"mountingStyle		integer, " +	//--
							"ULCert				integer, " +
							"springRange		integer, " +
							"baseReplacement	varchar(25), " +
							"maxThrust_18		real, " +
							"maxThrust_no		real " +
							");");
		// ---- index -----
		db.createIndex("CREATE UNIQUE INDEX Pneumatics_partNumber " +
							"ON Pneumatics " +
							"(partNumber);");
		db.createIndex("CREATE INDEX Pneumatics_type " +
							"ON Pneumatics " +
							"(type);");
		db.createIndex("CREATE INDEX Pneumatics_stroke " +	//--
							"ON Pneumatics " +
							"(stroke);");
		db.createIndex("CREATE INDEX Pneumatics_housing " +
							"ON Pneumatics " +
							"(housing);");
		db.createIndex("CREATE INDEX Pneumatics_stem " +
							"ON Pneumatics " +
							"(stem);");
		db.createIndex("CREATE INDEX Pneumatics_diaphArea " +
							"ON Pneumatics " +
							"(diaphArea);");
		db.createIndex("CREATE INDEX Pneumatics_diaphMaterial " +
							"ON Pneumatics " +
							"(diaphMaterial);");
		db.createIndex("CREATE INDEX Pneumatics_operatingTemp " +
							"ON Pneumatics " +
							"(operatingTemp);");
		db.createIndex("CREATE INDEX Pneumatics_isActuator " +	//--
							"ON Pneumatics " +
							"(isActuator);");
		db.createIndex("CREATE INDEX Pneumatics_clevis " +
							"ON Pneumatics " +
							"(clevis);");
		db.createIndex("CREATE INDEX Pneumatics_bracket " +
							"ON Pneumatics " +
							"(bracket);");
		db.createIndex("CREATE INDEX Pneumatics_ballJoint " +
							"ON Pneumatics " +
							"(ballJoint);");
		db.createIndex("CREATE INDEX Pneumatics_pivot " +
							"ON Pneumatics " +
							"(pivot);");
		db.createIndex("CREATE INDEX Pneumatics_posRelay " +
							"ON Pneumatics " +
							"(posRelay);");
		db.createIndex("CREATE INDEX Pneumatics_fwdTravelStops " +
							"ON Pneumatics " +
							"(fwdTravelStops);");
		db.createIndex("CREATE INDEX Pneumatics_mountingStyle " +	//--
							"ON Pneumatics " +
							"(mountingStyle);");
		db.createIndex("CREATE INDEX Pneumatics_ULCert " +
							"ON Pneumatics " +
							"(ULCert);");
		db.createIndex("CREATE INDEX Pneumatics_springRange " +
							"ON Pneumatics " +
							"(springRange);");
		db.createIndex("CREATE INDEX Pneumatics_maxThrust_18 " +
							"ON Pneumatics " +
							"(maxThrust_18);");
		db.createIndex("CREATE INDEX Pneumatics_maxThrust_no " +
							"ON Pneumatics " +
							"(maxThrust_no);");
		// ---- index -----
	}		


	// --- Create Accessories Table ---
	public void createAccessoriesTable(SqlJetDb db) throws SqlJetException
	{
		db.createTable("CREATE TABLE AccessoryKits (" +
//							"id					integer PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE," +
							"partNumber			varchar(25) PRIMARY KEY NOT NULL UNIQUE," +
							"fromDamper			integer, " +
							"fromPneumatic		integer " +
							");");
		// ---- index -----
		db.createIndex("CREATE UNIQUE INDEX AccessoryKits_partNumber " +
							"ON AccessoryKits " +
							"(partNumber);");
		db.createIndex("CREATE INDEX AccessoryKits_fromDamper " +
							"ON AccessoryKits " +
							"(fromDamper);");
		db.createIndex("CREATE INDEX AccessoryKits_fromPneumatic " +
							"ON AccessoryKits " +
							"(fromPneumatic);");
	}		


	// --- Create Damper Accessory Cross Table ---
	public void createDamperAccessoryCrossTable(SqlJetDb db) throws SqlJetException
	{
		db.createTable("CREATE TABLE CrossAccessoriesDampers (" +
							"id						integer PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE," +
							"productPartNumber		varchar(25)," +
							"accessoryPartNumber	varchar(25)," +
							/* Foreign keys */
							"FOREIGN KEY (productPartNumber) " +
								"REFERENCES Dampers (partNumber) " +
								"ON DELETE CASCADE " +
								"ON UPDATE CASCADE," +
							"FOREIGN KEY (accessoryPartNumber) " +
								"REFERENCES AccessoryKits (partNumber) " +
								"ON DELETE CASCADE " +
								"ON UPDATE CASCADE" +
							");");
		// ---- index -----
		db.createIndex("CREATE UNIQUE INDEX CrossAccessoriesDampers_id " +
							"ON CrossAccessoriesDampers " +
							"(id);");
		db.createIndex("CREATE INDEX CrossAccessoriesDampers_productPartNumber " +
							"ON CrossAccessoriesDampers " +
							"(productPartNumber);");
		db.createIndex("CREATE INDEX CrossAccessoriesDampers_accessoryPartNumber " +
							"ON CrossAccessoriesDampers " +
							"(accessoryPartNumber);");
	}		


	// --- Create Damper Accessory Cross Table ---
	public void createPneumaticAccessoryCrossTable(SqlJetDb db) throws SqlJetException
	{
		db.createTable("CREATE TABLE CrossAccessoriesPneumatics (" +
							"id						integer PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE," +
							"productPartNumber		varchar(25)," +
							"accessoryPartNumber	varchar(25)," +
							/* Foreign keys */
							"FOREIGN KEY (productPartNumber) " +
								"REFERENCES Pneumatics (partNumber) " +
								"ON DELETE CASCADE " +
								"ON UPDATE CASCADE," +
							"FOREIGN KEY (accessoryPartNumber) " +
								"REFERENCES AccessoryKits (partNumber) " +
								"ON DELETE CASCADE " +
								"ON UPDATE CASCADE" +
							");");
		// ---- index -----
		db.createIndex("CREATE UNIQUE INDEX CrossAccessoriesPneumatics_id " +
							"ON CrossAccessoriesPneumatics " +
							"(id);");
		db.createIndex("CREATE INDEX CrossAccessoriesPneumatics_productPartNumber " +
							"ON CrossAccessoriesPneumatics " +
							"(productPartNumber);");
		db.createIndex("CREATE INDEX CrossAccessoriesPneumatics_accessoryPartNumber " +
							"ON CrossAccessoriesPneumatics " +
							"(accessoryPartNumber);");
	}		


	// --- Create Cross Reference Table ---
	public void createCrossReferenceTable(SqlJetDb db) throws SqlJetException
	{
		db.createTable("CREATE TABLE CrossReference (" +
							"competitorPartNumber	varchar(25) NOT NULL," +
							"competitorName			varchar(25)," +
							"siemensPartNumber		varchart(25)," +
							"subProductPartNumber	varchar(25)," +
							"actualPartNumber		varchar(25)" + 
							");");
		// ---- index -----
		db.createIndex("CREATE INDEX competitorPartNumberIndex " + 
							"ON CrossReference " +
							"(competitorPartNumber);");
		db.createIndex("CREATE INDEX siemensPartNumberIndex " + 
							"ON CrossReference " +
							"(siemensPartNumber);");       
		db.createIndex("CREATE INDEX crossRefSubProductPartNumberIndex " + 
							"ON CrossReference " +
							"(subProductPartNumber);"); 
		db.createIndex("CREATE INDEX crossRefActualProductPartNumberIndex " + 
							"ON CrossReference " +
							"(actualPartNumber);"); 
	}		
}
