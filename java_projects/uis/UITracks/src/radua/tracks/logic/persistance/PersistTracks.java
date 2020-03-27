package radua.tracks.logic.persistance;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import radua.tracks.logic.models.TrackModel;
import radua.ui.logic.models.IBasicModel;
import radua.ui.logic.persistance.IPersister;
import radua.ui.logic.persistance.LoadContext;
import radua.ui.logic.persistance.Versioning;


public class PersistTracks implements IPersister
{
	private static final String PERSISTS = "persists";
	private static final String VERSION = "version";
	private static final String TRACKS = "trackModels";
	private static final String FILE = "saved_track_2.txt";
	
	
	private List<IBasicModel> _models;
	private LoadContext _context;
	
	
	public PersistTracks() {}
	public PersistTracks(List<IBasicModel> models) {
		_models = models;
	}
	
	
	public void setModels(List<IBasicModel> models) {
		_models = models;
	}
	public List<IBasicModel> getModels() {
		return _models;
	}
	public LoadContext getContext() {
		return _context;
	}
	
	
	public void save() {
		try {
			JSONObject json = saveJson();
			FileOutputStream output = new FileOutputStream(FILE);
			PrintWriter writer = new PrintWriter(output);
			writer.println(json.toString());
			writer.flush();
			output.close();
		}
		catch (Exception ex) {
			System.out.println("FUCK!");
		}
	}

	private JSONObject saveJson() {
		PersistToJsonTrackModel persist = new PersistToJsonTrackModel();
		
		// save general info
		JSONObject json = new JSONObject();
		json.put(PERSISTS, this.getClass().getSimpleName());
		json.put(VERSION, Versioning.CURRENT_VERSION);
		
		// save track models
		JSONArray jsonTracks = new JSONArray();
		for (IBasicModel track : _models) {
			jsonTracks.put(persist.setTrack((TrackModel) track).save(null));
		}
		json.put(TRACKS, jsonTracks);
		return json;
	}
	
	
	public void load() {
		_models = new ArrayList<>();
		_context = new LoadContext();
		try {
			FileInputStream input = new FileInputStream(FILE);
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			JSONObject json = new JSONObject(reader.readLine());
			input.close();
			load(json, _context);
		}
		catch (Exception ex) {
			System.out.println("FUCK!");
		}
	}

	
	private void load(JSONObject json, LoadContext context) throws Exception {
		PersistToJsonTrackModel persist = new PersistToJsonTrackModel();
		
		// load general information 
		context.loadVersion = json.getInt(VERSION);
		JSONArray jsonTracks = json.getJSONArray(TRACKS);
		
		// load track models
		for (Object jsonObj : jsonTracks) {
			persist.load((JSONObject) jsonObj, null, context);
			_models.add(persist.getTrack());
		}
		
		// update loaded track models
		for (Object jsonObj : jsonTracks) {
			persist.updateLoad((JSONObject) jsonObj, null, context);
		}
	}
}
