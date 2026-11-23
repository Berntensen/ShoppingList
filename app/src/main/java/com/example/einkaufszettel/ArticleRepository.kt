package com.example.einkaufszettel

import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    val articles: Flow<List<Article>>
    suspend fun addOrMerge(name: String, quantity: Int)
    suspend fun setQuantity(id: String, quantity: Int)
    suspend fun setDone(id: String, done: Boolean)
    suspend fun setCategory(article: Article, category: Category)
    suspend fun delete(id: String)
    suspend fun removeDone()
}

class RoomArticleRepository(
    private val dao: ArticleDao,
    private val mappingDao: CategoryMappingDao
) : ArticleRepository {

    override val articles: Flow<List<Article>> = dao.observeAll()

    override suspend fun addOrMerge(name: String, quantity: Int) {
        val key = InputParser.normalize(name)
        val existing = dao.getOpen().firstOrNull { InputParser.normalize(it.name) == key }
        if (existing != null) {
            dao.setQuantity(existing.id, (existing.quantity + quantity).coerceAtMost(999))
        } else {
            dao.insert(Article(name = name, quantity = quantity, categoryId = resolveCategory(name).name))
        }
    }

    // Own assignment first, then the built-in dictionary, otherwise "Other"
    private suspend fun resolveCategory(name: String): Category {
        val saved = mappingDao.get(InputParser.normalize(name))
        if (saved != null) return Category.fromId(saved)
        return CategoryDictionary.lookup(name) ?: Category.OTHER
    }

    override suspend fun setCategory(article: Article, category: Category) {
        val key = InputParser.normalize(article.name)
        mappingDao.upsert(CategoryMapping(key, category.name))
        dao.setCategory(article.id, category.name)
        dao.getOpen()
            .filter { InputParser.normalize(it.name) == key }
            .forEach { dao.setCategory(it.id, category.name) }
    }

    override suspend fun setQuantity(id: String, quantity: Int) = dao.setQuantity(id, quantity)
    override suspend fun setDone(id: String, done: Boolean) = dao.setDone(id, done)
    override suspend fun delete(id: String) = dao.delete(id)
    override suspend fun removeDone() = dao.deleteDone()
}