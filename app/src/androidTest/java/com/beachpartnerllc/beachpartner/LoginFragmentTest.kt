package com.beachpartnerllc.beachpartner

import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.beachpartnerllc.beachpartner.init.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

    @Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(
            MainActivity::class.java, true, false)

    @Before
    @Throws(Exception::class)
    fun setUp() {
    }
}
