package com.example.serna_agudo_isabel_pmdm_tarea04.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.serna_agudo_isabel_pmdm_tarea04.model.Contact;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Contact contact);

    @Query("DELETE FROM miscumples")
    void deleteAll();


    @Query("SELECT * FROM miscumples ORDER BY contact_id ASC")
    LiveData<List<Contact>> getContactsSortedAsc();

    @Query("UPDATE miscumples SET " +
            "contact_notification_type = :notifyType, " +
            "contact_message = :msg, " +
            "contact_phone_number = :phoneNumber," +
            "contact_birthdate = :birthday, " +
            "contact_name = :name " +
            "WHERE " +
            "contact_id = :id"
    )
    void update(String notifyType, String msg, String phoneNumber, String birthday, String name, int id);

    @Query("SELECT EXISTS(SELECT * FROM miscumples WHERE contact_phone_number = :phoneNumber)")
    Boolean exists(String phoneNumber);
}
