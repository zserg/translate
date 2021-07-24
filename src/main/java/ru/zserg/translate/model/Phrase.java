package ru.zserg.translate.model;

import java.util.List;

public class Phrase {
    private String word;
    private Pair pair;

    public Phrase(String word, Pair pair) {
        this.word = word;
        this.pair = pair;
    }


    public static Phrase empty(String word){
        return new Phrase(word, new Pair("-", "-"));
    }

    public String getRus() {
        return pair.getRus();
    }

    public String getEng() {
        return pair.getEng();
    }

    public String getWord() {
        return word;
    }

}
