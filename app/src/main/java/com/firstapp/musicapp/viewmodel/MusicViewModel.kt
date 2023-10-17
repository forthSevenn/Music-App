package com.firstapp.musicapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firstapp.musicapp.api.ApiConfig
import com.firstapp.musicapp.model.MusicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusicViewModel : ViewModel() {
    var musics = MutableLiveData<ArrayList<MusicResponse.Music>>()

    private val key = "752575da1fmsh269e8e8251bbb31p150baejsndcd1824b1aa2"
    private val host = "deezerdevs-deezer.p.rapidapi.com"

    fun getMusic(name:String){
        ApiConfig.getApiService()
            .getMusic(key,host, query = name)
            .enqueue(object : Callback<MusicResponse> {
                override fun onResponse(
                    call: Call<MusicResponse>,
                    response: Response<MusicResponse>,
                ) {
                    if (response.isSuccessful) {
                        musics.postValue(response.body()?.data)
                    }
                }

                override fun onFailure(call: Call<MusicResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun getListMusic(): LiveData<ArrayList<MusicResponse.Music>>{
        return musics
    }
}