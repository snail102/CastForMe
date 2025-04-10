package ru.anydevprojects.castforme.core.media.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.anydevprojects.castforme.core.media.AudioServiceHandler
import ru.anydevprojects.castforme.root.presentation.MainActivity
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MediaModule {

    companion object {

        @Provides
        @Singleton
        fun provideAudioAttributes(): AudioAttributes {
            return AudioAttributes.Builder()
                .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
                .setUsage(C.USAGE_MEDIA)
                .build()
        }

        @Provides
        @Singleton
        fun provideExoPlayer(@ApplicationContext context: Context): ExoPlayer {
            return ExoPlayer.Builder(context).build()
        }

        @Provides
        @Singleton
        fun provideMediaSession(
            @ApplicationContext context: Context,
            exoPlayer: ExoPlayer
        ): MediaSession {
            return MediaSession.Builder(context, exoPlayer)
                .also { builder ->
                    // Set the session activity to the PendingIntent returned by getSingleTopActivity() if it's not null
                    getSingleTopActivity(context)?.let { builder.setSessionActivity(it) }
                }
                .build() // Build the MediaSession instance
        }

        @Provides
        @Singleton
        fun provideAudioServiceHandler(
            @ApplicationContext context: Context,
            exoPlayer: ExoPlayer
        ): AudioServiceHandler {
            return AudioServiceHandler(context, exoPlayer)
        }
    }

}


private fun getSingleTopActivity(context: Context): PendingIntent? = PendingIntent.getActivity(
    context,
    0,
    Intent(context, MainActivity::class.java),
    immutableFlag or PendingIntent.FLAG_UPDATE_CURRENT
)

private val immutableFlag = PendingIntent.FLAG_IMMUTABLE
