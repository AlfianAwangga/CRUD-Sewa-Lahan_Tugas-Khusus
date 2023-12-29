package com.example.sewalahan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.sewalahan.databinding.ActivityHomeBinding
import com.example.sewalahan.databinding.ActivityLoginBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class login : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val db: DatabaseReference =database.getReference("user")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usnm = "admin"
        val pswd = "alfian"
        db.child("admin").child("username").setValue(usnm)
        db.child("admin").child("password").setValue(pswd)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username == usnm) {
                if (password == pswd) {
                    Intent(this,edit::class.java).also {
                        startActivity(it)
                    }
                }
                else {
                    Toast.makeText(this, "password anda salah", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(this, "username/password anda salah, hanya admin yang diperbolehkan login", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.admin -> Intent(this,login::class.java).also{
                startActivity(it)
            }
            R.id.home -> Intent(this,home::class.java).also{
                startActivity(it)
            }
            R.id.close -> finish()
        }
        return true
    }
}