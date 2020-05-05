package vantutrieu97.myapplication.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import vantutrieu97.myapplication.models.Animal
import vantutrieu97.myapplication.models.AnimalDatabase
import vantutrieu97.myapplication.models.AnimalService
import vantutrieu97.myapplication.utils.NotificationHelper

class AnimalsListViewModel(application: Application) : BaseViewModel(application) {
    private val animalService = AnimalService()
    private val disposable = CompositeDisposable()

    val animals = MutableLiveData<ArrayList<Animal>>()
    val animalLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val TAG = "ViewModel_Flow"

    init {
        fetchFromRemote()
    }

    fun refresh() {
        fetchFromDatabase()
    }

    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val animalslistFromDatabase =
                AnimalDatabase(getApplication()).animalDao().getAllAnimals()
            animalsRetrieved(animalslistFromDatabase as ArrayList<Animal>)
            Toast.makeText(
                getApplication(),
                "Refreshed data from local database",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            animalService.getAnimals()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ArrayList<Animal>>() {
                    override fun onSuccess(t: ArrayList<Animal>?) {
                        if (t != null) {
                            storeAnimalsLocally(t)
                            NotificationHelper(getApplication()).createNotification()
                        }
                    }

                    override fun onError(e: Throwable?) {
                        loading.value = false
                        animalLoadError.value = true
                    }

                })

        )
    }

    private fun animalsRetrieved(animalsList: ArrayList<Animal>) {
        animals.value = animalsList
        loading.value = false
        animalLoadError.value = false
    }

    private fun storeAnimalsLocally(newAnimalsList: ArrayList<Animal>) {
        launch {
            val dao = AnimalDatabase(getApplication()).animalDao()
            dao.deleteAnimals()
            val result = dao.insertAll(*newAnimalsList.toTypedArray())

            var i = 0
            while (i < newAnimalsList.size) {
                newAnimalsList[i].uuid = result[i].toInt()
                ++i
            }
            Toast.makeText(getApplication(), "Saved data to local", Toast.LENGTH_SHORT).show()
            animalsRetrieved(newAnimalsList)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}