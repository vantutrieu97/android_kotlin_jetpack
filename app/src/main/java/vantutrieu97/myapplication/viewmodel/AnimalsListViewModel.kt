package vantutrieu97.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import vantutrieu97.myapplication.models.Animal
import vantutrieu97.myapplication.models.AnimalService

class AnimalsListViewModel : ViewModel() {
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
                        animals.value = t
                        loading.value = false
                        animalLoadError.value = false
                    }

                    override fun onError(e: Throwable?) {
                        loading.value = false
                        animalLoadError.value = true
                    }

                })

        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}