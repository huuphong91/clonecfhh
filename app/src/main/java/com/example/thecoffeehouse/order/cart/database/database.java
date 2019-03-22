package com.example.thecoffeehouse.order.cart.database;

import android.content.Context;

import com.example.thecoffeehouse.order.cart.model.Cart;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Cart.class}, version = 1, exportSchema = false)
public abstract class database extends RoomDatabase {

    public abstract CartDAO cartDAO();

    private static database INSTANCE;

    public static database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder (context.getApplicationContext (),
                            database.class, "cart_db").allowMainThreadQueries ().build ();
                }
            }
        }
        return INSTANCE;
    }
}

