package ru.ctf.kartochki2;

import android.content.Context;
import android.preference.PreferenceManager;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {

    private Context context;

    public AddCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        String cookie = PreferenceManager.getDefaultSharedPreferences(context).getString("PREF_COOKIES", new String());
        if (!cookie.isEmpty()) {
            builder.addHeader("Cookie", cookie);
        }

        return chain.proceed(builder.build());
    }
}
