package ru.patriotovsky.gamesdatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ru.patriotovsky.gamesdatabase.adapters.PosterAdapter
import ru.patriotovsky.gamesdatabase.databinding.ActivityMainBinding
import ru.patriotovsky.gamesdatabase.models.Poster
import ru.patriotovsky.gamesdatabase.rawg.api.RawgApi
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val adapter = PosterAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val api = RawgApi(this)
        api.getPosters { posters: ArrayList<Poster> ->
            appendGames(posters)
        }

        setContentView(binding.root)
        init()
    }

    private fun init() = with(binding) {
        posters.layoutManager = LinearLayoutManager(this@MainActivity)
        posters.adapter = adapter
    }

    private fun loading() {
        binding.postersLoader.visibility = View.VISIBLE
        binding.posters.visibility = View.GONE
    }

    private fun appendGames(array: ArrayList<Poster>) {
        array.forEach { game -> adapter.addGame(game) }
        binding.postersLoader.visibility = View.GONE
        binding.posters.visibility = View.VISIBLE
    }

    fun gameClick(view: View) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra(GameActivity.GAME_ID, view.tag.toString().toInt());

        startActivity(intent)
    }

    fun searchClick(view: View) {
        loading()
        adapter.removeAllGames()

        val api = RawgApi(this)
        api.searchPosters(binding.search.text.toString()) { posters: ArrayList<Poster> ->
            appendGames(posters)
        }
    }
}