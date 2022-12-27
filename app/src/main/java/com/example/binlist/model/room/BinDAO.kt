package com.example.binlist.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.binlist.model.CardResponse
import java.util.UUID

@Dao
interface BinDAO {

    @Query("SELECT * FROM card_responses ORDER BY requestTimeMillis")
    fun getAll() : List<CardResponse>

    @Query("SELECT * FROM card_responses WHERE id = :id")
    fun getById(id : UUID) : CardResponse

    @Insert
    fun insert(item: CardResponse)
}