package io.csqn.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.Component
import retrofit2.Retrofit

import javax.inject.Singleton

@Singleton
@Component(modules = [CoreModule::class, CoreDataModule::class])
interface CoreComponent {
    fun application(): Application
    fun context(): Context
    fun sharedPreferences(): SharedPreferences
    fun retrofitBuilder(): Retrofit.Builder
    fun gson(): Gson
}
