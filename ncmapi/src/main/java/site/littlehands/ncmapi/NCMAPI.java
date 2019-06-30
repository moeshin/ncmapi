package site.littlehands.ncmapi;

import okhttp3.*;
import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.UTF_8;


/**
 * Netease Cloud Music API
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class NCMAPI {

    /* Encryption configuration */
    private static final String SECRET_KEY = "TA3YiYCfY2dDJQgg";
    private static final String ENC_SEC_KEY = "84ca47bca10bad09a6b04c5c927ef077d9b9f1e37098aa3eac6ea70eb59df0aa28b691b7e75e4f1f9831754919ea784c8f74fbfadf2898b0be17849fd656060162857830e241aba44991601f137624094c114ea8d17bce815b0cd4e5b8e2fbaba978c6d1d14dc3d1faf852bdd28818031ccdaaa13a6018e1024e2aae98844210";
    private static final String NONCE = "0CoJUm6Qyw8W8jud";
    private static final String IV = "0102030405060708";

    /* Request configuration */
    private static final String USERAGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.157 Safari/537.36";
    private static final String COOKIE = "os=pc; osver=Microsoft-Windows-10-Professional-build-10586-64bit; appver=2.0.3.131777; channel=netease; __remember_me=true";
    private static final String REFERER = "http://music.163.com/";

    /**
     * Search types class
     */
    public interface SearchType {
        int SONG = 1;
        int ALBUM = 10;
        int ARTIST = 100;
        int PLAYLIST = 1000;
        int USER = 1002;
        int LYRIC = 1006;
        int RADIO = 1009;
        int MV = 1014;
    }

    /**
     * The encryption algorithm is AES-128-CBC and the output format is base64 AES.
     * @see <a href="https://github.com/egdw/NeteaseMusic_api/blob/b8f811d754ca2ca465d91d98c812c3f696b26176/src/com/utils/AES.java#L20">egdw/NeteaseMusic_api/src/com/utils/AES.java</a>
     *
     * @param sSrc Source
     * @param sKey Secret key
     * @return base64 AES
     * @throws Exception Encryption error
     */
    private static String encrypt(String sSrc, String sKey) throws Exception {

        byte[] raw = sKey.getBytes(UTF_8);
        SecretKeySpec sKeySpec = new SecretKeySpec(raw, "AES");

        // "Algorithm/mode/complement mode"
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // Using CBC mode, need a vector iv to increase the strength of the encryption algorithm.
        IvParameterSpec iv = new IvParameterSpec(IV.getBytes());

        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());

        // Use base64 for transcoding, and it can also play the role of two encryption.
        return new String(org.apache.commons.codec.binary.Base64.encodeBase64(encrypted));
    }

    /**
     * Send request
     *
     * @param url API URL
     * @param params POST params
     * @return Response string
     * @throws IOException Response error
     */
    private static String request(String url, FormBody params) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Referer", REFERER)
                .addHeader("Cookie", COOKIE)
                .header("User-Agent", USERAGENT)
                .post(params)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful() && response.body() != null) {
            return response.body().string();
        }
        return null;
    }

    private static String request(String url, String params) throws Exception {
        return request(url, formBody(params));
    }

    /**
     * Make POST params
     *
     * @param json JSON string
     * @return Request
     * @throws Exception many errors
     */
    private static FormBody formBody(String json) throws Exception {
        String params = encrypt(json, NONCE);
        params = encrypt(params, SECRET_KEY);
        return new FormBody.Builder()
                .add("params", params)
                .add("encSecKey", ENC_SEC_KEY)
                .build();
    }

    /**
     * Search keyword
     *
     * @param s Keyword
     * @param limit Upper limit
     * @param offset Offset value
     * @param type Search type {@link SearchType}
     * @return JSON string
     * @throws Exception Request error
     */
    public static String search(String s, int limit, int offset, int type) throws Exception {
        String url = "https://music.163.com/weapi/v1/search/get?csrf_token=";
        String params = new JSONObject()
                .put("s", s)
                .put("type", type)
                .put("limit", limit)
                .put("total", "true")
                .put("offset", offset)
                .put("csrf_token", "")
                .toString();
        return request(url, params);
    }

    public static String search(String s, int limit, int offset) throws Exception {
        return search(s, limit, offset, SearchType.SONG);
    }

    public static String search(String s, int limit) throws Exception {
        return search(s, limit, 0);
    }

    public static String search(String s) throws Exception {
        return search(s, 30);
    }

    /**
     * Artist's information and hit songs
     *
     * @param id Artist id
     * @return JSON string
     * @throws Exception Request error
     */
    public static String artist(long id) throws Exception {
        String url = "http://music.163.com/weapi/v1/artist/" + id + "?csrf_token=";
        String params = new JSONObject()
                .put("csrf_token", "")
                .toString();
        return request(url, formBody(params));
    }

    /**
     * Album's information and songs of this album
     *
     * @param id Album id
     * @return JSON string
     * @throws Exception Request error
     */
    public static String album(long id) throws Exception {
        String url = "http://music.163.com/weapi/v1/album/" + id + "?csrf_token=";
        String params = new JSONObject()
                .put("csrf_token", "")
                .toString();
        return request(url, params);
    }

    /**
     * Song detail
     *
     * @param ids Song id
     * @return JSON string
     * @throws Exception Request error
     */
    public static String detail(long ...ids) throws Exception {
        int length = ids.length;
        String[] c = new String[length];
        for (int i = 0; i < length; i++) {
            c[i] = "{id:" + ids[i] + "}";
        }

        String url = "http://music.163.com/weapi/v3/song/detail?csrf_token=";
        String params = new JSONObject()
                .put("c", Arrays.toString(c))
                .put("csrf_token", "")
                .toString();
        return request(url, params);
    }

    /**
     * Song url
     *
     * @param br Bit rate
     * @param ids Song id
     * @return JSON string
     * @throws Exception Request error
     */
    public static String url(int br, long ...ids) throws Exception {
        String url = "http://music.163.com/weapi/song/enhance/player/url?csrf_token=";
        String params = new JSONObject()
                .put("ids", Arrays.toString(ids))
                .put("br", br)
                .put("csrf_token", "")
                .toString();
        return request(url, params);
    }

    public static String url(long ...ids) throws Exception {
        return url(999000, ids);
    }

    /**
     * Playlist
     *
     * @param id Playlist id
     * @param n Number of tracks
     * @return JSON string
     * @throws Exception Request error
     */
    public static String playlist(long id, int n) throws Exception {
        String url = "http://music.163.com/weapi/v3/playlist/detail?csrf_token=";
        String params = new JSONObject()
                .put("id", id)
                .put("n", n)
                .put("csrf_token", "")
                .toString();
        return request(url, params);
    }

    public static String playlist(long id) throws Exception {
        return playlist(id, 0);
    }

    /**
     * Lyric
     *
     * @param id Song id
     * @return JSON string
     * @throws Exception Request error
     */
    public static String lyric(long id) throws Exception {
        String url = "http://music.163.com/weapi/song/lyric?csrf_token=";
        String params = new JSONObject()
                .put("id", id)
                .put("os", "pc")
                .put("lv", -1)
                .put("kv", -1)
                .put("tv", -1)
                .put("csrf_token", "")
                .toString();
        return request(url, params);
    }

    /**
     * Movie
     * @param id MV id
     * @return JSON string
     * @throws Exception Request errors
     */
    public static String mv(long id) throws Exception {
        String url = "http://music.163.com/weapi/mv/detail?csrf_token=";
        String params = new JSONObject()
                .put("id", id)
                .put("csrf_token", "")
                .toString();
        return request(url, params);
    }

    /**
     * Radio
     *
     * @param id Radio id
     * @param limit Upper limit
     * @return JSON string
     * @throws Exception Request error
     */
    public static String radio(long id, int limit) throws Exception {
        String url = "http://music.163.com/weapi/dj/program/byradio?csrf_token=";
        String params = new JSONObject()
                .put("radioId", id)
                .put("csrf_token", "")
                .toString();
        return request(url, params);
    }

    public static String radio(long id) throws Exception {
        return radio(id, 1000);
    }
}
