package com.liuyihui.networkcontrol.http;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

public class XMLParseUtil {
    public static void parseDemoCode() {
        try {
            String getSTUrl = "<html>fjdksf</html>";
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(getSTUrl));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    System.out.println("Start document");
                } else if (eventType == XmlPullParser.START_TAG) {
                    System.out.println("Start tag " + xpp.getName());
                } else if (eventType == XmlPullParser.END_TAG) {
                    System.out.println("End tag " + xpp.getName());
                } else if (eventType == XmlPullParser.TEXT) {
                    System.out.println("Text " + xpp.getText());
                }
                eventType = xpp.next();
            }
            System.out.println("End document");

        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
    }
}
