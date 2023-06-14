package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


/**DAO = Data Access Object
 * The database operations can take a long time to execute, so they need to run on a separate thread. Room doesn't allow database access on the main thread.*/

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    /**This is to retrieve a specific item using ID as identifier*/
    @Query("SELECT * from item WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    /**To retrieve all items*/
    @Query("SELECT * FROM item ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>

}