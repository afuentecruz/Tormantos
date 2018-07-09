package com.alberto.tfg.tormantos.handler;

import android.content.Context;
import android.view.accessibility.AccessibilityEvent;

import com.alberto.tfg.tormantos.analyzer.impl.browsing.ChromeAnalyzerImpl;
import com.alberto.tfg.tormantos.analyzer.impl.browsing.FirefoxAnalyzerImpl;
import com.alberto.tfg.tormantos.analyzer.impl.communication.DialAnalyzerImpl;
import com.alberto.tfg.tormantos.analyzer.impl.communication.GmailAnalyzerImpl;
import com.alberto.tfg.tormantos.analyzer.impl.communication.SmsAnalyzerImpl;
import com.alberto.tfg.tormantos.analyzer.impl.messaging.TelegramAnalyzerImpl;
import com.alberto.tfg.tormantos.analyzer.impl.messaging.WhatsappAnalyzerImpl;
import com.alberto.tfg.tormantos.analyzer.impl.system.GeneralAppAnalyzerImpl;
import com.alberto.tfg.tormantos.analyzer.impl.system.NotificationAnalyzerImpl;
import com.alberto.tfg.tormantos.sto.EventSto;
import com.alberto.tfg.tormantos.utils.Helper;
import com.alberto.tfg.tormantos.utils.Strings;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Handler component.
 * <p>
 * This class manages all the events captured by the accessibility service
 * Looks for her package name calls the corresponding analyzer implementation.
 */
public class EventHandler {

    private static final String TAG = "EventHandler";

    private Context context;

    /**
     * Date formatter
     */
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");

    /**
     * String that stores the current packages the app is processing in a certain instant
     */
    private String currentPackage;

    /**
     * General communication analyzers
     */
    private GmailAnalyzerImpl gmailAnalyzer;
    private SmsAnalyzerImpl smsAnalyzer;
    private DialAnalyzerImpl dialAnalyzer;

    /**
     * Instant messaging analyzers
     */
    private WhatsappAnalyzerImpl whatsappAnalyzer;
    private TelegramAnalyzerImpl telegramAnalyzer;

    /**
     * Web browsing analyzers
     */
    private FirefoxAnalyzerImpl firefoxAnalyzer;
    private ChromeAnalyzerImpl chromeAnalyzer;

    /**
     * System events analyzers
     */
    private NotificationAnalyzerImpl notificationAnalyzer;

    /**
     * No of our interest apps, just he time of usage
     */
    private GeneralAppAnalyzerImpl generalAppAnalyzer;


    public EventHandler(Context context) {
        this.context = context;
        currentPackage = "";

        // -- communication analyzers
        dialAnalyzer = new DialAnalyzerImpl(context);
        gmailAnalyzer = new GmailAnalyzerImpl(context);
        smsAnalyzer = new SmsAnalyzerImpl(context);

        // -- instant messaging analyzers
        whatsappAnalyzer = new WhatsappAnalyzerImpl(context);
        telegramAnalyzer = new TelegramAnalyzerImpl(context);

        // -- Browsing analyzers
        firefoxAnalyzer = new FirefoxAnalyzerImpl(context);
        chromeAnalyzer = new ChromeAnalyzerImpl(context);

        // -- System analyzers
        notificationAnalyzer = new NotificationAnalyzerImpl(context);
        generalAppAnalyzer = new GeneralAppAnalyzerImpl(context);
    }

    /**
     * Checks if the user switched between apps
     *
     * @param eventSto the eventSto.
     */
    private void checkAppSwitch(EventSto eventSto) {

        if (!eventSto.getPackageName().equals(this.currentPackage)) {
            switch (currentPackage) {
                case Strings.PACKAGE_WHATSAPP:
                    whatsappAnalyzer.checkRemainingData(eventSto);
                    break;
                default:
                    break;
            }
            if (!Strings.PACKAGE_TORMANTOS.equals(eventSto.getPackageName())) {
                generalAppAnalyzer.confirmEndOfUsage(eventSto);
            }
        }

    }

