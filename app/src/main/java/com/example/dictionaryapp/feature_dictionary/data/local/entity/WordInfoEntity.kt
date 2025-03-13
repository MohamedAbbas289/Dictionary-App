package com.example.dictionaryapp.feature_dictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionaryapp.feature_dictionary.domain.model.License
import com.example.dictionaryapp.feature_dictionary.domain.model.Meaning
import com.example.dictionaryapp.feature_dictionary.domain.model.Phonetic
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo

@Entity
class WordInfoEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val meanings: List<Meaning>? = null,
    val word: String? = null,
    val phonetic: String? = null,
    val phonetics: List<Phonetic>? = null,
    val sourceUrls: List<String>? = null,
    val license: License? = null
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            license = license,
            meanings = meanings,
            phonetic = phonetic,
            phonetics = phonetics,
            sourceUrls = sourceUrls,
            word = word
        )
    }
}