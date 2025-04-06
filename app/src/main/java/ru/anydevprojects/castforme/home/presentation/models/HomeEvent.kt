package ru.anydevprojects.castforme.home.presentation.models

sealed interface HomeEvent {
    data object OpenFileChoose : HomeEvent
}