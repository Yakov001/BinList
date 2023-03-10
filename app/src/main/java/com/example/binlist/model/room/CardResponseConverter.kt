package com.example.binlist.model.room

import androidx.room.TypeConverter
import com.example.binlist.model.Bank
import com.example.binlist.model.Country
import com.example.binlist.model.Number

class CardResponseConverter {
    @TypeConverter
    fun fromBank(bank: Bank?) : String? {
        if (bank == null) return null
        return "${bank.city?.replace(" ", "_")} ${bank.name?.replace(" ", "_")} ${bank.url} ${bank.phone}"
    }
    @TypeConverter
    fun toBank(s: String?) : Bank? {
        if (s == null) return null
        s.split(" ").also {
            return Bank(
                city = if (it[0] == "null") null else it[0].replace("_", " "),
                name = if (it[1] == "null") null else it[1].replace("_", " "),
                url = if (it[2] == "null") null else it[2],
                phone = if (it[3] == "null") null else it[3]
            )
        }
    }

    @TypeConverter
    fun fromCountry(country: Country?) : String? {
        if (country == null) return null
        return "${country.longitude?.toString()} ${country.latitude?.toString()} ${country.name?.replace(" ", "_")} " +
                "${country.alpha2} ${country.currency} ${country.emoji} ${country.numeric}"
    }

    @TypeConverter
    fun toCountry(s: String?) : Country? {
        if (s == null) return null
        s.split(" ").also {
            return Country(
                longitude = it[0].toIntOrNull(),
                latitude = it[1].toIntOrNull(),
                name = if (it[2] == "null") null else it[2].replace("_", " "),
                alpha2 = if (it[3] == "null") null else it[3],
                currency = if (it[4] == "null") null else it[4],
                emoji = if (it[5] == "null") null else it[5],
                numeric = if (it[6] == "null") null else it[6],
            )
        }
    }

    @TypeConverter
    fun fromNumber(number : Number?) : String? {
        if (number == null) return null
        return "${number.length} ${number.luhn}"
    }

    @TypeConverter
    fun toNumber(s : String?) : Number? {
        if (s == null) return null
        s.split(" ").also {
            return Number(
                length = it[0].toIntOrNull(),
                luhn = it[1].toBooleanStrictOrNull()
            )
        }
    }
}