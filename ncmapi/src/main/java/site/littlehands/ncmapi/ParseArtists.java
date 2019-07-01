package site.littlehands.ncmapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.StringJoiner;

/**
 * Parse artists
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class ParseArtists {

    public static final String ARTISTS_DEFAULT_DELIMITER = ",";

    /**
     * Parse to string
     *
     * @param artists Artists
     * @param delimiter Delimiter
     * @return String type of artists
     * @throws JSONException Parse json error
     */
    public static String toString(JSONArray artists, String delimiter) throws JSONException {
        StringJoiner joiner = new StringJoiner(delimiter);
        int length = artists.length();
        for (int i = 0; i < length; i++) {
            joiner.add(artists.getJSONObject(i).getString("name"));
        }
        return joiner.toString();
    }

    public static String toString(JSONArray artists) throws JSONException {
        return toString(artists, ARTISTS_DEFAULT_DELIMITER);
    }

    public static String toString(JSONObject song, String name, String delimiter) throws JSONException {
        return toString(song.getJSONArray(name), delimiter);
    }

    public static String toString(JSONObject song, String name) throws JSONException {
        return toString(song.getJSONArray(name), ARTISTS_DEFAULT_DELIMITER);
    }
}
