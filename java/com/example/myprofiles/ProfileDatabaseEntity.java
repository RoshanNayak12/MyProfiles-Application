package com.example.myprofiles;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ProfileDetails")
public class ProfileDatabaseEntity {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    private Integer id;

    @NonNull
    @ColumnInfo(name = "Website Name")
    private String websiteName;

    @NonNull
    @ColumnInfo(name = "Image Link")
    private String imageUrl;

    @NonNull
    @ColumnInfo(name = "Profile Link")
    private String profileUrl;

    @NonNull
    public Integer getId() { return id; }
    @NonNull
    public String getWebsiteName() { return websiteName; }
    @NonNull
    public String getImageUrl() { return imageUrl; }
    @NonNull
    public String getProfileUrl() { return profileUrl; }

    public void setId(@NonNull Integer id) { this.id = id; }
    public void setWebsiteName(@NonNull String websiteName) { this.websiteName = websiteName; }
    public void setImageUrl(@NonNull String imageUrl) { this.imageUrl = imageUrl; }
    public void setProfileUrl(@NonNull String profileUrl) { this.profileUrl = profileUrl; }
}
