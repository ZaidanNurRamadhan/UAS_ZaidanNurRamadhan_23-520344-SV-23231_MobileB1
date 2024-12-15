package com.example.uas.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uas.R
import com.example.uas.database.FitEntity
import com.example.uas.database.FitRoomDatabase
import com.example.uas.model.Fitness

class FitnesUserAdapter(val dataFitness: List<Fitness>?, val database : FitRoomDatabase?) : RecyclerView.Adapter<FitnesUserAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameView: TextView = view.findViewById(R.id.name)
        private val categoryView: TextView = view.findViewById(R.id.category)
        private val durORrepView: TextView = view.findViewById(R.id.durORrep)
        private val bookmarkBtn: ImageView = view.findViewById(R.id.bookmark)
        fun bind(fitness: Fitness) {
            nameView.text = fitness.name
            categoryView.text = fitness.category
            durORrepView.text = fitness.durORrep

            database?.fitDao()?.isFitFavorite(fitness.name)?.observeForever { isFavorite ->
                bookmarkBtn.setImageResource(
                    if (isFavorite) R.drawable.bookmarked
                    else R.drawable.unbookmar
                )
            }

            bookmarkBtn.setOnClickListener {
                Thread {
                    val dao = database?.fitDao()
                    val existingFit = dao?.getFitByName(fitness.name)

                    if (existingFit != null) {
                        dao.delete(existingFit)
                    } else {
                        val fitEntity = FitEntity(
                            name = fitness.name ?: "Default",
                            category = fitness.category ?: "Default",
                            durORrep = fitness.durORrep ?: "Default"
                        )
                        dao?.insert(fitEntity)
                    }
                }.start()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fitness_item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataFitness?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataFitness?.get(position)?.let { holder.bind(it) }
    }
}