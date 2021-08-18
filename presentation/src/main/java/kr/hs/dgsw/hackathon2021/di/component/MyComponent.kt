package kr.hs.dgsw.hackathon2021.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.di.module.*
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
    fun inject(progressingClassFragment: ProgressingClassFragment)
    fun inject(endedClassFragment: EndedClassFragment)
    fun inject(settingFragment: SettingFragment)
    fun inject(calenderFragment: CalenderFragment)
    fun inject(addLectureFragment: AddLectureFragment)
    fun inject(userInfoFragment: UserInfoFragment)
    fun inject(myLectureFragment: MyLectureFragment)
    fun inject(passwordChkFragment: PasswordChkFragment)
    fun inject(proposedLectureFragment: ProposedLectureFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): MyComponent
    }
}