package site.littlehands.ncmapi;

public class Sample {

    public static void main(String[] args) throws Exception {
        search();
    }

    private static void print(String str) {
        System.out.printf("%s\n", str);
    }

    private static void search() throws Exception {
        print(NCMAPI.search("Hope"));
    }
}
