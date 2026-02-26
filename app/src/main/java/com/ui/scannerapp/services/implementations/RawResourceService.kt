package com.ui.scannerapp.services.implementations

import android.content.Context
import org.json.JSONObject
import java.io.InputStream
import java.lang.NumberFormatException

class RawResourceService(private val context: Context) {

    fun loadPredictionLabels(resourceId: Int): HashMap<Int, String> {
        val labelIdProduct = HashMap<Int, String>()
        try {
            val inputStream: InputStream = context.resources.openRawResource(resourceId)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(jsonString)
            val keys = jsonObject.keys()

            while (keys.hasNext()) {
                val key = keys.next()
                val value = jsonObject.getString(key)
                try {
                    labelIdProduct[key.toInt()] = value
                } catch (e: NumberFormatException) {
                    println("Skipping non-integer key: $key")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return labelIdProduct
    }
}
