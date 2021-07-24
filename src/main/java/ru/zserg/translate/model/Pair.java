package ru.zserg.translate.model;

import java.util.List;

public class Pair {
    private String rus;
    private String eng;

    public Pair(String rus, String eng) {
        this.rus = rus;
        this.eng = eng;
    }

    public Pair(String word, List<String> list) {
        this.rus = list.get(0);
        this.eng = list.get(1);
    }

    public static Pair empty(){
        return new Pair("-", "-");
    }

    public String getRus() {
        return rus;
    }

    public String getEng() {
        return eng;
    }

}
