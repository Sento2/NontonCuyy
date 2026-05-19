package com.kel6.nontoncuyy.data.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive

@Serializable
data class Movie(
    val id: String,
    val judul: String,
    val ringkasan: String,
    val gambar_poster: String,
    val gambar_sampul: String,
    @Serializable(with = StringCoerceSerializer::class)
    val tanggal_rilis: String,
    @Serializable(with = StringCoerceSerializer::class)
    val skor_rating: String,
    val kategori: String,
    val url_trailer: String
)

object StringCoerceSerializer : KSerializer<String> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("StringCoerceSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: String) {
        encoder.encodeString(value)
    }

    override fun deserialize(decoder: Decoder): String {
        return if (decoder is JsonDecoder) {
            val element = decoder.decodeJsonElement()
            if (element is JsonPrimitive) {
                element.content
            } else {
                element.toString()
            }
        } else {
            decoder.decodeString()
        }
    }
}
