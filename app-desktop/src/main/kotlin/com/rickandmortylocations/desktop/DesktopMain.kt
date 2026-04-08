package com.rickandmortylocations.desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.rickandmortylocations.data.di.DataModule
import com.rickandmortylocations.presentation.app.RickAndMortyDesktopApp
import com.rickandmortylocations.presentation.audio.AudioManager
import com.rickandmortylocations.presentation.di.PresentationModule
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun main() = application {
    val presentationModule = buildPresentationModule()

    Window(onCloseRequest = ::exitApplication, title = "Rick and Morty Locations") {
        RickAndMortyDesktopApp(presentationModule)
    }
}

private fun buildPresentationModule(): PresentationModule {
    val dataModule = buildDataModule()
    return PresentationModule(
        repository = dataModule.locationRepository,
        audioManager = AudioManager()
    )
}

private fun buildDataModule(): DataModule = DataModule(buildHttpClient())

private fun buildHttpClient(): HttpClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }
}
