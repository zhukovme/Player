package com.zhukovme.player

import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
object ExampleUnitTest : Spek({
    test("addition is correct") {
        assertThat(4).isEqualTo(2 + 2)
    }
})
