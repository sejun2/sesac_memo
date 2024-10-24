package model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Memo(
    val id: Int,
    val content: String
)
