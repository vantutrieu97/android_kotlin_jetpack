package vantutrieu97.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vantutrieu97.myapplication.models.Animal

class AnimalsListViewModel : ViewModel() {
    val animals = MutableLiveData<ArrayList<Animal>>()
    val animalLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val TAG = "ViewModel_Flow"

    fun refresh() {
        Log.i(TAG, "refresh() start")
        val animal0 =
            Animal("00", "Bread00", "10 years", "Group 00", "For 00", "Temperanment 00", "")
        val animal1 =
            Animal("01", "Bread01", "11 years", "Group 01", "For 01", "Temperanment 01", "")
        val animal2 =
            Animal("02", "Bread02", "12 years", "Group 02", "For 02", "Temperanment 02", "")
        val animal3 =
            Animal("03", "Bread03", "13 years", "Group 03", "For 03", "Temperanment 03", "")
        val animal4 =
            Animal("04", "Bread04", "14 years", "Group 04", "For 04", "Temperanment 04", "")
        val animal5 =
            Animal("05", "Bread05", "15 years", "Group 05", "For 05", "Temperanment 05", "")
        val animal6 =
            Animal("06", "Bread06", "16 years", "Group 06", "For 06", "Temperanment 06", "")
        val animalsListTemporal = arrayListOf<Animal>(
            animal0,
            animal1,
            animal2,
            animal3,
            animal4,
            animal5,
            animal6
        )
        animals.value = animalsListTemporal
        animalLoadError.value = false
        loading.value = false
        Log.i(TAG, "refresh() done")
    }
}