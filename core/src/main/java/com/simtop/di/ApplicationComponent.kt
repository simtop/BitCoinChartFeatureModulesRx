package com.simtop.di

import dagger.BindsInstance
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        UrlModule::class
    ]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: BitCoinAppApplication): ApplicationComponent
    }
    @Named("baseUrl")
    fun provideBitCoinUrl(): String
}
