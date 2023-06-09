package com.example.bunyamin_kiremit_9.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.bunyamin_kiremit_9.R
import com.example.bunyamin_kiremit_9.models.Note
import com.example.bunyamin_kiremit_9.models.NoteVal
import com.google.firebase.database.DatabaseReference

class ListAdapter(context: Context, notes: List<Note>, private val db: DatabaseReference) :
    ArrayAdapter<Note>(context, 0, notes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val note = getItem(position)

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_layout, parent, false)
        }

        val title = view!!.findViewById<TextView>(R.id.r_title)
        val city = view!!.findViewById<TextView>(R.id.r_city)
        val noteText = view.findViewById<TextView>(R.id.r_note)

        title.text = note!!.value.title
        city.text = note.value.city
        noteText.text = note.value.note

        return view
    }
    fun deleteData(position: Int) {
        val note = getItem(position)
        val noteKey = note?.key // Silinecek verinin benzersiz anahtarını al

        // Firebase veritabanından veriyi sil
        if (noteKey != null) {
            val noteRef = db.child(noteKey)
            noteRef.removeValue()
                .addOnSuccessListener {
                    // Silme işlemi başarılı olduğunda yapılacak işlemler
                    Toast.makeText(context, "Veri silindi", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    // Silme işlemi başarısız olduğunda yapılacak işlemler
                    Toast.makeText(context, "Veri silinirken hata oluştu", Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun setData(data: List<Note>) {
        clear()
        addAll(data)
        notifyDataSetChanged()
    }
}