    public void handleEvent(AccessibilityEvent event, Date timestamp) {

        EventSto eventSto = new EventSto(event, timestamp,
                event.getPackageName().toString(),
                event.getClassName().toString());


        // -- Stores the current listening app package name if it isn't the keyboard
        if (!eventSto.getPackageName().equals(Strings.PACKAGE_KEYBOARD) &&
                !eventSto.getClassName().equals(Strings.CLASS_NOTIFICATION)) {
            checkAppSwitch(eventSto);
            currentPackage = eventSto.getPackageName();
        }

        Helper.log(eventSto);


        switch (eventSto.getPackageName()) {

            // Communication cases
            case Strings.PACKAGE_GMAIL:
                gmailAnalyzer.compute(eventSto);
                break;
            case Strings.PACKAGE_SMS:
                smsAnalyzer.compute(eventSto);
                break;
            case Strings.PACKAGE_DIALER:
                dialAnalyzer.compute(eventSto);
                break;

            // Instant messaging cases
            case Strings.PACKAGE_WHATSAPP:
                whatsappAnalyzer.compute(eventSto);
                break;
            case Strings.PACKAGE_TELEGRAM:
                telegramAnalyzer.compute(eventSto);
                break;

            // Web browsing cases
            case Strings.PACKAGE_FIREFOX:
                firefoxAnalyzer.compute(eventSto);
                break;
            case Strings.PACKAGE_CHROME:
                chromeAnalyzer.compute(eventSto);
                break;

            //Social cases
            case Strings.PACKAGE_FACEBOOK:
                break;

            // Generals cases
            case Strings.PACKAGE_KEYBOARD: // Keyboard displayed event
                handleKeyboardEvent(eventSto);
                break;
            case Strings.PACKAGE_SHORTCUT: // App shortcut launch event
                handleShortchut(eventSto);
                break;

            default:
                break;
        } // -- switch packages


        switch (eventSto.getClassName()) {
            case Strings.CLASS_NOTIFICATION: // Android notification
                notificationAnalyzer.compute(eventSto);
                getMessagingSender(eventSto);
                break;
            default:
                break;
        }

        if (!Strings.PACKAGE_TORMANTOS.equals(eventSto.getPackageName())) {
            generalAppAnalyzer.compute(eventSto); // unwatched app
        }
    }


    /**
     * Handles a keyboard event in order to
     * confirm the user input in the correspond app analyzer
     *
     * @param eventSto the EventSto.
     */
    private void handleKeyboardEvent(EventSto eventSto) {
        if (Helper.getEventText(eventSto.getEvent()).equals(Strings.KEY_KEYBOARD_SHOW_MSG)
                && !Helper.getEventText(eventSto.getEvent()).equals(Strings.KEY_KEYBOARD_ALTERNATIVES_REJECTED)
                && !Helper.getEventText(eventSto.getEvent()).equals(Strings.KEY_KEYBOARD_ALTERNATIVES_AVAILABLE)
                && !Helper.getEventText(eventSto.getEvent()).equals(Strings.KEY_KEYBOARD_SHOW_SYMBOLS)) {
            handleShowKeyboard(eventSto);
        } else if (Helper.getEventText(eventSto.getEvent()).equals(Strings.KEY_KEYBOARD_HIDE_MSG)) {
            handleHideKeyboard(eventSto);
        }
    }

    /**
     * Handles a show keyboard event
     *
     * @param eventSto the EventSto.
     */
    private void handleShowKeyboard(EventSto eventSto) {
        switch (this.currentPackage) {
            case Strings.PACKAGE_WHATSAPP:
                whatsappAnalyzer.confirmKeyboardInput(eventSto.getCaptureInstant());
                break;
            case Strings.PACKAGE_TELEGRAM:
                telegramAnalyzer.confirmKeyboardInput(eventSto.getCaptureInstant());
            default:
                break;
        }
    }

    /**
     * Handles a hide keyboard event
     *
     * @param eventSto the EventSto.
     */
    private void handleHideKeyboard(EventSto eventSto) {
        switch (this.currentPackage) {
            case Strings.PACKAGE_FIREFOX:
                firefoxAnalyzer.confirmKeyboardInput(eventSto.getCaptureInstant());
            default:
            case Strings.PACKAGE_CHROME:
                chromeAnalyzer.confirmKeyboardInput(eventSto.getCaptureInstant());
                break;
        }
    }

    /**
     * Handles the launch of the shortcut of an application
     *
     * @param eventSto the EventSto.
     */
    private void handleShortchut(EventSto eventSto) {

        switch (Helper.getEventText(eventSto.getEvent())) {
            case Strings.KEY_WHATSAPP:
                whatsappAnalyzer.compute(eventSto);
                break;
            default:
                break;
        }
    }

//    /**
//     * Checks if the app changed suddenly (for example, navigated to home from any other activity)
//     * and try to store any previous content of the corresponding analyzer.
//     *
//     * @param eventSto the EventSto.
//     */
//    private void commitAnalyzerData(EventSto eventSto) {
//        if (!eventSto.getPackageName().equals(Strings.PACKAGE_KEYBOARD)
//                && !eventSto.getPackageName().equals(this.currentPackage)) {
//            switch (this.currentPackage) {
//                case Strings.PACKAGE_WHATSAPP:
//                    whatsappAnalyzer.checkRemainingData(eventSto);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }

    /**
     * Checks the notification content in order to extract any
     * information about origin of the notification.
     *
     * @param eventSto, The EventSto.
     */
    private void getMessagingSender(EventSto eventSto) {
        switch (eventSto.getPackageName()) {
            case Strings.PACKAGE_WHATSAPP:
                whatsappAnalyzer.setCurrentInterlocutor(
                        notificationAnalyzer.getNotificationContent(eventSto));
                break;
            default:
                break;
        }
    }
}
