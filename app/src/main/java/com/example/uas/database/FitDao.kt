package com.example.uas.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FitDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(fitEntity: FitEntity)

    @Delete
    fun delete(fitEntity: FitEntity)

    @Query("SELECT * FROM fitnes_table ORDER BY _id ASC")
    fun getAllFits(): LiveData<List<FitEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM fitnes_table WHERE name = :name)")
    fun isFitFavorite(name: String): LiveData<Boolean>

    @Query("SELECT * FROM fitnes_table WHERE name = :name LIMIT 1")
    fun getFitByName(name: String): FitEntity?
}