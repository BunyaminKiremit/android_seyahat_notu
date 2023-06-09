package com.example.bunyamin_kiremit_9

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    lateinit var username : EditText
    lateinit var password: EditText
    lateinit var forgotPasswordText:TextView
    lateinit var registerText:TextView
    lateinit var loginButton: Button
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        username=findViewById(R.id.ePosta)
        password=findViewById(R.id.password)
        forgotPasswordText=findViewById(R.id.forgotPasswordText)
        registerText=findViewById(R.id.registerText)
        loginButton=findViewById(R.id.resetButton)

        firebaseAuth=FirebaseAuth.getInstance()

        loginButton.setOnClickListener {
            val email = username.text.toString()
            val password = password.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val currentUser: FirebaseUser? = firebaseAuth.currentUser
                        if (currentUser != null) {
                            val userId = currentUser.uid
                            val intent = Intent(this, Travel::class.java)
                            intent.putExtra("userId", userId)
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this, "E-posta veya şifre hatalı" ,Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Boş Alan var lütfen doldurun !!", Toast.LENGTH_LONG).show()
            }
        }

        forgotPasswordText.setOnClickListener {

            val intent = Intent(this@MainActivity, ResetPassword::class.java)
            startActivity(intent)
        }
        registerText.setOnClickListener {

            val intent = Intent(this@MainActivity, Registraiton::class.java)
            startActivity(intent)
        }


    }
}