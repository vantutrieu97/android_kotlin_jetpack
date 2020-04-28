package vantutrieu97.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vantutrieu97.myapplication.models.Animal

class AnimalDetailViewModel : ViewModel() {
    val animal = MutableLiveData<Animal>()

    fun fetch() {
        val animalTemporal = Animal(
            "0-temporal",
            "Breed-temporal",
            "10 years-temporal",
            "Group 0-temporal",
            "For 0-temporal",
            "Temperanment 0-temporal",
            ""
        )
        animal.value = animalTemporal
    }
}