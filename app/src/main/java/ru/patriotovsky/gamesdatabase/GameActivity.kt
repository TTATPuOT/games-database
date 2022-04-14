package ru.patriotovsky.gamesdatabase

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import ru.patriotovsky.gamesdatabase.databinding.ActivityGameBinding
import ru.patriotovsky.gamesdatabase.rawg.api.RawgApi

class GameActivity : AppCompatActivity() {
    companion object {
        const val GAME_ID = ""
    }
    lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loader.visibility = View.VISIBLE
        binding.gameContent.visibility = View.GONE

        val api = RawgApi(this)
        api.getGame(intent.getIntExtra(GAME_ID, 0)) { game ->
            Picasso.get().load(game.imageUrl).placeholder(R.color.black).into(binding.banner)
            binding.banner.visibility = View.VISIBLE

            binding.title.text = game.title
            binding.description.text = game.description
            binding.genresList.text = game.generes.joinToString(separator = ", ")
            binding.platformsList.text = game.platforms.joinToString(separator = ", ")
            binding.developerList.text = game.developers.joinToString(separator = ", ")

            binding.loader.visibility = View.GONE
            binding.gameContent.visibility = View.VISIBLE
        }

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
    }
}