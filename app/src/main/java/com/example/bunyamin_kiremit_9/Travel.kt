package com.example.bunyamin_kiremit_9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.bunyamin_kiremit_9.adapter.ListAdapter
import com.example.bunyamin_kiremit_9.models.Note
import com.example.bunyamin_kiremit_9.models.NoteVal
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Travel : AppCompatActivity() {

    lateinit var baslikEditText:EditText
    lateinit var sehirEditText:EditText
    lateinit var notEditText:EditText
    lateinit var kaydetButton:Button
    lateinit var kayitlarListView:ListView

    private lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel)

        baslikEditText=findViewById(R.id.baslikEditText)
        sehirEditText=findViewById(R.id.sehirEditText)
        notEditText=findViewById(R.id.notEditText)
        kaydetButton=findViewById(R.id.kaydetButton)
        kayitlarListView=findViewById(R.id.kayitlarListView)

        val userId = intent.getStringExtra("userId")
        val db = FirebaseDatabase.getInstance().getReference("Note").child(userId!!)



        kaydetButton.setOnClickListener {
            val title = baslikEditText.text.toString()
            val city = sehirEditText.text.toString()
            val note = notEditText.text.toString()
            val fid = db.push().key
            val data = NoteVal(title, city, note)
            db.child(fid!!).setValue(data).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Kayıt Bşarılı", Toast.LENGTH_LONG).show()
                    baslikEditText.text.clear()
                    sehirEditText.text.clear()
                    notEditText.text.clear()
                } else {
                    Toast.makeText(this, "Hata", Toast.LENGTH_LONG).show()
                }
            }
        }

        adapter = ListAdapter(this, mutableListOf(), db)
        kayitlarListView.adapter = adapter


        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val noteList = mutableListOf<Note>()
                    snapshot.children.forEach {
                        val noteVal = it.getValue(NoteVal::class.java)
                        val note = Note(it.key!!, noteVal!!)
                        noteList.add(note)
                    }
                    adapter.setData(noteList) // Verileri adaptöre gönder ve güncelle
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_LONG).show()
            }
        })

        kayitlarListView.setOnItemLongClickListener { parent, view, position, id ->
            val note = adapter.getItem(position)
            val alertDialog = AlertDialog.Builder(this)
                .setTitle("Silmek İster misiniz?")
                .setPositiveButton("Evet") { dialog, which ->
                    adapter.deleteData(position) // Veriyi silme işlemini gerçekleştir
                }
                .setNegativeButton("Hayır", null)
                .create()
            alertDialog.show()
            true
        }
    }
    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Uygulamadan Çıkış Yapmak İstiyor musunuz?")
            .setPositiveButton("Evet") { dialog, which ->
                super.onBackPressed()
            }
            .setNegativeButton("Hayır", null)
            .create()
        alertDialog.show()
    }
}