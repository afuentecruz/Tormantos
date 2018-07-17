package com.alberto.tfg.tormantos.manager.network;

import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface ApiInterface {

    /**
     * Api interface with the endpoints to call the api
     */

    @POST("/api/browsing/storeChrome")
    void sendChromeDto(@Body JsonObject jsonObject, Callback<String> responseCallback);

    @POST("/api/browsing/storeFirefox")
    void sendFirefoxDto(@Body JsonObject jsonObject, Callback<String> responseCallback);

    @POST("/api/communication/storeDial")
    void sendDialDto(@Body JsonObject jsonObject, Callback<String> responseCallback);

    @POST("/api/communication/storeGmail")
    void sendGmailDto(@Body JsonObject jsonObject, Callback<String> responseCallback);

    @POST("/api/communication/storeSms")
    void sendSmsDto(@Body JsonObject jsonObject, Callback<String> responseCallback);

    @POST("/api/messaging/storeTelegram")
    void sendTelegramDto(@Body JsonObject jsonObject, Callback<String> responseCallback);

    @POST("/api/messaging/storeWhatsapp")
    void sendWhatsappDto(@Body JsonObject jsonObject, Callback<String> responseCallback);

    @POST("/api/system/storeGeneralApp")
    void sendGeneralAppDto(@Body JsonObject jsonObject, Callback<String> responseCallback);

    @POST("/api/system/storeLocation")
    void sendLocationDto(@Body JsonObject jsonObject, Callback<String> responseCallback);

    @POST("/api/system/storeNotification")
    void sendNotificationDto(@Body JsonObject jsonObject, Callback<String> responseCallback);
}
