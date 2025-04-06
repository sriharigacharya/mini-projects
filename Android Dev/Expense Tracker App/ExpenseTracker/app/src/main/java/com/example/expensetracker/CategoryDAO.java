package com.example.expensetracker;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Category category);

    @Delete
    void delete(Category category);

    @Update
    void update(Category category);

    @Query("SELECT * FROM category_table")
    List<Category> getAllCategories();

    @Query("SELECT id FROM category_table where category_name=:catname")
    int getidfromname(String catname);

    @Query("SELECT category_name from category_table where id= :idd")
    String getcatnamefromid(int idd);

    @Query("SELECT COUNT(*) FROM category_table WHERE category_name = :catname")
    int doesCategoryExist(String catname);

}
