package com.example.einkaufszettel

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

object Defaults {
    const val GROUP = "local"        // later: real group ID from the cloud
    const val LIST = "default"       // later: multiple lists
}

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val groupId: String = Defaults.GROUP,
    val listId: String = Defaults.LIST,
    val name: String,
    val quantity: Int = 1,
    val categoryId: String? = null,   // filled by the store feature later
    val done: Boolean = false,        // true = in the shopping cart
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "category_mappings")
data class CategoryMapping(
    @PrimaryKey val nameKey: String,   // normalized article name
    val categoryId: String
)