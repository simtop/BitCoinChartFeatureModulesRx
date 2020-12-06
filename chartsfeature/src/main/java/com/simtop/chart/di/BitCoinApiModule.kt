package com.simtop.chart.di

import dagger.Module
import dagger.Provides
import com.simtop.chart.data.network.BitCoinService
import com.simtop.di.FeatureScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
object BitCoinApiModule {

    private const val BASE_URL = "baseUrl"

    @Provides
    @FeatureScope
    fun provideBitCoinApi(retrofit: Retrofit): BitCoinService =
        retrofit.create(BitCoinService::class.java)

    @Provides
    @FeatureScope
    fun provideLogginInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Provides
    @FeatureScope
    fun providesBaseHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
        }
    }

    @Provides
    @FeatureScope
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
    @FeatureScope
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @FeatureScope
    fun provideAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }
}