package radua.ui.logic.persistance;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import radua.ui.logic.controllers.WorldController;
import radua.ui.logic.models.IBasicModel;
import radua.ui.logic.models.shapes.OvalModel;
import radua.ui.logic.models.shapes.OvalSnapModel;

public class WorldPersist 
{
	public static final int VERSION_1 	= 1;
	public static final String NAME = "name";
	public static final String X 	= "x";
	public static final String Y 	= "y";

	public static final String VERSION 		= "vers";
	public static final String WIDTH 		= "width";
	public static final String HEIGHT 		= "height";
	public static final String ROTATION	= "rotation";
	public static final String COLOR		= "color";
	
	
	private Hashtable<String, IPersist> persists;
	
	
	public WorldPersist() {
		initPersists();
	}
	
	
	//================================================================================	
	public JSONObject toJson(WorldController worldCrtl) {
		JSONArray arr = new JSONArray();
		for (IBasicModel model : worldCrtl.getModels()) {
			IPersist persist = getPersistByType(model.getClass().getSimpleName());
			JSONObject json = new JSONObject();
			persist.toJson(json, model);
			arr.add(json);
		}
		JSONObject worldJson = new JSONObject();
		worldJson.put("models", arr);
		return worldJson;
	}
	
	public void fromJson(WorldController worldCrtl, JSONObject worldJson) {
		JSONArray arr = (JSONArray) worldJson.get("models");
		for (int idx = 0; idx < arr.size(); idx++) {
			JSONObject json = (JSONObject) arr.get(idx);
			IPersist persist = getPersistByType(json.get(key))
	}
	
	
	
	
	
	
	public void toStream(OutputStream output, WorldController worldCrtl) {
	}
	public void fromStream(WorldController worldCrtl, InputStream input) {
	}
	
	
	public void toString(StringBuffer string, WorldController worldCrtl) {
	}
	public void fromString(WorldController worldCrtl, StringBuffer string) {
	}

	
	//================================================================================	
	
	private IPersist getPersistByType(String name) {
		return persists.get(name);
	}
	
	private void initPersists() {
		persists.put(OvalModel.class.getSimpleName(), new BasicPersist());
		persists.put(OvalModel.class.getSimpleName(), new BasicPersist());
		persists.put(OvalModel.class.getSimpleName(), new BasicPersist());
		persists.put(OvalModel.class.getSimpleName(), new BasicPersist());
		persists.put(OvalModel.class.getSimpleName(), new BasicPersist());
		persists.put(OvalModel.class.getSimpleName(), new BasicPersist());
	}
	
}
