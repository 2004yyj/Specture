package kr.hs.dgsw.hackathon2021

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import kr.hs.dgsw.data.network.service.UserService
import kr.hs.dgsw.domain.entity.request.LoginRequest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("kr.hs.dgsw.hackathon2021", appContext.packageName)
    }
}