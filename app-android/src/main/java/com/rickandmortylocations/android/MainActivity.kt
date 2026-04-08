package com.rickandmortylocations.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.rickandmortylocations.data.di.DataModule
import com.rickandmortylocations.presentation.app.RickAndMortyMobileApp
import com.rickandmortylocations.presentation.audio.AudioManager
import com.rickandmortylocations.presentation.di.PresentationModule
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val presentationModule = buildPresentationModule()
        applicationContext.showShortToast("Rick and Morty Locations pret")

        setContent {
            RickAndMortyMobileApp(presentationModule)
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

    private fun buildHttpClient(): HttpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
}
