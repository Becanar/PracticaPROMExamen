package com.example.practicaprom

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AniadirGrupo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aniadir_grupo)

        val bd = BD(this)
        val btnInsertar = findViewById<Button>(R.id.btnInsertarGrupo)
        val txtGrupo = findViewById<EditText>(R.id.txtCodGrupo)

        btnInsertar.setOnClickListener {
            if (txtGrupo.text.toString().isNotEmpty()) {
                // Iniciar un hilo para manejar la inserción en la base de datos
                Thread {
                    val insertSuccess: Boolean = bd.guardarGrupo(txtGrupo.text.toString().toInt())

                    runOnUiThread {
                        if (insertSuccess) {
                            // Si la inserción fue exitosa, mostrar un Toast y regresar
                            Toast.makeText(
                                this,
                                getString(R.string.grupo_creado_correctamente),
                                Toast.LENGTH_SHORT
                            ).show()
                            finish() // Regresar a la actividad anterior
                        } else {
                            // Si no fue exitoso, mostrar un mensaje indicando que ya existe
                            Toast.makeText(
                                this,
                                getString(R.string.el_grupo_ya_existe),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }.start()
            } else {
                // Si el campo está vacío, mostrar un mensaje de advertencia
                Toast.makeText(
                    this,
                    getString(R.string.debes_poner_un_codigo_de_grupo_para_poder_crearlo),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
