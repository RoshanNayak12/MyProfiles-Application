package com.example.myprofiles;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {
    private ProfileDatabaseDao profileDatabaseDao;

    //private LiveData<List<ProfileDatabaseEntity>> allProfileDetails;

    Repository(Context context) {
        ProfileDatabase database = ProfileDatabase.getInstance(context);
        profileDatabaseDao = database.profileDatabaseDao();
    }

    public LiveData<List<ProfileDatabaseEntity>> getAllProfileDetails() { return profileDatabaseDao.getAllProfiles(); }
    public Integer getProfileCount() { return profileDatabaseDao.getProfileCount(); }

    public void insertProfile(final ProfileDatabaseEntity profileDetails) {
        ProfileDatabase.threadPool.execute((new Runnable() {
            @Override
            public void run() {
                profileDatabaseDao.insertProfile(profileDetails);
            }
        }));
    }

    public void deleteProfile(final Integer id) {
        ProfileDatabase.threadPool.execute(new Runnable() {
            @Override
            public void run() {
                profileDatabaseDao.deleteProfile(id);
            }
        });
    }

    public void deleteAllProfiles() {
        ProfileDatabase.threadPool.execute(new Runnable() {
            @Override
            public void run() {
                profileDatabaseDao.deleteAllProfiles();
            }
        });
    }

}
