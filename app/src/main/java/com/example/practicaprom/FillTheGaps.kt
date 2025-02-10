package com.example.practicaprom

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FillTheGaps : AppCompatActivity() {

    private lateinit var bd:BD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_the_gaps)
        bd=BD(this)
        val btnComprobar=findViewById<Button>(R.id.btnComprobar)
        val txtTexto=findViewById<TextView>(R.id.textView)
        txtTexto.movementMethod = ScrollingMovementMethod()
        btnComprobar.setOnClickListener {
            val pantorrilla=findViewById<EditText>(R.id.txtPantorrilla).text.toString()
            val chiquitin=findViewById<EditText>(R.id.txtChiquitin).text.toString()
            val subeArriba=findViewById<EditText>(R.id.txtSubeArriba).text.toString()
            val queMeMareo=findViewById<EditText>(R.id.txtMareo).text.toString()
            if (subeArriba.trim().lowercase().equals("sube arriba") && queMeMareo.trim().lowercase().equals("que me mareo") &&
                pantorrilla.trim().lowercase().equals("pantorrilla") && (chiquitin.trim().lowercase().equals("chiquitin")||chiquitin.trim().lowercase().equals("chiquitín"))) {
                Thread{
                    bd.sumarPuntosUsuario(intent.getStringExtra("usuario").toString())
                }.start()
                finish()
            }else{
                Toast.makeText(this,
                    getString(R.string.algun_texto_no_es_correcto), Toast.LENGTH_SHORT).show()
            }
        }
    }
}