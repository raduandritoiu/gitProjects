package radua.tracks.logic.persistance;

import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import radua.tracks.logic.models.TrackModel;
import radua.ui.logic.basics.MPoint;
import radua.ui.logic.ids.ModelId;
import radua.ui.logic.ids.SnapId;
import radua.ui.logic.models.snaps.ISnapPoint;
import radua.ui.logic.persistance.IPersist;
import radua.ui.logic.persistance.LoadContext;
import radua.ui.logic.persistance.PersistHelper;


public class PersistToJsonTrackModel implements IPersist<JSONObject>
{
	private static final String CLASS 		= "class";
	private static final String ID 			= "id";
	private static final String POSITION 	= "position";
	private static final String ROTATION 	= "rotation";
	private static final String SNAPS 		= "snaps";
	private static final String IS_SNAPPED 	= "isSnapped";
	private static final String SNAP_ID 	= "snapId";
	

	private TrackModel _track;
	
	public PersistToJsonTrackModel() {}
	public PersistToJsonTrackModel(TrackModel track) {
		_track = track;
	}
	
	public PersistToJsonTrackModel setTrack(TrackModel track) {
		_track = track;
		return this;
	}
	
	public TrackModel getTrack() {
		return _track;
	}
	
	@Override
	public JSONObject save(OutputStream output) {
		JSONObject json = new JSONObject();
		json.put(CLASS, _track.getClass().getSimpleName());
		json.put(ID, _track.id().val);
		json.put(POSITION, PersistHelper.savePointToJson(_track.position()));
		json.put(ROTATION, _track.rotation());
		JSONArray jsonSnaps = new JSONArray();
		for (ISnapPoint snap : _track.snapPoints()) {
			JSONObject jsonSnap = new JSONObject();
			jsonSnap.put(ID, snap.id().val);
			jsonSnap.put(IS_SNAPPED, snap.isSnapped());
			if (snap.isSnapped()) {
				jsonSnap.put(SNAP_ID, snap.getSnap().id().val);
			}
			jsonSnaps.put(jsonSnap);
		}
		json.put(SNAPS, jsonSnaps);
		return json;
	}
	
	
	@Override
	public void load(JSONObject format, InputStream input, LoadContext context) {
		String trackType = format.getString(CLASS);
		ModelId jsonTrackId = new ModelId(format.getInt(ID));
		_track = PersistedTrackModelFactory.createNewModel(trackType);
		_track.moveTo(PersistHelper.loadPointFromJson(format.getJSONObject(POSITION), new MPoint()));
		_track.rotateTo(format.getDouble(ROTATION));
		JSONArray jsonSnaps = format.getJSONArray(SNAPS);
		for (int idx = 0; idx < jsonSnaps.length(); idx++) {
			JSONObject jsonSnap = (JSONObject) jsonSnaps.get(idx);
			SnapId jsonSnapId = new SnapId(jsonSnap.getInt(ID));
			ISnapPoint snap = _track.snapPoints().get(idx);
			// add to context
			context.crossRefIds.put(jsonSnapId, snap.id());
			context.elements.put(snap.id(), snap);
		}
		// add to context
		context.elements.put(_track.id(), _track);
		context.crossRefIds.put(jsonTrackId, _track.id());
	}
	
	
	@Override
	public void updateLoad(JSONObject format, InputStream input, LoadContext context) {
		// find current track
		ModelId jsonTrackId = new ModelId(format.getInt(ID));
		ModelId trackId = (ModelId) context.crossRefIds.get(jsonTrackId);
		_track = (TrackModel) context.elements.get(trackId);
		
		// recreate snaps for snap points
		JSONArray jsonSnaps = format.getJSONArray(SNAPS);
		for (int idx = 0; idx < jsonSnaps.length(); idx++) {
			JSONObject jsonSnap = (JSONObject) jsonSnaps.get(idx);
			boolean jsonIsSnapped = jsonSnap.getBoolean(IS_SNAPPED);
			if (!jsonIsSnapped) 
				continue;
			
			// recreate snap
			SnapId jsonRemoteSnapId = new SnapId(jsonSnap.getInt(SNAP_ID));
			SnapId remoteSnapId = (SnapId) context.crossRefIds.get(jsonRemoteSnapId);
			ISnapPoint snap = _track.snapPoints().get(idx);
			ISnapPoint remoteSnap = (ISnapPoint) context.elements.get(remoteSnapId);
			snap.setSnap(remoteSnap);
		}
	}
}
