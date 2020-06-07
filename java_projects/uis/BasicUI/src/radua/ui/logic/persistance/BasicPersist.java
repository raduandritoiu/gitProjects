package radua.ui.logic.persistance;

import java.io.InputStream;
import java.io.OutputStream;

import org.json.simple.JSONObject;

import radua.ui.logic.models.IBasicModel;


public class BasicPersist implements IPersist 
{
	public static final int VERSION_1 = 1;
	public static final String VERSION 	= "vers";
	public static final String NAME 		= "name";
	public static final String X	 		= "x";
	public static final String Y	 		= "y";

	
	
	public IBasicModel createModel() {
		return null;
	}

	
	public IPersist getPersistByType(String name) {
		return null;
	}

	
	public JSONObject toJson(IBasicModel model) { 
		JSONObject json = new JSONObject();
		json.put(VERSION, 	VERSION_1);
		json.put(NAME, 		model.type());
		json.put(X, 		VERSION_1);
		json.put(Y, 		VERSION_1);
		return json;
	}
	
	public IBasicModel fromJson(JSONObject object) { 
		return null; 
	}

	public String toString(IBasicModel model) { return null; }
	public IBasicModel fromString(JSONObject object) { return null; }

	public void toStream(OutputStream output, IBasicModel model) { }
	public IBasicModel fromStream(InputStream input) { return null; }
}
