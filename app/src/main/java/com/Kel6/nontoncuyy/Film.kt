package com.Kel6.nontoncuyy

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Film(
    val id: String,
    val judul: String,
    val ringkasan: String,

    @SerialName("gambar_poster")
    val gambarPoster: String,

    @SerialName("gambar_sampul")
    val gambarSampul: String,

    @SerialName("tanggal_rilis")
    val tanggalRilis: String,

    @SerialName("skor_rating")
    val skorRating: String,

    val kategori: String,

    @SerialName("url_trailer")
    val urlTrailer: String
)