package org.example.core.infra.reports

import com.aventstack.extentreports.ExtentReports
import com.aventstack.extentreports.ExtentTest

object ExtentTestManager {
    var extentTestMap: MutableMap<Int, ExtentTest> = HashMap()
    var extent: ExtentReports = ExtentManager.extentReports

    @get:Synchronized
    val test: ExtentTest?
        get() = extentTestMap[Thread.currentThread().id.toInt()]

    @Synchronized
    fun startTest(testName: String?, desc: String?): ExtentTest {
        val test = extent.createTest(testName, desc)
        extentTestMap[Thread.currentThread().id.toInt()] = test
        return test
    }
}
