package com.firstapp.musicapp

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.firstapp.musicapp.databinding.FragmentMusicPlayerBinding
import com.firstapp.musicapp.model.MusicResponse

class MusicPlayerFragment : Fragment() {

    private var _binding : FragmentMusicPlayerBinding? = null
    private val binding get() = _binding!!
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMusicPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(33)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val music: MusicResponse.Music = requireArguments().getParcelable("music")!!

        mediaPlayer = MediaPlayer.create(requireActivity().applicationContext,music.preview.toUri())
        mediaPlayer.start()
        playStatus(true)

        mediaPlayer.setOnCompletionListener {
            playStatus(false)
        }

        binding.apply {
            tvMusicPlayName.text = music.title
            tvArtistPlayName.text = music.artist.name
            Glide.with(this@MusicPlayerFragment)
                .load(music.album.cover_medium)
                .fitCenter()
                .into(ivMusicImagePlay)

            playPauseBtn.setOnClickListener {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.pause()
                    playStatus(false)
                }
                else {
                    mediaPlayer.start()
                    playStatus(true)
                }
            }

            musicPlayer.setOnClickListener {
                //prevent clicking on recycler view
                //future - will implement show music detail
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer.stop()
    }

    private fun playStatus(mediaStatus: Boolean){
        binding.apply {
            if (mediaStatus) {
                playPauseBtn.setBackgroundResource(R.drawable.ic_pause)
            }
            else {
                playPauseBtn.setBackgroundResource(R.drawable.ic_play)
            }
        }
    }
}