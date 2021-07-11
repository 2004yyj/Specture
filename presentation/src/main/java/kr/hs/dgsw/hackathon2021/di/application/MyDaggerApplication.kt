package kr.hs.dgsw.hackathon2021.di.application

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import kr.hs.dgsw.hackathon2021.di.component.DaggerMyComponent
import kr.hs.dgsw.hackathon2021.di.component.MyComponent

class MyDaggerApplication : DaggerApplication() {

    lateinit var daggerMyComponent: MyComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        daggerMyComponent.inject(this)
        return daggerMyComponent
    }

    override fun onCreate() {
        daggerMyComponent = DaggerMyComponent.factory().create(this)
        super.onCreate()
    }

}