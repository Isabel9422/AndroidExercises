package com.example.serna_agudo_isabel_pmdm_tarea04;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serna_agudo_isabel_pmdm_tarea04.model.Contact;
import com.example.serna_agudo_isabel_pmdm_tarea04.repository.ContactRepository;

public class PerfilContactos extends AppCompatActivity {

    EditText phoneNumber;
    EditText name;
    EditText birthdate;
    EditText msg;
    Button button;
    ContactRepository repository;

    Contact c = MainActivity.contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_contactos);
        button = findViewById(R.id.button);
        init();

        button.setOnClickListener(v -> repository.updateContact(c, c.getId()));

    }

    @Override
    protected void onStart() {
        super.onStart();
        setText();

    }

    void init() {
        phoneNumber = findViewById(R.id.phoneNumber);
        name = findViewById(R.id.name);
        birthdate = findViewById(R.id.birthdate);
        msg = findViewById(R.id.msg);

    }

    void setText() {
        phoneNumber.setText(c.getPhoneNumber());
        name.setText(c.getName());
        birthdate.setText(c.getBirthDate());
        msg.setText(c.getMsg());
    }


}