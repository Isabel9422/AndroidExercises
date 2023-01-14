package com.example.serna_agudo_isabel_pmdm_tarea04;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Contact contact);

    @Query("DELETE FROM miscumples")
    void deleteAll();

    @Query("SELECT * FROM miscumples ORDER BY id ASC")
    LiveData<List<Contact>> getAlphabetizedContacts();

}
