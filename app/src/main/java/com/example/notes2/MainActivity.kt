package com.example.notes2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import android.database.Cursor


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickTakeNoteTxt(v: View) {
        val startDetActiv = Intent(applicationContext, DetailsActivity::class.java)
        startActivity(startDetActiv)
    }

    override fun onResume(){
        super.onResume()
        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase
        // ----------------------------- usuwanie notatek -------------------------
        val cursor: Cursor = db.query(TableInfo.TABLE_NAME, null, null, null,null, null, null)
        val notesList = ArrayList<Note>()

// kursorem, ktory pobiera wszystkie rekordy z bazy, uzupełniamy arrayliste obiektów notes

        if (cursor.count > 0){
            cursor.moveToFirst()
            while (!cursor.isAfterLast){
                val note = Note()
                note.id = cursor.getInt(0)
                note.title = cursor.getString(1)
                note.message = cursor.getString(2)
                notesList.add(note)
                cursor.moveToNext()
            }
        }
        cursor.close()

        main_activity_recyclerView.layoutManager = GridLayoutManager(applicationContext, 1)
        main_activity_recyclerView.adapter = CardViewAdapter(applicationContext, db, notesList)

 // konstruktor CardViewAdapter(applicationContext, db, notes) wymaga aby w nawiasie były podane te wartości
    }
}
