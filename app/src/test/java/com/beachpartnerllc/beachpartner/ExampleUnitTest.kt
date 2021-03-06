package com.beachpartnerllc.beachpartner

import com.beachpartnerllc.beachpartner.user.auth.Auth
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun auth_isCorrect() {
        assertEquals(true, Auth("sam@test.com", "123123123").isValid())
    }
}
