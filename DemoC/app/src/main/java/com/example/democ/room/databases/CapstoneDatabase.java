package com.example.democ.room.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.democ.room.daos.UserDao;
import com.example.democ.room.entities.User;

import static com.example.democ.room.databases.CapstoneDatabase.DATABASE_VERSION;

@Database(entities = {User.class}, exportSchema = false, version = DATABASE_VERSION)
public abstract class CapstoneDatabase extends RoomDatabase {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "capstone_database_3";
    private static CapstoneDatabase INSTANCE;
    public abstract UserDao userDao();

    public static CapstoneDatabase getDatabase(Context context) {
        if(INSTANCE == null) {
            synchronized (CapstoneDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CapstoneDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }
}
