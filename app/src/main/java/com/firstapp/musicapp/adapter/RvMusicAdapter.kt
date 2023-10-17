package com.firstapp.musicapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firstapp.musicapp.MusicPlayerFragment
import com.firstapp.musicapp.R
import com.firstapp.musicapp.databinding.MusicListBinding
import com.firstapp.musicapp.model.MusicResponse

class RvMusicAdapter : RecyclerView.Adapter<RvMusicAdapter.ListViewHolder>() {
    private val listofMusic = ArrayList<MusicResponse.Music>()

    fun setListOfMusic(listMusic: ArrayList<MusicResponse.Music>){
        listofMusic.clear()
        listofMusic.addAll(listMusic)
        notifyDataSetChanged()
    }

    inner class ListViewHolder (private val binding: MusicListBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ClickableViewAccessibility", "Range")
        fun bind(music: MusicResponse.Music){
            binding.apply {
                tvSongName.text = music.title
                tvArtistName.text = music.artist.name
                tvAlbumName.text = music.album.title

                Glide.with(itemView.context)
                    .load(music.album.cover_medium)
                    .fitCenter()
                    .into(ivMusicImage)

                musicHolder.setOnClickListener {
                    clickMusic(itemView.context, music)
                }

            }
        }

        private fun clickMusic(context: Context, music: MusicResponse.Music) {
            val activity : AppCompatActivity = context as AppCompatActivity
            val fragment = MusicPlayerFragment()
            val fragmentManager = activity.supportFragmentManager

            val bundle = Bundle()
            bundle.putParcelable("music",music)
            fragment.arguments = bundle

            fragmentManager.beginTransaction()
                .replace(R.id.music_player_container, fragment)
                .commit()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = MusicListBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listofMusic[position])
    }

    override fun getItemCount(): Int = listofMusic.size
}