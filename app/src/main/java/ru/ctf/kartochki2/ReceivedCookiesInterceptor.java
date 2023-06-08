package ru.ctf.kartochki2;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor {
    private Context context;
    public ReceivedCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {

            String cookie = new String();
            for (String newCookie : originalResponse.headers("Set-Cookie")) {
                if (newCookie.split("=")[0].equals("session")) {
                    cookie = newCookie;
                }
            }

            if (!cookie.isEmpty()) {
                SharedPreferences.Editor memes = PreferenceManager.getDefaultSharedPreferences(context).edit();
                memes.putString("PREF_COOKIES", cookie);//.apply();
                memes.commit();
            }
        }

        return originalResponse;
    }
}