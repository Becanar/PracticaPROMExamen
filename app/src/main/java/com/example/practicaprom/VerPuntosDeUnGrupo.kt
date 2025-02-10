package com.example.practicaprom

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VerPuntosDeUnGrupo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bd = BD(this)
        setContentView(R.layout.activity_ver_puntos_por_grupo)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val txtGrupo=findViewById<TextView>(R.id.txtGrupo)

        // Asignar un adapter vacío antes de cargar los datos
        recyclerView.adapter = AlumnoGrupoAdapter(emptyMap())
        Thread {
            val usuario=intent.getStringExtra("usuario").toString()
            val txtGrupo=findViewById<TextView>(R.id.txtGrupo)
            txtGrupo.text= getString(R.string.grupo)+bd.obtenerGrupoDeUsuario(usuario).toString()
            val mapaAlumnos = bd.conseguirPuntosDelGrupo(usuario)
            runOnUiThread {
                recyclerView.adapter = AlumnoGrupoAdapter(mapaAlumnos) // Aquí se asigna el adaptador con datos
            }
        }.start()
    }
}
