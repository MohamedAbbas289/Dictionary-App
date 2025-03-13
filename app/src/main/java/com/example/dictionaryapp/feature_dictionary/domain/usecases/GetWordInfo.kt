package com.example.dictionaryapp.feature_dictionary.domain.usecases

import com.example.dictionaryapp.feature_dictionary.domain.repo.WordInfoRepository
import javax.inject.Inject

class GetWordInfo @Inject constructor(
    private val repository: WordInfoRepository
) {
    operator fun invoke(word: String) = repository.getWordInfo(word)
}