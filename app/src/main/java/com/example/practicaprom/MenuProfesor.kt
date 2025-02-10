package com.example.practicaprom

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MenuProfesor : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_profesor)
        val btnAniadirAlumno=findViewById<Button>(R.id.btnAniadirAlumno)
        val btnAniadirGrupo=findViewById<Button>(R.id.btnAniadirGrupo)
        val btnAsignarGrupo=findViewById<Button>(R.id.btnAsignarGrupo)
        val btnVerPuntuacion=findViewById<Button>(R.id.btnConsultarPuntuacion)
        btnAniadirAlumno.setOnClickListener {
            val intent = Intent(this, AniadirAlumno::class.java)
            startActivity(intent)
        }
        btnAniadirGrupo.setOnClickListener {
            val intent = Intent(this, AniadirGrupo::class.java)
            startActivity(intent)
        }
        btnAsignarGrupo.setOnClickListener {
            val intent = Intent(this, AsignarGrupo::class.java)
            startActivity(intent)
        }
        btnVerPuntuacion.setOnClickListener {
            val intent = Intent(this, VerPuntosPorGrupo::class.java)
            startActivity(intent)
        }
    }
}