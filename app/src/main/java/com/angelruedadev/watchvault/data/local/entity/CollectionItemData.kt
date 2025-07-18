package com.angelruedadev.watchvault.data.local.entity

interface CollectionItemData {
    val id: Int
    val title: String
    val userRating: Int
    val photoPath: String?
}