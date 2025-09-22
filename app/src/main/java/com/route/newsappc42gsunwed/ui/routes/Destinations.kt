package com.route.newsappc42gsunwed.ui.routes

import com.route.newsappc42gsunwed.model.CategoryDM
import kotlinx.serialization.Serializable

@Serializable
object CategoriesDestination

@Serializable
data class NewsDestination(val categoryAPIId: String)

