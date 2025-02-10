package com.example.practicaprom

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class IniciarSesion : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion)
        val bd=BD(this)
        val btnIniciarSesion=findViewById<Button>(R.id.btnIniciarSesion)
        val txtUsuario=findViewById<EditText>(R.id.txtNombre)
        val txtContrasenia=findViewById<EditText>(R.id.txtContrasenia)
        btnIniciarSesion.setOnClickListener {
            if (txtUsuario.text.toString().isNotEmpty() && txtContrasenia.text.toString().isNotEmpty()) {
                Thread {
                    // Verificar la conexión a la base de datos en segundo plano
                    val conexionExitosa = bd.probarConexion()

                    runOnUiThread {
                        if (!conexionExitosa) {
                            Toast.makeText(this, "No se pudo conectar a la base de datos", Toast.LENGTH_SHORT).show()
                            return@runOnUiThread
                        }

                        // Si la conexión es exitosa, verificar si el usuario existe
                        Thread {
                            val existe = bd.existeUsuario(txtUsuario.text.toString(), txtContrasenia.text.toString())
                            runOnUiThread {
                                if (!existe) {
                                    Toast.makeText(this, getString(R.string.usuario_o_contrase_a_incorrectos), Toast.LENGTH_SHORT).show()
                                } else {
                                    val intent = Intent(this, MenuAlumnos::class.java)
                                    intent.putExtra("usuario", txtUsuario.text.toString())
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        }.start()
                    }
                }.start()
            } else {
                Toast.makeText(this, getString(R.string.debes_introducir_el_usuario_y_la_contrase_a_para_poder_iniciar_sesion), Toast.LENGTH_SHORT).show()
            }
        }
    }
}