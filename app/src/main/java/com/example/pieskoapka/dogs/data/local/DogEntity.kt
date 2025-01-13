package com.example.pieskoapka.dogs.data.local

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "dogs")
data class DogEntity(
    val name: String,

    @ColumnInfo(defaultValue = "0")
    val isFav: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}

//Dao jest odpowiedzialny za wykonywanie operacji na modelu danych wyświetlanie, sortowanie itp.
@Dao
interface DogEntityDao {

    @Query("SELECT * FROM dogs")
    fun getAllDogs(): Flow<List<DogEntity>> //wyświetlanie wszystkich

    @Query("SELECT * FROM dogs ORDER BY uid DESC LIMIT 10") //sortowanie
    fun getSortedDogs(): Flow<List<DogEntity>>

    @Query("SELECT * FROM dogs WHERE isFav = 1") //wyświetlanie ulubionych
    fun getAllFavDogs(): Flow<List<DogEntity>>

    @Query("UPDATE dogs SET isFav = CASE WHEN isFav = 1 THEN 0 ELSE 1 END WHERE uid = :id") //zmiana
    suspend fun triggerFavDog(id: Int)

    @Insert //wprowadzanie
    suspend fun insertDog(dog: DogEntity)

    @Query("DELETE FROM dogs WHERE uid = :id") //usuwanie
    suspend fun removeDog(id: Int)
}