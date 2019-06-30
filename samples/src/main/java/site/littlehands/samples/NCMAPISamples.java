package site.littlehands.samples;

import site.littlehands.ncmapi.NCMAPI;

@SuppressWarnings("WeakerAccess")
public class NCMAPISamples {

    public static void main(String[] args) throws Exception {
        search();
//        artist();
//        album();
//        detail();
//        url();
//        lyric();
//        playlist();
//        mv();
//        radio();

        // I do not know why here cannot auto exit.
        System.exit(0);
    }

    private static void print(String string) {
        System.out.printf("%s\n", string);
    }

    public static void search() throws Exception {
        print(NCMAPI.search("Hope"));
    }

    public static void artist() throws Exception {
        print(NCMAPI.artist(20579));
    }

    public static void album() throws Exception {
        print(NCMAPI.album(34678333));
    }

    public static void detail() throws Exception {
        print(NCMAPI.detail(28306025));
    }

    public static void url() throws Exception {
        print(NCMAPI.url(28306025L));
    }

    public static void lyric() throws Exception {

        // Have not lyric
//        print(NCMAPI.lyric(28306025));

        // Have lyric
        print(NCMAPI.lyric(468490592));
    }

    public static void playlist() throws Exception {
        print(NCMAPI.playlist(2673407205L));
    }

    public static void mv() throws Exception {
        print(NCMAPI.mv(419444));
    }

    public static void radio() throws Exception {
        print(NCMAPI.radio(3295004));
    }
}
