package vantutrieu97.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import vantutrieu97.myapplication.models.Animal
import vantutrieu97.myapplication.models.AnimalDatabase

class AnimalDetailViewModel(application: Application) : BaseViewModel(application) {
    val animal = MutableLiveData<Animal>()

    fun fetch(uuid: String) {
        launch {
            val dao = AnimalDatabase(getApplication()).animalDao()
            val animalMatched = dao.getAnimalByID(uuid.toInt())
            animal.value = animalMatched
        }
    }
}