package ru.patriotovsky.gamesdatabase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.patriotovsky.gamesdatabase.databinding.ActivityAuthorsBinding

class AuthorsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthorsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthorsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}