package com.example.marvel.di

import com.example.marvel.Constants
import com.example.marvel.api.MarvelApiInterceptor
import com.example.marvel.api.MarvelApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideOkhttp(): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(MarvelApiInterceptor()).build()

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, client: OkHttpClient): MarvelApiService =
        Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(MarvelApiService::class.java)
}
