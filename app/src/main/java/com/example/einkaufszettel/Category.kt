package com.example.einkaufszettel

import androidx.annotation.StringRes

enum class Category(@StringRes val labelRes: Int) {
    FRUIT_VEG(R.string.cat_fruit_veg),
    DELI(R.string.cat_deli),
    ORGANIC(R.string.cat_organic),
    BAKERY(R.string.cat_bakery),
    COFFEE_TEA(R.string.cat_coffee_tea),
    BREAKFAST(R.string.cat_breakfast),
    STAPLES(R.string.cat_staples),
    SWEETS(R.string.cat_sweets),
    SPECIALTIES(R.string.cat_specialties),
    PASTA_RICE(R.string.cat_pasta_rice),
    OIL_SPICES(R.string.cat_oil_spices),
    DAIRY(R.string.cat_dairy),
    MEAT_CHEESE(R.string.cat_meat_cheese),
    ALCOHOL(R.string.cat_alcohol),
    DRINKS(R.string.cat_drinks),
    EGGS(R.string.cat_eggs),
    SNACKS(R.string.cat_snacks),
    CANNED(R.string.cat_canned),
    HOUSEHOLD(R.string.cat_household),
    DRUGSTORE(R.string.cat_drugstore),
    FROZEN(R.string.cat_frozen),
    OTHER(R.string.cat_other);

    companion object {
        fun fromId(id: String?): Category = entries.firstOrNull { it.name == id } ?: OTHER
    }
}