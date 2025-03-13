package com.example.dictionaryapp.feature_dictionary.domain.model

class WordInfo(
    val license: License? = null,
    val meanings: List<Meaning>? = null,
    val phonetic: String? = null,
    val phonetics: List<Phonetic>? = null,
    val sourceUrls: List<String>? = null,
    val word: String? = null
)