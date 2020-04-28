package vantutrieu97.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vantutrieu97.myapplication.models.AnimalBreed

class AnimalDetailViewModel : ViewModel() {
    val animal = MutableLiveData<AnimalBreed>()

    fun fetch() {
        val animalTemporal = AnimalBreed(
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