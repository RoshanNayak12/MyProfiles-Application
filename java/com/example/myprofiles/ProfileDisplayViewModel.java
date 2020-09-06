package com.example.myprofiles;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class ProfileDisplayViewModel extends AndroidViewModel {

    public LiveData<List<ProfileDatabaseEntity>> allProfileDetails;
    private Repository repository;

    public ProfileDisplayViewModel(@NonNull final Application application) {
        super(application);
        repository = new Repository(application);
        allProfileDetails = repository.getAllProfileDetails();
    }

    public void setAllProfileDetails(LiveData<List<ProfileDatabaseEntity>> allProfileDetails) { this.allProfileDetails = allProfileDetails; }
}
