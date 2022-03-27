package com.yago.gas.domain.injector

import android.app.Application
import androidx.room.Room
import com.yago.gas.data.api.GasBackendApi
import com.yago.gas.data.api.RestAdapter
import com.yago.gas.ui.binding.GasDataBindingComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideDataBindingComponent(application: Application): GasDataBindingComponent =
        GasDataBindingComponent(application)

    @Singleton
    @Provides
    fun provideGasBackendApi(restAdapter: RestAdapter): GasBackendApi =
        restAdapter.getGasBackendApi()

}