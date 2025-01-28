package com.example.dictionaryapp.feature_dictionary.data.remote.dto

import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo

data class WordInfoDto(
    val licenseDto: LicenseDto?,
    val meaning: List<MeaningDto>?,
    val phonetic: String?,
    val phonetics: List<PhoneticDto>?,
    val sourceUrls: List<String>?,
    val word: String?
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            license = licenseDto?.toLicense(),
            meaning = meaning?.map { it.toMeaning() },
            phonetic = phonetic.toString(),
            phonetics = phonetics?.map { it.toPhonetic() },
            sourceUrls = sourceUrls,
            word = word
        )
    }
}