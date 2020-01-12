package com.dht.interest.repository;

import android.app.Application;

import com.dht.database.BaseDatabase;
import com.dht.database.bean.app.DownloadBean;
import com.dht.database.dao.DownloadDao;


/**
 * created by dht on 2019/3/11 18:17
 */
public class DownloadRepository {

    private DownloadDao downloadDao;

    public DownloadRepository(Application application) {

        downloadDao = BaseDatabase.Companion.getInstance(application.getApplicationContext()).getDownloadDao();
    }

    public void addDownloadMusic(DownloadBean music) {
        downloadDao.addDownloadEntity(music);
    }
}
