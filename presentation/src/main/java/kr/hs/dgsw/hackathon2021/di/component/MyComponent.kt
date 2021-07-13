package kr.hs.dgsw.hackathon2021.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.di.module.*
import kr.hs.dgsw.hackathon2021.ui.view.activity.IntroActivity
import kr.hs.dgsw.hackathon2021.ui.view.activity.MainActivity
import kr.hs.dgsw.hackathon2021.ui.view.activity.SplashActivity
import kr.hs.dgsw.hackathon2021.ui.view.fragment.*
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    NetworkModule::class,
    RemoteModule::class,
    DataSourceModule::class,
    RepositoryModule::class,
    UseCaseModule::class
])
interface MyComponent: AndroidInjector<MyDaggerApplication> {

    fun inject(loginFragment: LoginFragment)
    fun inject(signUpInfoFragment: SignUpInfoFragment)
    fun inject(recruitingClassFragment: RecruitingClassFragment)
    fun inject(splashActivity: SplashActivity)
    fun inject(lectureDetailFragment: LectureDetailFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): MyComponent
    }
}