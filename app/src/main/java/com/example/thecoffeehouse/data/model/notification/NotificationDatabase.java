package com.example.thecoffeehouse.data.model.notification;

import android.content.Context;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.StoreDatabase;
import com.example.thecoffeehouse.data.model.store.Store;
import com.example.thecoffeehouse.data.model.store.StoreDao;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Notification.class}, version = 3, exportSchema = false)
public abstract class NotificationDatabase extends RoomDatabase {

    public abstract NotificationDAO notificationDAO();

    private static NotificationDatabase INSTANCE;


    public static NotificationDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (StoreDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NotificationDatabase.class, "notification_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

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
