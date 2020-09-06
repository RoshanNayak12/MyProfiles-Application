package com.example.myprofiles;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProfileDatabaseDao {

    @Query("SELECT * FROM ProfileDetails")
    LiveData<List<ProfileDatabaseEntity>> getAllProfiles();

    @Query("SELECT COUNT(*) FROM ProfileDetails")
    Integer getProfileCount();

    @Insert
    void insertProfile(ProfileDatabaseEntity profileDetails);

    @Query("DELETE FROM ProfileDetails WHERE Id = :id")
    void deleteProfile(Integer id);

    @Query("DELETE FROM ProfileDetails")
    void deleteAllProfiles();
}
