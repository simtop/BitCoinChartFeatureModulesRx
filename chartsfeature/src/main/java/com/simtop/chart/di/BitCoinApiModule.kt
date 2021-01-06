package com.simtop.chart.di

import dagger.Module
import dagger.Provides
import com.simtop.chart.data.network.BitCoinService
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object BitCoinApiModule {

    private const val BASE_URL = "baseUrl"

    @Provides
    @Singleton
    fun provideBitCoinApi(retrofit: Retrofit): BitCoinService =
        retrofit.create(BitCoinService::class.java)

    @Provides
    @Singleton
    fun provideLogginInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Provides
    @Singleton
    fun providesBaseHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        @Named(BASE_URL) baseUrl: String,
        converterFactory: Converter.Factory,
        httpClient: OkHttpClient.Builder,
        callAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(callAdapterFactory)
            .addConverterFactory(converterFactory)
            .baseUrl(baseUrl)
            .client(httpClient.build())
            .build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }
}