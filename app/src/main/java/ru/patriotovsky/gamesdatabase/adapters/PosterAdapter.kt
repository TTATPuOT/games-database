package ru.patriotovsky.gamesdatabase.adapters

import android.content.res.Resources.getSystem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.patriotovsky.gamesdatabase.R
import ru.patriotovsky.gamesdatabase.databinding.GameItemBinding
import ru.patriotovsky.gamesdatabase.models.Poster
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PosterAdapter: RecyclerView.Adapter<PosterAdapter.GameHolder>() {

    private val gamesList = ArrayList<Poster>()

    class GameHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = GameItemBinding.bind(item)
        private val picasso: Picasso = Picasso.get()
        private val posterHeight = (170 * getSystem().displayMetrics.density).toInt();

        fun bind(poster: Poster) = with(binding) {
            gamePoster.tag = poster.id
            posterTitle.text = poster.title
            posterDeveloper.text = poster.developer
            posterDate.text = SimpleDateFormat("MM.dd.yyyy", Locale.US).format(poster.releaseDate)

            picasso
                .load(poster.imageUrl)
                .resize(0, posterHeight)
                .into(this.poster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)

        return GameHolder(view)
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        holder.bind(gamesList[position])
    }

    override fun getItemCount(): Int {
        return gamesList.size
    }

    fun addGame(poster: Poster) {
        gamesList.add(poster)
        notifyDataSetChanged()
    }

    fun removeAllGames() {
        gamesList.clear()
        notifyDataSetChanged()
    }

}