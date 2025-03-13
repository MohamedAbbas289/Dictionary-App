package com.example.dictionaryapp.feature_dictionary.data.repo

import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.feature_dictionary.data.local.WordInfoDao
import com.example.dictionaryapp.feature_dictionary.data.remote.DictionaryApi
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo
import com.example.dictionaryapp.feature_dictionary.domain.repo.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WordInfoRepositoryImpl @Inject constructor(
    private val api: DictionaryApi,
    private val dao: WordInfoDao
) : WordInfoRepository {
    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())

        val wordInfos = dao.getWordInfos(word).map {
            it.toWordInfo()
        }

        try {
            val wordInfoResponse = api.getWordInfo(word)
            dao.deleteWordInfos(wordInfoResponse.map { it.word })
            dao.insertWordInfos(wordInfoResponse.map { it.toWordInfoEntity() })
        } catch (e: HttpException) {
            emit(Resource.Failure("error: ${e.message()}"))
        } catch (e: IOException) {
            emit(Resource.Failure("error: ${e.message}"))
        }

        val newWordInfos = dao.getWordInfos(word).map {
            it.toWordInfo()
        }
        emit(Resource.Success(newWordInfos))
    }
}