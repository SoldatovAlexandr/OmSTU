package com.example.omstugradebook.domain.connector;

import com.example.omstugradebook.App;
import com.example.omstugradebook.data.dao.UserDao;
import com.example.omstugradebook.data.model.grade.User;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import javax.inject.Inject;

public class Connector {
    static final String STUD_SES_ID = "STUDSESSID";

    private static final String UP_OMGTU = "http://up.omgtu.ru/";

    @Inject
    UserDao userDao;

    public Connector() {
        App.getComponent().injectConnector(this);
    }

    public Document get(String path) {
        Document doc = null;

        User activeUser = userDao.get();

        try {

            if (activeUser == null) {
                return null;
            }

            String token = activeUser.getToken();

            String url = UP_OMGTU + path;

            doc = Jsoup.connect(url).cookie(STUD_SES_ID, token).get();

            if (!doc.baseUri().equals(url)) {
                AuthService authService = new AuthService();

                String cookie = authService.getAuth(activeUser.getLogin(), activeUser.getPassword());

                String studSesId = authService.getStudSessId(cookie);

                activeUser.setToken(studSesId);

                userDao.update(activeUser);

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
}
