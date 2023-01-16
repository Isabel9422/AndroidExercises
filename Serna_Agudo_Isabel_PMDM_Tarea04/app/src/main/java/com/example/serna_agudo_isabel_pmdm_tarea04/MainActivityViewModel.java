package com.example.serna_agudo_isabel_pmdm_tarea04;

import android.database.Cursor;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.serna_agudo_isabel_pmdm_tarea04.model.Contact;
import com.example.serna_agudo_isabel_pmdm_tarea04.repository.ContactRepository;

public class MainActivityViewModel extends ViewModel {

    ContactRepository repository;

    public MainActivityViewModel(ContactRepository repository) {
        this.repository = repository;
    }

    public void getContacts(Cursor cursor, Cursor cPhoneNumber) {
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                cPhoneNumber.moveToNext();

                //obtener nombre de contacto
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                String number = cPhoneNumber.getString(cPhoneNumber.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String birthday = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Event.START_DATE));

                // Save the contacts into the database
                Contact contact = new Contact("SMS", "Message", number, birthday, name);
                insertContact(contact);
            }
        }
        cursor.close(); // Cerrar el cursor
    }


    private void insertContact(Contact contact) {
        repository.insert(contact);
    }

    static class Factory implements ViewModelProvider.Factory {
        ContactRepository repository;

        Factory(ContactRepository repository) {
            this.repository = repository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MainActivityViewModel(repository);
        }
    }
}
