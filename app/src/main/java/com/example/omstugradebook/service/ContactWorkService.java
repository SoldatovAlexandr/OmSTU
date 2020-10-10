package com.example.omstugradebook.service;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.omstugradebook.database.DataBaseManager;
import com.example.omstugradebook.database.daoimpl.UserDaoImpl;
import com.example.omstugradebook.model.contactwork.ContactWork;
import com.example.omstugradebook.model.contactwork.ContactWorksTask;
import com.example.omstugradebook.model.grade.User;
import com.example.omstugradebook.parser.ContactWorkParserImpl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ContactWorkService {
    static final String REMOTE_URL = "index.php?r=remote/read";
    static final String STUD_SES_ID = "STUDSESSID";
    private static final String TAG = "Auth Logs";
    private static final String UP_OMGTU = "http://up.omgtu.ru/";

    public List<ContactWork> getContactWork(long userId) {
        Document doc = tryToGetConnection(REMOTE_URL);
        if (doc == null) {
            return null;
        }
        return new ContactWorkParserImpl().getContactWorks(doc, userId);
    }

    public List<ContactWorksTask> getTaskContactWork(String path) {
        Document doc = tryToGetConnection(path);
        if (doc == null) {
            return null;
        }
        return new ContactWorkParserImpl().getTasks(doc);
    }

    private Document tryToGetConnection(String path) {
        Document doc = null;
        UserDaoImpl userTable = new UserDaoImpl();
        User activeUser = userTable.getActiveUser();
        try {
            if (activeUser == null) {
                return null;
            }
            String token = activeUser.getToken();
            String url = UP_OMGTU + path;
            doc = Jsoup.connect(url).cookie(STUD_SES_ID, token).get();
            Log.d(TAG, "сделан запрос");
            if (!doc.baseUri().equals(url)) {
                AuthService authService = new AuthService();
                Log.d(TAG, "токен не активный");
                String cookie = authService.getAuth(activeUser.getLogin(), activeUser.getPassword());
                String studSesId = authService.getStudSessId(cookie);
                activeUser.setToken(studSesId);
                userTable.update(activeUser);
                doc = Jsoup.connect(url).cookie(STUD_SES_ID, token).get();
                if (doc == null || !doc.baseUri().equals(url)) {
                    return null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public String downloadFile(String path, String fileName, Context context) {
        if (checkExistsFile(fileName)) {
            //тут потом можно будет и открыть его
            return "Файл уже был загружен...";
        } else {
            String url = UP_OMGTU + path.trim();
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                    DownloadManager.Request.NETWORK_MOBILE);
            request.setTitle(fileName);
            request.setDescription("Downloading file");
            request.allowScanningByMediaScanner();
            String cookie = STUD_SES_ID + "=" + DataBaseManager.getUserDao().getActiveUser().getToken() + "; Path=/; Domain=.up.omgtu.ru;";
            request.addRequestHeader("Cookie", cookie);
            request.setMimeType(gitMimeType(fileName));
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
            if (checkExistsFile(fileName)) {
                return "Неудалось загрузить файл...";
            } else {
                return fileName + " успешно загружен!";
            }
        }
    }

    private String gitMimeType(String fileName) {
        switch (getExtensionsByFileName(fileName)) {
            case "pdf":
                return "application/pdf";
            case "docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "doc":
                return "application/msword";
            case "ppt":
                return "application/vnd.ms-powerpoint";
            case "pptx":
                return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            default:
                return "text/plain";
        }
    }

    private boolean checkExistsFile(String fileName) {
        String filePath = Environment.getExternalStorageDirectory()
                + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + fileName;
        File file = new File(filePath);
        return file.exists();
    }

    private String getExtensionsByFileName(String fileName) {
        String[] strings = fileName.split("\\.");
        return strings[strings.length - 1];
    }
}
