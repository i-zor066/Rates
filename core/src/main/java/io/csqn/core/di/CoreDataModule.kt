package io.csqn.core.di

import dagger.Module
import dagger.Provides
import io.csqn.core.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class CoreDataModule {

    @Provides
    @Singleton
    fun providesRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(provideConverterFactory())
            .client(provideOkHttpClient())
    }

    private fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG)
                addInterceptor(provideLoggingInterceptor())
        }.build()
    }

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }
}
