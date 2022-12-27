package com.example.binlist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.binlist.model.room.CardResponseConverter
import java.util.*

@TypeConverters(CardResponseConverter::class)
@Entity(tableName = "card_responses")
data class CardResponse(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val bin: String? = null,
    val requestTimeMillis: Long? = null,

    val bank: Bank? = null,
    val brand: String? = null,
    val country: Country? = null,
    val number: Number? = null,
    val prepaid: Boolean? = null,
    val scheme: String? = null,
    val type: String? = null
)

data class Bank(
    val city: String? = null,
    val name: String? = null,
    val phone: String? = null,
    val url: String? = null
)

data class Country(
    val alpha2: String? = null,
    val currency: String? = null,
    val emoji: String? = null,
    val latitude: Int? = null,
    val longitude: Int? = null,
    val name: String? = null,
    val numeric: String? = null
)

data class Number(
    val length: Int? = null,
    val luhn: Boolean? = null
)