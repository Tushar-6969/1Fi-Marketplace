package com.example.fimarket_place.model

data class Product(
    val id: String,
    val name: String,
    val imageUrl: String,
    val description: String,
    val variants: List<ProductVariant>,
    val details: String,
    val category: String
)

data class ProductVariant(
    val id: String,
    val name: String,
    val price: Double,
    val originalPrice: Double? = null,
    val discountPercent: Int? = null,
    val emiPlans: List<EmiPlan> = emptyList()
)

data class Offer(
    val id: String,
    val title: String,
    val subtitle: String,
    val imageUrl: String,
    val priceText: String? = null
)

data class EmiPlan(
    val id: String,
    val months: Int,
    val monthlyAmount: Double,
    val interestRate: Double = 0.0,
    val isNoCost: Boolean = true
)

sealed class MarketplaceUiState {
    object Loading : MarketplaceUiState()
    data class Success(
        val products: List<Product>,
        val offers: List<Offer> = emptyList(),
        val categories: List<String> = emptyList()
    ) : MarketplaceUiState()
    data class Error(val message: String) : MarketplaceUiState()
}
