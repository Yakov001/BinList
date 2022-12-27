package com.example.binlist.model.room

import com.example.binlist.model.CardResponse
import java.util.UUID

class BinRepository(private val binDatabase: BinDAO) {

    suspend fun getAll() = binDatabase.getAll()

    suspend fun getById(id: UUID) = binDatabase.getById(id)

    suspend fun insert(item: CardResponse) = binDatabase.insert(item)
}