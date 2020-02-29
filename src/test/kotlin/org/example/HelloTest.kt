package org.example

import io.qameta.allure.Feature
import io.qameta.allure.Severity
import io.qameta.allure.SeverityLevel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class HelloTest {

    @Test
    @Feature("Some feature")
    @Severity(SeverityLevel.CRITICAL)
    fun testOutput() {
        Assertions.assertTrue(true)
    }

}
