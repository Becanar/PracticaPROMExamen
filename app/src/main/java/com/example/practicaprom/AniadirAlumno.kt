package com.example.practicaprom

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AniadirAlumno : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aniadir_alumno)

        val bd = BD(this)
        val btnInsertar = findViewById<Button>(R.id.btnInsertar)
        val txtUsuario = findViewById<EditText>(R.id.txtNombre)
        val txtContrasenia = findViewById<EditText>(R.id.txtContrasenia)

        btnInsertar.setOnClickListener {
            if (txtContrasenia.text.toString().isNotEmpty() && txtUsuario.text.toString().isNotEmpty()) {
                // Iniciar un hilo para manejar la inserción en la base de datos
                Thread {
                    val insertSuccess: Boolean = bd.guardarAlumno(
                        txtUsuario.text.toString(),
                        txtContrasenia.text.toString()
                    )

                    runOnUiThread {
                        if (insertSuccess) {
                            // Si la inserción fue exitosa, mostrar un Toast y regresar
                            Toast.makeText(
                                this,
                                getString(R.string.alumno_creado_correctamente),
                                Toast.LENGTH_SHORT
                            ).show()
                            finish() // Regresar a la actividad anterior
                        } else {
                            // Si no fue exitoso, mostrar un mensaje indicando que ya existe
                            Toast.makeText(
                                this,
                                getString(R.string.el_ususario_ya_existe),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }.start()
            } else {
                // Si algún campo está vacío, mostrar un mensaje de advertencia
                Toast.makeText(
                    this,
                    getString(R.string.debes_completar_los_campos_usuario_y_contrase_a_para_poder_insertar_al_alumno),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
