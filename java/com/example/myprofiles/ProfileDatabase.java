package com.example.myprofiles;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = ProfileDatabaseEntity.class, version = 1)
abstract class ProfileDatabase extends RoomDatabase {
    public abstract ProfileDatabaseDao profileDatabaseDao();

    private static volatile ProfileDatabase INSTANCE = null;
    private static final Integer NUMBER_OF_THREADS = 4;
    public static ExecutorService threadPool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static final String DATABASE_NAME = "Profile Details";

    public static ProfileDatabase getInstance(Context context) {
        synchronized (ProfileDatabase.class) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                        context,
                        ProfileDatabase.class,
                        DATABASE_NAME
                ).build();
            }
            return INSTANCE;
        }
    }
}
