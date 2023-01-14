package com.example.serna_agudo_isabel_pmdm_tarea04;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.serna_agudo_isabel_pmdm_tarea04.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;

    private ActivityResultLauncher<String> requestPermissionLauncher =
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

    private void retrieveContacts() {
        LoaderManager.getInstance(this).initLoader(0, null, this);
    }

    @SuppressLint("InlinedApi")
    private static final String[] PROJECTION = {
            ContactsContract.Contacts._ID,
//            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.HAS_PHONE_NUMBER,
            ContactsContract.Contacts.PHOTO_ID,
    };

    String WHERE =
            ContactsContract.Data.MIMETYPE + "= ? AND " +
                    ContactsContract.CommonDataKinds.Event.TYPE + "=" +
                    ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY;

    String[] SELECTION_ARGS = new String[]{
            ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        setContacts();

        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestContactPermission();

//        isConnected();
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

    private void setContacts() {

        // Cargarlos en la BBDD de room

        // Vamos a recuperar los datos de la BBDD

        // Vamos a mostar los datos de la BBDD en la pantalla

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Log.i("Cursor", "onCreateLoader");

        CursorLoader cl = new CursorLoader(
                this,
                ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                null,
                null,
                null
        );

        // Load contacts into DB
        viewModel.getContacts(cl.loadInBackground());

        return cl;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.i("Cursor", "onLoadFinished");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Log.i("Cursor", "onLoaderReset");

    }


//    public boolean isConnected() {
//
//        ConnectivityManager connMgr = (ConnectivityManager)
//                getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
//            Log.d("MainActivity", "Funciona la conexion");
//            return true;
//        } else {
//            Log.d("MainActivity", "Ha habido un problema");
//            return false;
//        }
//
//    }


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