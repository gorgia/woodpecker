package common.utils

import org.junit.Test
import webint.woodpecker.common.utils.log

class LoggingKtTest {

    @Test
    fun testLogging() {
        log(this::class).debug("Debug enabled")
        log(this::class).info("INFO enabled")
        log(this::class).error("ERROR enabled")
    }
}