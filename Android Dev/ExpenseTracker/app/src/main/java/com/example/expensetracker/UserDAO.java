package com.example.expensetracker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user_table")
    List<User> getAllUsers();
}
