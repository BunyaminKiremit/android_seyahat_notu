package com.example.bunyamin_kiremit_9

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class Registraiton : AppCompatActivity() {


    lateinit var ePosta:EditText
    lateinit var password:EditText
    lateinit var passwordAgain:EditText
    lateinit var logText:TextView
    lateinit var registerButton:Button

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registraiton)

        ePosta=findViewById(R.id.ePosta)
        password=findViewById(R.id.password)
        passwordAgain=findViewById(R.id.passwordAgain)
        registerButton=findViewById(R.id.resetButton)
        logText=findViewById(R.id.logText)

        firebaseAuth=FirebaseAuth.getInstance()

        registerButton.setOnClickListener {
            val email=ePosta.text.toString()
            val password=password.text.toString()
            val againpassword=passwordAgain.text.toString()

            if (email.isNotEmpty()&& password.isNotEmpty()&& againpassword.isNotEmpty()){
                if (password==againpassword){
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                        if (it.isSuccessful){
                            val intent=Intent(this,MainActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this,"Bir hata oluştu lüten tekrar deneyin",Toast.LENGTH_LONG).show()
                        }
                    }
                }else{
                    Toast.makeText(this,"\n" + "Şifre eşleşmiyor lütfen tekrar deneyin",Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this,"\n" + "Boş Alan var lütfen doldurun !! ",Toast.LENGTH_LONG).show()
            }
        }

        logText.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}