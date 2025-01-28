package com.example.dictionaryapp.feature_dictionary.domain.model

class WordInfo(
    val license: License?,
    val meaning: List<Meaning>?,
    val phonetic: String?,
    val phonetics: List<Phonetic>?,
    val sourceUrls: List<String>?,
    val word: String?
)