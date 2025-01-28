package com.example.dictionaryapp.feature_dictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionaryapp.feature_dictionary.domain.model.Meaning

@Entity
class WordInfoEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    val meanings: List<Meaning>? = null
    val word: String? = null
    val phonetic: String? = null
}