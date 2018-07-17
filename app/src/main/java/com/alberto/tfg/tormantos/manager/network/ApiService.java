package com.alberto.tfg.tormantos.manager.network;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * This ApiService class works with the configuration of auth
 * for post the database data to remote server
 */
public class ApiService {

    /** Base api url */
    private static final String BASE_URL = "http://tormantos.ddns.net";

    /** Api user credential */
    private static final String API_USER = "spilab";

    /** Api pass credential */
    private static final String API_PASS = "spilab2018";

    /** Access to the endpoints interface */
    private static ApiInterface client;

    /** Json serializer */
    private static Gson gson = new GsonBuilder()
            .setLenient().create();

    /**
     * Returns an instance of ApiInterface to call the api
     *
     * @return an ApiInterface with a basic auth client with user and pass credentials
     */
    public static ApiInterface getBasicAuthClient() {
        if (client == null) {

            // set endpoint url and use OkHTTP as HTTP client,
            // we have auth in headerString

            RestAdapter.Builder builder = new RestAdapter.Builder()
                    .setEndpoint(BASE_URL)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setConverter(new GsonConverter(gson)) //
                    .setClient(new OkClient(new OkHttpClient()))
                    .setLogLevel(RestAdapter.LogLevel.FULL).setLog(new RestAdapter.Log() {
                        public void log(String msg) {
                            Log.i("retrofit", msg);
                        }
                    });


            // concatenate username and password with colon for authentication
            final String credentials = API_USER + ":" + API_PASS;
            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    // If we dont need auth, only comment the line below
                    request.addHeader("Authorization", "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP));
                }
            });

            RestAdapter adapter = builder.build();
            client = adapter.create(ApiInterface.class);
        }

        return client;
    }

}
