package vantutrieu97.myapplication.models

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface AnimalApi {
    @GET("devtides/dogsapi/master/dogs.json")
    fun getAnimal(): Single<ArrayList<Animal>>
}