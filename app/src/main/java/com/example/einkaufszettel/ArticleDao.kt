package com.example.einkaufszettel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles ORDER BY createdAt")
    fun observeAll(): Flow<List<Article>>

    @Query("SELECT * FROM articles WHERE done = 0")
    suspend fun getOpen(): List<Article>

    @Insert
    suspend fun insert(article: Article)

    @Query("UPDATE articles SET done = :done WHERE id = :id")
    suspend fun setDone(id: String, done: Boolean)

    @Query("UPDATE articles SET quantity = :quantity WHERE id = :id")
    suspend fun setQuantity(id: String, quantity: Int)

    // inside ArticleDao:
    @Query("UPDATE articles SET categoryId = :categoryId WHERE id = :id")
    suspend fun setCategory(id: String, categoryId: String)

    @Query("DELETE FROM articles WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM articles WHERE done = 1")
    suspend fun deleteDone()
}

// at the end of the file (add imports for OnConflictStrategy if missing)
@Dao
interface CategoryMappingDao {
    @Query("SELECT categoryId FROM category_mappings WHERE nameKey = :key")
    suspend fun get(key: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(mapping: CategoryMapping)
}