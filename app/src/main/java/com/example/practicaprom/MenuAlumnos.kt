package com.example.practicaprom

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MenuAlumnos : AppCompatActivity() {

    private lateinit var usuario:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_alumnos)
        val btnDragAndDrop=findViewById<Button>(R.id.btnDragAndDrop)
        val btnFillTheGaps=findViewById<Button>(R.id.btnFillTheGaps)
        val btnConsultarPuntuacion=findViewById<Button>(R.id.btnVerPuntosGrupo)
        usuario=intent.getStringExtra("usuario").toString()
        btnDragAndDrop.setOnClickListener {
            val intent = Intent(this, DragAndDropp::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }
        btnFillTheGaps.setOnClickListener {
            val intent = Intent(this, FillTheGaps::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }
        btnConsultarPuntuacion.setOnClickListener {
            val intent = Intent(this, VerPuntosDeUnGrupo::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }
    }
}