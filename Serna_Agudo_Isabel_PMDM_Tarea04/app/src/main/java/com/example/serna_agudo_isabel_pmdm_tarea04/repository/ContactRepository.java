package com.example.serna_agudo_isabel_pmdm_tarea04.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.serna_agudo_isabel_pmdm_tarea04.model.Contact;
import com.example.serna_agudo_isabel_pmdm_tarea04.room.AppDatabase;
import com.example.serna_agudo_isabel_pmdm_tarea04.room.ContactDao;

import java.util.List;

public class ContactRepository {

    private final ContactDao contactDao;
    private final LiveData<List<Contact>> contactList;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public ContactRepository(Context appContext) {
        contactDao = AppDatabase.getDatabase(appContext).contactDao();
        contactList = contactDao.getContactsSortedAsc();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Contact>> getAllContacts() {
        return contactList;
    }

    public void updateContact(Contact contact, int id) {
        contactDao.update(
                contact.getNotifyType(),
                contact.getMsg(),
                contact.getPhoneNumber(),
                contact.getBirthDate(),
                contact.getName(),
                id
        );
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Contact contact) {
        AppDatabase.databaseWriteExecutor.execute(() -> insertIfNotExist(contact));
    }

    private void insertIfNotExist(Contact contact) {
        if (!contactDao.exists(contact.getPhoneNumber())) {
            contactDao.insert(contact);
        } else {
            contactDao.update(
                    contact.getNotifyType(),
                    contact.getMsg(),
                    contact.getPhoneNumber(),
                    contact.getBirthDate(),
                    contact.getName(),
                    contact.getId()
            );
        }
    }
}
