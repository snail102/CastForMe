package ru.anydevprojects.castforme.fileReader.domain

interface FileReader {

    suspend fun getContentFromFile(filePath: String): Result<String>
}