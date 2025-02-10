package com.example.practicaprom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlumnoAdapter(private val lista: List<Pair<Int, Int>>) :
    RecyclerView.Adapter<AlumnoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txtNombre)
        val txtPuntos: TextView = view.findViewById(R.id.txtPuntos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.elemento_lista, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (id, puntos) = lista[position]
        holder.txtNombre.text = id.toString()
        holder.txtPuntos.text = puntos.toString()
    }

    override fun getItemCount(): Int = lista.size
}

