package com.example.bunyamin_kiremit_9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ResetPassword : AppCompatActivity() {

    lateinit var ePosta:EditText
    lateinit var resetButton:Button

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        ePosta=findViewById(R.id.ePosta)
        resetButton=findViewById(R.id.resetButton)

        auth=FirebaseAuth.getInstance()

        resetButton.setOnClickListener {
            val resetp=ePosta.text.toString()
            auth.sendPasswordResetEmail(resetp)
                .addOnSuccessListener {
                    Toast.makeText(this,"Şifre yenilemek için E-posta adresini kontrol edin.",Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this,"Bu E-postaya ait hesap bulunamdı",Toast.LENGTH_LONG).show()
                }
        }

    }
}