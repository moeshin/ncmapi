package site.littlehands.ncmapi;

import org.junit.Test;

public class Samples {

    private void print(String string) {
        System.out.printf("%s\n", string);
    }

    @Test
    public void search() throws Exception {
        print(NCMAPI.search("Hope"));
    }

    @Test
    public void artist() throws Exception {
        print(NCMAPI.artist(20579));
    }

    @Test
    public void album() throws Exception {
        print(NCMAPI.album(34678333));
    }

    @Test
    public void detail() throws Exception {
        print(NCMAPI.detail(28306025));
    }

    @Test
    public void url() throws Exception {
        print(NCMAPI.url(28306025L));
    }

    @Test
    public void lyric() throws Exception {

        // Have not lyric
//        print(NCMAPI.lyric(28306025));

        // Have lyric
        print(NCMAPI.lyric(468490592));
    }

    @Test
    public void playlist() throws Exception {
        print(NCMAPI.playlist(2673407205L));
    }

    @Test
    public void mv() throws Exception {
        print(NCMAPI.mv(419444));
    }

    @Test
    public void radio() throws Exception {
        print(NCMAPI.radio(3295004));
    }
}
