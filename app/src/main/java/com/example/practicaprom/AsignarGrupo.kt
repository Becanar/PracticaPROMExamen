package com.example.practicaprom

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

class AsignarGrupo : AppCompatActivity() {

    private lateinit var cmbAlumnos: Spinner
    private lateinit var cmbGrupos: Spinner
    private lateinit var btnAsignar: Button
    private lateinit var bd:BD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd=BD(this)
        setContentView(R.layout.activity_asignar_grupo)
        btnAsignar=findViewById(R.id.btnAsignar)
        cmbAlumnos=findViewById(R.id.cmbAlumnos)
        cmbGrupos=findViewById(R.id.cmbGrupos)

        cargarAlumnos()
        cargarGrupos()

        btnAsignar.setOnClickListener {
            val alumnoSeleccionado = cmbAlumnos.selectedItem as String
            val grupoSeleccionado = cmbGrupos.selectedItem.toString().toInt()
            Thread {
                bd.asignarGrupo(alumnoSeleccionado, grupoSeleccionado)
                runOnUiThread {
                    Toast.makeText(this,
                        getString(R.string.asignacion_completada),Toast.LENGTH_SHORT).show()
                    finish()
                }
                cargarAlumnos()
                cargarGrupos()
            }.start()
        }
    }

    private fun cargarAlumnos() {
        Thread{
            val alumnos=bd.conseguirAlumnosSinGrupo()
            runOnUiThread {
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, alumnos)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                cmbAlumnos.adapter = adapter
            }
        }.start()
    }

    private fun cargarGrupos() {
        Thread {
            val grupos = bd.conseguirTodosGrupos()
            runOnUiThread {
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, grupos)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                cmbGrupos.adapter = adapter
            }
        }.start()
    }
}