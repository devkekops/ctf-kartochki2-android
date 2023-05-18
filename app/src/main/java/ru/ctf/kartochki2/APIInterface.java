package ru.ctf.kartochki2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.ctf.kartochki2.pojo.LicenseKey;
import ru.ctf.kartochki2.pojo.Word;

public interface APIInterface {
    @GET("/favicon.ico")
    Call<Void> doGetIcon();

    @GET("/api/getFreeWords")
    Call<List<Word>> doGetFreeWords();

    @GET("/api/getPaidWords")
    Call<List<Word>> doGetPaidWords();

    @POST("/api/activate")
    Call<Void> doActivate(@Body LicenseKey licenseKey);
}
