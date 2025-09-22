package com.route.newsappc42gsunwed.model

import com.route.newsappc42gsunwed.R
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDM(
    val title: Int? = null,
    val image: Int? = null,
    val apiId: String? = null
) {
    companion object {
        fun getCategoriesList(): List<CategoryDM> {
            return listOf(
                CategoryDM(title = R.string.general, R.drawable.general, "general"),
                CategoryDM(title = R.string.business, R.drawable.business, "business"),
                CategoryDM(title = R.string.sports, R.drawable.sports, "sports"),
                CategoryDM(title = R.string.technology, R.drawable.technology, "technology"),
                CategoryDM(
                    title = R.string.entertainment,
                    R.drawable.entertainment,
                    "entertainment"
                ),
                CategoryDM(title = R.string.science, R.drawable.science, "science"),
                CategoryDM(title = R.string.medicine, R.drawable.medicine, "health"),

                )
        }
    }

}
