package edu.farmingdale.pizzapartybottomnavbar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.math.ceil

class PizzaPartyViewModel : ViewModel() {
    var numPeople by mutableStateOf("")
    var slicesPerPerson by mutableStateOf(0)
    var totalPizzas by mutableStateOf(0)

    private val slicesPerPizza = 8

    fun calculatePizzas() {
        val numPeopleInt = numPeople.toIntOrNull() ?: 0
        totalPizzas = ceil(numPeopleInt * slicesPerPerson / slicesPerPizza.toDouble()).toInt()
    }

    fun updateSlicesPerPerson(hungerLevel: String) {
        slicesPerPerson = when (hungerLevel) {
            "Light" -> 2
            "Medium" -> 3
            "Hungry" -> 4
            "Very hungry" -> 5
            else -> 3
        }
    }
}

