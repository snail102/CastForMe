package ru.anydevprojects.castforme.fileReader.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.anydevprojects.castforme.fileReader.data.FileReaderImpl
import ru.anydevprojects.castforme.fileReader.domain.FileReader
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FileReaderModule {

    @Binds
    @Singleton
    fun bindFileReader(impl: FileReaderImpl): FileReader
}