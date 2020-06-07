package radua.ui.logic.persistance;

import java.io.InputStream;
import java.io.OutputStream;

import org.json.simple.JSONObject;

import radua.ui.logic.models.IBasicModel;

public interface IPersist 
{
	public IBasicModel getModel();
	
	public void toJson(JSONObject json, IBasicModel model);
	public void fromJson(JSONObject json, IBasicModel model);
	
	public void toStream(OutputStream output, IBasicModel model);
	public void fromStream(InputStream input, IBasicModel model);
	
	public void toString(StringBuffer string, IBasicModel model);
	public void fromString(StringBuffer string, IBasicModel model);
}
