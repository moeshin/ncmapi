package site.littlehands.ncm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parse share link
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class ParseShareInfo {

    /**
     * Source type
     */
    public interface Type {
        int UNDEFINED   = 0;
        int SONG        = 1;
        int PLAYLIST    = 2;
        int ARTIST      = 3;
        int ALBUM       = 4;
        int MV          = 5;
        int RADIO       = 6;
    }

    private int type;

    private long id;

    /**
     * Parse share link
     *
     * @param text Share text
     * @return Instance
     */
    public static ParseShareInfo parse(String text) {

        // https://www.debuggex.com/r/LWpqj_bRDIGGTYVa
        Pattern pattern = Pattern.compile("//music.163.com/([a-z]+)(?:/(?:\\?id=)?|\\?id=)(\\d+)(?:[/&]|$)");

        Matcher matcher = pattern.matcher(text);

        if (!matcher.find()) {
            return null;
        }

        int type = switchType(matcher.group(1));
        long id = Long.valueOf(matcher.group(2));

        return new ParseShareInfo(type, id);
    }

    /**
     * Switch source type
     * @param type Type string
     * @return type int
     */
    public static int switchType(String type) {
        switch (type) {
            case "song":        return Type.SONG;
            case "playlist":    return Type.PLAYLIST;
            case "artist":      return Type.ARTIST;
            case "album":       return Type.ALBUM;
            case "mv":          return Type.MV;
            case "radio":       return Type.RADIO;
            default:            return Type.UNDEFINED;
        }
    }

    private ParseShareInfo(int type, long id) {
        this.type = type;
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "{type:" + type + ",id:" + id + "}";
    }
}
