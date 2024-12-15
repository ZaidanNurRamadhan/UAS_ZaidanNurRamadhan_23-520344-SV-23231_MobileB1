package com.example.uas.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.uas.R
import com.example.uas.model.Fitness

class FitAdapter(val dataFitness: List<Fitness>?, private val onItemSelected: (Fitness) -> Unit) : RecyclerView.Adapter<FitAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameView : TextView = view.findViewById(R.id.name)
        private val categoryView : TextView = view.findViewById(R.id.category)
        private val durORrepView : TextView = view.findViewById(R.id.durORrep)
        private val editBtn : ImageView = view.findViewById(R.id.edit)
        private val hapusBtn : ImageView = view.findViewById(R.id.hapus)
        fun bind(fitness: Fitness) {
            nameView.text = fitness.name
            categoryView.text = fitness.category
            durORrepView.text = fitness.durORrep

            hapusBtn.setOnClickListener {
                onItemSelected(fitness)
            }

            editBtn.setOnClickListener{
                val arguments = Bundle().apply {
                    putString("id", fitness.id)
                    putString("name", fitness.name)
                    putString("category", fitness.category)
                    putString("durORrep", fitness.durORrep)
                }
                itemView.findNavController().navigate(R.id.action_homeAdminFragment_to_editPhotoFragment, arguments)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fitness_item_admin, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataFitness?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = dataFitness?.size ?: 0
}