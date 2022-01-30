package com.nopi.ngajiyuk.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nopi.ngajiyuk.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val login = findViewById<Button>(R.id.masuk)
        val user = findViewById<EditText>(R.id.editTextUsername)
        val pass = findViewById<EditText>(R.id.editTextPassword)
        val reset = findViewById<Button>(R.id.cancel)
        login.setOnClickListener {
            val email = user.text.toString()
            val password = pass.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Masukan Email dan Kata Sandi Anda", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (email == "nopii" && password == "123") {
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                intent.putExtra("id", user.text.toString())
                startActivity(intent)
            } else {
                Toast.makeText(this@MainActivity, "Nama Pengguna atau Kata Sandi Salah!", Toast.LENGTH_SHORT).show()
            }
        }
        reset.setOnClickListener {
            user.setText("")
            pass.setText("")
        }
    }
}

