package site.littlehands.samples;

import site.littlehands.ncmapi.ParseShareInfo;

public class ParseShareInfoSamples {

    public static void main(String[] args) {

        String string = "分享M.Graveyard的单曲《Hope》: http://music.163.com/song/28306025/?userid=412128285 (来自@网易云音乐)";
//        String string = "分享Littlehands创建的歌单「hope」: http://music.163.com/playlist/2673407205/412128285/?userid=412128285 (来自@网易云音乐)";
//        String string = "分享歌手M.Graveyard http://music.163.com/artist?id=20579&userid=412128285　(来自@网易云音乐)";
//        String string = "分享M.Graveyard的专辑《ずっと、ふたり。》: http://music.163.com/album/34678333/?userid=412128285 (来自@网易云音乐)";
//        String string = "http://music.163.com/song?id=468490592&userid=412128285";
//        String string = "http://music.163.com/radio/?id=3295004&userid=412128285";
//        String string = "http://music.163.com/mv/?id=419444&userid=412128285";

        ParseShareInfo info = ParseShareInfo.parse(string);
        System.out.print(info);
    }
}
