package ru.anydevprojects.castforme.root.presentation.models

sealed interface MainIntent {

    data object OnChangePlayState: MainIntent
}