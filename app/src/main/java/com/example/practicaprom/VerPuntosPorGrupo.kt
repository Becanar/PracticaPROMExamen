package com.example.practicaprom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class VerPuntosPorGrupo : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bd = BD(this)
        setContentView(R.layout.activity_ver_puntos_por_grupo)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Asignar un adapter vacío antes de cargar los datos
        recyclerView.adapter = AlumnoAdapter(emptyList())
        Thread {
            val mapaAlumnos = bd.conseguirSumaPuntosPorGrupo()
            runOnUiThread {
                val listaAlumnos = mapaAlumnos.toList()
                recyclerView.adapter = AlumnoAdapter(listaAlumnos) // Aquí se asigna el adaptador con datos
            }
        }.start()
    }
}
