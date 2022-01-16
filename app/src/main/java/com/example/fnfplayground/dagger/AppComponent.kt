package com.example.fnfplayground.dagger

import android.app.Application
import com.example.fnfplayground.MainActivity
import com.example.fnfplayground.fragments.SettingsFragment

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(settings: SettingsFragment)
    fun inject(mainActivity: MainActivity)

}
class MyApplication: Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}