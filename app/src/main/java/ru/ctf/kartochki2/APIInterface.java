package ru.ctf.kartochki2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.ctf.kartochki2.pojo.WordList;

public interface APIInterface {
    @GET("/api/getWords")
    Call<WordList> doGetWords();
}
