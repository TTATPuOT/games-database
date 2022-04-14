package ru.patriotovsky.gamesdatabase.rawg.api

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import ru.patriotovsky.gamesdatabase.models.Game
import ru.patriotovsky.gamesdatabase.models.Poster
import ru.patriotovsky.gamesdatabase.models.Store
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList


class RawgApi (context: AppCompatActivity) {
    private val endpoint = "https://api.rawg.io/api/"
    private val apiKey = "3ca2ea11c7494aed976c7587cc6d173f"

    private val queue: RequestQueue = Volley.newRequestQueue(context)

    fun getPosters(callback: (ArrayList<Poster>) -> Unit) {
        val request = JsonObjectRequest(Request.Method.GET, endpoint + "games?key=" + apiKey, null,
            { response ->
                val array = response.getJSONArray("results")
                val arrayList: ArrayList<Poster> = ArrayList()

                for (i in 0 until array.length()) {
                    val data = array.getJSONObject(i)
                    arrayList.add(
                        Poster(
                            data.getString("id").toInt(),
                            data.getString("name"),
                            data.getString("background_image"),
                            data.getString("name"),
                            SimpleDateFormat("yyyy-MM-dd").parse(data.getString("released"))
                        )
                    )
                }

                callback(arrayList)
            },
            { error ->
                Log.d("RESPONSE_ERROR", error.toString())
            }
        )

        queue.add(request)
    }

    fun searchPosters(query: String, callback: (ArrayList<Poster>) -> Unit) {
        val request = JsonObjectRequest(Request.Method.GET, endpoint + "games?search=" + java.net.URLEncoder.encode(query, "utf-8") + "&key=" + apiKey, null,
            { response ->
                val array = response.getJSONArray("results")
                val arrayList: ArrayList<Poster> = ArrayList()

                for (i in 0 until array.length()) {
                    val data = array.getJSONObject(i)
                    arrayList.add(
                        Poster(
                            data.getString("id").toInt(),
                            data.getString("name"),
                            data.getString("background_image"),
                            data.getString("name"),
                            SimpleDateFormat("yyyy-MM-dd").parse(data.getString("released"))
                        )
                    )
                }

                callback(arrayList)
            },
            { error ->
                Log.d("RESPONSE_ERROR", error.toString())
            }
        )

        queue.add(request)
    }

    fun getGame(id: Number, callback: (Game) -> Unit) {
        val request = JsonObjectRequest(Request.Method.GET, endpoint + "games/" + id.toString() + "?key=" + apiKey, null,
            { response ->
                val platforms = ArrayList<String>();

                val platformsArray = response.getJSONArray("platforms");
                for (i in 0 until platformsArray.length()) {
                    val data = platformsArray.getJSONObject(i)
                    val platform = data.getJSONObject("platform");

                    platforms.add(platform.getString("name"))
                }

                val stores = ArrayList<Store>()
                val storesArray = response.getJSONArray("stores");
                for (i in 0 until storesArray.length()) {
                    val data = storesArray.getJSONObject(i)
                    val store = data.getJSONObject("store");

                    stores.add(Store(store.getString("name"), store.getString("domain")))
                }

                callback(
                    Game(
                        response.getString("name"),
                        response.getString("background_image"),
                        response.getString("description_raw"),
                        getKeys(response.getJSONArray("genres"), "name"),
                        platforms,
                        getKeys(response.getJSONArray("developers"), "name"),
                        stores
                    )
                )
            },
            { error ->
                Log.d("RESPONSE_ERROR", error.toString())
            }
        )

        queue.add(request)
    }

    private fun getKeys(array: JSONArray, key: String): ArrayList<String> {
        val newArray = ArrayList<String>()

        for (i in 0 until array.length()) {
            val data = array.getJSONObject(i)

            newArray.add(data.getString(key))
        }

        return newArray
    }
}