package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    /**companion object, which allows access to the methods to create or get the database and uses the class name as the qualifier.
     * The Instance variable keeps a reference to the database, when one has been created. This helps maintain a single instance of the database opened at a given time, which is an expensive resource to create and maintain.*/
    companion object {
        @Volatile
        private var Instance: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }

    }
    /**Wrapping the code to get the database inside a synchronized block means that only one thread of execution at a time can enter this block of code, which makes sure the database only gets initialized once.*/
}