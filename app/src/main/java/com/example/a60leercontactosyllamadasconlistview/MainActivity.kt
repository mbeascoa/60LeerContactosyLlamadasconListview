package com.example.a60leercontactosyllamadasconlistview

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.provider.ContactsContract

import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    lateinit var lblmensaje: TextView
    lateinit var lsv1: ListView
    var arrayString= arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lblmensaje = infoTextView
        lsv1 = listaView


        CheckPermisions()

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            leerContactos()
            val adapt = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayString)
            lsv1.adapter = adapt

            lsv1.setOnItemClickListener {

                    parent, view, position, id ->

                lblmensaje.text = arrayString[position]

            }
        }

    }



    private fun CheckPermisions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS ) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale( this, Manifest.permission.READ_CONTACTS ) ) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                val array = arrayOf(Manifest.permission.READ_CONTACTS)
                ActivityCompat.requestPermissions(this, array, 1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }
    fun leerContactos() {
        val sColumnas = arrayOf(
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.TIMES_CONTACTED
        )
        val cursorContactos: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,//ruta de la base de datos
            sColumnas,//campos a comprobar
            null,//condicionales
            null,//filtro
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )
        if (cursorContactos != null) {
            while (cursorContactos.moveToNext()) {
                val nombre: String =
                    cursorContactos.getString(cursorContactos.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val numero: String =
                    cursorContactos.getString(cursorContactos.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val aaa: String =
                    cursorContactos.getString(cursorContactos.getColumnIndex(ContactsContract.Contacts.TIMES_CONTACTED))

                arrayString.add("$nombre $numero $aaa")
            }
        }
        cursorContactos?.close()

    }
}