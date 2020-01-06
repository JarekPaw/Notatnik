package com.example.notes2

import android.app.Service
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.database.Cursor
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_adapter_layout.view.*

class MvHolder(val viev1: View): RecyclerView.ViewHolder(viev1)

class CardViewAdapter (contx: Context, val db: SQLiteDatabase,
                       var notesList: ArrayList<Note>): RecyclerView.Adapter<MvHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MvHolder {
        val lyoutInfl = LayoutInflater.from(parent.context)
        val actvAdapterLyout: View = lyoutInfl.inflate(R.layout.activity_adapter_layout,
            parent, false)

        return MvHolder(actvAdapterLyout)
    }

    override fun getItemCount(): Int {
        val cursor1: Cursor = db.query(TableInfo.TABLE_NAME, null, null, null,
            null, null, null)
        val rowNumber = cursor1.count
        cursor1.close()
        return rowNumber
    }

    override fun onBindViewHolder(holder0: MvHolder, position: Int) {
        val myCardV: CardView = holder0.viev1.card_view
        val title: TextView = holder0.viev1.adapter_title
        val message: TextView = holder0.viev1.adapter_note
        val context: Context = holder0.viev1.context // potrzebne do edytowania notatek

        title.setText(notesList[holder0.adapterPosition].title)
        message.setText(notesList[holder0.adapterPosition].message) // message.text = notesList[holder0.adapterPosition].message

        // edycja notatki


        myCardV.setOnClickListener {
     // przesyłanie extra, będzie pobierał dane bezpośrednio z arraylisty
            val intentEditNote: Intent = Intent(context, DetailsActivity::class.java)
            val titleEdit: String = notesList[holder0.adapterPosition].title
            val messageEdit: String = notesList[holder0.adapterPosition].message
            val idEdit: String = notesList[holder0.adapterPosition].id.toString()  // nie potrzeba plu 1

            intentEditNote.putExtra("title", titleEdit)
            intentEditNote.putExtra("message", messageEdit)
            intentEditNote.putExtra("ID", idEdit)

            context.startActivity(intentEditNote)

            // w detailsActivity trzeba odebrać extra w nadpisanej metodzie onCreate()
        }
        // obsługa schowka - kopiowanie tekstu

        myCardV.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                val copyinfo = Toast.makeText(context, "notatka skopiwana", Toast.LENGTH_SHORT)
                val cm: ClipboardManager = context.getSystemService(Service.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData: ClipData = ClipData.newPlainText("copyText", "note: " + message.text)
                cm.setPrimaryClip(clipData)
                copyinfo.show()
                return true
            }
        })
    }
}

