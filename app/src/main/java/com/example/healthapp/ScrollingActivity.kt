package com.example.healthapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.healthapp.databinding.ActivityScrollingBinding
import androidx.core.content.edit

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding
    private val sharedPrefs by lazy { getSharedPreferences("meal_prefs", MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using view binding
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load saved meals on startup
        loadMeals()

        // Save meal when button is clicked
        binding.saveButton.setOnClickListener {
            val meal = binding.mealInput.text.toString().trim()
            if (meal.isNotEmpty()) {
                saveMeal(meal)
                binding.mealInput.text.clear()
            }
        }
    }

    private fun saveMeal(meal: String) {
        // Get existing meals and add the new one
        val meals = sharedPrefs.getStringSet("mealList", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        meals.add(meal)

        // Save to SharedPreferences
        sharedPrefs.edit() {
            putStringSet("mealList", meals)
        }

        loadMeals()
    }

    private fun loadMeals() {
        val meals = sharedPrefs.getStringSet("mealList", mutableSetOf()) ?: mutableSetOf()
        binding.mealList.text = if (meals.isNotEmpty()) meals.joinToString("\n") else "No meals recorded yet"
    }
}
