package com.example.serna_agudo_isabel_pmdm_tarea04;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.serna_agudo_isabel_pmdm_tarea04.databinding.ActivityMainBinding;
import com.example.serna_agudo_isabel_pmdm_tarea04.model.Contact;
import com.example.serna_agudo_isabel_pmdm_tarea04.repository.ContactRepository;

public class MainActivity extends AppCompatActivity implements BirthdayHelperRecyclerViewAdapter.AdapterClickListener {

    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;
    static Contact contact;

    Uri uri = ContactsContract.Data.CONTENT_URI;

    String[] projection = new String[]{
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Event.START_DATE,
    };

    String where =
            ContactsContract.Data.MIMETYPE + "= ? AND " +
                    ContactsContract.CommonDataKinds.Event.TYPE + "=" +
                    ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY;

    String[] selectionArgs = new String[]{
            ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
    };
    String sortOrder = null;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your app.
                    retrieveContacts();
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // feature requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                    Toast.makeText(this, "No se puede acceder a los contactos", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(
                this,
                new MainActivityViewModel.Factory(new ContactRepository(getApplicationContext()))
        ).get(MainActivityViewModel.class);

        setObservers();

        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestContactPermission();
    }

    private void requestContactPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            retrieveContacts();
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected, and what
            // features are disabled if it's declined. In this UI, include a
            // "cancel" or "no thanks" button that lets the user continue
            // using your app without granting the permission.
            showContactDialog();
        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS);
        }
    }

    private void showContactDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permiso de Contactos")
                .setMessage("Se requiere que aceptes el permiso para acceder a tus contactos")
                .setPositiveButton(android.R.string.ok, (dialog, id) -> {
                    // Request contact permission again
                    startActivity(new Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", this.getPackageName(), null)
                            )
                    );
                })
                // Create the AlertDialog object and show it
                .create()
                .show();
    }

    private void setObservers() {
        viewModel.repository.getAllContacts().observe(this, contacts -> {
            // Creamos el Adapter y lo cargamos en el recycler view.
            Log.d("Contacts", "Contactos: " + contacts.size());

            BirthdayHelperRecyclerViewAdapter adapter = new BirthdayHelperRecyclerViewAdapter(this, contacts, this);
            binding.mainActivityContactsRecyclerView.setAdapter(adapter);
        });
    }

    void retrieveContacts() {

        String[] proj = new String[]{
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Event.START_DATE,
        };

        Cursor c = getContentResolver().query(uri, projection, where, selectionArgs, sortOrder);
        Cursor cPhoneNumber = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, proj, null, null, null);
        // Load contacts into DB
        viewModel.getContacts(c, cPhoneNumber);
    }

    @Override
    public void onClickListener(Contact c) {
        Intent i = new Intent(MainActivity.this, PerfilContactos.class);
        contact = c;
        startActivity(i);
    }

//    public void enviarSMS(String telefono, String mensaje) {
//        try {
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage(telefono, null, mensaje, null, null);
//            Toast.makeText(getApplicationContext(), "SMS enviado.", Toast.LENGTH_LONG).show();
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(),
//                    "SMS no enviado, por favor, inténtalo otra vez.",
//                    Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }
//    }
}