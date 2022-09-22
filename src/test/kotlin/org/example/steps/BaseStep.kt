package org.example.steps

import org.example.core.infra.allure.Reporter

open class BaseStep {
    protected val reporter: Reporter = Reporter.instance
}
