package com.yago.gas.domain.injector

import com.yago.gas.ui.views.main.DetailFragment
import com.yago.gas.ui.views.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailFragment

}