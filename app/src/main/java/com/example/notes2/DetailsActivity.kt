package com.example.notes2

import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_details.*
import android.util.Log

class DetailsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val bdHelper = DataBaseHelper(applicationContext)
        val db = bdHelper.writableDatabase

        //sprawdzenie czy intent z cardViewAdapter ma extras

        if (intent.hasExtra(TableInfo.TABLE_COLUMN_TITLE))
            actv_det_title.setText(intent.getStringExtra(TableInfo.TABLE_COLUMN_TITLE))
        if (intent.hasExtra(TableInfo.TABLE_COLUMN_MESSAGE))
            activ_det_note.setText(intent.getStringExtra(TableInfo.TABLE_COLUMN_MESSAGE))

    }

//okodowanie menu Zapisz (menu_activ_det.xml) activity_details
// musi być poza metodą onCreate()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activ_det, menu)
        return super.onCreateOptionsMenu(menu)
    }
    //zapisywanie/edycja  i usówanie notatki

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase
        when (item.itemId){
            R.id.save_menu_activ_det -> {

                //przechwytujemy to co będzie wpisane w pola edycyjne
                val title = actv_det_title.text.toString()
                val note = activ_det_note.text.toString()
                val value = ContentValues()
                value.put("title", title)
                value.put("message", note)

                if (intent.hasExtra("ID")) {
                    db.update(
                        TableInfo.TABLE_NAME, value, BaseColumns._ID + "=?",
                        arrayOf(intent.getStringExtra("ID"))
                    )
                    Toast.makeText(this, "Notatka została zmieniona", Toast.LENGTH_SHORT).show()

                } else {
                    if (!title.isNullOrEmpty() || !note.isNullOrEmpty()) {
                        Toast.makeText(applicationContext, "wcisniety", Toast.LENGTH_SHORT).show()
                        db.insertOrThrow(TableInfo.TABLE_NAME, null, value)
                    } else {
                        Toast.makeText(this, "Nie zapisane pusta notatka", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                db.close()
                onBackPressed()
            }
            R.id.delete_menu_activ_det -> {
                Toast.makeText(this, "Notatka usunięta!", Toast.LENGTH_LONG).show()
                // usówanie z bazy
                db.delete(TableInfo.TABLE_NAME, BaseColumns._ID + "=?",
                    arrayOf(intent.getStringExtra("ID")))
                db.close()
                onBackPressed()

            }

        }
        return super.onOptionsItemSelected(item)
    }


}