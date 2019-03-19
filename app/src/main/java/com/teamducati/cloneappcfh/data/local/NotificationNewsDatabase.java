package com.teamducati.cloneappcfh.data.local;

import android.content.Context;
import android.os.AsyncTask;

import com.teamducati.cloneappcfh.data.local.dao.NotificationDao;
import com.teamducati.cloneappcfh.entity.Notification;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Notification.class}, version = 3, exportSchema = false)
public abstract class NotificationNewsDatabase extends RoomDatabase {

    private static NotificationNewsDatabase INSTANCE;
    private static ArrayList<Notification> arrayList = new ArrayList<>();
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    public static NotificationNewsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NotificationNewsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NotificationNewsDatabase.class, "notification_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }
        return INSTANCE;
    }


    public abstract NotificationDao notificationDao();

    private static class deleteAllDevicessAsyncTask extends AsyncTask<Void, Void, Void> {
        private NotificationDao mAsyncTaskDao;

        deleteAllDevicessAsyncTask(NotificationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    /**
     * Delete a single Devices from the database.
     */
    private static class deleteDevicesAsyncTask extends AsyncTask<Notification, Void, Void> {
        private NotificationDao mAsyncTaskDao;

        deleteDevicesAsyncTask(NotificationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Notification... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
