package com.example.serna_agudo_isabel_pmdm_tarea04.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.serna_agudo_isabel_pmdm_tarea04.model.Contact;
import com.example.serna_agudo_isabel_pmdm_tarea04.room.ContactDao;
import com.example.serna_agudo_isabel_pmdm_tarea04.room.ContactRoomDatabase;

import java.util.List;

public class ContactRepository {

    private ContactDao mContactDao;
    private LiveData<List<Contact>> mAllContacts;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    ContactRepository(Application application) {
        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(application);
        mContactDao = db.contactDao();
        mAllContacts = mContactDao.getAlphabetizedContacts();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Contact>> getAllContacts() {
        return mAllContacts;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Contact contact) {
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> {
            mContactDao.insert(contact);
        });
    }
}
