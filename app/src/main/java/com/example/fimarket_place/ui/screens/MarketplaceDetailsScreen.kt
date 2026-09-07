package com.example.fimarket_place.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fimarket_place.model.EmiPlan
import com.example.fimarket_place.model.Product
import com.example.fimarket_place.model.ProductVariant
import com.example.fimarket_place.ui.components.EmiPlanItem
import com.example.fimarket_place.ui.components.SuccessAnimationView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketplaceDetailsScreen(
    product: Product,
    onBack: () -> Unit
) {
    var selectedVariant by remember { mutableStateOf(product.variants.first()) }
    var selectedEmiPlan by remember { mutableStateOf<EmiPlan?>(null) }
    var isBookingSuccess by remember { mutableStateOf(false) }

    AnimatedContent(targetState = isBookingSuccess, label = "success_flow") { success ->
        if (success) {
            SuccessAnimationView(onDone = onBack)
        } else {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Product Details", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                        navigationIcon = {
                            IconButton(onClick = onBack) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .background(Color(0xFFF9F9F9)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(product.name, color = Color.LightGray, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = product.name, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                        Text(text = product.description, color = Color.Gray, fontSize = 16.sp)
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "₹${selectedVariant.price.toInt()}",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary
                            )
                            if (selectedVariant.originalPrice != null) {
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "₹${selectedVariant.originalPrice?.toInt()}",
                                    fontSize = 18.sp,
                                    color = Color.Gray,
                                    textDecoration = TextDecoration.LineThrough
                                )
                            }
                        }

                        if (product.variants.size > 1) {
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(text = "Choose Variant", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            LazyRow {
                                items(product.variants) { variant ->
                                    FilterChip(
                                        selected = selectedVariant == variant,
                                        onClick = { 
                                            selectedVariant = variant 
                                            selectedEmiPlan = null // Reset EMI when variant changes
                                        },
                                        label = { Text(variant.name) },
                                        modifier = Modifier.padding(end = 8.dp),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        Text(text = "Select EMI Plan", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(text = "Backed by your investments", color = Color.Gray, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        LazyRow(modifier = Modifier.fillMaxWidth()) {
                            items(selectedVariant.emiPlans) { plan ->
                                EmiPlanItem(
                                    plan = plan,
                                    isSelected = selectedEmiPlan == plan,
                                    onSelect = { selectedEmiPlan = plan }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Text(text = "Product Highlights", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = product.details, color = Color.DarkGray, fontSize = 14.sp, lineHeight = 20.sp)

                        Spacer(modifier = Modifier.height(40.dp))
                        
                        Button(
                            onClick = { isBookingSuccess = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            enabled = selectedEmiPlan != null
                        ) {
                            Text(
                                text = if (selectedEmiPlan != null) "Proceed with ₹${selectedEmiPlan?.monthlyAmount?.toInt()}/mo" else "Select an EMI plan",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}
