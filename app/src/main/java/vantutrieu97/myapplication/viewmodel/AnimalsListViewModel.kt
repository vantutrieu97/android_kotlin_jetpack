package vantutrieu97.myapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import vantutrieu97.myapplication.models.Animal
import vantutrieu97.myapplication.models.AnimalDatabase
import vantutrieu97.myapplication.models.AnimalService

class AnimalsListViewModel(application: Application) : BaseViewModel(application) {
    private val animalService = AnimalService()
    private val disposable = CompositeDisposable()

    val animals = MutableLiveData<ArrayList<Animal>>()
    val animalLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val TAG = "ViewModel_Flow"

    fun refresh() {
        Log.i(TAG, "refresh() start")
        fetchFromRemote()
        Log.i(TAG, "refresh() done")
    }

    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            animalService.getAnimals()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ArrayList<Animal>>() {
                    override fun onSuccess(t: ArrayList<Animal>?) {
                        if (t != null) {
                            storeAnimalsLocally(t)
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
            Log.i("BaseViewModel", "launch")
            val dao = AnimalDatabase(getApplication()).animalDao()
//            dao.deleteAnimals()
            val result = dao.insertAll(*newAnimalsList.toTypedArray())

            var i = 0
            while (i < newAnimalsList.size) {
                newAnimalsList[i].uuid = result[i].toInt()
                ++i
            }
            animalsRetrieved(newAnimalsList)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}