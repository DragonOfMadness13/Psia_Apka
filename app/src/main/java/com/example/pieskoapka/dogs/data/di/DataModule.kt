package com.example.pieskoapka.dogs.data.di

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pieskoapka.dogs.data.local.DogEntity

@Database(entities = [DogEntity::class], version = 2, autoMigrations = [
    AutoMigration(from = 1, to = 2)
])
abstract class AppDatabase: RoomDatabase() {
    abstract fun dogDao(): DogEntityDao
}