package com.example.practicaprom

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnProfesor=findViewById<Button>(R.id.btnProfesor)
        val btnAlumno=findViewById<Button>(R.id.btnAlumno)
        btnProfesor.setOnClickListener {
            val intent = Intent(this, MenuProfesor::class.java)
            startActivity(intent)
            finish()
        }
        btnAlumno.setOnClickListener {
            val intent = Intent(this, IniciarSesion::class.java)
            startActivity(intent)
            finish()
        }
    }
}