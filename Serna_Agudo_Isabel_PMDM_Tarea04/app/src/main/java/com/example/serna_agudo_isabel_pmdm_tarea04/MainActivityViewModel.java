package com.example.serna_agudo_isabel_pmdm_tarea04;

import android.database.Cursor;
import android.provider.ContactsContract;

import androidx.lifecycle.ViewModel;

import com.example.serna_agudo_isabel_pmdm_tarea04.model.Contact;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivityViewModel extends ViewModel {

    ArrayList<String> contacts = new ArrayList<>();


    public void getContacts(Cursor cursor) {
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                //obtener id de contacto
                String id = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                //obtener nombre de contacto
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                //si tiene teléfono, lo agregamos a la lista de contactos
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    contacts.add(name);
                }

                // Save the contacts into the database
                Contact contact = new Contact("SMS", "Message", 11111111, "12-11-1999", name);
            }
        }
        cursor.close(); //cerrar el cursor
    }

    // Cada vez que un dato se guarda en la base de datos, el LiveData tendría que notificar a la interfaz que la información ha cambiado.
}


