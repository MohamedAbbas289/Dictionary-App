package com.example.dictionaryapp.feature_dictionary.domain.model


class Meaning(
    val antonyms: List<String?>?,
    val definitions: List<Definition?>?,
    val partOfSpeech: String?,
    val synonyms: List<String?>?
)