package com.alberto.tfg.tormantos.manager.network;

import android.util.Log;

import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RetrofitError;

public class SendDeviceData {

    private static final String TAG = "SendDeviceData";

    /**
     * Send to api a chrome dto as json
     */
    public static void postChromeJson(JsonObject jsonObject) {

        ApiService.getBasicAuthClient().sendChromeDto(jsonObject, new Callback<String>() {
            @Override
            public void success(String s, retrofit.client.Response response) {
                Log.d(TAG, s);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                // do nothing
            }
        });
    }

    /**
     * Send to api a firefox dto as json
     */
    public static void postFirefoxJson(JsonObject jsonObject) {

        ApiService.getBasicAuthClient().sendFirefoxDto(jsonObject, new Callback<String>() {
            @Override
            public void success(String s, retrofit.client.Response response) {
                Log.d(TAG, s);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                // do nothing
            }
        });
    }

    /**
     * Send to api a dial dto as json
     */
    public static void postDialJson(JsonObject jsonObject) {

        ApiService.getBasicAuthClient().sendDialDto(jsonObject, new Callback<String>() {
            @Override
            public void success(String s, retrofit.client.Response response) {
                Log.d(TAG, s);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                // do nothing
            }
        });
    }

    /**
     * Send to api a gmail dto as json
     */
    public static void postGmailJson(JsonObject jsonObject) {

        ApiService.getBasicAuthClient().sendGmailDto(jsonObject, new Callback<String>() {
            @Override
            public void success(String s, retrofit.client.Response response) {
                Log.d(TAG, s);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                // do nothing
            }
        });
    }

    /**
     * Send to api a sms dto as json
     */
    public static void postSmsJson(JsonObject jsonObject) {

        ApiService.getBasicAuthClient().sendSmsDto(jsonObject, new Callback<String>() {
            @Override
            public void success(String s, retrofit.client.Response response) {
                Log.d(TAG, s);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                // do nothing
            }
        });
    }

    /**
     * Send to api a telegram dto as json
     */
    public static void postTelegramJson(JsonObject jsonObject) {

        ApiService.getBasicAuthClient().sendTelegramDto(jsonObject, new Callback<String>() {
            @Override
            public void success(String s, retrofit.client.Response response) {
                Log.d(TAG, s);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                // do nothing
            }
        });
    }

    /**
     * Send to api a whatsapp dto as json
     */
    public static void postWhatsappJson(JsonObject jsonObject) {

        ApiService.getBasicAuthClient().sendWhatsappDto(jsonObject, new Callback<String>() {
            @Override
            public void success(String s, retrofit.client.Response response) {
                Log.d(TAG, s);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                // do nothing
            }
        });
    }

    /**
     * Send to api a generalApp dto as json
     */
    public static void postGeneralAppJson(JsonObject jsonObject) {

        ApiService.getBasicAuthClient().sendGeneralAppDto(jsonObject, new Callback<String>() {
            @Override
            public void success(String s, retrofit.client.Response response) {
                Log.d(TAG, s);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                // do nothing
            }
        });
    }

    /**
     * Send to api a location dto as json
     */
    public static void postLocationJson(JsonObject jsonObject) {

        ApiService.getBasicAuthClient().sendLocationDto(jsonObject, new Callback<String>() {
            @Override
            public void success(String s, retrofit.client.Response response) {
                Log.d(TAG, s);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                // do nothing
            }
        });
    }

    /**
     * Send to api a notification dto as json
     */
    public static void postNotificationJson(JsonObject jsonObject) {

        ApiService.getBasicAuthClient().sendNotificationDto(jsonObject, new Callback<String>() {
            @Override
            public void success(String s, retrofit.client.Response response) {
                Log.d(TAG, s);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                // do nothing
            }
        });
    }
}
