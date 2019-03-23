package com.example.thecoffeehouse.order.cart.database;

import com.example.thecoffeehouse.order.cart.model.Cart;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CartDAO {

    @Query("SELECT * from cart")
    LiveData<List<Cart>> getAllCarts();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Cart device);

    @Delete
    int deleteCart(Cart cart);

    @Query("DELETE from cart")
    int delall();

    @Update
    int update(Cart cart);
}
