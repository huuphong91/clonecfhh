package com.example.thecoffeehouse.data.model.entity;

import android.content.Context;

import com.example.thecoffeehouse.news.News;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static com.facebook.accountkit.internal.AccountKitController.getApplicationContext;

@Database(entities = {ResponseNews.class}, version = 2, exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {
    public abstract NewsDataDao newsDao();

    private static volatile NewsDatabase INSTANCE;

    public static NewsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NewsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            NewsDatabase.class, "thecoffeehousenews")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback sRoomDatabaseCallback = new Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
