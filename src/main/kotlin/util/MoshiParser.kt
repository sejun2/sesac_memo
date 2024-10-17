package util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import model.Memo

class MoshiParser {
    companion object {
        private val moshi: Moshi =
            Moshi.Builder()
                .build()

        @OptIn(ExperimentalStdlibApi::class)
        @JvmStatic
        val jsonAdapter: JsonAdapter<List<Memo>> = moshi.adapter()
    }
}