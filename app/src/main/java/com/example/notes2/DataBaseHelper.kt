package com.example.notes2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

// stałe z nazwami
object TableInfo: BaseColumns {
    const val TABLE_NAME = "Notes"
    const val TABLE_COLUMN_TITLE = "title"
    const val TABLE_COLUMN_MESSAGE = "message"
}

//podst komendy
object BasicCommand{
    const val SQL_CREATE_TABLE: String =
        "CREATE TABLE IF NOT EXISTS ${TableInfo.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${TableInfo.TABLE_COLUMN_TITLE} TEXT NOT NULL, " +
                "${TableInfo.TABLE_COLUMN_MESSAGE} TEXT NOT NULL)"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${TableInfo.TABLE_NAME}"
}

class DataBaseHelper (cntx: Context): SQLiteOpenHelper(cntx, TableInfo.TABLE_NAME, null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        //tworzenie tabeli
        db?.execSQL(BasicCommand.SQL_CREATE_TABLE)
    }
    //tą metodę trzeba zaimplementować! -- Jeśli wersje się różnią, to usuniemy naszą tabelę i stworzymy nową
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
       db?.execSQL(BasicCommand.SQL_DELETE_TABLE)
        onCreate(db)
    }
}

