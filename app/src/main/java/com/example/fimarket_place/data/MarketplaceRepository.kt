package com.example.fimarket_place.data

import com.example.fimarket_place.model.EmiPlan
import com.example.fimarket_place.model.Offer
import com.example.fimarket_place.model.Product
import com.example.fimarket_place.model.ProductVariant
import kotlinx.coroutines.delay

interface MarketplaceRepository {
    suspend fun getProducts(): Result<List<Product>>
    suspend fun getOffers(): Result<List<Offer>>
    suspend fun getCategories(): Result<List<String>>
}

class MockMarketplaceRepository : MarketplaceRepository {
    override suspend fun getProducts(): Result<List<Product>> {
        delay(800)
        return try {
            val genericEmi = { price: Double ->
                listOf(
                    EmiPlan("e1", 3, price / 3),
                    EmiPlan("e2", 6, price / 6),
                    EmiPlan("e3", 9, price / 9),
                    EmiPlan("e4", 12, price / 12)
                )
            }

            val products = listOf(
                Product(
                    id = "p1",
                    name = "Apple iPhone 15",
                    imageUrl = "",
                    description = "Super Retina XDR display, Advanced camera system.",
                    category = "Electronics",
                    details = "The iPhone 15 features a durable color-infused glass and aluminum design, the Dynamic Island, a 48MP Main camera, and USB-C.",
                    variants = listOf(
                        ProductVariant("v1_1", "128GB", 79900.0, 85000.0, 6, genericEmi(79900.0)),
                        ProductVariant("v1_2", "256GB", 89900.0, 95000.0, 5, genericEmi(89900.0)),
                        ProductVariant("v1_3", "512GB", 109900.0, 115000.0, 4, genericEmi(109900.0))
                    )
                ),
                Product(
                    id = "p2",
                    name = "Samsung Galaxy S24 Ultra",
                    imageUrl = "",
                    description = "Galaxy AI is here. Stunning 200MP camera.",
                    category = "Electronics",
                    details = "Meet Galaxy S24 Ultra, the ultimate form of Galaxy Ultra with a new titanium exterior and a 6.8\" flat display.",
                    variants = listOf(
                        ProductVariant("v2_1", "256GB / 12GB RAM", 129999.0, 134999.0, 3, genericEmi(129999.0)),
                        ProductVariant("v2_2", "512GB / 12GB RAM", 139999.0, 144999.0, 3, genericEmi(139999.0))
                    )
                ),
                Product(
                    id = "p3",
                    name = "Sony WH-1000XM5",
                    imageUrl = "",
                    description = "Industry-leading noise cancellation.",
                    category = "Accessories",
                    details = "With two processors controlling eight microphones, Auto NC Optimizer for automatically optimizing noise cancelling based on your wearing conditions and environment.",
                    variants = listOf(
                        ProductVariant("v3_1", "Black", 29990.0, 34990.0, 14, genericEmi(29990.0)),
                        ProductVariant("v3_2", "Silver", 29990.0, 34990.0, 14, genericEmi(29990.0))
                    )
                ),
                Product(
                    id = "p4",
                    name = "Levis 501 Original Jeans",
                    imageUrl = "",
                    description = "The original button fly jeans since 1873.",
                    category = "Fashion",
                    details = "Close your eyes. Think “jeans.” Now open. They were 501s®, right? They’re literally the blueprint for every pair of jeans in existence.",
                    variants = listOf(
                        ProductVariant("v4_1", "32W x 32L", 4599.0, 5999.0, 23, genericEmi(4599.0)),
                        ProductVariant("v4_2", "34W x 32L", 4599.0, 5999.0, 23, genericEmi(4599.0)),
                        ProductVariant("v4_3", "36W x 32L", 4799.0, 6199.0, 22, genericEmi(4799.0))
                    )
                )
            )
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getOffers(): Result<List<Offer>> {
        return Result.success(listOf(
            Offer("o1", "FESTIVE SALE", "Up to 12 Months No-Cost EMI on Flagships", "", "0% Interest"),
            Offer("o2", "HOLIDAY SPECIAL", "Book your dream trip with 1Fi Dues", "", "Starts @ ₹1,999/mo"),
            Offer("o3", "TECH UPGRADE", "Exchange your old phone for a new iPhone 15", "", "Save up to ₹20k")
        ))
    }

    override suspend fun getCategories(): Result<List<String>> {
        return Result.success(listOf("All", "Electronics", "Fashion", "Accessories", "Home & Living"))
    }
}
