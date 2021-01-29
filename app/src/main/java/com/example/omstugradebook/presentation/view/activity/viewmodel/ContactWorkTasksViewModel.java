package com.example.omstugradebook.presentation.view.activity.viewmodel;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omstugradebook.App;
import com.example.omstugradebook.data.dao.UserDao;
import com.example.omstugradebook.data.model.contactwork.ContactWorksTask;
import com.example.omstugradebook.data.parser.ContactWorkParser;
import com.example.omstugradebook.domain.connector.Connector;
import com.example.omstugradebook.presentation.view.activity.model.ContactWorkTasksModel;

import java.io.File;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class ContactWorkTasksViewModel extends ViewModel {
    static final String STUD_SES_ID = "STUDSESSID";

    private static final String UP_OMGTU = "http://up.omgtu.ru/";

    private final MutableLiveData<ContactWorkTasksModel> contactWorkModelLiveData;

    private final MutableLiveData<String> fileStatusLiveData;

    @Inject
    UserDao userDao;


    public ContactWorkTasksViewModel() {
        App.getComponent().injectContactWorkTaskViewModel(this);

        contactWorkModelLiveData = new MutableLiveData<>();

        fileStatusLiveData = new MutableLiveData<>();
    }

    public LiveData<ContactWorkTasksModel> getContactWorkModelLiveData() {
        return contactWorkModelLiveData;
    }

    public LiveData<String> getFileStatusLiveData() {
        return fileStatusLiveData;
    }

    public void startDownloading(ContactWorksTask task, Context context) {
        CompletableFuture
                .supplyAsync(() -> task.getFile().trim().replaceAll(" ", "_"))
                .thenApply(fileName -> downloadFile(task.getLink(), fileName, context))
                .thenAccept(fileStatusLiveData::postValue);
    }

    public void sendRequestToGetContactWorkTasks(String path) {
        CompletableFuture
                .supplyAsync(() -> new Connector().get(path))
                .thenApply(document -> new ContactWorkParser().getTasks(document))
                .thenAccept(tasks -> {
                    if (tasks != null) {
                        contactWorkModelLiveData.postValue(new ContactWorkTasksModel(tasks));
                    }
                });
    }

    private String downloadFile(String path, String fileName, Context context) {
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

            String cookie = STUD_SES_ID + "=" + userDao.get().getToken() + "; Path=/; Domain=.up.omgtu.ru;";

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

    private boolean checkExistsFile(String fileName) {
        String filePath = Environment.getExternalStorageDirectory()
                + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + fileName;

        File file = new File(filePath);

        return file.exists();
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

    private String getExtensionsByFileName(String fileName) {
        String[] strings = fileName.split("\\.");

        return strings[strings.length - 1];
    }

}
