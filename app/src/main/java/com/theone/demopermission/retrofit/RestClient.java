package com.theone.demopermission.retrofit;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.theone.demopermission.BuildConfig;
import com.theone.demopermission.R;
import com.theone.demopermission.utills.NetworkReceiver;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jaykishan on 08-04-2019,
 * at TheOneTechnologies
 */

public class RestClient {

    private static RestClient instance;
    private static ApiInterface apiInterface;
    Context context;
    private boolean addHeader = true;

    public RestClient(final Context context) {

        if (NetworkReceiver.getConnectivityStatus(context) != 0) {
            instance = this;
            this.context = context;
            initRestClient(context, false);
        } else {
            Toast.makeText(context, "" + context.getResources().getString(R.string.str_no_internet), Toast.LENGTH_SHORT).show();
        }

    }

    public RestClient(final Context context, boolean addHeader) {
        if (NetworkReceiver.getConnectivityStatus(context) != 0) {
            instance = this;
            this.context = context;
            initRestClient(context, false);
            this.addHeader = addHeader;
        } else {
            Toast.makeText(context, "" + context.getResources().getString(R.string.str_no_internet), Toast.LENGTH_SHORT).show();
        }
    }


    public static RestClient getInstance() {
        return instance;
    }

    public static ApiInterface getApiInterface(Context context) {
        if (NetworkReceiver.getConnectivityStatus(context) != 0) {
            return apiInterface;
        } else {
            Toast.makeText(context, "" + context.getResources().getString(R.string.str_no_internet), Toast.LENGTH_SHORT).show();
            return apiInterface;
        }
    }

    public void initRestClient(final Context context, boolean isValidateToken) {

        final OkHttpClient.Builder okHttpClientBuilder = getUnsafeOkHttpClient();
        okHttpClientBuilder.connectTimeout(2, TimeUnit.MINUTES);
        okHttpClientBuilder.writeTimeout(2, TimeUnit.MINUTES);
        okHttpClientBuilder.readTimeout(2, TimeUnit.MINUTES);

        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(interceptor);

        final String token = "Token Type" + " " + "Token";
        if (!TextUtils.isEmpty(token)) {
            okHttpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    final Request original = chain.request();
                    final Request.Builder requestBuilder = original.newBuilder();
                    if (addHeader) {
                        requestBuilder.addHeader("Authorization", String.format(Locale.getDefault(), token));
                    }
                    return chain.proceed(requestBuilder.build());
                }
            });
        }

        final Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl(BuildConfig.BASE_URL);
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());

        final Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(5);
        okHttpClientBuilder.dispatcher(dispatcher);

        final OkHttpClient okHttpClient = okHttpClientBuilder.build();
        retrofitBuilder.client(okHttpClient);
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());

        final Retrofit retrofit = retrofitBuilder.build();
        apiInterface = retrofit.create(ApiInterface.class);

    }

    public OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
