package ru.anydevprojects.castforme.fileReader.data

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.anydevprojects.castforme.fileReader.domain.FileReader
import javax.inject.Inject

class FileReaderImpl @Inject constructor(
    @ApplicationContext
    private val applicationContext: Context
) : FileReader {

    override suspend fun getContentFromFile(filePath: String): Result<String> {
        return runCatching {
            val inputStream = applicationContext.contentResolver.openInputStream(
                Uri.parse(filePath)
            )
            inputStream?.use {
                it.bufferedReader().readText()
            }.orEmpty()
        }
    }
}