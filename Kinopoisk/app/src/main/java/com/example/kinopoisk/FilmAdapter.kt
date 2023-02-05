package com.example.kinopoisk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.kinopoisk.databinding.FilmItemBinding

class FilmAdapter: RecyclerView.Adapter<FilmAdapter.FilmHolder>() {
    val filmList = ArrayList<Film>()
    class FilmHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = FilmItemBinding.bind(item)
        fun bind(film: Film) = with(binding) {
            fcImage.setImageResource(film.imageId)
            fcTitle.setText(film.titleId)
            fcGenerYear.setText(film.gener_yearID)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false)
        return FilmHolder(view)
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    override fun onBindViewHolder(holder: FilmHolder, position: Int) {
        holder.bind(filmList[position])
    }

    fun addFilm(film: Film) {
        filmList.add(film)
        notifyDataSetChanged()
    }
}