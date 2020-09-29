package com.example.omstugradebook.service;

import android.content.Context;
import android.util.Log;

import com.example.omstugradebook.database.daoimpl.UserDaoImpl;
import com.example.omstugradebook.model.grade.GradeBook;
import com.example.omstugradebook.model.grade.User;
import com.example.omstugradebook.parser.impl.GradeParserImpl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class AuthService {
    private final static String URL_AUTH = "https://omgtu.ru/ecab/index.php?login=yes";
    private final static String URL_STUDENT_1 = "https://omgtu.ru/ecab/up.php?student=1";
    static final String URL = "https://up.omgtu.ru/index.php?r=student/index";
    static final String STUD_SES_ID = "STUDSESSID";
    private static final String TAG = "Auth Logs";

    public AuthService() {
    }

    private String getStudSessionString(String redirectURL) throws IOException {
        URL url = new URL(redirectURL);
        HttpURLConnection redirectConnection = (HttpURLConnection) url.openConnection();
        redirectConnection.setRequestMethod("GET");
        redirectConnection.setInstanceFollowRedirects(false);
        redirectConnection.connect();
        List<String> studSesId = redirectConnection.getHeaderFields().get("Set-Cookie");
        String studSes = studSesId.get(1);
        return studSes.split(";")[0];
    }

    public String getStudSessId(String cookie) {
        try {
            URL url = new URL(URL_STUDENT_1);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Cookie", cookie);
            connection.setUseCaches(false);
            connection.connect();
            String redirectURL = connection.getHeaderField("Location");
            String id = getStudSessionString(redirectURL);
            return id.substring("STUDSESSID=".length());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getQuery(List<AbstractMap.SimpleEntry<String, String>> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (AbstractMap.SimpleEntry<String, String> pair : params) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }
            result.append(URLEncoder.encode(pair.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    public String getAuth(String login, String password) {
        try {
            URL url = new URL(URL_AUTH);
            HttpURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            List<AbstractMap.SimpleEntry<String, String>> params = new ArrayList<>();
            params.add(new AbstractMap.SimpleEntry<>("AUTH_FORM", "Y"));
            params.add(new AbstractMap.SimpleEntry<>("TYPE", "AUTH"));
            params.add(new AbstractMap.SimpleEntry<>("USER_LOGIN", login));
            params.add(new AbstractMap.SimpleEntry<>("USER_PASSWORD", password));
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
            writer.write(getQuery(params));
            writer.flush();
            writer.close();
            os.close();
            connection.connect();
            List<String> headerField = connection.getHeaderFields().get("Set-Cookie");
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : headerField) {
                if (stringBuilder.length() != 0) {
                    stringBuilder.append("; ");
                }
                stringBuilder.append(s.split(";")[0]);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    public GradeBook getGradeBook(Context context) {
        Document doc = null;
        UserDaoImpl userTable = new UserDaoImpl();
        try {
            User activeUser = userTable.getActiveUser(context);
            String token = activeUser.getToken();
            doc = Jsoup.connect(URL).cookie(STUD_SES_ID, token).get();
            Log.d(TAG, "сделан запрос");
            if (!doc.baseUri().equals(URL)) {
                Log.d(TAG, "токен не активный");
                String cookie = getAuth(activeUser.getLogin(), activeUser.getPassword());
                String studSesId = getStudSessId(cookie);
                activeUser.setToken(studSesId);
                userTable.update(activeUser, context);
                doc = Jsoup.connect(URL).cookie(STUD_SES_ID, token).get();
                if (doc == null || !doc.baseUri().equals(URL)) {
                    return null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (doc == null) {
            return null;
        }
        return new GradeParserImpl().getGradeBook(doc);
    }

    public GradeBook getGradeBook(String studSesId) {
        Document doc = null;
        try {
            doc = Jsoup.connect(URL).cookie(STUD_SES_ID, studSesId).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "сделан запрос");
        if (doc == null || !doc.baseUri().equals(URL)) {
            return null;
        }
        return new GradeParserImpl().getGradeBook(doc);
    }
}
