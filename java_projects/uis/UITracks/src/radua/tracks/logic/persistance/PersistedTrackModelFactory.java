package radua.tracks.logic.persistance;

import java.lang.reflect.Constructor;

import radua.tracks.logic.models.CurvedTrack;
import radua.tracks.logic.models.SplitTrack;
import radua.tracks.logic.models.StraightTrack;
import radua.tracks.logic.models.TrackModel;

@SuppressWarnings("all")
public class PersistedTrackModelFactory 
{
	public static TrackModel createNewModel(String modelType) {
		if (modelType.equals("StraightTrack")) {
			return new StraightTrack(0, 0);
		}
		
		if (modelType.equals("CurvedTrack")) {
			return new CurvedTrack(0, 0);
		}
		
		if (modelType.equals("SplitTrack")) {
			return new SplitTrack(0, 0);
		}
		
		return null;
	}
}
