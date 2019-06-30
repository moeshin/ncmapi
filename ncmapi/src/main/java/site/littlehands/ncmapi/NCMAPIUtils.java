package site.littlehands.ncmapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.StringJoiner;

@SuppressWarnings({"WeakerAccess", "unused"})
public class NCMAPIUtils {

    public static final String ARTISTS_DEFAULT_DELIMITER = ",";

    /**
     * Artists to string
     *
     * @param artists Artists
     * @param delimiter Delimiter
     * @return String type of artists
     * @throws JSONException Parse json error
     */
    public static String artistsToString(JSONArray artists, String delimiter) throws JSONException {
        StringJoiner joiner = new StringJoiner(delimiter);
        int length = artists.length();
        for (int i = 0; i < length; i++) {
            joiner.add(artists.getJSONObject(i).getString("name"));
        }
        return joiner.toString();
    }

    public static String artistsToString(JSONArray artists) throws JSONException {
        return artistsToString(artists, ARTISTS_DEFAULT_DELIMITER);
    }

    public static String artistsToString(JSONObject song, String name, String delimiter) throws JSONException {
        return artistsToString(song.getJSONArray(name), delimiter);
    }

    public static String artistsToString(JSONObject song, String name) throws JSONException {
        return artistsToString(song.getJSONArray(name), ARTISTS_DEFAULT_DELIMITER);
    }
}
