package com.example.practicaprom

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AniadirAlumno : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bd=BD(this)
        setContentView(R.layout.activity_aniadir_alumno)
        val btnInsertar=findViewById<Button>(R.id.btnInsertar)
        val txtUsuario=findViewById<EditText>(R.id.txtNombre)
        val txtContrasenia=findViewById<EditText>(R.id.txtContrasenia)
        btnInsertar.setOnClickListener {
            if(txtContrasenia.text.toString().isNotEmpty()&&txtUsuario.text.toString().isNotEmpty()) {

                Thread {
                    val insertSuccess: Boolean = bd.guardarAlumno(
                        txtUsuario.text.toString(),
                        txtContrasenia.text.toString()
                    )
                    if (!insertSuccess) {
                        runOnUiThread {
                            Toast.makeText(
                                this,
                                getString(R.string.el_ususario_ya_existe), Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }.start()
            }else{
                Toast.makeText(this,
                    getString(R.string.debes_completar_los_campos_usuario_y_contrase_a_para_poder_insertar_al_alumno), Toast.LENGTH_SHORT).show()
            }
        }
    }
}