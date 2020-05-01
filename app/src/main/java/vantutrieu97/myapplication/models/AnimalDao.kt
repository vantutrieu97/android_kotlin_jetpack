package vantutrieu97.myapplication.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AnimalDao {
    @Insert
    suspend fun insertAll(vararg animal: Animal): List<Long>

    @Query("select * from animal")
    suspend fun getAllAnimals(): List<Animal>

    @Query("select * from animal where uuid = :animalId")
    suspend fun getAnimalByID(animalId: Int): Animal

    @Query("delete from animal")
    suspend fun deleteAnimals()
}