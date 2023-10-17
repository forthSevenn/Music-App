package com.firstapp.musicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.firstapp.musicapp.adapter.RvMusicAdapter
import com.firstapp.musicapp.databinding.ActivityMainBinding
import com.firstapp.musicapp.viewmodel.MusicViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var musicViewModel: MusicViewModel
    private lateinit var rvMusicAdapter: RvMusicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.supportActionBar?.hide()

        musicViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MusicViewModel::class.java]

        rvMusicAdapter = RvMusicAdapter()
        rvMusicAdapter.notifyDataSetChanged()

        binding.apply {
            rvMusic.layoutManager = LinearLayoutManager(this@MainActivity)
            rvMusic.setHasFixedSize(true)
            rvMusic.adapter = rvMusicAdapter

            showMusic()

            searchMusic.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchMusic()
                }
                true
            }

            searchBtn.setOnClickListener {
                searchMusic()
            }

        }

        musicViewModel.getListMusic().observe(this){
            if (it!=null) rvMusicAdapter.setListOfMusic(it)
            else showMusic()
            showLoading(false)
        }
    }

    private fun searchMusic(){
        binding.apply {
            searchMusic.clearFocus()
            musicViewModel.getMusic(searchMusic.text.toString())
            showLoading(true)
        }
    }

    private fun showMusic(){
        var name = "justin bieber"
        musicViewModel.getMusic(name)
        showLoading(true)
    }

    private fun showLoading(status: Boolean){
        binding.apply {
            if (status) progressBar.visibility = View.VISIBLE
            else progressBar.visibility = View.GONE
        }
    }
}