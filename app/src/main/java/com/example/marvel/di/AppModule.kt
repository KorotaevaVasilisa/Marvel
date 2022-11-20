package com.example.marvel.di

import android.content.Context
import androidx.room.Room
import com.example.marvel.utils.Constants
import com.example.marvel.api.MarvelApiInterceptor
import com.example.marvel.api.MarvelApiService
import com.example.marvel.data.HeroesDb
import com.example.marvel.data.dao.HeroesDao
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    //?

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit =
        Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .build()


    @Provides
    @Singleton
    fun provideMarvelApiService(retrofit: Retrofit): MarvelApiService =
        retrofit.create(MarvelApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): HeroesDb =
        Room.databaseBuilder(
            context,
            HeroesDb::class.java,
            "marvel_database"
        ).build()

    @Provides
    @Singleton
    fun provideHeroesDao(heroesDb: HeroesDb): HeroesDao = heroesDb.getHeroesDao()

}
