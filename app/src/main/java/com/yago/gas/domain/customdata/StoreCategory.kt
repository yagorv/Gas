package com.yago.gas.domain.customdata

sealed class StoreCategory {
    object FOOD : StoreCategory()
    object BEAUTY : StoreCategory()
    object SHOPPING : StoreCategory()
    object UNKNOWN : StoreCategory()

    companion object {

        private const val FOOD_VALUE = "FOOD"
        private const val BEAUTY_VALUE = "BEAUTY"
        private const val SHOPPING_VALUE = "SHOPPING"

        fun getFromValue(value: String?): StoreCategory =
            when (value) {
                FOOD_VALUE -> FOOD
                BEAUTY_VALUE -> BEAUTY
                SHOPPING_VALUE -> SHOPPING
                else -> {
                    UNKNOWN
                }
            }
    }
}