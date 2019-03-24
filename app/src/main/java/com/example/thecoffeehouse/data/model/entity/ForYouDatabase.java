package com.example.thecoffeehouse.data.model.entity;

import android.content.Context;

import com.example.thecoffeehouse.news.News;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {ResponseForYou.class},version = 2,exportSchema = false)
public abstract class ForYouDatabase extends RoomDatabase {
    public abstract ForYouDao forYouDao();

    private static volatile ForYouDatabase INSTANCE;

    public static ForYouDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ForYouDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            ForYouDatabase.class, "thecoffeehouseforyounews")
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
            //Xóa all data
            //Completable.fromAction(() -> INSTANCE.storeDao().deleteAll()).subscribeOn(Schedulers.io()).subscribe();
        }

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
