package app.itsyour.chakra.android.feature.main

import app.itsyour.chakra.android.app.di.scope.FragmentScoped
import app.itsyour.chakra.android.feature.main.feed.FeedFragment
import app.itsyour.chakra.android.feature.main.location.LocationFragment
import app.itsyour.chakra.android.feature.main.profile.ProfileFragment
import dagger.android.ContributesAndroidInjector

@dagger.Module
abstract class MainModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun feedFragment(): FeedFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun mainFragment(): LocationFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun no1Fragment(): ProfileFragment
}