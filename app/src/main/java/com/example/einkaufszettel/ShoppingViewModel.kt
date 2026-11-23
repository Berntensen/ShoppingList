package com.example.einkaufszettel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ShoppingViewModel(app: Application) : AndroidViewModel(app) {

    private val repo: ArticleRepository = AppDatabase.get(app).let {
        RoomArticleRepository(it.articleDao(), it.categoryMappingDao())
    }

    private val prefs = app.getSharedPreferences("settings", Context.MODE_PRIVATE)

    val articles: StateFlow<List<Article>> = repo.articles
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    // Shopping mode survives app restarts, so items in the cart don't vanish
    private val _shopping = MutableStateFlow(prefs.getBoolean("shopping", false))
    val shopping: StateFlow<Boolean> = _shopping.asStateFlow()

    fun add(input: String) {
        val parsed = InputParser.parse(input)
        if (parsed.name.isEmpty()) return
        viewModelScope.launch { repo.addOrMerge(parsed.name, parsed.quantity) }
    }

    fun changeQuantity(article: Article, delta: Int) {
        val newValue = (article.quantity + delta).coerceIn(1, 999)
        if (newValue == article.quantity) return
        viewModelScope.launch { repo.setQuantity(article.id, newValue) }
    }

    fun setCategory(article: Article, category: Category) {
        viewModelScope.launch { repo.setCategory(article, category) }
    }

    fun setDone(id: String, done: Boolean) {
        viewModelScope.launch { repo.setDone(id, done) }
    }

    fun delete(id: String) {
        viewModelScope.launch { repo.delete(id) }
    }

    fun startShopping() = setShopping(true)

    fun finishShopping() {
        setShopping(false)
        viewModelScope.launch { repo.removeDone() }
    }

    private fun setShopping(value: Boolean) {
        _shopping.value = value
        prefs.edit().putBoolean("shopping", value).apply()
    }
}