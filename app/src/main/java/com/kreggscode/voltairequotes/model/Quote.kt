package com.kreggscode.voltairequotes.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "quotes")
data class Quote(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val Number: Int? = null,
    val Category: String,
    val Quote: String,
    val French: String,
    val Work: String,
    val Year: Int,
    val Tags: String,
    val Context: String,
    val Popularity: String,
    val Reference: String,
    val Bio: String? = null,
    val isFavorite: Boolean = false
)

// For merging datasets
fun Quote.toMergedQuote(): Quote {
    return this.copy(
        French = French.replace("[translate:", "").replace("]", "").trim()
    )
}
