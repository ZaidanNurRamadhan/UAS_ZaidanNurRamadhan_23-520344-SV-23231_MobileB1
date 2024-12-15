package com.example.uas.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uas.R
import com.example.uas.database.FitEntity
import com.example.uas.database.FitRoomDatabase
import com.example.uas.databinding.FitnessItemUserBinding
import com.example.uas.model.Fitness

class BookmarkAdapter(
    private val listFitness: List<Fitness>,
    private val database : FitRoomDatabase?
): RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: FitnessItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : Fitness) {
            with(binding) {
                name.text = data.name
                category.text = data.category
                durORrep.text = data.durORrep

                database?.fitDao()?.isFitFavorite(data.name)?.observeForever { isFavorite ->
                    bookmark.setImageResource(
                        if (isFavorite) R.drawable.bookmarked
                        else R.drawable.unbookmar
                    )
                }

                bookmark.setOnClickListener {
                    Thread {
                        val dao = database?.fitDao()
                        val existingFit = dao?.getFitByName(data.name)

                        if (existingFit != null) {
                            dao.delete(existingFit)
                        } else {
                            val fitEntity = FitEntity(
                                name = data.name,
                                durORrep = data.durORrep,
                                category = data.category!!
                            )
                            dao?.insert(fitEntity)
                        }
                    }.start()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FitnessItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listFitness.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFitness[position])
    }

}