package ru.anydevprojects.castforme.home.presentation.models

import android.net.Uri

sealed interface HomeIntent {
    data object OnImportBtnClick: HomeIntent
    data class SelectedImportFile(val uri: Uri?): HomeIntent
}