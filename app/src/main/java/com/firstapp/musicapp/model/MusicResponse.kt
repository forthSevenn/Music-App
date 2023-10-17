package com.firstapp.musicapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigInteger

data class MusicResponse (
    val data: ArrayList<Music>
) {
    @Parcelize
    data class Music (
        val id: BigInteger,
        val title: String,
        val preview: String,
        val artist: Artist,
        val album: Album
    ):Parcelable {
        @Parcelize
        data class Artist (
            val name: String
        ):Parcelable
        @Parcelize
        data class Album (
            val title: String,
            val cover_medium: String
        ):Parcelable
    }
}