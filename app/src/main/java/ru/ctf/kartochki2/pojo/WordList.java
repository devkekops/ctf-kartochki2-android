package ru.ctf.kartochki2.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WordList {
    @SerializedName("words")
    public List<Word> words = new ArrayList();

    public class Word {
        @SerializedName("esp")
        public String esp;
        @SerializedName("rus")
        public String rus;
    }
}
