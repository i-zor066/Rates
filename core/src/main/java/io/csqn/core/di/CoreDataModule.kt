package io.csqn.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import io.csqn.core.BuildConfig
import io.csqn.core.network.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class CoreDataModule {

    @Provides
    @Singleton
    fun providesRetrofitBuilder(context: Context): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(provideConverterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(provideOkHttpClient(context))
    }

    private fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    private fun provideOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(NetworkConnectionInterceptor(context))
            if (BuildConfig.DEBUG)
                addInterceptor(provideLoggingInterceptor())
        }.build()
    }

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }
}
