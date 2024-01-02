package com.edge.core.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class JsonReader {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            JSONObject json = readJsonFromBufferedReader(rd);
            return json;
        } finally {
            is.close();
        }
    }

    public static JSONObject readJsonFromBufferedReader(BufferedReader rd) throws IOException {
        String jsonText = readAll(rd);
        return new JSONObject(jsonText);
    }

    public static void main(String[] args) throws IOException, JSONException {
        JSONObject json = readJsonFromUrl("https://graph.com/19292868552");
        System.out.println(json.toString());
        System.out.println(json.get("id"));
    }
}
